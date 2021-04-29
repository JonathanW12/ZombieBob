package dk.sdu.mmmi.cbse.standardZombieSystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonlocations.SpawnerLocation;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService {

    private ZombieCreator zombieCreator = new ZombieCreator();
    private int level;
    private int previousLevel = 1;
    double defaultHealth = 100;
    double difficulty = 0.10;
   // double increment = difficulty * level;
    double currentIncrease;
    int min = 0;
    int max = 5;
    private SpawnerLocation location = new SpawnerLocation();

    @Override
    public void process(GameData gameData, World world) {

        level = gameData.getLevelInformation().getCurrentLevel();

        if (level > min && level == previousLevel){
            System.out.println("LEVEL "+ level);
            System.out.println("PREVIOUSLEVEL "+ previousLevel);
            // Incrementing enemy count until certain level

            if (level%5 != 0){
                if (level > min && level <= max) {
                    currentIncrease = difficulty * level;
                    for (int i = 0; i < level; i++) {
                        zombieCreator.createZombie((int) (defaultHealth * (1 + currentIncrease)), location.random(gameData), world);
                    }
                }

                // After max count reached
                if (level > max) {
                    currentIncrease = difficulty * level;
                    for (int i = 0; i < max; i++) {
                        zombieCreator.createZombie((int) (defaultHealth * (1 + currentIncrease)), location.random(gameData), world);
                    }
                }
            }
            previousLevel++;
        }

    }
}
