package dk.sdu.mmmi.cbse.commontiles;

import java.util.UUID;

public class Tile {
    private float width;
    private float height;
    private float x;
    private float y;
    private UUID uuid;
    private boolean isWalkable;
    
    public Tile(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
    
    public void setWalkable(boolean isWalkable) {
        this.isWalkable = isWalkable;
    }
    
}
