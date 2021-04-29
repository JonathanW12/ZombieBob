package dk.sdu.mmmi.cbse.spiderboss;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonlocations.SpawnerLocation;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService{

    private SpiderCreator spiderCreator = new SpiderCreator();
    private int level;
    private int previousLevel = 1;
    private double defaultBoss = 800;
    private double difficulty = 0.10;
    private double increment = difficulty * level;
    private double currentIncrease;
    private int min = 0;
    private int max = 2;
    private SpawnerLocation location = new SpawnerLocation();
    private boolean spawned = false;

    @Override
    public void process(GameData gameData, World world) {
        level = gameData.getLevelInformation().getCurrentLevel();

        if (level > 0 && !spawned){
            if (level%5 == 0){
                currentIncrease = +increment;
                spiderCreator.createSpiderBoss(
                        (int) (defaultBoss * (1 + currentIncrease)),
                        location.random(gameData), world);
                spawned = true;
            }
        }
        if (level%5 != 0){
            spawned = false;
        }
    }
}
