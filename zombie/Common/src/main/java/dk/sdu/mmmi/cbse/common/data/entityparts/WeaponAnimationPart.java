package dk.sdu.mmmi.cbse.common.data.entityparts;

public class WeaponAnimationPart implements EntityPart {
    private final String idleSpriteName;
    private final String attackAnimationName;
    private final String walkAnimationName;
    private int attackAnimationFrameCount;
    private int walkAnimationFrameCount;
    private float attackAnimationFrameDuration;
    private float walkAnimationFrameDuration;
    
    public WeaponAnimationPart(
            String idleSpriteName, String attackAnimationName,
            String walkAnimationName, int attackAnimationFrameCount,
            int walkAnimationFrameCount, float attackAnimationFrameDuration,
            float walkAnimationFrameDuration
    ) {
        this.idleSpriteName = idleSpriteName;
        this.attackAnimationName = attackAnimationName;
        this.walkAnimationName = walkAnimationName;
        this.attackAnimationFrameCount = attackAnimationFrameCount;
        this.walkAnimationFrameCount = walkAnimationFrameCount;
        this.attackAnimationFrameDuration = attackAnimationFrameDuration;
        this.walkAnimationFrameDuration = this.walkAnimationFrameDuration;
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
        return walkAnimationFrameCount;
    }

    public float getAttackAnimationFrameDuration() {
        return attackAnimationFrameDuration;
    }

    public float getWalkAnimationFrameDuration() {
        return walkAnimationFrameDuration;
    }

    public void setAttackAnimationFrameCount(int attackAnimationFrameCount) {
        this.attackAnimationFrameCount = attackAnimationFrameCount;
    }

    public void setWalkAnimationFrameCount(int walkAnimationFrameCount) {
        this.walkAnimationFrameCount = walkAnimationFrameCount;
    }

    public void setAttackAnimationFrameDuration(float attackAnimationFrameDuration) {
        this.attackAnimationFrameDuration = attackAnimationFrameDuration;
    }

    public void setWalkAnimationFrameDuration(float walkAnimationFrameDuration) {
        this.walkAnimationFrameDuration = walkAnimationFrameDuration;
    }
}
