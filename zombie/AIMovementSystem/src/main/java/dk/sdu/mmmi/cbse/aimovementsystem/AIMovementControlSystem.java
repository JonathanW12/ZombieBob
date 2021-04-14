package dk.sdu.mmmi.cbse.aimovementsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AiMovementPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;


import java.util.Map;
import java.util.UUID;

@ServiceProviders(value = {
        @ServiceProvider(service = IEntityProcessingService.class)})
public class AIMovementControlSystem implements IEntityProcessingService{

    SimpleMovement simpleMovement = new SimpleMovement();
    Pathfinding pathfinding = new Pathfinding();

    @Override
    public void process(GameData gameData, World world) {


        // Check if hashmap exists
        if (world.getMapByPart(AiMovementPart.class.getSimpleName()) != null){

            // Loops through all AIMovementParts to get IDS
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(AiMovementPart.class.getSimpleName()).entrySet()){

                // process specific movingParts
                AiMovementPart aiMovementPart = (AiMovementPart) world.getMapByPart(AiMovementPart.class.getSimpleName()).get(entry.getKey());
                MovingPart movingPart = (MovingPart) world.getMapByPart(MovingPart.class.getSimpleName()).get(entry.getKey());
                PositionPart positionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey());

                // Level 1 logic
                if (aiMovementPart.getLevel() == 1){
                    simpleMovement.forward(movingPart);
                }

                // Level 2 logic
                if (aiMovementPart.getLevel() == 2){
                    simpleMovement.backward(movingPart);
                }

                // Level 3 logic
                if (aiMovementPart.getLevel() == 3){
                    simpleMovement.random(movingPart);
                }

                // Level 4 logic
                if (aiMovementPart.getLevel() == 4){
                    pathfinding.findTarget(movingPart,positionPart, gameData);
                }
                // Level 5 aim with mouse
                if (aiMovementPart.getLevel() == 5){
                    pathfinding.findMouse(positionPart, gameData);
                }
            }
        }
    }
}
