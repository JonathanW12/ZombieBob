package dk.sdu.mmmi.cbse.common.data.entitytypeparts;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

public class VisualPart implements EntityPart {

    private float radius;
    private float[] color = new float[]{240f,52f,52f,1f};
    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];

    public VisualPart(float radius,float[] color){
        this.radius = radius;
        this.color = color;
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public float[] getColor() {
        return this.color;
    }

    public void setColor(float[] c) {
        this.color = c;
    }

    @Override
    public void process(GameData gameData, EntityPart entityPart) {

    }
}
