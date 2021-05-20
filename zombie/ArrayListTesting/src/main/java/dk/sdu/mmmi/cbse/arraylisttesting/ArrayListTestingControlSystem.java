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

    private int jobsDone = 0;
    private int iterationCount = 0;
    private int totalIterations = 0;

    @Override
    public void process(GameData gameData, World world) {

        if (jobsDone == 0) {
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

            jobsDone++;

            System.out.println("Execution time in milliseconds Arraylist, Players 1%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }
        if (jobsDone == 1) {
            Long startTime = System.nanoTime();

            // 25 % Test, iterate through all weaponParts, change damage
            for (Entity entity : world.getEntities()) {
                if (entity.getPart(WeaponPart.class) != null) {
                    WeaponPart weaponPart = entity.getPart(WeaponPart.class);
                    weaponPart.setDamage(200);
                }
                iterationCount++;
            }
            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            jobsDone++;

            System.out.println("Execution time in milliseconds Arraylist, mutate WeaponParts 25%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }
        if (jobsDone == 2) {
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

            jobsDone++;

            System.out.println("Execution time in milliseconds Arraylist, mutate LifeParts 50%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }
        if (jobsDone == 3) {
            Long startTime = System.nanoTime();

            // 75 % Test, iterate through all combatParts, setAttacking true
            for (Entity entity : world.getEntities()) {
                if (entity.getPart(CombatPart.class) != null) {
                    CombatPart combatPart = entity.getPart(CombatPart.class);
                    combatPart.setAttacking(true);
                }
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            jobsDone++;

            System.out.println("Execution time in milliseconds Arraylist, mutate CombatParts 75%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }

        if (jobsDone == 4) {
            Long startTime = System.nanoTime();

            // 100 % Test, iterate through all positionParts, setRadians
            for (Entity entity : world.getEntities()) {
                if (entity.getPart(PositionPart.class) != null) {
                    PositionPart positionPart = entity.getPart(PositionPart.class);
                    positionPart.setRadians(2);
                }
                iterationCount++;
            }

            Long endTime = System.nanoTime();
            Long timeElapsed = endTime - startTime;

            jobsDone++;

            System.out.println("Execution time in milliseconds Arraylist, mutate PositionParts 100%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
        }
        if (jobsDone == 5) {
            Long startTime = System.nanoTime();

            // 100 % Test, iterate through all positionParts, find lootableParts and remove
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

            jobsDone++;

            System.out.println("Execution time in milliseconds ArrayList, find PositionParts, remove related LootableParts, 100%:    ->" + timeElapsed / 1000000+" ms");
            System.out.println("Iteration Count: "+iterationCount);
            totalIterations +=iterationCount;
            iterationCount = 0;
            System.out.println();
            System.out.println("Total Iteration Count: "+ totalIterations);

        }




    }
}
