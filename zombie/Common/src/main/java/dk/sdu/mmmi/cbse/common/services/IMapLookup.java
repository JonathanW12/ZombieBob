package dk.sdu.mmmi.cbse.common.services;

import java.util.Map;

public interface IMapLookup {
    
    /**
     * Returns all maps in the application maps folder
     * @return A Map containing strings of filenames for 
     * map names and thumbnails
     */
    public Map<String, String> findAllMaps();
}
