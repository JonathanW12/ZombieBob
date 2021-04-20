package dk.sdu.mmmi.cbse.common.data.entityammoparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IWeapon {
    public String getIdleSpriteName();
    public String getAttackAnimationName();
    public String getWalkAnimationName();
    public Entity createWeapon(GameData gameData, World world, Entity owner);
    
    public int getAttackAnimationFrameCount();
    public int getWalkAnimationFrameCount();
    public float getAttackAnimationFrameDuration();
    public float getWalkAnimationFrameDuration();
    public int getLevel();
    
    public void setAttackAnimationFrameCount(int attackAnimationFrameCount);
    public void setWalkAnimationFrameCount(int walkAnimationFrameCount);
    public void setAttackAnimationFrameDuration(float attackAnimationFrameDuration);
    public void setWalkAnimationFrameDuration(float walkAnimationFrameDuration);
}
