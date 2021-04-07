/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.lootingsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author phili
 * if entity is colliding with 
 */
public class LootingProcessingSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for(Map.Entry<UUID,EntityPart> entry : world.getMapByPart(CollectorPart.class.getSimpleName()).entrySet()){
            WeaponInventoryPart weaponInventory = world.getMapByPart(WeaponInventoryPart.class.getSimpleName()).get(entry.getKey());
            if(weaponInventory!= null){
                if(entry.getValue().isCollecting()){
                    for(UUID collidingEntity: world.getMapByPart(ColliderPart.class.getSimpleName()).get(entry.getKey()).getCollidingEntities()){
                        
                        if(world.getMapByPart(WeaponPart.class.getSimpleName()).get(collidingEntity)!= null && world.getMapByPart(LootablePart.class.getSimpleName()).get(collidingEntity)!= null){
                            //tilføj våben til weaponInventory part, og fjern visual part og position part fra entiteten, så den ikke længere kan ses på mappen
                            //og nu hvor den bliver samlet op, så collider den ikke længere
                            weaponInventory.addWeapon(collidingEntity);
                            
                            //måske skulle visualPart ikke fjernes, men rettere ændres til en form for "inventory" icon, 
                            //for eller kan det være svært, 
                            //hvis man vælger at droppe våbnet igen. Hvordan skal man så vide hvilken visualpart det skal have når det igen ligger på "banen"
                            //man kunne også give den en værdi, som hedder "is visible"
                            world.getMapByPart(VisualPart.class.getSimpleName()).remove(collidingEntity);
                            world.getMapByPart(PositionPart.class.getSimpleName()).remove(collidingEntity);
                            world.getMapByPart(ColliderPart.class.getSimpleName()).get(collidingEntity).getCollidingEntities().remove(collidingEntity);
                            
                            //måske skal den også fjerne ColliderPart helt fra verdenen
                            //og lootable part, da den vel ikke kan være lootable for fx zombier, hvis de fx skal søge lootable parts. 
                            
                        }
                    }
                }
            }
            ItemInventoryPart itemInventory = world.getMapByPart(ItemInventoryPart.class.getSimpleName()).get(entry.getKey());
            if(itemInventory != null){
                for(UUID collidingEntity: world.getMapByPart(ColliderPart.class.getSimpleName()).get(entry.getKey()).getCollidingEntities()){
                        
                        if(world.getMapByPart(ItemPart.class.getSimpleName()).get(collidingEntity)!= null && world.getMapByPart(LootablePart.class.getSimpleName()).get(collidingEntity)!= null){
                            //tilføj våben til weaponInventory part, og fjern visual part og position part fra entiteten, så den ikke længere kan ses på mappen
                            weaponInventory.addItem(collidingEntity);
                            
                            //måske skulle visualPart ikke fjernes, men rettere ændres til en form for "inventory" icon, 
                            //for eller kan det være svært, 
                            //hvis man vælger at droppe våbnet igen. Hvordan skal man så vide hvilken visualpart det skal have når det igen ligger på "banen"
                            //man kunne også give den en værdi, som hedder "is visible"
                            world.getMapByPart(VisualPart.class.getSimpleName()).remove(collidingEntity);
                            world.getMapByPart(PositionPart.class.getSimpleName()).remove(collidingEntity);
                            world.getMapByPart(ColliderPart.class.getSimpleName()).get(collidingEntity).getCollidingEntities().remove(collidingEntity);
                        }
                    }
            
            }
        }
    }
    
}
