/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author phili
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class)})
public class CollisionPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stop(GameData gameData, World world) {
        if(world.getMapByPart(ColliderPart.class.getSimpleName())!= null){
            for (Map.Entry<UUID, EntityPart> entry: world.getMapByPart(ColliderPart.class.getSimpleName()).entrySet()) {
                ((ColliderPart)entry.getValue()).getCollidingEntities().clear();
            }
        }
    }
    
}
