/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart.ShapePoint;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart.Vector2;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import java.util.ArrayList;

/**
 *
 * @author phili
 */
public class BoxCollisionChecker {
    
    

    public boolean areColliding(ColliderPart collider1, PositionPart position1, ColliderPart collider2, PositionPart position2){
        
        //create a shadow for each side vector
        ArrayList<ShapePoint> shape1Points = collider1.getShapePoints();
        ArrayList<ShapePoint> shape2Points = collider2.getShapePoints();
        
        
        ArrayList<Vector2> shape1Corners = collider1.getCornerVecs(position1);
        ArrayList<Vector2> shape2Corners = collider2.getCornerVecs(position2);
        
        ArrayList<Vector2> shape1sides = ColliderPart.getSideVecs(shape1Corners);
        ArrayList<Vector2> shape2sides = ColliderPart.getSideVecs(shape2Corners);
        
        
        for(Vector2 vec: shape2sides){
            //check for division by;
            float normX = - vec.y;
            float normY = vec.x;
            
            float[] maxNmin1 = getMaxAndMinProjections(shape2Corners, normX, normY);
            float[] maxNmin2 = getMaxAndMinProjections(shape1Corners, normX, normY);
           
            
            if(!(maxNmin2[0]>=maxNmin1[1] && maxNmin1[0] >= maxNmin2[1])){
                return false;
            };
        }
        for(Vector2 vec: shape1sides){
            //check for division by;
            float normX = - vec.y;
            float normY = vec.x;
            
            float[] maxNmin1 = getMaxAndMinProjections(shape1Corners, normX, normY);
            float[] maxNmin2 = getMaxAndMinProjections(shape2Corners, normX, normY);
           
            
            if(!(maxNmin2[0]>=maxNmin1[1] && maxNmin1[0] >= maxNmin2[1])){
                return false;
            };
        }
        return true;
    }
    
    public float[] getMaxAndMinProjections(ArrayList<Vector2> corners, float normX, float normY){
        float[] maxNmin = new float[2];
            maxNmin[0] = Float.MIN_VALUE;
            maxNmin[1] = Float.MAX_VALUE;
            for(Vector2 corner: corners){
                float projection = (corner.x*normX) + (corner.y*normY);
                
                if(projection > maxNmin[0]){
                    maxNmin[0] = projection;
                }
                if(projection < maxNmin[1]){
                    maxNmin[1] = projection; 
                }
            }
            return maxNmin;
    }
}
