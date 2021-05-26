/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart.Vector2;
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
        position1 = new PositionPart(100,100, (float)Math.PI);
        
        collider2 = new ColliderPart(10, 10);
        position2 = new PositionPart(105,105, (float)Math.PI);
        
        BoxCollisionChecker instance = new BoxCollisionChecker();
        boolean expResult = true;
        boolean result = instance.areColliding(collider1, position1, collider2, position2);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGivenCollidingRotatedBoxesReturnTrue() {
        collider1 = new ColliderPart(10, 10);
        position1 = new PositionPart(100,100, (float)Math.PI/7);
        
        collider2 = new ColliderPart(10, 10);
        position2 = new PositionPart(105,105, (float)Math.PI/7);
        
        BoxCollisionChecker instance = new BoxCollisionChecker();
        boolean expResult = true;
        boolean result = instance.areColliding(collider1, position1, collider2, position2);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGivenNotCollidingBoxesReturnFalse() {
        collider1 = new ColliderPart(10, 10);
        position1 = new PositionPart(100,100, (float)Math.PI);
        
        collider2 = new ColliderPart(10, 10);
        position2 = new PositionPart(120,120, (float)Math.PI);
        
        BoxCollisionChecker instance = new BoxCollisionChecker();
        boolean expResult = false;
        boolean result = instance.areColliding(collider1, position1, collider2, position2);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGivenNotCollidingRotatedBoxesReturnFalse() {
        collider1 = new ColliderPart(10, 10);
        position1 = new PositionPart(100,100, (float)Math.PI/10);
        
        collider2 = new ColliderPart(10, 10);
        position2 = new PositionPart(120,120, (float)Math.PI/10);
        
        BoxCollisionChecker instance = new BoxCollisionChecker();
        boolean expResult = false;
        boolean result = instance.areColliding(collider1, position1, collider2, position2);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGivenCollidingComplexPolygonsReturnTrue(){
        
        collider1 = new ColliderPart();
        //creating a convex star shape with 4 corners
        for(int i = 0; i < 8; i++){
            collider1.addShapePoint(10 * (0.2f + i % 2),(float) (i * Math.PI / 4));
        }
        position1 = new PositionPart(100,100, 0);
        
        collider2 = new ColliderPart();
        for(int i = 0; i < 8; i++){
            collider2.addShapePoint(10 * (0.2f + i % 2),(float) (i * Math.PI / 4));
        }
        position2 = new PositionPart(120,100, 0);
        
        
        BoxCollisionChecker instance = new BoxCollisionChecker();
        boolean expResult = true;
        boolean result = instance.areColliding(collider1, position1, collider2, position2);
        assertEquals(expResult, result);
        
    }
    @Test
    public void testGivenNonCollidingComplexRotatedPolygonsReturnFalse(){
        
        collider1 = new ColliderPart();
        //creating a convex star shape with 4 corners
        for(int i = 0; i < 8; i++){
            collider1.addShapePoint(10 * (0.1f + i % 2),(float) (i * Math.PI / 4));
        }
        position1 = new PositionPart(100,100, 0);
        
        collider2 = new ColliderPart();
        for(int i = 0; i < 8; i++){
            collider2.addShapePoint(10 * (0.1f + i % 2),(float) (i * Math.PI / 4));
        }
        
        //rotating one star by 45 degrees to fit one of the star points into a concave space of the other star
        position2 = new PositionPart(120,100, (float)Math.PI/4);
        
        
        BoxCollisionChecker instance = new BoxCollisionChecker();
        boolean expResult = false;
        boolean result = instance.areColliding(collider1, position1, collider2, position2);
        assertEquals(expResult, result);
        
    }
    
    //testing that the corners of the a regtangle gets positioned correct,
    @Test
    public void testShapeCornersOfSimpleRegtangle(){
        collider1 = new ColliderPart(10, 10);
        position1 = new PositionPart(0,0, 0);
        
        ArrayList<Vector2> corners = collider1.getCornerVecs(position1);
        
        float corner0x = 5;
        float corner0y = -5;
        assertEquals(corner0x, corners.get(0).x, "corner x0");
        assertEquals(corner0y, corners.get(0).y,  "corner y0");
        
        float corner1x = 5;
        float corner1y = 5;
        assertEquals(corner1x, corners.get(1).x,  "corner x1");
        assertEquals(corner1y, corners.get(1).y,  "corner y1");
        
        float corner2x = -5;
        float corner2y = 5;
        assertEquals(corner2x, corners.get(2).x,  "corner x2");
        assertEquals(corner2y, corners.get(2).y,  "corner y2");
        
        float corner3x = -5;
        float corner3y = -5;
        assertEquals(corner3x, corners.get(3).x,  "corner x3");
        assertEquals(corner3y, corners.get(3).y,  "corner y3");
    }
}
