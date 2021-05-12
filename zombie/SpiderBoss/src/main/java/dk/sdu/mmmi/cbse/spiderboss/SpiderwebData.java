package dk.sdu.mmmi.cbse.spiderboss;

public class SpiderwebData {
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
    private static SpiderwebData instance;

    private SpiderwebData() {
        visualPartName = "web";
        idleSpriteName = "spiderIdle";
        attackAnimationName = "spiderAttack"; // SPIDER ATTACK
        walkAnimationName = "spiderWalk";
        attackAnimationFrameCount = 1;
        walkAnimationFrameCount = 4;
        damage = 100;
        range = 800f;
        fireRate = 2f;
        attackAnimationFrameDuration = 0.3f;
        walkAnimationFrameDuration = 0.3f;
        shootingSoundFileName = "gun-shooting.wav"; // TO BE CHANGED TO SPIDER SOUND
    }

    public static SpiderwebData getInstance() {
        if (instance == null) {
            instance = new SpiderwebData();
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
