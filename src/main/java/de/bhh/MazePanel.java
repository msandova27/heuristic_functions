package de.bhh;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MazePanel extends JPanel {
    private final int[][] maze = {
            {1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1},
            {1, 0, 1, 0, 1},
            {1, 0, 1, 0, 1},
            {1, 1, 1, 1, 1}
    };

    private final int[][] LargeMaze = new int[20][20];

    @Override
    protected void paintComponent(Graphics g) {
        Arrays.stream(LargeMaze).forEach(a -> Arrays.fill(a, 1));
        int[][] currentMaze = LargeMaze;
        super.paintComponent(g);
        int cellSize = 50;
        for (int y = 0; y < currentMaze.length; y++) {
            for (int x = 0; x < currentMaze[0].length; x++) {
                if (currentMaze[y][x] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x * cellSize, y * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 550);
        frame.add(new MazePanel());
        frame.setVisible(true);
    }
}
