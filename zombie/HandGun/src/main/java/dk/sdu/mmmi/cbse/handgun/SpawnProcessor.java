package dk.sdu.mmmi.cbse.handgun;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LootablePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService {
    
    private final Random randomGenerator = new Random();
    private final long spawnInterval = 5000;
    private long currentTime = System.currentTimeMillis();
    private long lastSpawnTime = currentTime;
    
    @Override
    public void process(GameData gameData, World world) {
        currentTime = System.currentTimeMillis();
        
        if (currentTime > lastSpawnTime + spawnInterval) {
            spawnGun(gameData, world);
            lastSpawnTime = currentTime;
        }
    }
    
    private void spawnGun(GameData gameData, World world) {
        Entity gun = HandgunCreator.scaffoldGun(world);
        
        VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(gun.getUUID());
        visualPart.setIsVisible(true);
        
        float spawnX = randomGenerator.nextFloat() * gameData.getDisplayWidth();
        float spawnY = randomGenerator.nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;
        
        PositionPart positionPart = new PositionPart(spawnX, spawnY, radians);

        world.addtoEntityPartMap(positionPart, gun);
        world.addtoEntityPartMap(new LootablePart(), gun);
        
        HandgunProcessor.addToProcessingList(gun);
    }
    
}
