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
        
        tiles = new Tile[verticalTiles + 1][horizontalTiles + 1];
        
        for (int i = 0; i <= verticalTiles; i++) {
            for (int j = 0; j <= horizontalTiles; j++) {
                tiles[i][j] = new Tile(
                    j * tileWidth,
                    i * tileHeight,
                    tileWidth,
                    tileHeight
                );
            }
        }
    }
}
