/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snakegame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    private static final int SCREEN_WIDTH = 600;
    private static final int SCREEN_HEIGHT = 600;
    private static final int UNIT_SIZE = 25;
    private static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    private static final int DELAY = 75;

    private final ArrayList<Snake> snake = new ArrayList<>();
    private Apple apple;
    private char direction = 'R';
    private boolean running = false;
    private Timer timer;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame() {
        snake.add(new Snake(0, 0, UNIT_SIZE));
        apple = new Apple(SCREEN_WIDTH, SCREEN_HEIGHT, UNIT_SIZE);
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            apple.draw(g);
            for (Snake s : snake) {
                s.draw(g);
            }
        } else {
            gameOver(g);
        }
    }

    public void move() {
        for (int i = snake.size() - 1; i > 0; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }
        Snake head = snake.get(0);
        switch (direction) {
            case 'U' -> head.setY(head.getY() - UNIT_SIZE);
            case 'D' -> head.setY(head.getY() + UNIT_SIZE);
            case 'L' -> head.setX(head.getX() - UNIT_SIZE);
            case 'R' -> head.setX(head.getX() + UNIT_SIZE);
        }
    }

    public void checkApple() {
        if ((snake.get(0).getX() == apple.getX()) && (snake.get(0).getY() == apple.getY())) {
            snake.add(new Snake(-1, -1, UNIT_SIZE)); // Temporary position, will be updated in move()
            apple.newApple();
        }
    }

    public void checkCollisions() {
        Snake head = snake.get(0);
        // Check if head collides with body
        for (int i = snake.size() - 1; i > 0; i--) {
            if ((head.getX() == snake.get(i).getX()) && (head.getY() == snake.get(i).getY())) {
                running = false;
            }
        }
        // Check if head collides with walls
        if (head.getX() < 0 || head.getX() >= SCREEN_WIDTH || head.getY() < 0 || head.getY() >= SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
