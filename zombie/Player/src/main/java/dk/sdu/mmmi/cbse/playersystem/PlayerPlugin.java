package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.*;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.util.Map;
import java.util.UUID;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class PlayerPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {

        // Add player to the world
        createPlayer(gameData, world);

    }


    private void createPlayer(GameData gameData, World world) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        
        
        
                
        Entity playerGun = new Entity();
        
        
        Entity player = new Entity();

        // TESTING THE HASHMAP, created entity got ID and linked all parts to ID.
        AnimationPart animationPart = new AnimationPart(true);
        animationPart.addAnimation("walk", "PlayerWalk", 4, 0.2f);
        animationPart.setCurrentAnimation("walk");
        
        world.addtoEntityPartMap(new VisualPart("playerIdle", 80, 80), player);
        world.addtoEntityPartMap(animationPart, player);
        world.addtoEntityPartMap(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed),player);
        world.addtoEntityPartMap(new PositionPart(x, y, radians),player);
        world.addtoEntityPartMap(new PlayerPart(),player);
        world.addtoEntityPartMap(new VisualPart(10,new float[]{60f, 179f, 113f, 1f}),player);
        world.addtoEntityPartMap(new TimerPart(10),player);
        world.addtoEntityPartMap(new LifePart(100), player);

        world.addtoEntityPartMap(new CombatPart(playerGun.getUUID()), player);
        world.addtoEntityPartMap(new PlayerPart(),player);
    }

    @Override
    public void stop(GameData gameData, World world) {

        for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()){
            // for every player in world get UUID and remove everything for that UUID
            world.removeEntityParts(entry.getKey());
        }

    }

}
