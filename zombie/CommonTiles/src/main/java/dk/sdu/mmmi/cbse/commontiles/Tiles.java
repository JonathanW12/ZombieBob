package dk.sdu.mmmi.cbse.commontiles;

import dk.sdu.mmmi.cbse.common.data.GameData;

public class Tiles {
    
    private Tile[][] tiles;
    private static Tiles instance;
    private float tileWidth;
    private float tileHeight;
    
    private Tiles(GameData gameData) {
        tileWidth = 60;
        tileHeight = 60;
        initializeTiles(gameData);
    }
    
    public static Tiles getInstance(GameData gameData) {
        if (instance == null) {
            instance = new Tiles(gameData);
        }
        
        return instance;
    }
    
    public Tile[][] getTiles() {
        return tiles;
    }
    
    private void initializeTiles(GameData gameData) {
        int verticalTiles = (int) (gameData.getDisplayHeight() / tileHeight);
        int horizontalTiles = (int) (gameData.getDisplayWidth() / tileWidth);
        
        tiles = new Tile[verticalTiles][horizontalTiles];
        
        for (int i = 0; i < verticalTiles; i++) {
            for (int j = 0; j < horizontalTiles; j++) {
                tiles[i][j] = new Tile(
                    j * tileWidth,
                    i * tileHeight,
                    tileWidth,
                    tileHeight
                );
            }
        }
    }
    
    public float getTileWidth() {
        return tileWidth;
    }
    
    public float getTileHeight() {
        return tileHeight;
    }
    
    public Tile getTileByPosition(float x, float y) {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j ++) {
                Tile tile = tiles[i][j];
                if (
                    ((x >= tile.getX()) && (x < tile.getX() + tile.getWidth())) &&
                    ((y >= tile.getY()) && (y < tile.getY() + tile.getHeight()))
                ) {
                    return tile;
                }
            }
        }
        
        return null;
    }
    
}
