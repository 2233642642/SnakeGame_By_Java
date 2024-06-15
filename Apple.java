/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snakegame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Apple {
    private int x;
    private int y;
    private final int unitSize;
    private final int screenWidth;
    private final int screenHeight;
    private final Random random;

    public Apple(int screenWidth, int screenHeight, int unitSize) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.unitSize = unitSize;
        this.random = new Random();
        newApple();
    }

    public void newApple() {
        x = random.nextInt((int) (screenWidth / unitSize)) * unitSize;
        y = random.nextInt((int) (screenHeight / unitSize)) * unitSize;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, unitSize, unitSize);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
