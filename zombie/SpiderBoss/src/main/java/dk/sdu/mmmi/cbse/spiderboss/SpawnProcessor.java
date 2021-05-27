package dk.sdu.mmmi.cbse.spiderboss;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.Position;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.List;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService {

    private SpiderCreator spiderCreator = new SpiderCreator();
    private int level;
    private int previousLevel;
    private double defaultBoss;
    private double difficulty;
    //private double increment = difficulty * level;
    private double currentIncrease;
    private int min;
    private int max;
    private boolean spawned;
    private Random randomGenerator;
    private List<Position> spawnPositions;

    public SpawnProcessor() {
        previousLevel = 1;
        defaultBoss = 800;
        difficulty = 0.10;
        min = 0;
        max = 2;
        spawned = false;
        randomGenerator = new Random();
    }

    @Override
    public void process(GameData gameData, World world) {
        level = gameData.getLevelInformation().getCurrentLevel();

        if (level > 0 && !spawned) {
            spawnPositions = world.getEnemySpawnPositions();

            if (level % 10 == 0) {
                difficulty = difficulty * 2;
            }

            if (level % 5 == 0) {
                Position spawnPosition = spawnPositions.get(randomGenerator.nextInt(spawnPositions.size()));
                currentIncrease = difficulty * level;
                spiderCreator.createSpiderBoss(
                        (int) (defaultBoss * (1 + currentIncrease)),
                        spawnPosition, world);
                spawned = true;
            }
        }
        if (level % 5 != 0) {
            spawned = false;
        }
    }

}
