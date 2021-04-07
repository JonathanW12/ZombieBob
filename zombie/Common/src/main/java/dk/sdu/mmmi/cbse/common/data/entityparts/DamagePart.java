package dk.sdu.mmmi.cbse.common.data.entityparts;

/**
 * Has information about the damage, used on projectiles when they are created in the world.
 * Gets its data from the weaponPart
 *
 */

public class DamagePart implements EntityPart{

    private int damage;

    public DamagePart(int damage){
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
