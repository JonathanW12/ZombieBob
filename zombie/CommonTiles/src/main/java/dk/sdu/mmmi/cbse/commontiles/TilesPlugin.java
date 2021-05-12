package dk.sdu.mmmi.cbse.commontiles;


import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)
public class TilesPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        Tiles.getInstance(gameData).initializeTiles(gameData);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Do nothing
    }
    
}
