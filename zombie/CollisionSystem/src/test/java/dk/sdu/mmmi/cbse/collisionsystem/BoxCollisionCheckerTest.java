/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.collisionsystem.BoxCollisionChecker.Point;
import dk.sdu.mmmi.cbse.collisionsystem.BoxCollisionChecker.Shape;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author phili
 */
public class BoxCollisionCheckerTest {
    
        ColliderPart collider1;
        PositionPart position1;
        ColliderPart collider2;
        PositionPart position2;
    
    public BoxCollisionCheckerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of areColliding method, of class BoxCollisionChecker.
     */
    @Test
    public void testGivenCollidingBoxesReturnTrue() {
        collider1 = new ColliderPart(10, 10);
        position1 = new PositionPart(100,100, 0);
        
        collider2 = new ColliderPart(10, 10);
        position2 = new PositionPart(105,105, 0);
        
        
        System.out.println("areColliding");
        
        BoxCollisionChecker instance = new BoxCollisionChecker();
        boolean expResult = true;
        boolean result = instance.areColliding(collider1, position1, collider2, position2);
        assertEquals(expResult, result);
    }
    
//    @Test
//    public void testGetMaxAndMinProjections(){
//        BoxCollisionChecker instance = new BoxCollisionChecker();
//        ArrayList<Point> corners = new ArrayList<>();
//        corners.add(new Point());
//        
//        
//        instance.getMaxAndMinProjections(corners, 10, 0)
//        
//        
//    }
    
    @Test
    public void testShapeCorners(){
        collider1 = new ColliderPart(10, 10);
        position1 = new PositionPart(0,0, 0);
        
        
        
        Shape shape = new Shape(collider1, position1);
        
        float corner0x = 5;
        float corner0y = -5;
        assertEquals(corner0x, shape.getCornerVertices().get(0).x, "corner x0");
        assertEquals(corner0y, shape.getCornerVertices().get(0).y,  "corner y0");
        
        float corner1x = 5;
        float corner1y = 5;
        assertEquals(corner1x, shape.getCornerVertices().get(1).x,  "corner x1");
        assertEquals(corner1y, shape.getCornerVertices().get(1).y,  "corner y1");
        
        float corner2x = -5;
        float corner2y = 5;
        assertEquals(corner2x, shape.getCornerVertices().get(2).x,  "corner x2");
        assertEquals(corner2y, shape.getCornerVertices().get(2).y,  "corner y2");
        
        float corner3x = -5;
        float corner3y = -5;
        assertEquals(corner3x, shape.getCornerVertices().get(3).x,  "corner x3");
        assertEquals(corner3y, shape.getCornerVertices().get(3).y,  "corner y3");
        
    }
    
    @Test
    public void testShapeSides(){
        collider1 = new ColliderPart(10, 10);
        position1 = new PositionPart(0,0, 0);
        
        Shape shape = new Shape(collider1, position1);
        
        float side0x = 0;
        float side0y = -10;
        assertEquals(side0x, shape.getSideVertices().get(0).x, "side x0");
        assertEquals(side0y, shape.getSideVertices().get(0).y,  "side y0");
        
        float side1x = 10;
        float side1y = 0;
        assertEquals(side0x, shape.getSideVertices().get(0).x, "side x0");
        assertEquals(side0y, shape.getSideVertices().get(0).y,  "side y0");
        
        float side2x = 0;
        float side2y = 10;
        assertEquals(side0x, shape.getSideVertices().get(0).x, "side x0");
        assertEquals(side0y, shape.getSideVertices().get(0).y,  "side y0");
        
        float side3x = -10;
        float side3y = 0;
        assertEquals(side0x, shape.getSideVertices().get(0).x, "side x0");
        assertEquals(side0y, shape.getSideVertices().get(0).y,  "side y0");
    }
    
}
