package dk.sdu.mmmi.cbse.astar;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AiMovementPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.PlayerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commontiles.Tile;
import dk.sdu.mmmi.cbse.commontiles.Tiles;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class Astar implements IEntityProcessingService {
    private Queue<Node> openSet;
    private Queue<Node> closedSet;
    private Node currentNode;
    private Node goalNode;
    private NodeGenerator nodeGenerator;
    private static List<Node> path;
    
    public Astar() {
        openSet = new PriorityQueue<>();
        closedSet = new PriorityQueue<>();
        path = new ArrayList<>();
        nodeGenerator = new NodeGenerator();
        nodeGenerator.createNodes();
    }
    
    @Override
    public void process(GameData gameData, World world) {
        if (world.getMapByPart(AiMovementPart.class.getSimpleName()) != null && world.getMapByPart(PlayerPart.class.getSimpleName()) != null) {
            UUID playerUUID = (UUID) world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray()[0];
            PositionPart playerPos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(playerUUID);

            for (Map.Entry<UUID,EntityPart> entry : world.getMapByPart(AiMovementPart.class.getSimpleName()).entrySet()) {
                AiMovementPart aiPart = (AiMovementPart) entry.getValue();
                long currentTime = System.currentTimeMillis();
                
                if (aiPart.getLevel() < 5 && (currentTime > aiPart.getLastUpdate() + aiPart.getDelay()) && entry.getKey() != playerUUID) {
                    aiPart.resetDelay();
                    PositionPart positionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(entry.getKey());
                    MovingPart movingPart = (MovingPart) world.getMapByPart(MovingPart.class.getSimpleName()).get(entry.getKey());
                    currentNode = getCurrentNode(gameData, positionPart);
                    goalNode = getGoalNode(gameData, world);
                    
                    // Handle nodes being null
                    if (currentNode != null && goalNode != null) {
                        // If player is in same tile, switch to simple AI
                        if (currentNode.equals(goalNode)) {
                            chasePlayerInTile(positionPart, movingPart, world);
                        } else {
                            findPlayerPath(gameData, world, entry.getKey());                   
                            if (path.size() > 0) {
                                Node node = path.get(path.size() - 1);
                                float newDirection = (float) Math.atan2(
                                    node.getY() - positionPart.getY(),
                                    node.getX() - positionPart.getX()
                                );
                                positionPart.setRadians(newDirection);
                                movingPart.setUp(true);
                            } else {
                                movingPart.setUp(false);
                            }
                        }
                    }  
                }                
            }
        }
    }
    
    // Find player by using A*
    private void findPlayerPath(GameData gameData, World world, UUID enemyId) {
        PositionPart startPos = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(enemyId);
        Node lowestNode;
        Node originalNode = getCurrentNode(gameData, startPos);
        path.clear();
        openSet.clear();
        closedSet.clear();
        openSet.add(originalNode);
        
        while (openSet.size() > 0) {
            lowestNode = openSet.peek();
            
            for (Node node: openSet) {
                if (node.getF() < lowestNode.getF()) {
                    lowestNode = node;
                }
            }
            
            // Check for solution
            if (lowestNode.equals(getGoalNode(gameData, world))) {
                Node temp = lowestNode;
                path.add(temp);
                
                while (temp.getParent() != null && !temp.getParent().equals(originalNode)) {
                    path.add(temp.getParent());
                    temp = temp.getParent();
                }
            }
            
            openSet.remove(lowestNode);
            closedSet.add(lowestNode);
            
            for (Node neighbor: lowestNode.getNeighbors()) {
                if (!closedSet.contains(neighbor) && !neighbor.getIsObstacle()) {            
                    if (!openSet.contains(neighbor)) {
                        openSet.add(neighbor);
                    } 
                   
                    neighbor.setG(lowestNode.getG() + 1);
                    neighbor.setH(calculateH(neighbor, getGoalNode(gameData, world)));
                    neighbor.setF(neighbor.getG() + neighbor.getH());
                    neighbor.setParent(lowestNode);
                }
            }
        }
    }
    
    private float calculateH(Node a, Node b) {
        return (float) (
            Math.sqrt(
                Math.pow(a.getX() - b.getX(), 2) +
                Math.pow(a.getY() - b.getY(), 2)
            )
        );
    }
    
    private Node getCurrentNode(GameData gameData, PositionPart positionPart) {
        int row = (int) (positionPart.getY() / Tiles.getTileHeight());
        int col = (int) (positionPart.getX() / Tiles.getTileWidth());
        Tile currentTile = Tiles.getInstance(gameData).getTileByRowAndCol(row, col);
        if (currentTile != null) {
            return nodeGenerator.getNode(row, col);
        }
        
        return null;
    }
    
    private Node getGoalNode(GameData gameData, World world) {
        UUID playerUUID;
        PositionPart positionPart;
        Tile goalTile;
        int row;
        int col;
        
        if (world.getMapByPart(PlayerPart.class.getSimpleName()) != null) {
            playerUUID = (UUID) world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray()[0];
            positionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(playerUUID);
            
            row = (int) (positionPart.getY() / Tiles.getTileHeight());
            col = (int) (positionPart.getX() / Tiles.getTileWidth());
            goalTile = Tiles.getInstance(gameData).getTileByRowAndCol(row, col);
            if (goalTile != null) {
                return nodeGenerator.getNode(row, col);
            }
        }
        
        return null;
    }
    
    private void chasePlayerInTile(PositionPart positionPart, MovingPart movingPart, World world) {
        if (world.getMapByPart(PlayerPart.class.getSimpleName()) != null) {
            UUID playerUUID = (UUID) world.getMapByPart(PlayerPart.class.getSimpleName()).keySet().toArray()[0];
            PositionPart playerPositionPart = (PositionPart) world.getMapByPart(PositionPart.class.getSimpleName()).get(playerUUID);
            
            movingPart.setUp(true);
            positionPart.setRadians((float)
                Math.atan2(
                    playerPositionPart.getY() - positionPart.getY(),
                    playerPositionPart.getX() - positionPart.getX()
                )
            );
        } 
    }
    
}
