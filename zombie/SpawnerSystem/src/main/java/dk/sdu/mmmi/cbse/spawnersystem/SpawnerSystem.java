/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.spawnersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TextPart;
import dk.sdu.mmmi.cbse.common.services.IEnemyCreatorService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonenemy.EnemyPlugin;
import java.util.UUID;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;



/**
 *
 * @author tobia
 */

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnerSystem implements IEntityProcessingService {

    private long currentTime = System.currentTimeMillis();
    private long lastSpawnTime = currentTime;
    private SpawnerLocation location = new SpawnerLocation();
    private SpawnerEntities spawnEntities = new SpawnerEntities();
    private UUID levelInformationEntityID;
    private int level = 1;

    @Override
    public void process(GameData gameData, World world) {
        if (lastSpawnTime < currentTime - 7000) {
            lastSpawnTime = currentTime;

            if (level == 1){
                spawnEntities.createGun(gameData,world);
                spawnEntities.createSpider(200, location.topMid(gameData),world);
            }

            waveControl(level, gameData, world);
            level++;
        }
        displayLevelInformation(world);
        updateTime();
    }
    
    private void updateTime() {
        currentTime = System.currentTimeMillis();
    }

    public void waveControl(int level, GameData gameData, World world){
        double defaultHealth = 100;
        double increment = 0.10 *level;
        double currentIncrease = 1;
        int max = 4;
        if (level <= max){
            currentIncrease =+ increment;
            for (int i = 0; i < level; i++) {
                spawnEntities.createZombie((int)(defaultHealth*(1+currentIncrease)), location.random(gameData),world);

            }

        }
        if (level > max){
            currentIncrease =+ increment;
            System.out.println("Health: "+(int)(defaultHealth*(1+currentIncrease)));
            for (int i = 0; i < max; i++) {
                spawnEntities.createZombie((int)(defaultHealth*(1+currentIncrease)), location.random(gameData),world);
            }

        }
    }
    private void displayLevelInformation(World world) {
        if(levelInformationEntityID == null){
        Entity levelInformation = new Entity();
        levelInformationEntityID = levelInformation.getUUID();
        
        
        world.addtoEntityPartMap(new PositionPart(600,750,2f), levelInformation);
        world.addtoEntityPartMap(new TextPart(null,4), levelInformation);
        }
        TextPart textPart = (TextPart) world.getMapByPart("TextPart").get(levelInformationEntityID);
        String levelMessage = ("Level: " + level);
        
        textPart.setMessage(levelMessage);
    }
}
