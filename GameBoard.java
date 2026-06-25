/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trongame;

/**
 *
 * @author erenkurmanaliev
 */
public class GameBoard {
    private final int widthCells;
    private final int heightCells;
    private Player p1;
    private Player p2;
    private Motor m1;
    private Motor m2;
    private final Trace t1 = new Trace();
    private final Trace t2 = new Trace();
    private boolean running = false;
    private String winnerName = null;
    
    public GameBoard(int widthCells, int heightCells){
        this.widthCells = widthCells;
        this.heightCells = heightCells;
    }
    /**
     * starts the new round with given players
     * Resets traces, sets motors, and changes game to running
     * @param p1 player 1 
     * @param p2 player 2
     */
    public void start(Player p1, Player p2){
        this.p1 = p1;
        this.p2 = p2;
        t1.clear();
        t2.clear();
        m1 = new Motor(widthCells / 4, heightCells / 2, Motor.Direction.RIGHT);
        m2 = new Motor((widthCells * 3) / 4, heightCells / 2, Motor.Direction.LEFT);
        t1.add(m1.getX(), m1.getY());
        t2.add(m2.getX(), m2.getY());
        running = true;
        winnerName = null;
    }
    
    public boolean isRunning(){return running;}
    public int getWidthCells(){return widthCells;}
    public int getHeightCells(){return heightCells;}
    
    public Player getP1(){return p1;}
    public Player getP2(){return p2;}
    public Motor getM1(){return m1;}
    public Motor getM2(){return m2;}
    public Trace getT1(){return t1;}
    public Trace getT2(){return t2;}
    public String getWinnerName(){return winnerName;}
    
    public void setP1Dir(Motor.Direction d){
        if(m1 != null){
            m1.setDir(d);
        }
    }
    public void setP2Dir(Motor.Direction d){
        if(m2 != null){
            m2.setDir(d);
        }
    }
    /**
     * checks if the motor is in the bounds of the board
     * @param x x coordinate
     * @param y y coordinate
     * @return true or false
     */
    private boolean outOfBounds(int x, int y){
        return x < 0  || y < 0 || x >= widthCells || y >= heightCells;
    }
    /**
     * Advances the game by 1 tick
     * moves both motors forward, check for bounds and collisions ends the game if someone loses
     */
    public void tick(){
        if(!running) return;
        
        m1.stepForward();
        m2.stepForward();
        boolean p1Lost = outOfBounds(m1.getX(), m1.getY()) || t2.contains(m1.getX(), m1.getY());
        boolean p2Lost = outOfBounds(m2.getX(), m2.getY()) || t1.contains(m2.getX(), m2.getY());
        
        if(p1Lost || p2Lost){
            running = false;
            if(p1Lost && !p2Lost){
                winnerName = p2.getName();
            }else if(p2Lost && !p1Lost){
                winnerName = p1.getName();
            }
            return;
        }
        t1.add(m1.getX(), m1.getY());
        t2.add(m2.getX(), m2.getY());
    }
}
