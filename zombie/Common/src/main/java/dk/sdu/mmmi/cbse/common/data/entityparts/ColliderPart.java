/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author phili
 * 
 */
public class ColliderPart implements EntityPart {
    private ArrayList<UUID> collidingEntities = new ArrayList<UUID>();
    
    private ArrayList<ShapePoint> shapePoints = new ArrayList<ShapePoint>(); 
    private float radius = 0;
    
    
    public ColliderPart(float width, float height){
            float a = height/2;
            float b = width/2;
            float c = (float)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
            float B = (float)Math.acos(a/c);
            this.shapePoints.add(new ShapePoint(c, -B));
            this.shapePoints.add(new ShapePoint(c,B));
            this.shapePoints.add(new ShapePoint(c,(float)Math.PI - B));
            this.shapePoints.add(new ShapePoint(c,(float)Math.PI + B));
        
        if(width <= height){
            this.radius = height;
        } else {
            this.radius = width;
        } 
    }
    
    public ColliderPart(){
        
    }
    
    public void addShapePoint(float distance, float relativeRadians){
        if(distance > radius){
            this.radius = distance; 
        }
        this.shapePoints.add(new ShapePoint(distance, relativeRadians));
    }
    
    public ArrayList<UUID> getCollidingEntities(){
        return this.collidingEntities;
    }

    public float getRadius() {
        return this.radius;
    }
    
    public ArrayList<ShapePoint> getShapePoints(){
        return this.shapePoints;
    }
    
    public ArrayList<Vector2> getCornerVecs(PositionPart position){
        ArrayList<Vector2> corners = new ArrayList<>();
    
        for(int i = 0; i < this.shapePoints.size(); i++){
            ColliderPart.ShapePoint shapePoint1 = this.shapePoints.get(i);
            
            float x = Math.round(position.getX() + shapePoint1.distance * ((float)Math.cos(position.getRadians() + shapePoint1.relRadians)));
            float y = Math.round(position.getY() + shapePoint1.distance * ((float)Math.sin(position.getRadians() + shapePoint1.relRadians)));
            
            corners.add(new Vector2(x,y));
        }
        return corners; 
    }
    
    public static ArrayList<Vector2> getSideVecs(ArrayList<Vector2> corners){
        ArrayList<Vector2> sides = new ArrayList<>();
    
        for(int i = 0; i < corners.size(); i++){
            
            float sideVecX = corners.get(i).x - corners.get((i+1)%corners.size()).x;
            float sideVecY = corners.get(i).y - corners.get((i+1)%corners.size()).y;
            sides.add(new Vector2(sideVecX, sideVecY));
        }
        return sides;
    }
    
    public class ShapePoint{
        public float distance;
        public float relRadians;
        /**
         * 
         * @param distance the shape point's distance from the shape center
         * @param relRadians the points rotation relative to the shapes rotation
         */
        protected ShapePoint(float distance, float relRadians){
            this.distance = distance;
            this.relRadians = relRadians;
        }
    }
    
    public static class Vector2 {
    public float x;
    public float y;
    
    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
        
    }
    }
}
    
    
    
    
//    public class Shape{
//        private ArrayList<ShapePoint> cornerVecs = new ArrayList<>();
//        private ArrayList<ShapePoint> sideVecs = new ArrayList<>();
//        
//        private ArrayList<ShapePoint> shapePoints = new ArrayList<ShapePoint>();
//        
//        protected Shape(PositionPart position){
//            float a = height/2;
//            float b = width/2;
//            float c = (float)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
//            float B = (float)Math.acos(a/c);
//            this.cornerVecs.add(new ShapePoint((position.getRadians()) -  B, c, position));
//            this.cornerVecs.add(new ShapePoint((position.getRadians())  + B, c, position));
//            this.cornerVecs.add(new ShapePoint((position.getRadians()) - B + (float)Math.PI, c, position));
//            this.cornerVecs.add(new ShapePoint((position.getRadians()) + B + (float)Math.PI, c, position));
//            
//            
//            //måske man bare kan få fat i en vector med en get metode istedet for, at en array skal fyldes op hver gang..
//            for(int i = 0; i < 4; i++ ){
//                this.sideVecs.add(new ShapePoint(
//                        Math.abs(cornerVecs.get(i).x - this.cornerVecs.get((i + 1) % 4).x),
//                        Math.abs(cornerVecs.get(i).y - this.cornerVecs.get((i + 1) % 4).y)));
//            }
//        }
//        public ArrayList<ShapePoint> getSideVertices(){
//            return this.sideVecs;
//        }
//        
//        public ArrayList<ShapePoint> getCornerVertices(){
//            return this.cornerVecs;
//        }
//        
//    }
    
    
    

