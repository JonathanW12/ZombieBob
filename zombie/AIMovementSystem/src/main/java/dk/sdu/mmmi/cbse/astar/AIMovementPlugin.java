package dk.sdu.mmmi.cbse.astar;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AiMovementPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)
public class AIMovementPlugin implements IGamePluginService {
    
    public void start(GameData gameData, World world) {
        // Do nothing
    }
    
    public void stop(GameData gameData, World world) {
        if (world.getMapByPart(AiMovementPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry: world.getMapByPart(AiMovementPart.class.getSimpleName()).entrySet()) {
                MovingPart movingPart = (MovingPart) world.getMapByPart(MovingPart.class.getSimpleName()).get(entry.getKey());
                movingPart.setUp(false);
            }
        }
    }
}
