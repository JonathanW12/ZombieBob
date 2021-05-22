package dk.sdu.mmmi.cbse.arraylisttesting;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class ArrayListTestingControlSystem implements IEntityProcessingService {

    private int job = 1;
    private int iterationCount = 0;
    private int totalIterations = 0;

    @Override
    public void process(GameData gameData, World world) {

        if (job == 1) {
            Long startTime = System.nanoTime();

            // Iterate over all entities but do nothing
            for (Entity entity: world.getEntities()){
                iterationCount++;
            }



            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            job++;

            System.out.println("Test 1: ArrayList, Execution Time ->" + timeElapsed / 1000000+" ms Iterations Done ->:  "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }
        if (job == 2) {
            Long startTime = System.nanoTime();

            // 1 % Test, Find players
            for (Entity entity : world.getEntities()) {
                if (entity.getPart(PlayerPart.class) != null) {
                    PlayerPart playerPart = entity.getPart(PlayerPart.class);
                }
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            job++;

            System.out.println("Test 2: ArrayList, Execution Time ->" + timeElapsed / 1000000+" ms Iterations Done ->:  "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }
        if (job == 3) {
            Long startTime = System.nanoTime();

            // 50 % Test, iterate through all lifeparts, change health
            for (Entity entity : world.getEntities()) {
                if (entity.getPart(LifePart.class) != null) {
                    LifePart lifePart = entity.getPart(LifePart.class);
                    lifePart.setLife(lifePart.getLife()-1);
                }
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            job++;

            System.out.println("Test 3: ArrayList, Execution Time ->" + timeElapsed / 1000000+" ms Iterations Done ->:  "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }
        if (job == 4) {
            Long startTime = System.nanoTime();

            // 75 % Test, iterate through all combatParts, setAttacking true
            for (Entity entity : world.getEntities()) {
                if (entity.getPart(CombatPart.class) != null) {
                    entity.add(new WeaponInventoryPart(2));
                }
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            job++;

            System.out.println("Test 4: ArrayList, Execution Time ->" + timeElapsed / 1000000+" ms Iterations Done ->:  "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }

        if (job == 5) {
            Long startTime = System.nanoTime();

            // 100 % Test, iterate through all positionParts, remove lootableParts
            for (Entity entity : world.getEntities()) {
                if (entity.getPart(PositionPart.class) != null) {
                    if (entity.getPart(LootablePart.class) != null){
                        entity.remove(LootablePart.class);
                    }
                }
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            job++;

            System.out.println("Test 5: ArrayList, Execution Time ->" + timeElapsed / 1000000+" ms Iterations Done ->:  "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
            System.out.println("5/5 tests done, total iterations -> "+totalIterations);
        }

    }
}
