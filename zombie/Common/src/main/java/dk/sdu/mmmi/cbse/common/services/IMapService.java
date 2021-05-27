package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.Map;

public interface IMapService {

    /**
     * Returns all maps in the application maps folder
     *
     * @return A Map containing strings of filenames for map names and
     * thumbnails
     */
    public Map<String, String> findAllMaps();

    /**
     * Sets the current map
     */
    public void setMap(String mapName, GameData gameData, World world);
}
