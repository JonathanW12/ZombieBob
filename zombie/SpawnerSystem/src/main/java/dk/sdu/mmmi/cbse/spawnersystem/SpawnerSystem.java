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
 * @author tobia
 */

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnerSystem implements IEntityProcessingService {

    private long currentTime = System.currentTimeMillis();
    private long lastSpawnTime = currentTime;
    private SpawnerLocation location = new SpawnerLocation();
    private SpawnerEntities spawnEntities = new SpawnerEntities();
    private UUID levelInformationEntityID;
    private int level = 0;
    private long roundDelay = 7000;
    private int min = 0;
    private int max = 5;

    @Override
    public void process(GameData gameData, World world) {

        if (lastSpawnTime < currentTime - roundDelay) {
            lastSpawnTime = currentTime;

            System.out.println("LEVEL "+ level);

            waveControl(level, gameData, world);

            if (level > 0 && level%5 == 0){
                roundDelay = 14000;
            } else {
                roundDelay = 7000;
            }

            level++;



        }
        displayLevelInformation(world);
        updateTime();
    }

    private void updateTime() {
        currentTime = System.currentTimeMillis();
    }

    public void waveControl(int level, GameData gameData, World world) {
        double defaultHealth = 100;
        double defaultBoss = 800;
        double difficulty = 0.10;
        double increment = difficulty * level;
        double currentIncrease;
        int min = this.min;
        int max = this.max;

        boolean bossWave = (level>min && ((level%5) == 0));

        // Item spawn
        if (level == 0) {
            spawnEntities.createGun(gameData, world);
        }

        // Incrementing enemy count until certain level
        if (level > min && level <= max && !bossWave) {
            currentIncrease = +increment;
            for (int i = 0; i < level; i++) {
                spawnEntities.createZombie((int) (defaultHealth * (1 + currentIncrease)), location.random(gameData), world);
            }
        }

        // After max count reached
        if (level > max && !bossWave) {
            currentIncrease = +increment;
            // System.out.println("Health: "+(int)(defaultHealth*(1+currentIncrease)));
            for (int i = 0; i < max; i++) {
                spawnEntities.createZombie((int) (defaultHealth * (1 + currentIncrease)), location.random(gameData), world);
            }
        }

        // If boss wave
        if (bossWave) {
            currentIncrease = +increment;
            spawnEntities.createSpider((int) (defaultBoss * (1 + currentIncrease)), location.topMid(gameData), world);
            this.max++;
        }
    }

    private void displayLevelInformation(World world) {
        if (levelInformationEntityID == null) {
            Entity levelInformation = new Entity();
            levelInformationEntityID = levelInformation.getUUID();

            world.addtoEntityPartMap(new PositionPart(600, 750, 2f), levelInformation);
            world.addtoEntityPartMap(new TextPart(null, 4), levelInformation);
        }

        TextPart textPart = (TextPart) world.getMapByPart("TextPart").get(levelInformationEntityID);
        String levelMessage;
        if (level-1 < 1) {
            levelMessage = ("Level: " + "Starting Soon");
        } else if (level>2 && ((level-1)%5) == 0){
            levelMessage = ("Level: " + (level - 1)+ " - BOSS -");
        } else {
            levelMessage = ("Level: " + (level - 1));
        }

        textPart.setMessage(levelMessage);
    }
}
