package dk.sdu.mmmi.cbse.common.data.entityparts;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;

public class TextPart implements EntityPart{
    
    private String message;
    private int zPosition;
    private boolean isVisible;
    
    public TextPart(String message, int yPosition) {
        this.message = message;
        this.zPosition = zPosition;
        this.isVisible = true;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setzPosition(int zPosition) {
        this.zPosition = zPosition;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String getMessage() {
        return message;
    }

    public int getzPosition() {
        return zPosition;
    }

    public boolean isIsVisible() {
        return isVisible;
    }

}
