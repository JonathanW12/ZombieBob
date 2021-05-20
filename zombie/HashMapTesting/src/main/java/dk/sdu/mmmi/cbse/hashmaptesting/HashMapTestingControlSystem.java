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

    private int jobsDone = 0;
    private int iterationCount = 0;
    private int totalIterations = 0;

    @Override
    public void process(GameData gameData, World world) {

        if (jobsDone == 0) {
            Long startTime = System.nanoTime();

            // 1 % Test, Find players
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(PlayerPart.class.getSimpleName()).entrySet()) {
                PlayerPart playerPart = (PlayerPart) entry.getValue();
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            jobsDone++;

            System.out.println("Execution time in milliseconds HashMap, Players 1%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }

        if (jobsDone == 1) {
            Long startTime = System.nanoTime();

            // 25 % Test, iterate through all weaponParts, change damage
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(WeaponPart.class.getSimpleName()).entrySet()) {
                WeaponPart weaponPart = (WeaponPart) entry.getValue();
                weaponPart.setDamage(200);
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            jobsDone++;

            System.out.println("Execution time in milliseconds HashMap, mutate WeaponParts 25%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations += iterationCount;
            iterationCount = 0;
        }

        if (jobsDone == 2) {
            Long startTime = System.nanoTime();

            // 50 % Test, iterate through all lifeparts, change health
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(LifePart.class.getSimpleName()).entrySet()) {
                LifePart lifePart = (LifePart) entry.getValue();
                lifePart.setLife(lifePart.getLife() - 1);
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            jobsDone++;

            System.out.println("Execution time in milliseconds HashMap, mutate LifeParts 50%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }

        if (jobsDone == 3) {
            Long startTime = System.nanoTime();

            // 75 % Test, iterate through all combatParts, setAttacking true
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(CombatPart.class.getSimpleName()).entrySet()) {
                CombatPart combatPart = (CombatPart) entry.getValue();
                combatPart.setAttacking(true);
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            jobsDone++;

            System.out.println("Execution time in milliseconds HashMap, mutate CombatParts 75%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }

        if (jobsDone == 4) {
            Long startTime = System.nanoTime();

            // 100 % Test, iterate through all positionParts, setRadians
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(PositionPart.class.getSimpleName()).entrySet()) {
                PositionPart positionPart = (PositionPart) entry.getValue();
                positionPart.setRadians(2);
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            jobsDone++;

            System.out.println("Execution time in milliseconds HashMap, mutate PositionParts 100%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }

        if (jobsDone == 5) {
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

            jobsDone++;

            System.out.println("Execution time in milliseconds HashMap, find PositionParts, remove related LootableParts, 100%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
            System.out.println();
            System.out.println("Total Iteration Count: "+ totalIterations);
            System.out.println();
        }

    }


}

