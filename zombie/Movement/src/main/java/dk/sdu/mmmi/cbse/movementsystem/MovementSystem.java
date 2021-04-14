package dk.sdu.mmmi.cbse.movementsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

import java.util.Map;
import java.util.UUID;

@ServiceProvider(service = IPostEntityProcessingService.class)
public class MovementSystem implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart("MovingPart").entrySet()){

            MovingPart movingPart = (MovingPart) entry.getValue();
            PositionPart positionPart = (PositionPart) world.getMapByPart("PositionPart").get(entry.getKey());

                float x = positionPart.getX();
                float y = positionPart.getY();
                float radians = positionPart.getRadians();
                float dt = gameData.getDelta();

                // Movement
                float dx = movingPart.getDx();
                float dy = movingPart.getDy();
                boolean left = movingPart.isLeft();
                boolean right = movingPart.isRight();
                boolean up = movingPart.isUp();
                boolean down =  movingPart.isDown();
                float movementSpeed = movingPart.getMovementSpeed();

                if (left) {
                    //radians += rotationSpeed * dt;
                    movingPart.setDx(-movementSpeed);
                }

                if (right) {
                    //radians -= rotationSpeed * dt;
                    movingPart.setDx(movementSpeed);
                }

                // accelerating
                if (up) {
                    movingPart.setDy(movementSpeed);
                }

                if (down) {
                    movingPart.setDy(-movementSpeed);
                }

                if (!up && !down){
                    movingPart.setDy(0);
                }
                if (!left && !right){
                    movingPart.setDx(0);
                }

                // set position
                x += dx;
                y += dy;

                if (x > gameData.getDisplayWidth()) {
                    x = 0;
                } else if (x < 0) {
                    x = gameData.getDisplayWidth();
                }

                if (y > gameData.getDisplayHeight()) {
                    y = 0;
                } else if (y < 0) {
                    y = gameData.getDisplayHeight();
                }

                positionPart.setX(x);
                positionPart.setY(y);

                positionPart.setRadians(radians);
            }
        }
    }
