package dk.sdu.mmmi.cbse.common.services;

import com.badlogic.gdx.files.FileHandle;
import java.util.Map;

public interface IMapLookup {
    
    /**
     * Returns all maps in the application maps folder
     * @return A Map containing maps names and thumbnails
     */
    public Map<String, FileHandle> findAllMaps();
}
