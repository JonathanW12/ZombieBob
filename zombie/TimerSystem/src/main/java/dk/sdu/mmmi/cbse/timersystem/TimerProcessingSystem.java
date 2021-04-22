package dk.sdu.mmmi.cbse.timersystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.util.Map;
import java.util.UUID;

@ServiceProviders(value = {
        @ServiceProvider(service = IEntityProcessingService.class)})
public class TimerProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        // Check if hashmap exists
        if (world.getMapByPart(TimerPart.class.getSimpleName()) != null){

            // Loops through all TimerParts in TimerPart hashmap
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(TimerPart.class.getSimpleName()).entrySet()){

                // process specific timerPart
                TimerPart timerPart = (TimerPart) world.getMapByPart(TimerPart.class.getSimpleName()).get(entry.getKey());

                // reduce expiration by delta
                if (timerPart.getExpiration() > 0) {
                    timerPart.reduceExpiration(gameData.getDelta());
                } 
            }
        }
    }
}
