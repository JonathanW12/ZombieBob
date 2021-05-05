package dk.sdu.mmmi.cbse.audiosystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AudioPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class AudioSystem implements IEntityProcessingService {
    
    @Override
    public void process(GameData gameData, World world) {
        if (world.getMapByPart(AudioPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry: world.getMapByPart(AudioPart.class.getSimpleName()).entrySet()) {
                AudioPart audioPart = (AudioPart) entry.getValue();
                
                // Replay audio at a random time within the audiopart's min and max values
                if (audioPart.getIsRandomlyReplaying()) {
                    if (System.currentTimeMillis() > audioPart.getActualDelay() + audioPart.getLastReplayTime()) {
                        audioPart.setIsPlaying(true);
                        audioPart.resetDelayTimer();
                    }
                }
            }
        }
    }
}
