/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trongame;

/**
 *
 * @author erenkurmanaliev
 */
public class Motor {
    public enum Direction{ UP, DOWN, LEFT, RIGHT}
    private int x;
    private int y;
    private Direction dir;
    
    public Motor(int startX, int startY, Direction startDir){
        this.x = startX;
        this.y = startY;
        this.dir = startDir;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public Direction getDir(){
        return dir;
    }
    
    public void setDir(Direction newDir) {
        if (newDir == null) return;
        if ((dir == Direction.UP && newDir == Direction.DOWN) ||
            (dir == Direction.DOWN && newDir == Direction.UP) ||
            (dir == Direction.LEFT && newDir == Direction.RIGHT) ||
            (dir == Direction.RIGHT && newDir == Direction.LEFT)) {
            return;
        }
        dir = newDir;
    }
    /**
     * moves a motor forward by 1 cell towards its direction
     */
    public void stepForward(){
        switch(dir) {
            case UP -> y--;
            case DOWN -> y++;
            case LEFT -> x--;
            case RIGHT -> x++;
        }
    }
}
