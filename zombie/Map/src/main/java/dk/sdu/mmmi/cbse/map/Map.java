package dk.sdu.mmmi.cbse.map;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Map {
    private String mapName;
    private Sprite thumbnail;
    
    public Map(String mapName, Sprite thumbnail) {
        this.mapName = mapName;
        this.thumbnail = thumbnail;
    }
    
    public String getMapName() {
        return mapName;
    }
    
    public Sprite getThumbnail() {
        return thumbnail;
    }

}
