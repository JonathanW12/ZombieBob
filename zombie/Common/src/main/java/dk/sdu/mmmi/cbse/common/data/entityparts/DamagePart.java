package dk.sdu.mmmi.cbse.common.data.entityparts;

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
