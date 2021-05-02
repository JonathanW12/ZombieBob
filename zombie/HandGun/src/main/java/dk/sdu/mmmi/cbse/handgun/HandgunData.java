package dk.sdu.mmmi.cbse.handgun;

public class HandgunData {
    private final String visualPartName;
    private final String idleSpriteName;
    private final String attackAnimationName;
    private final String walkAnimationName;
    private final int attackAnimationFrameCount;
    private final int walkAnimationFrameCount;
    private final int damage;
    private final float range;
    private final float fireRate;
    private final float attackAnimationFrameDuration;
    private final float walkAnimationFrameDuration;
    private final String shootingSoundFileName;
    private static HandgunData instance;
    
    private HandgunData() {
        visualPartName = "projectile";
        idleSpriteName = "PlayerGun1";
        attackAnimationName = "PlayerShootGun";
        walkAnimationName = "PlayerWalkGun";
        attackAnimationFrameCount = 2;
        walkAnimationFrameCount = 2;
        damage = 80;
        range = 800f;
        fireRate = 0.3f;
        attackAnimationFrameDuration = 0.02f;
        walkAnimationFrameDuration = 0.2f;
        shootingSoundFileName = "gun-shooting.wav";
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
    
    public int getDamage() {
        return damage;
    }
    
    public float getRange() {
        return range;
    }
    
    public float getFireRate() {
        return fireRate;
    }
    
    public float getAttackAnimationFrameDuration() {
        return attackAnimationFrameDuration;
    }
    
    public float getWalkAnimationFrameDuration() {
        return walkAnimationFrameDuration;
    }
    
    public String getShootingSoundFileName() {
        return shootingSoundFileName;
    }
    
}
