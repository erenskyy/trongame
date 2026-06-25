/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trongame;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erenkurmanaliev
 */
public class Database {
    private final String url;
    public static class ScoreRow{
        public final String name;
        public final int score;
        public ScoreRow(String name, int score) {this.name = name; this.score = score; }
    }
    
    public Database(){
        this("tron.db");
    }
    
    public Database(String sqliteFilePath){
        this.url = "jdbc:sqlite:" + sqliteFilePath;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "sqlite not found", e
            );
        }
        init();
    }
    /**
     * creates the database table
     */
    private void init(){
        try(Connection c = DriverManager.getConnection(url);
                Statement st = c.createStatement()) {
            st.executeUpdate(
                "CREATE TABLE IF NOT EXISTS scores (" +
                    "name TEXT PRIMARY KEY," +
                    "score INTEGER NOT NULL" +
                    ")");
        } catch(SQLException e){
            throw new RuntimeException("DB update failed: " + e.getMessage(), e);
        } 
    }
    /**
     * Increments the win number of the given player by 1
     * @param name winners name
     */
    public void incrementWinner(String name){
        if(name == null || name.trim().isEmpty()) return;
        String n = name.trim();
        
        String sql =
            "INSERT INTO scores(name, score) VALUES(?, 1) " +
            "ON CONFLICT(name) DO UPDATE SET score = score + 1";

        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, n);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB update failed: " + e.getMessage(), e);
        }
    }
    /**
     * gives top 10 players ordered by score descending then name ascending
     * @return list of 10 rows
     */
    public List<ScoreRow> top10(){
        List<ScoreRow> rows = new ArrayList<>();
        try(Connection c = DriverManager.getConnection(url);
            PreparedStatement ps = c.prepareStatement("SELECT name, score FROM scores ORDER BY score DESC, name ASC LIMIT 10");
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rows.add(new ScoreRow(rs.getString("name"), rs.getInt("score")));
            }
        } catch(SQLException e)  {
            throw new RuntimeException("DB read failed: " + e.getMessage(), e);
        }
        return rows;
    }
}
