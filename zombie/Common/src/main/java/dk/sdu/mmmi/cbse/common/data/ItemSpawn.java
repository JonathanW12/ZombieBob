package dk.sdu.mmmi.cbse.common.data;

import java.util.UUID;

public class ItemSpawn {
    private UUID currentItem;
    private Position position;
    
    public ItemSpawn() {
        currentItem = null;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public int getPosX() {
        return position.getX();
    }
    
    public int getPosY() {
        return position.getY();
    }
    
    public UUID getCurrentItem() {
        return currentItem;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public void setCurrentItem(UUID currentItem) {
        this.currentItem = currentItem;
    }
    
    public void removeCurrentItem() {
        this.currentItem = null;
    }
    
}
