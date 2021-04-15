/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.spawnersystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.EnemyPlugin;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author tobia
 */

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnerSystem implements IEntityProcessingService {
    
    private EnemyPlugin enemyPlugin = EnemyPlugin.getInstance();
    private long currentTime = System.currentTimeMillis();
    private long lastSpawnTime = currentTime;
    
    @Override
    public void process(GameData gameData, World world) {
        if (lastSpawnTime < currentTime - 5000) {
            lastSpawnTime = currentTime;
            enemyPlugin.spawn(400, 200, world);
        }
        
        updateTime();
    }
    
    private void updateTime() {
        currentTime = System.currentTimeMillis();
    }
}
