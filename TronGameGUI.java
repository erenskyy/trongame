/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trongame;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author erenkurmanaliev
 */
public class TronGameGUI extends JFrame {
    private final Database db = new Database("tron.db");
    private final GameBoard board = new GameBoard(80, 60);
    private final GameBoardGUI gui = new GameBoardGUI(board, db, 10);
    
    public TronGameGUI() {
        super("Tron Light Cycles (2 Players)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(gui, BorderLayout.CENTER);
        setJMenuBar(buildMenuBar());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        restartGame();
    }
    private JMenuBar buildMenuBar(){
        JMenuBar bar = new JMenuBar();
        JMenu game = new JMenu("Game");
        
        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener(e -> restartGame());
        JMenuItem highscores = new JMenuItem("Highscores (Top 10)");
        highscores.addActionListener(e -> gui.showHighScores());
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));
        
        game.add(restart);
        game.add(highscores);
        game.addSeparator();
        game.add(exit);
        
        bar.add(game);
        return bar;
    }
    
    private void restartGame(){
        gui.stopGame();
        Player p1 = askPlayer("Player 1 (WASD)", Color.CYAN);
        if(p1 == null) return;
        
        Player p2 = askPlayer("Player 2 (Arrow keys)", Color.ORANGE);
        if(p2 == null) return;
        
        gui.startGame(p1, p2);
        
    }
    
    private Player askPlayer(String label, Color defaultColor){
        String name = JOptionPane.showInputDialog(this, "Enter name for " + label + ":", label);
        if(name == null) return null;
        Color color = JColorChooser.showDialog(this, "Choose trace color for " + name, defaultColor);
        if(color == null) color = defaultColor;
        return new Player(name, color);
    }
}
