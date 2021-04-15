package dk.sdu.mmmi.cbse.common.data.entitytypeparts;

import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

/**
 * Used by entities with visuals
 */

public class VisualPart implements EntityPart {

    private String spriteName;
    private float width;
    private float height;
    //Z position defines on what layer the sprite is drawn. 3 is default. Higher layers get drawn above.
    private int zPosition;
    
    public VisualPart(String spriteName, float width, float height){
        this.spriteName = spriteName;
        this.width = width;
        this.height = height;
        this.zPosition = 3;
    }
    
    public VisualPart(String spriteName, float width, float height, int zPosition){
        this.spriteName = spriteName;
        this.width = width;
        this.height = height;
        this.zPosition = zPosition;
    }

    public int getZPostion(){
        return zPosition;
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
