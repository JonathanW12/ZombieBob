/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import java.util.ArrayList;

/**
 *
 * @author phili
 */
public class BoxCollisionChecker {
    
    public boolean areColliding(ColliderPart collider1, PositionPart position1, ColliderPart collider2, PositionPart position2){
        Shape shape1 = new Shape(collider1, position1);
        Shape shape2 = new Shape(collider2, position2);
        
        //create a shadow for each side vector
        for(Point vec: shape1.getSideVertices()){
            //check for division by;
            float normX;
            try{
                normX = - vec.y;
            } catch(ArithmeticException e){
                normX = -1;
            }
            
            float normY = vec.x;
            
            float[] maxNmin1 = getMaxAndMinProjections(shape1.getCornerVertices(), normX, normY);
            float[] maxNmin2 = getMaxAndMinProjections(shape2.getCornerVertices(), normX, normY);
           
            
            if(!(maxNmin2[0]>=maxNmin1[1] && maxNmin1[0] >= maxNmin2[1])){
                return false;
            };
        }
        return true;
    }
    //index 0 is max projection and index 1 is min projection
    public float[] getMaxAndMinProjections(ArrayList<Point> corners, float normX, float normY){
        float[] maxNmin = new float[2];
            maxNmin[0] = Float.MIN_VALUE;
            maxNmin[1] = Float.MAX_VALUE;
            for(Point corner: corners){
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
    
    public static class Shape{
        private ArrayList<Point> cornerVecs = new ArrayList<>();
        private ArrayList<Point> sideVecs = new ArrayList<>();
        
        protected Shape(ColliderPart collider, PositionPart position){
            float a = collider.getHeight()/2;
            float b = collider.getWidth()/2;
            float c = (float)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
            float B = (float)Math.acos(a/c);
            this.cornerVecs.add(new Point((position.getRadians()) -  B, c, position));
            this.cornerVecs.add(new Point((position.getRadians())  + B, c, position));
            this.cornerVecs.add(new Point((position.getRadians()) - B + (float)Math.PI, c, position));
            this.cornerVecs.add(new Point((position.getRadians()) + B + (float)Math.PI, c, position));
            
            
            //måske man bare kan få fat i en vector med en get metode istedet for, at en array skal fyldes op hver gang..
            for(int i = 0; i < 4; i++ ){
                this.sideVecs.add(new Point(
                        cornerVecs.get(i).x - this.cornerVecs.get((i + 1) % 3).x,
                        cornerVecs.get(i).y - this.cornerVecs.get((i + 1) % 3).y));
            }
        }
        public ArrayList<Point> getSideVertices(){
            return this.sideVecs;
        }
        
        public ArrayList<Point> getCornerVertices(){
            return this.cornerVecs;
        }
        
    }
    
    static class Point{
        float x;
        float y;
        
        protected Point(float angle, float hypotenuse, PositionPart position){
            this.x = Math.round(position.getX() + hypotenuse * (float)Math.cos(angle));
            this.y = Math.round(position.getY() + hypotenuse * (float)Math.sin(angle));
        }
        protected Point(float x, float y){
            this.x = x;
            this.y = y;
        }
    }
    
}
