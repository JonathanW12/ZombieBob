/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.throwingknife;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.ItemSpawn;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author lake
 */@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService {
     private boolean spawnedWeapon;
    private ThrowingKnifeCreator knifeCreator;
    private Random randomGenerator;
    
    public SpawnProcessor() {
        spawnedWeapon = false;
        knifeCreator = new ThrowingKnifeCreator();
        spawnedWeapon = false;
        randomGenerator = new Random();
    }

    @Override
    public void process(GameData gameData, World world) {
        if (gameData.getLevelInformation().getCurrentLevel() == 0 && !spawnedWeapon){
            ItemSpawn spawn = getSpawnPosition(world);
            
            UUID knifeID = knifeCreator.spawnKnifeData(spawn.getPosition(), gameData, world);
            spawn.setCurrentItem(knifeID);
            spawnedWeapon = true;
        }
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
  

