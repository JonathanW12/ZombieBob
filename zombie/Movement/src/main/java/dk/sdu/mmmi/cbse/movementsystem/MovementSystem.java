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
                float vec = (float) sqrt(dx * dx + dy * dy);
                if (up) {
                    movingPart.setDx(dx += cos(radians) * acceleration * dt);
                    movingPart.setDy(dy += sin(radians) * acceleration * dt);
                }

                else if (down) {
                    
                    movingPart.setDx(dx -= cos(radians) * acceleration * dt);
                    movingPart.setDy(dy -= sin(radians) * acceleration * dt);
                }
                
                if(up){
                    if (vec > maxSpeed ) {
                    movingPart.setDx((float)cos(radians)*(maxSpeed-1));
                    movingPart.setDy((float)sin(radians)*(maxSpeed-1));
                    }
                }
                else if(down){
                    if (vec > maxSpeed ) {
                    movingPart.setDx((float)cos(radians)*(maxSpeed-1)*-1);
                    movingPart.setDy((float)sin(radians)*(maxSpeed-1)*-1);
                    }
                }
               
                
                // deccelerating (currently modelling friction of walking ie. no friction when walking (no air friction))
                if(!up && !down || left || right){
                    if (vec > 0) {
                        movingPart.setDx(dx -= (dx / vec) * deceleration * dt);
                        movingPart.setDy(dy -= (dy / vec) * deceleration * dt);
                    }
                }


                // set position
                x += movingPart.getDx() * dt;
                y += movingPart.getDy() * dt;


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

