package dk.sdu.mmmi.cbse.testingsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class TestingControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        System.out.println("HELLO");
    }
}
