package dk.sdu.mmmi.cbse.levelsystem;

import dk.sdu.mmmi.cbse.commonlevel.LevelInformation;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class LevelSystem implements IEntityProcessingService {

    private LevelInformation levelInformation = new LevelInformation();
    private long currentTime = System.currentTimeMillis();
    private long lastSpawnTime = currentTime;
    private long roundDelay = 7000;
    private int level = 0;

    @Override
    public void process(GameData gameData, World world) {
        if (lastSpawnTime < currentTime - roundDelay) {
            lastSpawnTime = currentTime;

            System.out.println("LEVEL "+ level);

            // Every 5yh round will have double time
            if (level > 0 && level%5 == 0){
                roundDelay = 14000;
            } else {
                roundDelay = 7000;
            }

            gameData.getLevelInformation().setCurrentLevel(level);
            level++;

        }


        // displayLevelInformation(world);
        updateTime();

    }
    private void updateTime() {
        currentTime = System.currentTimeMillis();
    }
}
