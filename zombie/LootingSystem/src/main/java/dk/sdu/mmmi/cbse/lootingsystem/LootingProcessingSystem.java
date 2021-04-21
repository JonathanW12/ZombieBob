package dk.sdu.mmmi.cbse.lootingsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.CombatPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LootablePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author phili
 * if entity is colliding with 
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class LootingProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        boolean enterKeyPressed = gameData.getKeys().isPressed(GameKeys.ENTER);
        
        if (world.getMapByPart(PlayerPart.class.getSimpleName()) != null && world.getMapByPart(LootablePart.class.getSimpleName()) != null) { 
            for (Map.Entry<UUID, EntityPart> player : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()) {
                PositionPart pPos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(player.getKey());
                CombatPart combatPart = (CombatPart) world.getMapByPart(CombatPart.class.getSimpleName()).get(player.getKey());
                
                for (Map.Entry<UUID, EntityPart> item : world.getMapByPart(LootablePart.class.getSimpleName()).entrySet()) {
                    PositionPart iPos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(item.getKey());
                    float distance = (float) Math.sqrt(
                        Math.pow(pPos.getX() - iPos.getX(), 2) +
                        Math.pow(pPos.getY() - iPos.getY(), 2)
                    );
                    
                    if (distance < 50 && enterKeyPressed) {
                        // Equip weapon
                        if (world.getMapByPart(WeaponPart.class.getSimpleName()).get(item.getKey()) != null) {
                            VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(item.getKey());
                            
                            combatPart.setCurrentWeapon(item.getKey());
                            world.addtoEntityPartMap(pPos, item.getKey());
                            visualPart.setIsVisible(false);
                        }
                    }
                }
            }
        }
    }
    /*
    @Override
    public void process(GameData gameData, World world) {
        
        if(world.getMapByPart(CollectorPart.class.getSimpleName()) !=null){
        for(Map.Entry<UUID,EntityPart> entry : world.getMapByPart(CollectorPart.class.getSimpleName()).entrySet()){
            WeaponInventoryPart weaponInventory = ((WeaponInventoryPart)world.getMapByPart(WeaponInventoryPart.class.getSimpleName()).get(entry.getKey()));
            if(weaponInventory!= null){
                if(((CollectorPart)entry.getValue()).isCollecting()){
                    for(UUID collidingEntity: ((ColliderPart)world.getMapByPart(ColliderPart.class.getSimpleName()).get(entry.getKey())).getCollidingEntities()){
                        
                        if(world.getMapByPart(WeaponPart.class.getSimpleName()).get(collidingEntity)!= null && world.getMapByPart(LootablePart.class.getSimpleName()).get(collidingEntity)!= null){
                            //tilføj våben til weaponInventory part, og fjern visual part og position part fra entiteten, så den ikke længere kan ses på mappen
                            //og nu hvor den bliver samlet op, så collider den ikke længere
                            if(!weaponInventory.addWeapon(collidingEntity)){
                                CombatPart combatPart = ((CombatPart)world.getMapByPart(CombatPart.class.getSimpleName()).get(entry.getKey()));
                                if(combatPart != null){
                                    UUID currentWeapon = combatPart.getCurrentWeapon();
                                    weaponInventory.removeWeapon(currentWeapon);
                                    //maybe now the current weapon should regain its visualPart, ColliderPart and PositionPart since it is dropped into the world map again
                                    if(!weaponInventory.addWeapon(collidingEntity)){
                                        System.out.println("Something went wrong - check looting system");
                                    };
                                    combatPart.setCurrentWeapon(collidingEntity);
                                }
                            }
                            
                            //måske skulle visualPart ikke fjernes, men rettere ændres til en form for "inventory" icon, 
                            //for eller kan det være svært, 
                            //hvis man vælger at droppe våbnet igen. Hvordan skal man så vide hvilken visualpart det skal have når det igen ligger på "banen"
                            //man kunne også give den en værdi, som hedder "is visible"
                            world.getMapByPart(VisualPart.class.getSimpleName()).remove(collidingEntity);
                            PositionPart ownerPosition = ((PositionPart)world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey()));
                            world.getMapByPart(PositionPart.class.getSimpleName()).replace(collidingEntity, ownerPosition);
                            ((ColliderPart)world.getMapByPart(ColliderPart.class.getSimpleName()).get(collidingEntity)).getCollidingEntities().remove(collidingEntity);
                            
                            //måske skal den også fjerne ColliderPart helt fra verdenen
                            //og lootable part, da den vel ikke kan være lootable for fx zombier, hvis de fx skal søge lootable parts. 
                            
                        }
                    }
                }
            }
            ItemInventoryPart itemInventory = (ItemInventoryPart)world.getMapByPart(ItemInventoryPart.class.getSimpleName()).get(entry.getKey());
            if(itemInventory != null){
                for(UUID collidingEntity: ((ColliderPart)world.getMapByPart(ColliderPart.class.getSimpleName()).get(entry.getKey())).getCollidingEntities()){
                        
                        if(world.getMapByPart(ItemPart.class.getSimpleName()).get(collidingEntity)!= null && world.getMapByPart(LootablePart.class.getSimpleName()).get(collidingEntity)!= null){
                            //tilføj våben til weaponInventory part, og fjern visual part og position part fra entiteten, så den ikke længere kan ses på mappen
                            itemInventory.addItem(collidingEntity);
                            
                            //måske skulle visualPart ikke fjernes, men rettere ændres til en form for "inventory" icon, 
                            //for eller kan det være svært, 
                            //hvis man vælger at droppe våbnet igen. Hvordan skal man så vide hvilken visualpart det skal have når det igen ligger på "banen"
                            //man kunne også give den en værdi, som hedder "is visible"
                            world.getMapByPart(VisualPart.class.getSimpleName()).remove(collidingEntity);
                            PositionPart ownerPosition = ((PositionPart)world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey()));
                            world.getMapByPart(PositionPart.class.getSimpleName()).replace(collidingEntity, ownerPosition);
                            ((ColliderPart)world.getMapByPart(ColliderPart.class.getSimpleName()).get(collidingEntity)).getCollidingEntities().remove(collidingEntity);
                        }
                    }
                }
            }
        }
    }*/
}
