package dk.sdu.mmmi.cbse.handgun;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)
public class HandgunPlugin implements IGamePluginService {
    
    @Override
    public void start(GameData gameData, World world) {
        // Do nothing
    }
    
    @Override
    public void stop(GameData gameData, World world) {
        HandgunProcessor.clearProcessingList();
        SpawnProcessor.resetSpawnProcessor();
    }
}