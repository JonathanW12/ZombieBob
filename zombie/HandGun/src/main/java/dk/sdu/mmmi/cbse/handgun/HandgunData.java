package dk.sdu.mmmi.cbse.handgun;

public class HandgunData {
    private final String visualPartName;
    private final String idleSpriteName;
    private final String attackAnimationName;
    private final String walkAnimationName;
    private final int attackAnimationFrameCount;
    private final int walkAnimationFrameCount;
    private final float attackAnimationFrameDuration;
    private final float walkAnimationFrameDuration;
    private static HandgunData instance;
    
    private HandgunData() {
        visualPartName = "projectile";
        idleSpriteName = "PlayerGun1";
        attackAnimationName = "PlayerShootGun";
        walkAnimationName = "PlayerWalkGun";
        attackAnimationFrameCount = 2;
        walkAnimationFrameCount = 2;
        attackAnimationFrameDuration = 0.03f;
        walkAnimationFrameDuration = 0.2f;
    }
    
    public static HandgunData getInstance() {
        if (instance == null) {
            instance = new HandgunData();
        }
        
        return instance;
    }
    
    public String getVisualPartName() {
        return visualPartName;
    }
    
    public String getIdleSpriteName() {
        return idleSpriteName;
    }
    
    public String getAttackAnimationName() {
        return attackAnimationName;
    }
    
    public String getWalkAnimationName() {
        return walkAnimationName;
    }
    
    public int getAttackAnimationFrameCount() {
        return attackAnimationFrameCount;
    }
    
    public int getWalkAnimationFrameCount() {
        return  walkAnimationFrameCount;
    }
    
    public float getAttackAnimationFrameDuration() {
        return attackAnimationFrameDuration;
    }
    
    public float getWalkAnimationFrameDuration() {
        return walkAnimationFrameDuration;
    }
    
}
