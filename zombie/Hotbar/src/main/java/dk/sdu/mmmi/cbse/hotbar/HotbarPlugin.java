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
    private float hotbarHeight = 59;
    private float hotbarWidth = 430;

    @Override
    public void start(GameData gameData, World world) {
        createHotbar(gameData,world);
        HotbarContolSystem.resetInventory();
    }
    
    private void createHotbar(GameData gameData, World world){

        float x = gameData.getDisplayWidth()/2;
        float y = gameData.getDisplayHeight()-hotbarHeight/2;
        float radians = 3.1415f / 2;
        
        Entity hotbar = new Entity();
        
        world.addtoEntityPartMap(new PositionPart(x,y,radians), hotbar);
        world.addtoEntityPartMap(new HotbarPart(),hotbar);
        world.addtoEntityPartMap(new VisualPart("hotbar_sprite",hotbarWidth,hotbarHeight,3),hotbar);
        VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(hotbar.getUUID());
        visualPart.setResizable(false);
    }

    @Override
    public void stop(GameData gameData, World world) {
        HotbarContolSystem.resetInventory();
    }
    
}
