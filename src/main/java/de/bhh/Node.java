package de.bhh;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private Node prev = null;
    private int x, y, f;
    private int g = Integer.MAX_VALUE;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public List<Node> getNeighbors(Node[][] maze) {
        ArrayList<Node> neighbors = new ArrayList<Node>();
        int[][] directions = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};
        for (int[] dir : directions) {
            int neighborX = x + dir[0];
            int neighborY = y + dir[1];

            boolean isPossibleX = neighborX > -1 && neighborX < maze.length;
            boolean isPossibleY = neighborY > -1 && neighborY < maze[0].length;
            if (isPossibleY && isPossibleX) {
                neighbors.add(maze[neighborY][neighborX]);
            }
        }
        return neighbors;
    }

    public boolean equals(Node n) {
        if (n == null) {
            return false;
        }
        return this.x == n.getX() && this.y == n.getY();
    }
}
