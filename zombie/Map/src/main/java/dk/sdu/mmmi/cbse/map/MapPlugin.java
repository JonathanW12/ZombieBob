package dk.sdu.mmmi.cbse.map;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.StructurePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class MapPlugin implements IGamePluginService{
    private final float gameHeight = 800;
    private final float gameWidth = 1100;
    private final float wallWidth = 50;
    private final float wallRadians = 3.1415f / 2;
    
    @Override
    public void start(GameData gameData, World world) {
        createBackground(gameData,world);
        //Creating Boundary walls. Note that the "top" of the map is at 650px due to the hotbar
        //The walls start 30 px in to make the width of the game match the height
        createWallRight(world,0,650,1100);
        createWallRight(world,0,0,1100);
        createWallUp(world,0,0,650);
        createWallUp(world,1100,0,650);
        
        //Creating a few obstacles
        createWallRight(world,200,200,300);
        createWallUp(world,500,200,200);
        
        createWallRight(world,800,550,150);
        createWallUp(world,950,200,350);
        
        createWallRight(world,100,450,150);
    }
    
    private void createBackground(GameData gameData, World world){
        float x = gameData.getDisplayWidth()/2;
        float y = gameData.getDisplayHeight()/2;
        
        Entity background = new Entity();
        
        world.addtoEntityPartMap(new PositionPart(x,y,wallRadians), background);
        world.addtoEntityPartMap(new VisualPart("background_sprite",gameWidth,gameHeight,0),background);
    }
    
    private void createWallRight(World world, float x, float y, float length){
        //Drawing wall blocks as many times as nescesarry
        int brickLength = (int) (length/wallWidth);
        for(int i = 0; i <= brickLength;i++){
            float xPos = x + wallWidth*i;
            
            Entity wall = new Entity();
            
            world.addtoEntityPartMap(new PositionPart(xPos,y,wallRadians), wall);
            world.addtoEntityPartMap(new VisualPart("wall_sprite",wallWidth,wallWidth,1),wall);
            world.addtoEntityPartMap(new ColliderPart(wallWidth/2),wall);
            world.addtoEntityPartMap(new StructurePart(), wall);
        }
    }
    private void createWallUp(World world,float x, float y, float length){
        int brickLength = (int) (length/wallWidth);
        for(int i = 0; i <= brickLength;i++){
            float yPos = y + wallWidth*i;
            
            Entity wall = new Entity();
            
            world.addtoEntityPartMap(new PositionPart(x,yPos,wallRadians), wall);
            world.addtoEntityPartMap(new VisualPart("wall_sprite",wallWidth,wallWidth,1),wall);
            world.addtoEntityPartMap(new ColliderPart(wallWidth/2),wall);
            world.addtoEntityPartMap(new StructurePart(), wall);
        }
    }
    @Override
    public void stop(GameData gameData, World world) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
