package dk.sdu.mmmi.cbse.common.data.entityparts;

public class AudioPart implements EntityPart {
    
    private String fileName;
    private boolean isPlaying;
    
    public AudioPart(String fileName) {
        this.fileName = fileName;
        isPlaying = false;
    }
            
    public String getFileName() {
        return fileName;
    }
    
    public boolean getIsPlaying() {
        return isPlaying;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    
}
