package dk.sdu.mmmi.cbse.common.data.entityparts;

public class ExplosivePart implements EntityPart {
    
    private boolean isReadyToExplode;
    
    public ExplosivePart() {
        isReadyToExplode = false;
    }
    
    public boolean getIsReadyToExplode() {
        return isReadyToExplode;
    }
    
    public void setIsReadyToExplode(boolean isReadyToExplode) {
        this.isReadyToExplode = isReadyToExplode;
    }
}
