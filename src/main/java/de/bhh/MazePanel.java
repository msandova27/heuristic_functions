package de.bhh;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class MazePanel extends JPanel {

    private static final int ROWS = 20;
    private static final int COLUMNS = 20;
    private static final int TILE_SIZE = 50;

    private static Node start = new Node(0, 2);
    private static Node goal = new Node(10, ROWS - 1);
    private static Node current;

    private static boolean pathFound = false;
    private static LinkedList<Node> path = new LinkedList<Node>();

    private static PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
    private static HashSet<Node> closedSet = new HashSet<>();

    private static Node[][] maze = initMaze();


    private static Node[][] initMaze() {
        Node[][] newMaze = new Node[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                newMaze[i][j] = new Node(j, i);
            }
        }
        return newMaze;
    }

    private static int calculateManhattanDistance(Node current) {
        return Math.abs(current.getX() - goal.getX()) + Math.abs(current.getY() - goal.getY());
    }

    private static void initPathFinding() {
        start.setG(0);
        start.setF(start.getG() + calculateManhattanDistance(start));
        openSet.add(start);
    }

    private void nextStep() {

        if (openSet.isEmpty() || pathFound) {
            return;
        }

        current = openSet.poll();
        closedSet.add(current);

        if (current.equals(goal)) {
            pathFound = true;
            calculatePath();
            repaint();
            return;
        }

        for (Node neighbor : current.getNeighbors(maze)) {
            if (closedSet.contains(neighbor)) {
                continue;
            }

            int tentativeG = current.getG() + 1;
            if (tentativeG < neighbor.getG()) {
                neighbor.setPrev(current);
                neighbor.setG(tentativeG);
                neighbor.setF(neighbor.getG() + calculateManhattanDistance(neighbor));

                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                }
            }
        }
        repaint();
    }

    private static void calculatePath() {
        Node cur = current.getPrev();
        while (cur.getPrev() != null) {
            path.add(cur);
            cur = cur.getPrev();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Node node = maze[y][x];

                if (node == null) {
                    g.setColor(Color.WHITE);
                } else if (node.equals(start) || node.equals(goal)) {
                    g.setColor(Color.GREEN);
                } else if (node.equals(current)) {
                    g.setColor(Color.BLUE);
                } else if (path.contains(node)) {
                    g.setColor(Color.RED);
                } else if (closedSet.contains(node)) {
                    g.setColor(Color.LIGHT_GRAY);
                } else if (openSet.contains(node)) {
                    g.setColor(Color.ORANGE);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.GRAY);
                g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze");
        MazePanel panel = new MazePanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1050, 1050);
        frame.add(panel);
        frame.setVisible(true);

        initPathFinding();

        new javax.swing.Timer(100, e -> panel.nextStep()).start();
    }
}
