package dk.sdu.mmmi.cbse.levelsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)
public class LevelPlugin implements IGamePluginService {
    
    public void start(GameData gameData, World world) {
        // Do nothing
    }
    
    public void stop(GameData gameData, World world) {
        LevelSystem.resetLevel(gameData);
    }
}
