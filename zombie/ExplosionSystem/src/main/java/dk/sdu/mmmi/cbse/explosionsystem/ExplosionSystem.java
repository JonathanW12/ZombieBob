package dk.sdu.mmmi.cbse.explosionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimationPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.AudioPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.DamagePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ExplosivePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class ExplosionSystem implements IEntityProcessingService {
    
    private Map<Entity, Long> activeExplosions = new HashMap<>();
    private int frameCount = 5;
    private float frameDuration = 0.05f;
    private long explosionDuration = (long) (frameCount * frameDuration * 1000);
    
    @Override
    public void process(GameData gameData, World world) {
        if (world.getMapByPart(ExplosivePart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry: world.getMapByPart(ExplosivePart.class.getSimpleName()).entrySet()) {
                ExplosivePart explosivePart = (ExplosivePart) entry.getValue();
                PositionPart positionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey());
                
                if (explosivePart.getIsReadyToExplode()) {
                    Entity explosion = new Entity();
                    
                    float radians = 3.14159f / 2;
                    float width = 100;
                    float height = 100;
                    
                    AnimationPart animationPart = new AnimationPart();
                    animationPart.addAnimation(
                        "explode", // Set animation Id
                        "explosion", // Animation asset name
                        frameCount, // Frame count
                        frameDuration, // Frame duration
                        false // Animation can't be interrupted
                    );
                    animationPart.setCurrentAnimation("explode");
                    
                    AudioPart explosionSound = new AudioPart(
                            "rocket-explosion.wav",
                            1f
                    );
                    explosionSound.setIsPlaying(true);

                    world.addtoEntityPartMap(new PositionPart(positionPart.getX(), positionPart.getY(), radians), explosion);
                    world.addtoEntityPartMap(new ColliderPart(width, height), explosion);
                    world.addtoEntityPartMap(new DamagePart(800), explosion);
                    world.addtoEntityPartMap(new VisualPart("idleExplosion", width * 2, height * 2, 3), explosion);
                    world.addtoEntityPartMap(animationPart, explosion);
                    world.addtoEntityPartMap(explosionSound, explosion);
                    
                    activeExplosions.put(explosion, System.currentTimeMillis());
                }
            }
        }
        
        processActiveExplosions(world);
    }
    
    private void processActiveExplosions(World world) {
        Iterator<Map.Entry<Entity, Long>> activeExplosionsIterator = activeExplosions.entrySet().iterator();
        
        while (activeExplosionsIterator.hasNext()) {
            Map.Entry<Entity, Long> explosionMap = activeExplosionsIterator.next();
            Entity explosion = explosionMap.getKey();
            AnimationPart animationPart = (AnimationPart) world.getMapByPart(AnimationPart.class.getSimpleName()).get(explosion.getUUID());
            Long spawnTime = explosionMap.getValue();
            
            animationPart.setIsAnimated(true);
            animationPart.setCurrentAnimation("explode");
            
            if (spawnTime + explosionDuration < System.currentTimeMillis()) {
                activeExplosionsIterator.remove();
                world.removeEntityParts(explosion.getUUID());
            }
        }
    } 
}
