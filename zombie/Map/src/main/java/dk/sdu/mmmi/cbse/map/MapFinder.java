package dk.sdu.mmmi.cbse.map;

import com.badlogic.gdx.files.FileHandle;

public class MapFinder {
    
    private static FileHandle[] filehandles;
    
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
}
