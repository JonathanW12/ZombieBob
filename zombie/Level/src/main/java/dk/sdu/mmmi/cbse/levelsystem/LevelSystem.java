package dk.sdu.mmmi.cbse.levelsystem;

import dk.sdu.mmmi.cbse.commonlevel.LevelInformation;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class LevelSystem implements IEntityProcessingService {

    private LevelInformation levelInformation = new LevelInformation();
    private static long currentTime = 0;
    private static long lastSpawnTime = currentTime;
    private long roundDelay = 7000;
    private static int level = 0;

    @Override
    public void process(GameData gameData, World world) {
        if (lastSpawnTime < currentTime - roundDelay) {
            lastSpawnTime = currentTime;

            // Every 5yh round will have double time
            if (level > 0 && level % 5 == 0) {
                roundDelay = 14000;
            } else {
                roundDelay = 7000;
            }

            gameData.getLevelInformation().setCurrentLevel(level);
            level++;

        }
        updateTime(gameData.getDelta());

    }

    private void updateTime(float delta) {
        currentTime += delta * 1000;
    }

    protected static void resetLevel(GameData gameData) {
        level = 0;
        currentTime = 0;
        lastSpawnTime = currentTime;
        gameData.getLevelInformation().resetLevel();
    }
}
