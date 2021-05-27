/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.throwingknife;

/**
 *
 * @author lake
 */
public class ThrowingKnifeData {

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
    private static ThrowingKnifeData instance;

    private ThrowingKnifeData() {
        visualPartName = "Sword";
        idleSpriteName = "playerIdleKnife";
        attackAnimationName = "PlayerKnifeMove2";
        walkAnimationName = "PlayerKnifeWalking";
        attackAnimationFrameCount = 1;
        walkAnimationFrameCount = 2;
        damage = 200;
        range = 250f;
        fireRate = 1f;
        attackAnimationFrameDuration = 0.03f;
        walkAnimationFrameDuration = 0.2f;
        shootingSoundFileName = "ThrowingKnife.wav";
    }

    public static ThrowingKnifeData getInstance() {
        if (instance == null) {
            instance = new ThrowingKnifeData();
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
        return walkAnimationFrameCount;
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
