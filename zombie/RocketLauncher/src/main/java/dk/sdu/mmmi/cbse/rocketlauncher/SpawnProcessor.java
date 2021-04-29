package dk.sdu.mmmi.cbse.rocketlauncher;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService{

    private boolean spawnedWeapon = false;
    private RocketLauncherCreator rocketLauncherCreator = new RocketLauncherCreator();

    @Override
    public void process(GameData gameData, World world) {

        if (gameData.getLevelInformation().getCurrentLevel() == 0 && !spawnedWeapon){
            rocketLauncherCreator.spawnRocketLauncher(gameData,world);
            spawnedWeapon = true;
        }

    }
}
