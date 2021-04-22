package dk.sdu.mmmi.cbse.spawnersystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.Position;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEnemyCreatorService;
import dk.sdu.mmmi.cbse.common.services.IWeaponCreatorService;
import org.openide.util.Lookup;

public class SpawnerEntities {

    public void createZombie(int health, Position position, World world){
        Lookup.getDefault().lookup(IEnemyCreatorService.class).createEnemy(health, position, world);
    }
    public void createGun(GameData gameData, World world){
        Lookup.getDefault().lookup(IWeaponCreatorService.class).spawnGun(gameData, world);
    }
}
