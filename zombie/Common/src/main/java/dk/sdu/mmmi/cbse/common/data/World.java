package dk.sdu.mmmi.cbse.common.data;

import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private Map<String, Map<UUID,EntityPart>> entityPartMap = new ConcurrentHashMap<>();
    
    public void addtoEntityPartMap(EntityPart entityPart, Entity entity){
        String entityPartNameKey = entityPart.getClass().getSimpleName();

        if (!entityPartMap.containsKey(entityPartNameKey)){
            entityPartMap.put(entityPartNameKey, new ConcurrentHashMap<UUID, EntityPart>());
            entityPartMap.get(entityPartNameKey).put(entity.getUUID(),entityPart);

        } else {
            entityPartMap.get(entityPartNameKey).put(entity.getUUID(),entityPart);
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

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

}
