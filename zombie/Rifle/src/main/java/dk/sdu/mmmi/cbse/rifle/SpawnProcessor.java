package dk.sdu.mmmi.cbse.rifle;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService{

    private boolean spawnedWeapon = false;
    private RifleCreator rifleCreator = new RifleCreator();

    @Override
    public void process(GameData gameData, World world) {

        if (gameData.getLevelInformation().getCurrentLevel() == 0 && !spawnedWeapon){
            rifleCreator.spawnRifleData(gameData,world);
            spawnedWeapon = true;
        }

    }
}
