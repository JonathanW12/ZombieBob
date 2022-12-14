package dk.sdu.mmmi.cbse.lootingsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CollectorPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.CombatPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LootablePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponInventoryPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Map;
import java.util.UUID;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 * @author phili if entity is colliding with
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class LootingProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        //Check wether any entities in the world has a collectorPart ie. the ability to loot items
        if (world.getMapByPart(CollectorPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(CollectorPart.class.getSimpleName()).entrySet()) {
                WeaponInventoryPart weaponInventory = ((WeaponInventoryPart) world.getMapByPart(WeaponInventoryPart.class.getSimpleName()).get(entry.getKey()));

                //if entity with collector part do not have an inventory they cannot hold the item
                if (weaponInventory != null) {

                    //is the entity trying to pick up an item
                    if (((CollectorPart) entry.getValue()).isCollecting()) {

                        PositionPart collectorPos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey());

                        if (world.getMapByPart(LootablePart.class.getSimpleName()) != null) {

                            //find all items within reach
                            for (Map.Entry<UUID, EntityPart> item : world.getMapByPart(LootablePart.class.getSimpleName()).entrySet()) {

                                PositionPart iPos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(item.getKey());
                                if (iPos != null) {

                                    float distance = (float) Math.sqrt(
                                            Math.pow(collectorPos.getX() - iPos.getX(), 2)
                                            + Math.pow(collectorPos.getY() - iPos.getY(), 2)
                                    );
                                    //check ??if item is within reach
                                    if (distance < 50) {

                                        //check if the item is a weapon before trying to add it wot weaponInventory
                                        if (world.getMapByPart("WeaponPart").get(item.getKey()) != null) {

                                            //check for capacity in inventory
                                            if (weaponInventory.getCapacity() <= weaponInventory.getInventory().size()) {

                                                //when looting with full inventory, the entity should drop the current weapon and exchange it with the looted weapon
                                                CombatPart combatPart = ((CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(entry.getKey()));
                                                if (combatPart != null) {
                                                    UUID currentWeapon = combatPart.getCurrentWeapon();

                                                    //make room for the new weapon
                                                    weaponInventory.removeWeapon(currentWeapon);

                                                    //give dropped weapon its own positionPart
                                                    world.getMapByPart("PositionPart").replace(currentWeapon, new PositionPart(
                                                            collectorPos.getX(),
                                                            collectorPos.getY(),
                                                            (float) Math.PI / 2)
                                                    );
                                                    //make it lootable again
                                                    world.addtoEntityPartMap(new LootablePart(), currentWeapon);
                                                    ((VisualPart) world.getMapByPart("VisualPart").get(currentWeapon)).setIsVisible(true);

                                                    //add to inventory, and share position with collector. Item is no longer lootable.
                                                    weaponInventory.addWeapon(item.getKey());
                                                    world.getMapByPart("PositionPart").replace(item.getKey(), collectorPos);
                                                    world.getMapByPart("LootablePart").remove(item.getKey());
                                                    ((VisualPart) world.getMapByPart("VisualPart").get(item.getKey())).setIsVisible(false);

                                                    //combat part has the current weapon to be the weapon on the ground. Set this to be the looted weapon.
                                                    if (weaponInventory.getInventory().isEmpty()) {
                                                        combatPart.setCurrentWeapon(item.getKey());
                                                    }

                                                    // Remove item from spawn
                                                    world.getItemSpawnByCurrentItem(combatPart.getCurrentWeapon()).removeCurrentItem();
                                                }
                                            } else {
                                                //add to inventory, and share position with collector. Item is no longer lootable.
                                                weaponInventory.addWeapon(item.getKey());
                                                world.getMapByPart("PositionPart").replace(item.getKey(), collectorPos);
                                                world.getMapByPart("LootablePart").remove(item.getKey());
                                                ((VisualPart) world.getMapByPart("VisualPart").get(item.getKey())).setIsVisible(false);
                                                CombatPart combatPart = ((CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(entry.getKey()));
                                                if (combatPart != null) {
                                                    if (weaponInventory.getInventory().size() == 1) {
                                                        combatPart.setCurrentWeapon(item.getKey());
                                                    }
                                                    // Remove item from spawn
                                                    world.getItemSpawnByCurrentItem(item.getKey()).removeCurrentItem();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
