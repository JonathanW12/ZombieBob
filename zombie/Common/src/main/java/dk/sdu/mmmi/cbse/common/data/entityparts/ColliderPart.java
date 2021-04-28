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
    private float width;
    private float height;
    private float radius;
    
    
    public ColliderPart(float width, float height){
        this.width = width;
        this.height = height;
    }
    public ColliderPart(float radius){
        this.radius = radius; 
    }
    
    public ArrayList<UUID> getCollidingEntities(){
        return this.collidingEntities;
    }

    public float getWidth() {
        return this.width; 
    }
    
    public float getHeight(){
        return this.height; 
    }

    public float getRadius() {
        return this.radius;
    }
    
    public Shape getShape(PositionPart position){
        return new Shape(position);
    }
    
    public class Shape{
        private ArrayList<Point> cornerVecs = new ArrayList<>();
        private ArrayList<Point> sideVecs = new ArrayList<>();
        
        protected Shape(PositionPart position){
            float a = height/2;
            float b = width/2;
            float c = (float)Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
            float B = (float)Math.acos(a/c);
            this.cornerVecs.add(new Point((position.getRadians()) -  B, c, position));
            this.cornerVecs.add(new Point((position.getRadians())  + B, c, position));
            this.cornerVecs.add(new Point((position.getRadians()) - B + (float)Math.PI, c, position));
            this.cornerVecs.add(new Point((position.getRadians()) + B + (float)Math.PI, c, position));
            
            
            //måske man bare kan få fat i en vector med en get metode istedet for, at en array skal fyldes op hver gang..
            for(int i = 0; i < 4; i++ ){
                this.sideVecs.add(new Point(
                        Math.abs(cornerVecs.get(i).x - this.cornerVecs.get((i + 1) % 4).x),
                        Math.abs(cornerVecs.get(i).y - this.cornerVecs.get((i + 1) % 4).y)));
            }
        }
        public ArrayList<Point> getSideVertices(){
            return this.sideVecs;
        }
        
        public ArrayList<Point> getCornerVertices(){
            return this.cornerVecs;
        }
        
    }
    
    public class Point{
        public float x;
        public float y;
        
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
