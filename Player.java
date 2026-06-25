/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trongame;
import java.awt.Color;
/**
 *
 * @author erenkurmanaliev
 */
public class Player {
    private final String name;
    private final Color color;
    
    public Player(String name, Color color){
        this.name = name;
        this.color = color;
    }
    
    public String getName(){
        return name;
    }
    
    public Color getColor(){
        return color;
    }
}
