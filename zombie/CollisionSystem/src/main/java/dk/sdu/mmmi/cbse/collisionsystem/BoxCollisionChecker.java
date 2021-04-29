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
    
    
//    public boolean areColliding2(ColliderPart collider1, PositionPart position1, ColliderPart collider2, PositionPart position2){
//        
//        ArrayList<Point> corners = collider1.getShape(position1).getCornerVertices();
//        ArrayList<Float> lengths = new ArrayList<>();
//        for(int i = 0; i < corners.size(); i++){
//            float vx = corners.get(i).x - position2.getX();
//            float vy = corners.get(i).y - position2.getY();
//            float len = (float)Math.sqrt(Math.pow(vx,2)+ Math.pow(vy,2));
//            if(len < collider2.getRadius()){
//                return true;
//            }
//        }
//        float[] maxNmin1;
//        float[] maxNmin2 = new float[2];
//        
//        //create a shadow for each side vector
//        for(ColliderPart.Point vec: collider1.getShape(position1).getSideVertices()){
//            //check for division by;
//            float normX = - vec.y;
//            float normY = vec.x;
//            
//            float maxProjection = ((position2.getX() + collider2.getRadius())*normX) + ((position2.getY()+collider2.getRadius())*normY);
//            float minProjection = ((position2.getX() - collider2.getRadius())*normX) + ((position2.getY() - collider2.getRadius())*normY);
//            maxNmin1 = getMaxAndMinProjections(collider1.getShape(position1).getCornerVertices(), normX, normY);
//            maxNmin2[0] = maxProjection;
//            maxNmin2[1] = minProjection;
//            
//           
//            
//            if(!(maxNmin2[0]>=maxNmin1[1] && maxNmin1[0] >= maxNmin2[1])){
//                return false;
//            };
//        }
//        
//        float normX = position2.getX() - position1.getX();
//        float normY = position2.getY() - position1.getY();
//        //System.out.println("2max: " + maxNmin2[0] + ", 2min: " + maxNmin2[1]);
//        System.out.println("2max: " + maxNmin2[0] + ", 2min: " + maxNmin2[1]);
//        return true;
//    }
    
//    public boolean circleCollidingWithPoly(ColliderPart collider1, PositionPart position1, ColliderPart collider2, PositionPart position2){
//        float cx = position2.getX();
//        float cy = position2.getY();
//        
//        ArrayList<Point> corners = collider1.getShape(position1).getCornerVertices();
//        ArrayList<Float> lengths = new ArrayList<>();
//        for(int i = 0; i < corners.size(); i++){
//            float vx = corners.get(i).x - position2.getX();
//            float vy = corners.get(i).y - position2.getY();
//            float len = (float)Math.sqrt(Math.pow(vx,2)+ Math.pow(vy,2));
//            //System.out.println("len: "+ len + " radius: " + collider2.getRadius());
//            if(len < collider2.getRadius()){
//                return true;
//            }
//            lengths.add(len);
//        }
//        for(int i = 0; i < corners.size(); i++){
//            //point 1
//            float x1 = corners.get(i).x;
//            float y1 = corners.get(i).y;
//            
//            //point 2
//            float x2 = corners.get((i + 1) % corners.size()).x;
//            float y2 = corners.get((i + 1) % corners.size()).y;
//            
//            float dot = ( ((cx-x1)*(x2-x1)) + ((cy-y1)*(y2-y1)) ) / (float)Math.pow(lengths.get(i),2);
//            
//            float closestX = x1 + (dot * (x2-x1));
//            float closestY = y1 + (dot * (y2-y1));
//            
//            float buffer = 3f;
//            
//            float p2pLen = (float)Math.sqrt(Math.pow(x1-x2,2)+ Math.pow(y1-y2,2));
//            
//            boolean onSegment = lengths.get(i) + lengths.get((i+1)%corners.size()) - buffer <= p2pLen;
//                                
//            if (!onSegment){
//                
//                continue;
//            }
//            System.out.println("on segment");
//            
//            float distX = closestX - cx;
//            float distY = closestY - cy;
//            float distance = (float)Math.sqrt( (distX*distX) + (distY*distY) );
//            
//            if(distance <= collider2.getRadius()){
//                return true;
//            }
//        }
//        return false;
//    }
    //index 0 is max projection and index 1 is min projection
    
}
