package dk.sdu.mmmi.cbse.hashmaptesting;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.*;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;

import java.util.Map;
import java.util.UUID;

@ServiceProvider(service = IEntityProcessingService.class)
public class HashMapTestingControlSystem implements IEntityProcessingService {

    private int job = 1;
    private int iterationCount = 0;
    private int totalIterations = 0;

    @Override
    public void process(GameData gameData, World world) {

        if (job == 1) {
            Long startTime = System.nanoTime();


            // 100 % Test, iterate through all positionParts, find lootableParts and remove
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(PositionPart.class.getSimpleName()).entrySet()) {
                iterationCount++;
            }



            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            job++;

            System.out.println("Test 1: HashMap, Execution Time ->" + timeElapsed / 1000000+" ms Iterations Done ->:  "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }

        if (job == 2) {
            Long startTime = System.nanoTime();

            // 1 % Test, Find players
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()) {
                PlayerPart playerPart = (PlayerPart) entry.getValue();
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            job++;

            System.out.println("Test 2: HashMap, Execution Time ->" + timeElapsed / 1000000+" ms Iterations Done ->:  "+iterationCount);
            totalIterations += iterationCount;
            iterationCount = 0;
        }

        if (job == 3) {
            Long startTime = System.nanoTime();

            // 50 % Test, iterate through all lifeparts, change health
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(LifePart.class.getSimpleName()).entrySet()) {
                LifePart lifePart = (LifePart) entry.getValue();
                lifePart.setLife(lifePart.getLife() - 1);
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            job++;

            System.out.println("Test 3: HashMap, Execution Time ->" + timeElapsed / 1000000+" ms Iterations Done ->:  "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }

        if (job == 4) {
            Long startTime = System.nanoTime();

            // 75 % Test, iterate through all combatParts, add a WeaponInventoryPart
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(CombatPart.class.getSimpleName()).entrySet()) {
                world.addtoEntityPartMap(new WeaponInventoryPart(2),entry.getKey());
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            job++;

            System.out.println("Test 4: HashMap, Execution Time ->" + timeElapsed / 1000000+" ms Iterations Done ->:  "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }

        if (job == 5) {
            Long startTime = System.nanoTime();

            // 100 % Test, iterate through all positionParts, find lootableParts and remove
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(PositionPart.class.getSimpleName()).entrySet()) {

                if (world.getMapByPart(LootablePart.class.getSimpleName()).get(entry.getKey()) != null){
                    world.getMapByPart(LootablePart.class.getSimpleName()).remove(entry.getKey());
                }
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            job++;

            System.out.println("Test 5: HashMap, Execution Time ->" + timeElapsed / 1000000+" ms Iterations Done ->:  "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
            System.out.println("5/5 tests done, total iterations -> "+totalIterations);
            System.out.println();
        }
    }
}

