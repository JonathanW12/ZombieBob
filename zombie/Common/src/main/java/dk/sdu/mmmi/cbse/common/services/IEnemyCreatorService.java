package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.World;

import java.util.UUID;

public interface IEnemyCreatorService {
    public void createEnemy(World world);
    public void createBoss();
}
