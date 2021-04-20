/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author lake
 */

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class EnemyControlSystem implements IEntityProcessingService {
 Random randomGenerator = new Random();
 int randomNumb = randomGenerator.nextInt(3);
    @Override
    public void process(GameData gd, World world) {
          // all playerParts
        for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart("EnemyPart").entrySet()){

            // entity parts on enemy
            PositionPart positionPart = (PositionPart) world.getMapByPart("PositionPart").get(entry.getKey());
            MovingPart movingPart = (MovingPart) world.getMapByPart("MovingPart").get(entry.getKey());
            VisualPart visualPart = (VisualPart) world.getMapByPart("VisualPart").get(entry.getKey());

            // movement
            switch(randomNumb) {       
                case 1:
                movingPart.setLeft(true);
                break;
                case 2:
                movingPart.setRight(true);
                break;
                case 3:
                movingPart.setUp(true);
                break;
                case 4:
                movingPart.setDown(true);
            }
        }
    }
}
