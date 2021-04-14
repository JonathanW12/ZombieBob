package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.util.Map;
import java.util.UUID;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class)})
public class PlayerControlSystem implements IEntityProcessingService {

    private int cooldown = 250;
    private long shootDelay = System.currentTimeMillis();

    @Override
    public void process(GameData gameData, World world) {

        // all playerParts
        if (world.getMapByPart(PlayerPart.class.getSimpleName()) != null){
            for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()){

                // entity parts on player
                PositionPart positionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey());
                MovingPart movingPart = (MovingPart) world.getMapByPart(MovingPart.class.getSimpleName()).get(entry.getKey());
                VisualPart visualPart = (VisualPart) world.getMapByPart(VisualPart.class.getSimpleName()).get(entry.getKey());
                //LifePart lifePart = (LifePart) world.getMapByPart(LifePart.class.getSimpleName()).get(entry.getKey());

                // Mouse event testing
                if (gameData.getMouse().isLeftClick() && shootDelay <= System.currentTimeMillis()){
                    shootDelay = System.currentTimeMillis()+cooldown;
                    System.out.println("Left Click");
                    //System.out.println("X: "+gameData.getMouse().getX()+" Y: "+gameData.getMouse().getY());
                }
                if (gameData.getMouse().isRightClick()){
                    System.out.println("Right Click");
                }
                if (gameData.getMouse().isMiddleClick()){
                    System.out.println("Middle Click");
                }
                if (gameData.getMouse().getScroll() == -1){
                    System.out.println("Scroll Up");
                    gameData.getMouse().setScroll(0);
                }
                if (gameData.getMouse().getScroll() == 1){
                    System.out.println("Scroll Down");
                    gameData.getMouse().setScroll(0);
                }

                // movement
                movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
                movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
                movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
                movingPart.setDown(gameData.getKeys().isDown(GameKeys.DOWN));

                // update
                updateShape(visualPart, positionPart);
            }
        }
    }

    private void updateShape(VisualPart entity, PositionPart position) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = position;
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
