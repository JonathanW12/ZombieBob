package dk.sdu.mmmi.cbse.rocketlauncher;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.ItemSpawn;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService{

    private static boolean spawnedWeapon;
    private RocketLauncherCreator rocketLauncherCreator;
    private Random randomGenerator;
    
    public SpawnProcessor() {
        spawnedWeapon = false;
        rocketLauncherCreator = new RocketLauncherCreator();
        spawnedWeapon = false;
        randomGenerator = new Random();
    }

    @Override
    public void process(GameData gameData, World world) {
        if (gameData.getLevelInformation().getCurrentLevel() == 10 && !spawnedWeapon) {
            ItemSpawn spawn = getSpawnPosition(world);
            
            UUID rocketLauncherID = rocketLauncherCreator.spawnRocketLauncher(spawn.getPosition(), gameData, world);
            spawn.setCurrentItem(rocketLauncherID);
            spawnedWeapon = true;
        }
    }
    
    protected static void resetSpawnProcessor()  {
        spawnedWeapon = false;
    }
    
    private ItemSpawn getSpawnPosition(World world) {
        List<ItemSpawn> availableSpawns = getAvailableItemSpawns(world);
        ItemSpawn spawn = availableSpawns.get(randomGenerator.nextInt(availableSpawns.size()));
        
        return spawn;
    }
    
    private List<ItemSpawn> getAvailableItemSpawns(World world) {
        List<ItemSpawn> resultList = new ArrayList<>();
        
        for (ItemSpawn spawn: world.getItemSpawns()) {
            if (spawn.getCurrentItem() == null) {
                resultList.add(spawn);
            }
        }
        
        return resultList;
    }
    
}
