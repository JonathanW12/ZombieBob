package dk.sdu.mmmi.cbse.astar;

import dk.sdu.mmmi.cbse.commontiles.Tiles;
import dk.sdu.mmmi.cbse.commontiles.Tile;
import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>{
    private int tileRow;
    private int tileCol;
    private float f;
    private float g;
    private float h;
    private float x;
    private float y;
    private List<Node> neighbors;
    private Node parent;
    
    public Node(int tileRow, int tileCol, float x, float y) {
        this.tileRow = tileRow;
        this.tileCol = tileCol;
        this.x = x;
        this.y = y;
        f = 0;
        g = 0;
        h = 0;
        neighbors = new ArrayList<>();
    }
    
    public int compareTo(Node otherNode) {
        return (int) (getF() - otherNode.getF());
    }
    
    public void addNeighbor(Node neighbor) {
        neighbors.add(neighbor);
    }
    
    public int getTileRow() {
        return tileRow;
    }
    
    public int getTileCol() {
        return tileCol;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public List<Node> getNeighbors() {
        return neighbors;
    }
    
    public void setG(float g) {
        this.g = g;
    } 
    
    public void setH(float h) {
        this.h = h;
    }
    
    public void setF(float f) {
        this.f = f;
    }
    
    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    public float getG() {
        return g;
    }
    
    public float getH() {
        return h;
    }
    
    public float getF() {
        return f;
    }
    
    public Node getParent() {
        return parent;
    }
    
    public boolean getIsObstacle() {
        Tile tile = Tiles.getTileByRowAndCol(getTileRow(), getTileCol());
        
        return !tile.getIsWalkable();
    }
    
}
