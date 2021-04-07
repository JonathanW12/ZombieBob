package dk.sdu.mmmi.cbse.common.data.entitytypeparts;

import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

public class VisualPart implements EntityPart {
    
    private String spriteName;
    private float width;
    private float height;
    
    public VisualPart(String spriteName, float width, float height){
        this.spriteName = spriteName;
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
    
    public String getSpriteName() {
        return spriteName;
    }
    
    public void setWidth(float width) {
        this.width = width;
    }
    
    public void setHeight(float height) {
        this.height = height;
    }
    
    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }
}
