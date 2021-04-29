package dk.sdu.mmmi.cbse.handgun;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LootablePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class SpawnProcessor implements IEntityProcessingService {
    
    private HandgunCreator handgunCreator = new HandgunCreator();
    private boolean spawnedWeapon = false;
    
    @Override
    public void process(GameData gameData, World world) {

        if (gameData.getLevelInformation().getCurrentLevel() == 0 && !spawnedWeapon){
            handgunCreator.spawnGun(gameData,world);
            spawnedWeapon = true;
        }
    }
}
