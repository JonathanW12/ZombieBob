package dk.sdu.mmmi.cbse.handgun;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CombatPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponInventoryPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)
public class HandgunPlugin implements IGamePluginService {
    
    @Override
    public void start(GameData gameData, World world) {
        // Do nothing
    }
    
    @Override
    public void stop(GameData gameData, World world) {
        if (world.getMapByPart(PlayerPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()) {
                 WeaponInventoryPart weaponInventoryPart = (WeaponInventoryPart) world.getMapByPart("WeaponInventoryPart").get(entry.getKey());
                 weaponInventoryPart.removeWeapon(HandgunCreator.getHandgunID());

                 CombatPart combatPart = (CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(entry.getKey());
                 if(combatPart.getCurrentWeapon() == HandgunCreator.getHandgunID()){
                     combatPart.removeCurrentWeapon();
                 }  
            }
        }
        HandgunProcessor.clearProcessingList();
        SpawnProcessor.resetSpawnProcessor();
    }
}

