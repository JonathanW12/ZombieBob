package dk.sdu.mmmi.cbse.spiderboss;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.*;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import java.util.Map;

@ServiceProvider(service = IGamePluginService.class)
public class SpiderPlugin implements IGamePluginService{

    @Override
    public void start(GameData gameData, World world) {
        // Do nothing
    }
    @Override
    public void stop(GameData gameData, World world) {
        // Removing spiders
        if (world.getMapByPart(SpiderPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(SpiderPart.class.getSimpleName()).entrySet()){
                world.removeEntityParts(entry.getKey());
            }
            // Clears processing array for webshooters (Spider guns)
            SpiderWebProcessor.clearProcessingList();
        }
    }
}
