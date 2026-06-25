/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trongame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.awt.Color;


/**
 *
 * @author erenkurmanaliev
 */
public class GameBoardGUI extends JPanel{
    private final GameBoard board;
    private final Database db;
    private final Timer timer;
    private final int cellSize;
    
    public GameBoardGUI(GameBoard board, Database db, int cellSize){
        this.board = board;
        this.db = db;
        this.cellSize = cellSize;
        setFocusable(true);
        setBackground(Color.BLACK);
        timer = new Timer(80, e -> onTick());
        setupKeys();
    }
    /**
    * Starts a new game with the given players and begins the tick timer.
    *
    * @param p1 player 1
    * @param p2 player 2
    */
    public void startGame(Player p1, Player p2){
        board.start(p1, p2);
        timer.start();
        requestFocusInWindow();
        repaint();
    }
    /**
     * Stops the game and the timer
     */
    public void stopGame(){
        timer.stop();
    }
    /**
     * checks each tick for a winner, if there is one it pops up scoreboard and lets the user choose
     * either to restart or close the game
     */
    private void onTick(){
        board.tick();
        repaint();
        
        if(!board.isRunning()){
            timer.stop();
            String winner = board.getWinnerName();
            if(winner != null) {db.incrementWinner(winner);}
                showHighScores();
                int choice = JOptionPane.showOptionDialog(this,
                        "Play Again?",
                        "Restart ",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new Object[]{"Restart", "Exit"},
                        "Restart");
            if( choice == JOptionPane.YES_OPTION){
                startGame(board.getP1(), board.getP2());
            }else{
                Window w = SwingUtilities.getWindowAncestor(this);
                if( w != null ) w.dispose();
            }
        }
    }
    /**
     * Shows the table with the top 10 players of the game
     */
    public void showHighScores(){
        List<Database.ScoreRow> top = db.top10();
        String[] cols = {"Rank", "Player", "Score"};
        Object[][] data = new Object[top.size()][3];
        for(int i = 0; i < top.size(); i++){
            data[i][0] = i + 1;
            data[i][1] = top.get(i).name;
            data[i][2] = top.get(i).score;
        }
        JTable table = new JTable(data, cols);
        table.setEnabled(false);
        JOptionPane.showMessageDialog(table, new JScrollPane(table),
                "Highscores (Top 10)", JOptionPane.PLAIN_MESSAGE);
    }
    /**
     * sets the controlling keys
     */
    private void setupKeys(){
        InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();
        
        im.put(KeyStroke.getKeyStroke("W"), "p1_up");
        im.put(KeyStroke.getKeyStroke("S"), "p1_down");
        im.put(KeyStroke.getKeyStroke("A"), "p1_left");
        im.put(KeyStroke.getKeyStroke("D"), "p1_right");
        
        im.put(KeyStroke.getKeyStroke("UP"), "p2_up");
        im.put(KeyStroke.getKeyStroke("DOWN"), "p2_down");
        im.put(KeyStroke.getKeyStroke("LEFT"), "p2_left");
        im.put(KeyStroke.getKeyStroke("RIGHT"), "p2_right");
        
        am.put("p1_up", new AbstractAction() { public void actionPerformed(ActionEvent e) { board.setP1Dir(Motor.Direction.UP); }});
        am.put("p1_down", new AbstractAction() { public void actionPerformed(ActionEvent e) { board.setP1Dir(Motor.Direction.DOWN); }});
        am.put("p1_left", new AbstractAction() { public void actionPerformed(ActionEvent e) { board.setP1Dir(Motor.Direction.LEFT); }});
        am.put("p1_right", new AbstractAction() { public void actionPerformed(ActionEvent e) { board.setP1Dir(Motor.Direction.RIGHT); }});

        am.put("p2_up", new AbstractAction() { public void actionPerformed(ActionEvent e) { board.setP2Dir(Motor.Direction.UP); }});
        am.put("p2_down", new AbstractAction() { public void actionPerformed(ActionEvent e) { board.setP2Dir(Motor.Direction.DOWN); }});
        am.put("p2_left", new AbstractAction() { public void actionPerformed(ActionEvent e) { board.setP2Dir(Motor.Direction.LEFT); }});
        am.put("p2_right", new AbstractAction() { public void actionPerformed(ActionEvent e) { board.setP2Dir(Motor.Direction.RIGHT); }});
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(board.getWidthCells() * cellSize, board.getHeightCells() * cellSize);
    }
    
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(board.getP1() == null || board.getP2() == null) return;
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setColor(board.getP1().getColor());
        for(var p : board.getT1().getCells()){
            g2.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
        }
        g2.setColor(board.getP2().getColor());
        for(var p : board.getT2().getCells()){
            g2.fillRect(p.x * cellSize, p.y * cellSize, cellSize, cellSize);
        }
        
        Motor m1 = board.getM1();
        Motor m2 = board.getM2();
        if(m1 != null){
            g2.setColor(board.getP1().getColor().brighter());
            g2.fillRect(m1.getX() * cellSize, m1.getY() * cellSize, cellSize, cellSize);
        }
        if(m2 != null){
            g2.setColor(board.getP2().getColor().brighter());
            g2.fillRect(m2.getX() * cellSize, m2.getY() * cellSize, cellSize, cellSize);
        }
        g2.dispose();
    }
}
