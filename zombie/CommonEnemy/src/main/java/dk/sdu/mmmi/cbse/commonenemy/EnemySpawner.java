package dk.sdu.mmmi.cbse.commonenemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.Position;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.EnemyPart;
import dk.sdu.mmmi.cbse.common.services.IEnemyCreatorService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEnemyCreatorService.class)
public class EnemySpawner implements IEnemyCreatorService {
    @Override
    public void createEnemy(int health, Position position, World world) {
        float speed = 2;
        float radians = 3.1415f / 2;
        float rotationSpeed = 3;

        Entity zombie = new Entity();

        world.addtoEntityPartMap(new VisualPart("sword_sprite",80,80), zombie);
        world.addtoEntityPartMap(new MovingPart(speed,rotationSpeed), zombie);
        world.addtoEntityPartMap(new PositionPart(position.getX(), position.getY(),radians), zombie);
        world.addtoEntityPartMap(new LifePart(health), zombie);
        world.addtoEntityPartMap(new AiMovementPart(4), zombie);
        world.addtoEntityPartMap(new EnemyPart(), zombie);
        world.addtoEntityPartMap(new ColliderPart(40),zombie);
    }

    @Override
    public void createBoss() {
    }
}
