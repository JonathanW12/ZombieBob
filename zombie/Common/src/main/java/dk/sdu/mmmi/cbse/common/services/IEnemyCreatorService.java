package dk.sdu.mmmi.cbse.common.services;

import com.badlogic.gdx.math.Vector2;
import dk.sdu.mmmi.cbse.common.data.Position;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.UUID;

public interface IEnemyCreatorService {
    public void createEnemy(int Health, Position position, World world);
    public void createBoss(int Health, Position position, World world);
}
