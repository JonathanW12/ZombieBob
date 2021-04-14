package dk.sdu.mmmi.cbse.hotbar;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.*;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.HashMap;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class HotbarPlugin implements IGamePluginService {
    public float hotbarHeight = 150;
    protected float hotbarWidth = 1100;

    @Override
    public void start(GameData gameData, World world) {
        createHotbar(gameData,world);
        //gameData.setDisplayHeight(gameData.getDisplayHeight()-(int) hotbarHeight);
    }
    
    private void createHotbar(GameData gameData, World world){

        float x = gameData.getDisplayWidth()/2;
        float y = gameData.getDisplayHeight()-hotbarHeight/2;
        float radians = 3.1415f / 2;
        
        Entity hotbar = new Entity();
        
        world.addtoEntityPartMap(new PositionPart(x,y,radians), hotbar);
        world.addtoEntityPartMap(new VisualPart("hotbar_sprite",1100,150),hotbar);
    }

    @Override
    public void stop(GameData gameData, World world) {
    }
    
}
