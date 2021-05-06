package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LootablePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private Map<String, Map<UUID,EntityPart>> entityPartMap = new ConcurrentHashMap<>();
    private List<Position> enemySpawnerPositions;
    private List<ItemSpawn> itemSpawns;
    
    public World() {
        enemySpawnerPositions = new ArrayList<>();
        itemSpawns = new ArrayList<>();
    }
    
    public void addtoEntityPartMap(EntityPart entityPart, Entity entity){
        String entityPartNameKey = entityPart.getClass().getSimpleName();

        if (!entityPartMap.containsKey(entityPartNameKey)){
            entityPartMap.put(entityPartNameKey, new ConcurrentHashMap<UUID, EntityPart>());
            entityPartMap.get(entityPartNameKey).put(entity.getUUID(),entityPart);

        } else {
            entityPartMap.get(entityPartNameKey).put(entity.getUUID(),entityPart);
        }

    }
    
    public void addtoEntityPartMap(EntityPart entityPart, UUID entityUUID){
        String entityPartNameKey = entityPart.getClass().getSimpleName();

        if (!entityPartMap.containsKey(entityPartNameKey)){
            entityPartMap.put(entityPartNameKey, new ConcurrentHashMap<UUID, EntityPart>());
            entityPartMap.get(entityPartNameKey).put(entityUUID, entityPart);

        } else {
            entityPartMap.get(entityPartNameKey).put(entityUUID, entityPart);
        }

    }
    
    public void removeFromEntityPartMap(EntityPart entityPart, UUID entityUUID) {
        String entityPartNameKey = entityPart.getClass().getSimpleName();
        
        if (entityPartMap.containsKey(entityPartNameKey)) { 
            entityPartMap.remove(entityPartNameKey, entityUUID);
        }
    }
    
    public void getMapForSpecificPart(){

    }
    public Map<UUID,EntityPart> getMapByPart(String partName){
        return entityPartMap.get(partName);
    }

    public void removeEntityParts(UUID uuid){
        // Check every hashmap
        for (Map.Entry<String,Map<UUID,EntityPart>> entry : entityPartMap.entrySet() ){
            // Look for UUID in innerMaps(value map) remove if found
            entry.getValue().entrySet().removeIf(innerEntry -> innerEntry.getKey().equals(uuid));
        }
    }

    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }
    
    public ItemSpawn getItemSpawnByCurrentItem(UUID currentItem) {
        for (ItemSpawn spawn: itemSpawns) {
            if (currentItem.equals(spawn.getCurrentItem())) {
                return spawn;
            }
        }
        
        return null;
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }
    
    public List<Position> getEnemySpawnPositions() {
        return enemySpawnerPositions;
    }
    
    public List<ItemSpawn> getItemSpawns() {
        return itemSpawns;
    }
    
    public void addEnemySpawnPosition(int x, int y) {
        Position position = new Position(x, y);
        enemySpawnerPositions.add(position);
    }
    
    public void addItemSpawnPosition(int x, int y) {
        ItemSpawn itemSpawn = new ItemSpawn();
        Position position = new Position(x, y);
        
        itemSpawn.setPosition(position);
        itemSpawns.add(itemSpawn);
    }

}
