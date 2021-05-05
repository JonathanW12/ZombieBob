package dk.sdu.mmmi.cbse.astar;

import dk.sdu.mmmi.cbse.commontiles.Tile;
import dk.sdu.mmmi.cbse.commontiles.Tiles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NodeGenerator {
    
    private List<Node> nodes;
    private HashMap<String, Node> nodeByPos;
    
    public NodeGenerator() {
        nodes = new ArrayList<>();
        nodeByPos = new HashMap<>();
    }
    
    public void createNodes() {
        Tile[][] tiles = Tiles.getTiles();
        
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile tile = tiles[i][j];
                
                nodes.add(
                    new Node(
                        tile.getRow(),
                        tile.getCol(),
                        tile.getX() + (tile.getWidth() / 2),
                        tile.getY() + (tile.getHeight() / 2)
                    )
                );
            }
        }
        
        for (Node node: nodes) {
            addNeighbors(node);
        }
    }
    
    public Node getNode(int tileRow, int tileCol) {
        if (nodeByPos.isEmpty()) {
            addNodesToMap();
        } else {
            String pos = "row" + tileRow + "-col" + tileCol;
            return nodeByPos.get(pos);
        }
      
        return null;
    }
    
    private void addNodesToMap() {
        Tile[][] tiles = Tiles.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j ++) { 
                for (Node node: nodes) {
                    if (node.getTileRow() == i && node.getTileCol() == j) {
                        String pos = "row" + i + "-col" + j;
                        nodeByPos.put(pos, node);
                    }
                }
            }
        }
    }
    
    private void addNeighbors(Node node) {
        int nodeCol = node.getTileCol();
        int nodeRow = node.getTileRow();
        
        if (nodeCol > 0) {
            node.addNeighbor(
                getNode(nodeRow, nodeCol - 1)
            );
        }
        
        if (nodeCol < Tiles.getTiles()[0].length - 1) {
            node.addNeighbor(
                getNode(nodeRow, nodeCol + 1)
            );
        }
        
        if (nodeRow > 0) {
            node.addNeighbor(
                getNode(nodeRow - 1, nodeCol)
            );
        }
        
        if (nodeRow < Tiles.getTiles().length - 1) {
            node.addNeighbor(
                getNode(nodeRow + 1, nodeCol)
            );
        }
    } 
}
