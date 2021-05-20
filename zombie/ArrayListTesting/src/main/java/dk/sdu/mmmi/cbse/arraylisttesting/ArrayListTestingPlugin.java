package dk.sdu.mmmi.cbse.arraylisttesting;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.EnemyPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)
public class ArrayListTestingPlugin implements IGamePluginService {

    private int limit = 5000000;
    private double playerCount = limit*0.01;
    private double weaponCount = limit*0.24;
    private double enemyCount = limit*0.5;
    private double bulletCount = limit*0.25;

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < playerCount; i++) {
            createPlayer(world);
            playerCount--;
        }
        for (int i = 0; i < weaponCount; i++) {
            createWeapon(world);
            weaponCount--;
        }
        for (int i = 0; i < enemyCount; i++) {
            createEnemy(world);
            enemyCount--;
        }
        for (int i = 0; i < bulletCount; i++) {
            createBullet(world);
            bulletCount--;
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

        Player.add(new PositionPart(10, 20, radians));
        Player.add(new MovingPart(speed,rotationSpeed));
        Player.add(new PlayerPart());
        Player.add(new WeaponInventoryPart(4));
        Player.add(new CollectorPart());

        world.addEntity(Player);

    }
    public void createEnemy(World world){

        Entity Enemy = new Entity();

        float speed = 3;
        float rotationSpeed = 5;
        float radians = 3.1415f / 2;

        Enemy.add(new PositionPart(10, 20, radians));
        Enemy.add(new MovingPart(speed,rotationSpeed));
        Enemy.add(new EnemyPart());
        Enemy.add(new LifePart(5));
        Enemy.add(new CombatPart());
        Enemy.add(new WeaponInventoryPart(4));

        world.addEntity(Enemy);

    }
    public void createBullet(World world){

        Entity Bullet = new Entity();

        Bullet.add(new MovingPart(20, 1000));
        Bullet.add(new PositionPart(50, 50,3.1415f / 2));
        Bullet.add(new ProjectilePart(200));
        Bullet.add(new ColliderPart(5));
        Bullet.add(new DamagePart(10));

        world.addEntity(Bullet);

    }
    public void createWeapon(World world){

        Entity Weapon = new Entity();

        Weapon.add(new PositionPart(60, 70, 3.1415f / 2));
        Weapon.add(new LootablePart());
        Weapon.add(new CombatPart());
        Weapon.add(new WeaponPart(10,20,100,1));

        world.addEntity(Weapon);

    }
}
