/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.commonenemy;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.*;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import java.util.Map;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class EnemyPlugin implements IGamePluginService{

    private static EnemyPlugin instance;
    
    public EnemyPlugin(){
    }

    private EnemyPlugin(String s){
        
    }
    
    public static EnemyPlugin getInstance(){
        if(instance == null){
            instance = new EnemyPlugin("test");  
        }
        return instance;
    }
    
    public void spawn(float x, float y, World world){
        float speed = 2;
        float radians = 3.1415f / 2;
        float rotationSpeed = 3;
        
        Entity zombie = new Entity();
             
        world.addtoEntityPartMap(new VisualPart("sword_sprite", 80, 80), zombie);
        world.addtoEntityPartMap(new MovingPart(speed,rotationSpeed), zombie);
        world.addtoEntityPartMap(new PositionPart(x,y,radians), zombie);
        world.addtoEntityPartMap(new LifePart(100), zombie);
        world.addtoEntityPartMap(new AiMovementPart(4), zombie);
        world.addtoEntityPartMap(new EnemyPart(), zombie);
        world.addtoEntityPartMap(new ColliderPart(40),zombie);
    }
    
    @Override
    public void start(GameData gameData, World world) {
        
    }
    
    @Override
    public void stop(GameData gameData, World world) {
        for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(EnemyPart.class.getSimpleName()).entrySet()){
            world.removeEntityParts(entry.getKey());
        }
    }
    
}
