/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.EnemyPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author lake
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class EnemyPlugin implements IGamePluginService {

    @Override
    public void start(GameData gd, World world) {
          createEnemyShip(gd, world);
    }

    private void createEnemyShip(GameData gameData, World world) {

        float deacceleration = 10;
        float acceleration = 150;
        float maxSpeed = 200;
        float rotationSpeed = 5;
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;

        Entity enemyShip = new Entity();

        // TESTING THE HASHMAP, created entity got ID and linked all parts to ID.
        world.addtoEntityPartMap(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed), enemyShip);
        world.addtoEntityPartMap(new PositionPart(x, y, radians),enemyShip);
        world.addtoEntityPartMap(new EnemyPart(),enemyShip);
        world.addtoEntityPartMap(new VisualPart(10,new float[]{60f, 179f, 113f, 1f}),enemyShip);

        
    }
    @Override
    public void stop(GameData gd, World world) {
      
    }
    
}
