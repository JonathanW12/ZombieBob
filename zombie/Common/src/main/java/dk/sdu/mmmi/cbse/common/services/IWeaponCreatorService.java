package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IWeaponCreatorService {
    public Entity createWeapon(GameData gameData, World world, Entity owner);
    public void spawnGun(GameData gameData, World world);
}
