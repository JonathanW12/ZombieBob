package dk.sdu.mmmi.cbse.movementsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

import java.util.Map;
import java.util.UUID;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@ServiceProvider(service = IPostEntityProcessingService.class)
public class MovementSystem implements IPostEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(MovingPart.class.getSimpleName()).entrySet()){

            MovingPart movingPart = (MovingPart) entry.getValue();
            PositionPart positionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey());

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
            float rotationSpeed = movingPart.getRotationSpeed();

            // Player logic differs from everyhing else
            if (world.getMapByPart(PlayerPart.class.getSimpleName()).get(entry.getKey()) != null){

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

            // for everything else
            } else {
                if (left) {
                    radians += rotationSpeed * dt;
                }

                if (right) {
                    radians -= rotationSpeed * dt;
                }

                if (up) {
                    movingPart.setDx((float)cos(radians) * movementSpeed);
                    movingPart.setDy((float)sin(radians) * movementSpeed);
                }
                if (down) {
                    movingPart.setDx((float)cos(radians) * -movementSpeed);
                    movingPart.setDy((float)sin(radians) * -movementSpeed);
                }
            }

            // set position
            x += dx;
            y += dy;

            ProjectilePart projectilePart = null;

            if (world.getMapByPart(ProjectilePart.class.getSimpleName()) != null){
                projectilePart = (ProjectilePart) world.getMapByPart(ProjectilePart.class.getSimpleName()).get(entry.getKey());
            }

            if (projectilePart == null){
                /*
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

                 */

                if (x > gameData.getDisplayWidth()) {
                    x = gameData.getDisplayWidth();
                } else if (x < 0) {
                    x = 0;
                }

                if (y > 650) {
                    y = 650;
                } else if (y < 0) {
                    y = 0;
                }
            } else {
                LifePart lifePart = (LifePart) world.getMapByPart(LifePart.class.getSimpleName()).get(entry.getKey());
                if (x > gameData.getDisplayWidth() || x == 0){
                    lifePart.setDead(true);
                }
                if (y > 650 || y == 0) {
                    lifePart.setDead(true);
                }
            }



            positionPart.setX(x);
            positionPart.setY(y);

            positionPart.setRadians(radians);
        }
    }
}
