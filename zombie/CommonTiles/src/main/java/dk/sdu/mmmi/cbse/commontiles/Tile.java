package dk.sdu.mmmi.cbse.commontiles;

import java.util.UUID;

public class Tile {
    private final int row;
    private final int col;
    private final float width;
    private final float height;
    private final float x;
    private final float y;
    private final UUID uuid;
    private boolean isWalkable;
    
    public Tile(int col, int row, float width, float height) {
        this.col = col;
        this.row = row;
        this.width = width;
        this.height = height;
        x = col * width;
        y = row * height;
        uuid = UUID.randomUUID();
        isWalkable = true;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public UUID getUUID() {
        return uuid;
    }
    
    public boolean getIsWalkable() {
        return isWalkable;
    }
    
    public void setIsWalkable(boolean isWalkable) {
        this.isWalkable = isWalkable;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
}
