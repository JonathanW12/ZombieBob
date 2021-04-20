package dk.sdu.mmmi.cbse.handgun;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService {
    
    private final HandgunData gunData = HandgunData.getInstance();
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
        Entity gun = new Entity();
        
        float width = 30;
        float height = 30;
        float spawnX = randomGenerator.nextFloat() * gameData.getDisplayWidth();
        float spawnY = randomGenerator.nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;
        
        WeaponPart weaponPart = new WeaponPart(
            gunData.getDamage(),
            gunData.getRange(),
            gunData.getFireRate(),
            1
        );
        VisualPart visualPart = new VisualPart(
            gunData.getVisualPartName(),
            width,
            height
        );
        PositionPart positionPart = new PositionPart(spawnX, spawnY, radians);
        
        world.addtoEntityPartMap(weaponPart, gun);
        world.addtoEntityPartMap(visualPart, gun);
        world.addtoEntityPartMap(positionPart, gun);
    }
    
}