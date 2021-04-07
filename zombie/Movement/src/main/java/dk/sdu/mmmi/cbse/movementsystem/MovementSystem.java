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

import static java.lang.Math.*;

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
                float maxSpeed = movingPart.getMaxSpeed();
                float rotationSpeed = movingPart.getRotationSpeed();
                float acceleration = movingPart.getAcceleration();
                float deceleration = movingPart.getDeceleration();

                if (left) {
                    radians += rotationSpeed * dt;
                }

                if (right) {
                    radians -= rotationSpeed * dt;
                }

                // accelerating
                if (up) {
                    movingPart.setDx(dx += cos(radians) * acceleration * dt);
                    movingPart.setDy(dy += sin(radians) * acceleration * dt);
                }

                if (down) {
                    movingPart.setDx(dx -= cos(radians) * acceleration * dt);
                    movingPart.setDy(dy -= sin(radians) * acceleration * dt);
                }


                // deccelerating

                float vec = (float) sqrt(dx * dx + dy * dy);
                if (vec > 0) {
                    movingPart.setDx(dx -= (dx / vec) * deceleration * dt);
                    movingPart.setDy(dy -= (dy / vec) * deceleration * dt);
                }
                if (vec > maxSpeed) {
                    dx = (dx / vec) * maxSpeed;
                    dy = (dy / vec) * maxSpeed;
                }



                // set position
                x += dx * dt;
                y += dy * dt;


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
