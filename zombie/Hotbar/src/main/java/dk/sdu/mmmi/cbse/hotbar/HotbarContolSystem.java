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
    ArrayList<UUID> excistingItems = new ArrayList<UUID>();
        
    @Override
    public void process(GameData gameData, World world) {
        UUID weaponID;
        String weaponSprite;
    for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart("PlayerPart").entrySet()){
        WeaponInventoryPart weaponInventoryPart = (WeaponInventoryPart) world.getMapByPart("WeaponInventoryPart").get(entry.getKey());
        
        if(weaponInventoryPart.getInventory()!=null){
        //Checking if hotbar contains items that are not in playerinventory
        for(UUID id : excistingItems){
            if(!weaponInventoryPart.getInventory().contains(id)){
                excistingItems.remove(id);
                world.removeEntityParts(id);
            }
        }
        //Checking if playerInventory contains an item that is not in the hotbar
        for(UUID id : weaponInventoryPart.getInventory()){
            //Adding new items
            if(!excistingItems.contains(id)){
                weaponID = id;
                excistingItems.add(id);
                VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(weaponID);
                weaponSprite = visualPart.getSpriteName();
            
                Entity hotbarItem = new Entity();
            
                //return the position in the inventory the item should be placed
                itemIndex = weaponInventoryPart.getInventory().indexOf(id);
                        
                world.addtoEntityPartMap(new PositionPart(itemPositionsX[itemIndex],itemPositionsY,radians), hotbarItem);
                world.addtoEntityPartMap(new VisualPart(weaponSprite,itemPicSize,itemPicSize,4),hotbarItem);
        }
        }
    }
    }
    }
}
