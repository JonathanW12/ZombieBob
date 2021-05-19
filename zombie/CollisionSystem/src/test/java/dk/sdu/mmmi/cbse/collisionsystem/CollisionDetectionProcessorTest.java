/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author phili
 */
public class CollisionDetectionProcessorTest {
    
    Entity e1;
    ColliderPart collider1;
    PositionPart position1;
    
    Entity e2;
    ColliderPart collider2;
    PositionPart position2;
    
    Map<UUID, EntityPart> colliderParts;
    Map<UUID, EntityPart> positionParts;
    
    public CollisionDetectionProcessorTest() {
    }
    
    
    @BeforeEach
    public void setUp() {
        colliderParts = new ConcurrentHashMap<UUID, EntityPart>();
        
        
        positionParts = new ConcurrentHashMap<UUID, EntityPart>();
        
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of process method, of class CollisionDetectionProcessor.
     */
    @Test
    public void testGivenCollisionThenCollidingEntitiesUpdates() {
        e1 = new Entity();
        collider1 = new ColliderPart(10,10);
        position1 = new PositionPart(0,0,0);
        
        e2 = new Entity();
        collider2 = new ColliderPart(10,10);
        position2 = new PositionPart(0,5,0);
        
        colliderParts.put(e1.getUUID(), collider1);
        colliderParts.put(e2.getUUID(), collider2);
        
        positionParts.put(e1.getUUID(), position1);
        positionParts.put(e2.getUUID(), position2);
        
        
        GameData mockGameData = mock(GameData.class);
        World mockWorld = mock(World.class);
        
        when(mockWorld.getMapByPart("ColliderPart")).thenReturn(colliderParts);
        when(mockWorld.getMapByPart("PositionPart")).thenReturn(positionParts);
        
        CollisionDetectionProcessor instance = new CollisionDetectionProcessor();
        instance.process(mockGameData, mockWorld);
        
        assertTrue(collider1.getCollidingEntities().contains(e2.getUUID()));
        assertTrue(collider2.getCollidingEntities().contains(e1.getUUID()));
        
        assertFalse(collider1.getCollidingEntities().contains(e1.getUUID()));
        assertFalse(collider2.getCollidingEntities().contains(e2.getUUID()));
    }
    
    @Test
    public void testGivenNoCollisionThenCollidingEntitiesDontUpdate() {
        e1 = new Entity();
        collider1 = new ColliderPart(10,10);
        position1 = new PositionPart(0,0,0);
        
        e2 = new Entity();
        collider2 = new ColliderPart(10,10);
        position2 = new PositionPart(0,11,0);
        
        colliderParts.put(e1.getUUID(), collider1);
        colliderParts.put(e2.getUUID(), collider2);
        
        positionParts.put(e1.getUUID(), position1);
        positionParts.put(e2.getUUID(), position2);
        
        
        GameData mockGameData = mock(GameData.class);
        World mockWorld = mock(World.class);
        
        when(mockWorld.getMapByPart("ColliderPart")).thenReturn(colliderParts);
        when(mockWorld.getMapByPart("PositionPart")).thenReturn(positionParts);
        
        CollisionDetectionProcessor instance = new CollisionDetectionProcessor();
        instance.process(mockGameData, mockWorld);
        
        assertFalse(collider1.getCollidingEntities().contains(e2.getUUID()));
        assertFalse(collider2.getCollidingEntities().contains(e1.getUUID()));
        
        assertFalse(collider1.getCollidingEntities().contains(e1.getUUID()));
        assertFalse(collider2.getCollidingEntities().contains(e2.getUUID()));
    }
    
    
    
}
