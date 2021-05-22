package dk.sdu.mmmi.cbse.hashmaptesting;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.EnemyPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)
public class HashMapTestPlugin implements IGamePluginService {

    private int limit = 2500000;
    private double playerCount = limit*0.01;
    private double weaponCount = limit*0.24;
    private double enemyCount = limit*0.5;
    private double bulletCount = limit*0.25;

    @Override
    public void start(GameData gameData, World world) {
        world.clearPartMap();


        for (int i = 0; i < playerCount; i++) {
            createPlayer(world);
        }
        for (int i = 0; i < weaponCount; i++) {
            createWeapon(world);
        }
        for (int i = 0; i < enemyCount; i++) {
            createEnemy(world);
        }
        for (int i = 0; i < bulletCount; i++) {
            createBullet(world);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {

    }

    public void createPlayer(World world){
        float speed = 3;
        float rotationSpeed = 5;
        float radians = 3.1415f / 2;

        Entity Player = new Entity();

        // Important Test Parts
        world.addtoEntityPartMap(new PositionPart(10, 20, radians), Player);
        world.addtoEntityPartMap(new PlayerPart(),Player);

        world.addtoEntityPartMap(new MovingPart(speed,rotationSpeed),Player);
        world.addtoEntityPartMap(new WeaponInventoryPart(4), Player);
        world.addtoEntityPartMap(new CollectorPart(), Player);

    }
    public void createEnemy(World world){

        Entity Enemy = new Entity();

        float speed = 3;
        float rotationSpeed = 5;
        float radians = 3.1415f / 2;

        // Important Test Parts
        world.addtoEntityPartMap(new PositionPart(10, 20, radians), Enemy);
        world.addtoEntityPartMap(new EnemyPart(),Enemy);
        world.addtoEntityPartMap(new MovingPart(speed,rotationSpeed),Enemy);
        world.addtoEntityPartMap(new LifePart(5), Enemy);
        world.addtoEntityPartMap(new WeaponInventoryPart(4), Enemy);
        world.addtoEntityPartMap(new CombatPart(), Enemy);

    }
    public void createBullet(World world){

        Entity Bullet = new Entity();

        MovingPart movingPart = new MovingPart(20, 1000);
        world.addtoEntityPartMap(movingPart, Bullet);
        world.addtoEntityPartMap(new PositionPart(50, 50,3.1415f / 2) , Bullet);
        world.addtoEntityPartMap(new ProjectilePart(200),Bullet);
        world.addtoEntityPartMap(new ColliderPart(5), Bullet);
        world.addtoEntityPartMap(new DamagePart(10), Bullet);

    }
    public void createWeapon(World world){

        Entity Weapon = new Entity();

        PositionPart positionPart = new PositionPart(60, 70, 3.1415f / 2);
        world.addtoEntityPartMap(positionPart, Weapon);
        world.addtoEntityPartMap(new LootablePart(), Weapon);
        world.addtoEntityPartMap(new WeaponPart(10,20,100,1), Weapon);
        world.addtoEntityPartMap(new CombatPart(), Weapon);

    }
}
