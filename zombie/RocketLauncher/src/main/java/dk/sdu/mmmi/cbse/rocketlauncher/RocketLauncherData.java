package dk.sdu.mmmi.cbse.rocketlauncher;

public class RocketLauncherData {
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
    private static RocketLauncherData instance;

    private RocketLauncherData() {
        visualPartName = "Rocket";
        idleSpriteName = "playerbazooka";
        attackAnimationName = "PlayerShootBazooka";
        walkAnimationName = "PlayerWalkBazooka";
        attackAnimationFrameCount = 1;
        walkAnimationFrameCount = 2;
        damage = 200;
        range = 500f;
        fireRate = 0.8f;
        attackAnimationFrameDuration = 0.1f;
        walkAnimationFrameDuration = 0.2f;
        shootingSoundFileName = "rocket-shooting.wav";
    }

    public static RocketLauncherData getInstance() {
        if (instance == null) {
            instance = new RocketLauncherData();
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