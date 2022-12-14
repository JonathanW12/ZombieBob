package dk.sdu.mmmi.cbse.map;

import com.badlogic.gdx.files.FileHandle;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.ColliderPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SpawnerPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.VisualPart;
import dk.sdu.mmmi.cbse.common.data.entitytypeparts.StructurePart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.commontiles.Tile;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class MapPlugin implements IGamePluginService {

    private final float standardRadians = 3.1415f / 2;

    @Override
    public void start(GameData gameData, World world) {
        // Do nothing    
    }

    public void generateMapFromFile(String mapName, World world, Tile[][] tiles) {
        resetMap(world);

        FileHandle filehandle;
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            filehandle = new FileHandle("../../maps/" + mapName + ".txt");
        } else {
            filehandle = new FileHandle("./maps/" + mapName + ".txt");
        }

        if (filehandle.exists()) {
            String text = filehandle.readString();
            String[] lines = text.split("\\r?\\n");
            int lineCount = lines.length - 1;

            for (int i = 0; i < lines.length; i++) {
                String[] cells = lines[i].split("");
                for (int j = 0; j < cells.length; j++) {
                    Tile tile = tiles[lineCount - i][j];

                    if (cells[j].equals("0")) {
                        placeFloor(tile, world);
                    } else if (cells[j].equals("1")) {
                        placeWall(tile, world);
                    } else if (cells[j].equals("2")) {
                        placeFloor(tile, world);
                        placeEnemySpawn(tile, world);
                        world.addEnemySpawnPosition(
                                (int) (tile.getX() + tile.getWidth() / 2),
                                (int) (tile.getY() + tile.getHeight() / 2)
                        );
                    } else if (cells[j].equals("3")) {
                        placeFloor(tile, world);
                        placeItemSpawn(tile, world);
                        world.addItemSpawnPosition(
                                (int) (tile.getX() + tile.getWidth() / 2),
                                (int) (tile.getY() + tile.getHeight() / 2)
                        );
                    }
                }
            }
        }
    }

    private void resetMap(World world) {
        world.getItemSpawns().clear();
        world.getEnemySpawnPositions().clear();
    }

    public void createBackground(GameData gameData, World world) {
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;

        Entity background = new Entity();

        world.addtoEntityPartMap(new PositionPart(x, y, standardRadians), background);
        world.addtoEntityPartMap(new VisualPart(
                "background_sprite",
                gameData.getDisplayWidth() + gameData.getDisplayWidth() / 2,
                gameData.getDisplayHeight() + gameData.getDisplayWidth() / 2, 0),
                background
        );
    }

    private void placeFloor(Tile tile, World world) {
        Entity floor = new Entity();

        world.addtoEntityPartMap(new PositionPart(
                tile.getX() + tile.getWidth() / 2,
                tile.getY() + tile.getHeight() / 2,
                standardRadians
        ), floor);
        world.addtoEntityPartMap(new VisualPart(
                "floorTexture",
                tile.getWidth(),
                tile.getHeight(),
                1
        ), floor);
    }

    private void placeWall(Tile tile, World world) {
        Entity wall = new Entity();

        world.addtoEntityPartMap(new PositionPart(
                tile.getX() + tile.getWidth() / 2,
                tile.getY() + tile.getHeight() / 2,
                standardRadians
        ), wall);
        world.addtoEntityPartMap(new VisualPart(
                "wall_sprite",
                tile.getWidth(),
                tile.getHeight(),
                1
        ), wall);
        world.addtoEntityPartMap(new ColliderPart(tile.getWidth(), tile.getHeight()), wall);
        world.addtoEntityPartMap(new StructurePart(), wall);

        tile.setIsWalkable(false);
    }

    private void placeEnemySpawn(Tile tile, World world) {
        Entity enemySpawn = new Entity();

        world.addtoEntityPartMap(new PositionPart(
                tile.getX() + tile.getWidth() / 2,
                tile.getY() + tile.getHeight() / 2,
                3.14159f / 2
        ), enemySpawn);
        world.addtoEntityPartMap(new VisualPart(
                "enemySpawn",
                tile.getWidth(),
                tile.getHeight(),
                2
        ), enemySpawn);
    }

    private void placeItemSpawn(Tile tile, World world) {
        Entity itemSpawn = new Entity();

        world.addtoEntityPartMap(new PositionPart(
                tile.getX() + tile.getWidth() / 2,
                tile.getY() + tile.getHeight() / 2,
                3.14159f / 2
        ), itemSpawn);
        world.addtoEntityPartMap(new VisualPart(
                "itemSpawn",
                tile.getWidth(),
                tile.getHeight(),
                2
        ), itemSpawn);

        world.addtoEntityPartMap(new SpawnerPart(), itemSpawn);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Do nothing
    }

}
