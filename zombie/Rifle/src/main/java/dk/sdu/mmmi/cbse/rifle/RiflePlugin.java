package dk.sdu.mmmi.cbse.rifle;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)
public class RiflePlugin implements IGamePluginService {
    
    @Override
    public void start(GameData gameData, World world) {
        // Do nothing
    }
    
    @Override
    public void stop(GameData gameData, World world) {
        RifleProcessor.clearProcessingList();
        SpawnProcessor.resetSpawnProcessor();
    }
}