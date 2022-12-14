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
import dk.sdu.mmmi.cbse.common.services.IMapService;

public class GameLookup {

    private GameData gameData;
    private World world;
    private static GameLookup instance;
    private final Lookup lookup;
    private List<IGamePluginService> gamePlugins;
    private Lookup.Result<IGamePluginService> result;

    private GameLookup(GameData gameData, World world) {
        this.gameData = gameData;
        this.world = world;
        lookup = Lookup.getDefault();
        gamePlugins = new CopyOnWriteArrayList<>();

        // Get game plugin services via lookup
        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        initializePlugins();
    }

    private void initializePlugins() {
        gamePlugins.clear();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
    }

    // Restart all game plugins found by lookup
    public void restartPlugins() {
        world.clearEntityMaps();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.stop(gameData, world);
        }

        gamePlugins.clear();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
    }

    public static GameLookup getInstance(GameData gameData, World world) {
        if (instance == null) {
            instance = new GameLookup(gameData, world);
        }

        return instance;
    }

    public Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    public Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }

    public IMapService getMapService() {
        return lookup.lookup(IMapService.class);
    }

    public final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {
            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us : updated) {
                // Install new modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Uninstall modules no longer in lookup result list
            for (IGamePluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }
    };
}
