/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trongame;
import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
/**
 *
 * @author erenkurmanaliev
 */
public class Trace {
    private final Set<Point> cells = new HashSet<>();
    /**
     * adds cell position to the trace
     * @param x coordinate
     * @param y coordinate
     */
    public void add(int x, int y){
        cells.add(new Point(x, y));
    }
    /**
     * checks if trace contains the given cell
     * @param x coordinate
     * @param y coordinate
     * @return true if the cell is in the trace
     */
    public boolean contains (int x, int y){
        return cells.contains(new Point(x, y));
    }
    /**
     * clears all trace cells
     */
    public void clear(){
        cells.clear();
    }
    
    public Set<Point> getCells(){
        return cells;
    }
}
