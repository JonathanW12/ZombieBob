package dk.sdu.mmmi.cbse.standardZombieSystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.Position;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.List;
import java.util.Random;

import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService {

    private ZombieCreator zombieCreator = new ZombieCreator();
    private int level;
    private int nextLevel;
    double defaultHealth;
    double difficulty;
    //double increment = difficulty * level;
    private double currentIncrease;
    private int min;
    private int max;
    private Random randomGenerator;
    private List<Position> spawnPositions;

    public SpawnProcessor() {
        nextLevel = 1;
        defaultHealth = 100;
        difficulty = 0.10;
        min = 0;
        max = 5;
        randomGenerator = new Random();
    }

    @Override
    public void process(GameData gameData, World world) {

        level = gameData.getLevelInformation().getCurrentLevel();

        if (level > min && level == nextLevel) {
            spawnPositions = world.getEnemySpawnPositions();
/*
            if (level % 10 == 0){
                difficulty = difficulty*2;
            }
*/
            if (level % 500 != 0) {
                if (level > min && level <= max) {
                    currentIncrease = difficulty * level;
                    for (int i = 0; i < level; i++) {
                        Position spawnPosition = spawnPositions.get(randomGenerator.nextInt(spawnPositions.size()));
                        zombieCreator.createZombie((int) (defaultHealth * (1 + currentIncrease)), spawnPosition, world);
                    }
                }

                // After max count reached
                if (level > max) {
                    currentIncrease = difficulty * level;
                    for (int i = 0; i < max; i++) {
                        Position spawnPosition = spawnPositions.get(randomGenerator.nextInt(spawnPositions.size()));
                        zombieCreator.createZombie((int) (defaultHealth * (1 + currentIncrease)), spawnPosition, world);
                    }
                }
            }
            nextLevel++;
        }

        // Handles map reset
        if (level == 0) {
            nextLevel = 1;
        } else if (nextLevel != level+1){
            nextLevel=level+1;
        }
    }
}
