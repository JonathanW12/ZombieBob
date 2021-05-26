package dk.sdu.mmmi.cbse.commontiles;

import dk.sdu.mmmi.cbse.common.data.GameData;
import java.util.HashMap;

public class Tiles {

    private static Tile[][] tiles;
    private static Tiles instance;
    private static float tileWidth;
    private static float tileHeight;
    private static HashMap<String, Tile> tileByRowAndCol;

    private Tiles(GameData gameData) {
        tileWidth = 60;
        tileHeight = 60;
        tileByRowAndCol = new HashMap<>();
        initializeTiles(gameData);
    }

    public static Tiles getInstance(GameData gameData) {
        if (instance == null) {
            instance = new Tiles(gameData);
        }

        return instance;
    }

    public static Tile[][] getTiles() {
        return tiles;
    }

    protected static void initializeTiles(GameData gameData) {
        int verticalTiles = (int) (gameData.getDisplayHeight() / tileHeight);
        int horizontalTiles = (int) (gameData.getDisplayWidth() / tileWidth);

        tiles = new Tile[verticalTiles][horizontalTiles];

        for (int i = 0; i < verticalTiles; i++) {
            for (int j = 0; j < horizontalTiles; j++) {
                String pos = "row" + i + "-col" + j;
                Tile tile = new Tile(
                        j,
                        i,
                        tileWidth,
                        tileHeight
                );
                tiles[i][j] = tile;
                tileByRowAndCol.put(pos, tile);
            }
        }
    }

    public static float getTileWidth() {
        return tileWidth;
    }

    public static float getTileHeight() {
        return tileHeight;
    }

    public static Tile getTileByRowAndCol(int row, int col) {
        if (tileByRowAndCol.isEmpty()) {
            addTilesToMap();
        } else {
            String pos = "row" + row + "-col" + col;
            return tileByRowAndCol.get(pos);
        }

        return null;
    }

    private static void addTilesToMap() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile tile = tiles[i][j];
                String pos = "row" + i + "-col" + j;
                tileByRowAndCol.put(pos, tile);
            }
        }
    }

    public int getTileRow(Tile tile) {
        return tile.getRow();
    }

    public int getTileCol(Tile tile) {
        return tile.getCol();
    }

}
