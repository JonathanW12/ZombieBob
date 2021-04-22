package dk.sdu.mmmi.cbse.hotbar;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.WeaponInventoryPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class)})
public class HotbarContolSystem implements IPostEntityProcessingService {
    float radians = 3.1415f / 2;
    int itemPicSize = 92;
    //positions match the spaces on the hotbar sprite
    float[] itemPositionsX = new float[]{125,250,375,500};
    float itemPositionsY = 725;
    int currentItem;
    int itemIndex;
    ArrayList<UUID> itemsToBeRemoved = new ArrayList<UUID>();
    //This hashMap: Keys are weapon IDs in the players inventory and values are weapon IDs in the hotbar
    HashMap<UUID,UUID> excistingItems2 = new HashMap<UUID,UUID>();
    @Override
    public void process(GameData gameData, World world) {
        UUID weaponID;
        String weaponSprite;
    for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart("PlayerPart").entrySet()){
        WeaponInventoryPart weaponInventoryPart = (WeaponInventoryPart) world.getMapByPart("WeaponInventoryPart").get(entry.getKey());
        if(weaponInventoryPart.getInventory()!=null){
            
        //Checking if hotbar contains items that are not in playerinventory
        if(!excistingItems2.isEmpty()){
        for(Map.Entry weaponId : excistingItems2.entrySet()){
            if(!weaponInventoryPart.getInventory().contains(weaponId.getKey())){
                //Removing player item from hotbar list
                itemsToBeRemoved.add((UUID) weaponId.getKey());
                //Removing hotbar item from world
                world.removeEntityParts((UUID) weaponId.getValue());
            }
        }
        //Removing player item from hotbar list
        itemsToBeRemoved.forEach(id -> excistingItems2.remove(id));
        itemsToBeRemoved.clear();
        }
        
        //Checking if playerInventory contains an item that is not in the hotbar
        for(UUID id : weaponInventoryPart.getInventory()){
            //Adding new Entity items to the hotbar
            if(!excistingItems2.containsKey(id)){
                weaponID = id;
                VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(weaponID);
                weaponSprite = visualPart.getSpriteName();
            
                Entity hotbarItem = new Entity();
                //return the position in the inventory the item should be placed
                itemIndex = weaponInventoryPart.getInventory().indexOf(id);
                        
                world.addtoEntityPartMap(new PositionPart(itemPositionsX[itemIndex],itemPositionsY,radians), hotbarItem);
                world.addtoEntityPartMap(new VisualPart(weaponSprite,itemPicSize,itemPicSize,4),hotbarItem);
                excistingItems2.put(id, hotbarItem.getUUID());
        }
        }
    }
    }
    }
}
