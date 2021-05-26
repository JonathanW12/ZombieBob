package dk.sdu.mmmi.cbse.combatsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CombatPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)
public class CombatPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        if (world.getMapByPart(CombatPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(CombatPart.class.getSimpleName()).entrySet()) {
                ((CombatPart) entry.getValue()).setAttacking(false);
            }
        }
        if (world.getMapByPart(WeaponPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(WeaponPart.class.getSimpleName()).entrySet()) {
                ((WeaponPart) entry.getValue()).setIsAttacking(false);
            }
        }
    }
}
