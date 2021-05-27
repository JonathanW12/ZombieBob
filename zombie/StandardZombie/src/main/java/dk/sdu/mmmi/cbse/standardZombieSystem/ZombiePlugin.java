package dk.sdu.mmmi.cbse.standardZombieSystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.*;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import java.util.Map;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class ZombiePlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        // Do nothing
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove all zombies
        if (world.getMapByPart(ZombiePart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(ZombiePart.class.getSimpleName()).entrySet()) {
                world.removeEntityParts(entry.getKey());
            }
        }
    }
}
