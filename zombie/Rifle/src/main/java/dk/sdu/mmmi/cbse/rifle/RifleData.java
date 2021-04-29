package dk.sdu.mmmi.cbse.rifle;

public class RifleData {
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
    private static RifleData instance;

    private RifleData() {
        visualPartName = "Ammobox";
        idleSpriteName = "PlayerUzi";
        attackAnimationName = "PlayerShootRifle";
        walkAnimationName = "PlayerWalkRifle";
        attackAnimationFrameCount = 1;
        walkAnimationFrameCount = 2;
        damage = 100;
        range = 600f;
        fireRate = 0.15f;
        attackAnimationFrameDuration = 0.03f;
        walkAnimationFrameDuration = 0.2f;
    }

    public static RifleData getInstance() {
        if (instance == null) {
            instance = new RifleData();
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
}
