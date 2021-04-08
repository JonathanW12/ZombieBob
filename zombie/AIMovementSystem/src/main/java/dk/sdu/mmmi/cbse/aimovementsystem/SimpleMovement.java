package dk.sdu.mmmi.cbse.aimovementsystem;

import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;

public class SimpleMovement {
    public void forward(MovingPart movingPart){
        movingPart.setUp(true);
        movingPart.setDown(false);
        movingPart.setRight(false);
        movingPart.setLeft(false);
    }

    public void backward(MovingPart movingPart){
        movingPart.setDown(true);
        movingPart.setUp(false);
        movingPart.setRight(false);
        movingPart.setLeft(false);
    }

    public void random(MovingPart movingPart){
        double random =  Math.random()*100;
        if (random > 50){
            movingPart.setUp(true);
            movingPart.setLeft(true);
            movingPart.setDown(false);
        } else {
            movingPart.setUp(false);
            movingPart.setDown(true);
            movingPart.setLeft(false);
            movingPart.setRight(true);
        }
    }

}
