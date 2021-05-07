package dk.sdu.mmmi.cbse.map;

import com.badlogic.gdx.files.FileHandle;
import java.util.HashMap;
import java.util.Map;
import dk.sdu.mmmi.cbse.common.services.IMapLookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IMapLookup.class)
public class MapFinder implements IMapLookup {
    
    private static FileHandle[] filehandles;
    private FileHandle[] mapThumbnailFiles;
    
    public Map<String, String> findAllMaps() {
        FileHandle[] mapFiles = getMapFiles();
        Map<String, String> resultMap = new HashMap<>();
        
        for (FileHandle mapFile: mapFiles) {
            String mapName = mapFile.nameWithoutExtension();
            String mapThumbnailFileName = getMapThumbnail(mapName);
            
            if (mapThumbnailFileName != null) {
                resultMap.put(mapName, mapThumbnailFileName);
            }
        }
        
        return resultMap;
    }
    
    public static FileHandle[] getMapFiles() {
        if (filehandles == null) {
            FileHandle[] foundFiles;
            
            String osName = System.getProperty("os.name");
            if (osName.startsWith("Windows")) {
                foundFiles = new FileHandle("../../maps").list();
            } else {
                foundFiles = new FileHandle("./maps").list();
            }
            
            filehandles = foundFiles;
        }
        
        return filehandles;
    }
    
    private FileHandle[] getMapThumbnails() {
        if (mapThumbnailFiles == null) {
            FileHandle[] foundThumbnails;
            
            String osName = System.getProperty("os.name");
            if (osName.startsWith("Windows")) {
                foundThumbnails = new FileHandle("../../maps/thumbnails").list();
            } else {
                foundThumbnails = new FileHandle("./maps/thumbnails").list();
            }
            
            mapThumbnailFiles = foundThumbnails;
        }
        
        return mapThumbnailFiles;
    }
    
    private String getMapThumbnail(String mapName) {
        getMapThumbnails();
        
        for (FileHandle file: mapThumbnailFiles) {
            if (file.nameWithoutExtension().equals(mapName)) {
                return file.name();
            }
        }
              
        return null;
    }
}
