package dk.sdu.mmmi.cbse.core.main;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

public class GameLookup {
    
    private GameData gameData;
    private World world;
    
    private final Lookup lookup;
    private List<IGamePluginService> gamePlugins;
    private Lookup.Result<IGamePluginService> result;
       
    public GameLookup(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
        lookup = Lookup.getDefault();
        gamePlugins = new CopyOnWriteArrayList<>();
        
        // Get game plugin services via lookup
        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        // Start all game plugins found by lookup
        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
    }
    
    public Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    public Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }

    public final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {
            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us: updated) {
                // Install new modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Uninstall modules no longer in lookup result list
            for (IGamePluginService gs: gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }
    };
}
