package dk.sdu.mmmi.cbse.hotbar;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TextPart;
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

    // Information Variables
    private UUID levelInformationEntityID = null;
    private UUID killInformationEntityID;
    private int level;
    private int zombiesKilled = 0;


    ArrayList<UUID> itemsToBeRemoved = new ArrayList<UUID>();
    //This hashMap: Keys are weapon IDs in the players inventory and values are weapon IDs in the hotbar
    HashMap<UUID,UUID> excistingItems2 = new HashMap<UUID,UUID>();
    
    @Override
    public void process(GameData gameData, World world) {
    for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart("PlayerPart").entrySet()){
        WeaponInventoryPart weaponInventoryPart = (WeaponInventoryPart) world.getMapByPart("WeaponInventoryPart").get(entry.getKey());
        if(weaponInventoryPart.getInventory()!=null){ 
        if(!excistingItems2.isEmpty()){
            removeItemsNoLongerInInventory(world,weaponInventoryPart);
            reorganizeHotbarItemPositions(world, weaponInventoryPart);
        }
        for(UUID id : weaponInventoryPart.getInventory()){
            addNewItemsToHotbar(world,id,weaponInventoryPart);
        }
    }
        displayPlayerHp();

        // update Levelinformation and Enemies killed information on Hotbar
        displayLevelInformation(gameData,world);
        displayKillInformation(gameData, world);

    }
}
    private void reorganizeHotbarItemPositions(World world, WeaponInventoryPart weaponInventoryPart){
        for(Map.Entry weaponId : excistingItems2.entrySet()){
            PositionPart positionPart = (PositionPart) world.getMapByPart("PositionPart").get(weaponId.getValue());

            itemIndex = weaponInventoryPart.getInventory().indexOf(weaponId.getKey());
            positionPart.setX(itemPositionsX[itemIndex]);
        }
    }
    private void addNewItemsToHotbar(World world, UUID playerWeaponID, WeaponInventoryPart weaponInventoryPart){
        if(!excistingItems2.containsKey(playerWeaponID)){
            VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(playerWeaponID);

            Entity hotbarItem = new Entity();
            //return the position in the inventory the item should be placed
            itemIndex = weaponInventoryPart.getInventory().indexOf(playerWeaponID);

            world.addtoEntityPartMap(new PositionPart(itemPositionsX[itemIndex],itemPositionsY,radians), hotbarItem);
            world.addtoEntityPartMap(new VisualPart(visualPart.getSpriteName(),itemPicSize,itemPicSize,4),hotbarItem);
            excistingItems2.put(playerWeaponID, hotbarItem.getUUID());
    }
    }
    private void removeItemsNoLongerInInventory(World world, WeaponInventoryPart weaponInventoryPart){
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
    private void displayPlayerHp(){
        
    }

    private void displayLevelInformation(GameData gameData,World world) {
        if (levelInformationEntityID == null) {
            Entity levelInformation = new Entity();
            levelInformationEntityID = levelInformation.getUUID();

            world.addtoEntityPartMap(new PositionPart(600, 750, 2f), levelInformation);
            world.addtoEntityPartMap(new TextPart(null, 4), levelInformation);
        }

        level = gameData.getLevelInformation().getCurrentLevel();

        TextPart textPart = (TextPart) world.getMapByPart("TextPart").get(levelInformationEntityID);
        String levelMessage;
        if (level-1 < 1) {
            levelMessage = ("Level: " + "Starting Soon");
        } else if (level>2 && ((level-1)%5) == 0){
            levelMessage = ("Level: " + (level - 1)+ " - BOSS -");
        } else {
            levelMessage = ("Level: " + (level - 1));
        }

        textPart.setMessage(levelMessage);
    }

    private void displayKillInformation(GameData gameData, World world) {
        if(killInformationEntityID == null){
            Entity killInformation = new Entity();
            killInformationEntityID = killInformation.getUUID();
            world.addtoEntityPartMap(new PositionPart(600,700,2f), killInformation);
            world.addtoEntityPartMap(new TextPart(null,4), killInformation);
        }

        zombiesKilled = gameData.getLevelInformation().getEnemiesKilled();

        TextPart textPart = (TextPart) world.getMapByPart("TextPart").get(killInformationEntityID);
        String killMessage = ("Zombies Slain: " + zombiesKilled);
        textPart.setMessage(killMessage);
    }
}
