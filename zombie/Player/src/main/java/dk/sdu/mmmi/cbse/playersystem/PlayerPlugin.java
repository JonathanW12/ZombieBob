package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.*;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.ArrayList;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class PlayerPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        createPlayerShip(gameData, world);
    }

    private void createPlayerShip(GameData gameData, World world) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Entity playerShip = new Entity();

        // TESTING THE HASHMAP, created entity got ID and linked all parts to ID.
        AnimationPart animationPart = new AnimationPart();
        animationPart.addAnimation("walk", "PlayerWalkAnimation", 2, 0.2f);
        animationPart.setCurrentAnimation("walk");
        
        //WeaponInventoryPart. Seems too messy. Maybe change
        WeaponInventoryPart weaponInventoryPart = new WeaponInventoryPart(2);
        
        world.addtoEntityPartMap(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed),playerShip);
        world.addtoEntityPartMap(new PositionPart(x, y, radians),playerShip);
        world.addtoEntityPartMap(new PlayerPart(),playerShip);
        world.addtoEntityPartMap(new VisualPart("PlayerWalk1", 80, 80), playerShip);
        world.addtoEntityPartMap(animationPart, playerShip);
        world.addtoEntityPartMap(weaponInventoryPart, playerShip);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        //world.removeEntity(player);
    }

}
