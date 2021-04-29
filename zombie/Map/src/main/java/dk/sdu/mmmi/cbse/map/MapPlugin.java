package dk.sdu.mmmi.cbse.map;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.StructurePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commontiles.Tile;
import dk.sdu.mmmi.cbse.commontiles.Tiles;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class MapPlugin implements IGamePluginService{
    private float gameHeight;
    private float gameWidth;
    private final float wallWidth = 60;
    private final float wallRadians = 3.1415f / 2;
    private World world;
    private Tile[][] tiles;
    
    @Override
    public void start(GameData gameData, World world) {
        gameHeight = gameData.getDisplayHeight();
        gameWidth = gameData.getDisplayWidth();
        this.world = world;
        tiles = Tiles.getInstance(gameData).getTiles();
        int verticalTiles = tiles.length;
        int horizontalTiles = tiles[0].length;
        
        createBackground(gameData,world);
        
        generateMapFromFile("map1");
    }
    
    private void generateMapFromFile(String mapName) {
        FileHandle filehandle = new FileHandle("../../maps/" + mapName + ".txt");
        
        if (filehandle.exists()) {
            String text = filehandle.readString();
            String[] lines = text.split("\\r?\\n");
            System.out.println(lines.length );
            for (int i = 0; i < lines.length; i++) {
                String[] cells = lines[i].split("");
                
                for (int j = 0; j < cells.length; j++) {
                    if (cells[j].equals("1")) {
                        placeWall(tiles[i][j]);
                    }
                }
            }
        }
    }
    
    private void placeWall(Tile tile) {
        Entity wall = new Entity();
            
        world.addtoEntityPartMap(new PositionPart(
            tile.getX() + tile.getWidth() / 2,
            tile.getY() + tile.getHeight() / 2,
            wallRadians
        ), wall);
        world.addtoEntityPartMap(new VisualPart("wall_sprite", tile.getWidth(), tile.getHeight(), 1), wall);
        world.addtoEntityPartMap(new ColliderPart(tile.getWidth(), tile.getHeight()), wall);
        world.addtoEntityPartMap(new StructurePart(), wall);
        
        tile.setIsWalkable(false);
    }
    
    private void createHoriziontalWallSection(int startHorizontalIndex, int verticalIndex, float width) {
        for (int i = startHorizontalIndex; i < startHorizontalIndex + width; i++) {
            placeWall(tiles[verticalIndex][i]);
        }
    }
    
    private void createVerticalWallSection(int startVerticalIndex, int horizontalIndex, float height) {
        for (int i = startVerticalIndex; i < startVerticalIndex + height; i++) {
            placeWall(tiles[i][horizontalIndex]);
        }
    }
    
    private void createBackground(GameData gameData, World world){
        float x = gameData.getDisplayWidth()/2;
        float y = gameData.getDisplayHeight()/2;
        
        Entity background = new Entity();
        
        world.addtoEntityPartMap(new PositionPart(x,y,wallRadians), background);
        world.addtoEntityPartMap(new VisualPart("background_sprite",gameData.getDisplayWidth()+gameData.getDisplayWidth()/2,gameData.getDisplayHeight()+gameData.getDisplayWidth()/2,0),background);
    }
   
    @Override
    public void stop(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
