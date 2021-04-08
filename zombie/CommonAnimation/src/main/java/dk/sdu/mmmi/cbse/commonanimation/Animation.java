package dk.sdu.mmmi.cbse.commonanimation;

public class Animation {
    private String textureFileName;
    private int frameCount;
    private int currentFrame;
    private float frameDuration;
    private float currentFrameDuration;
    private boolean isFrameExpired;
    
    public Animation(String textureFileName, int frameCount, float frameDuration) {
        this.textureFileName = textureFileName;
        this.frameCount = frameCount;
        this.frameDuration = frameDuration;
        currentFrame = 1;
        currentFrameDuration = 0;
        isFrameExpired = false;
    }
    
    public String getTextureFileName() {
        return textureFileName;
    }
    
    public int getFrameCount() {
        return frameCount;
    }
    
    public int getCurrentFrame() {
        return currentFrame;
    }
    
    public float getFrameDuration() {
        return frameDuration;
    }
    
    public float getCurrentFrameDuration() {
        return currentFrameDuration;
    }
    
    public boolean getIsFrameExpired() {
        return isFrameExpired;
    }
    
    public void setFrameDuration(float frameDuration) {
        this.frameDuration = frameDuration;
    }
    
    public void increaseCurrentFrameDuration(float dt) {
        currentFrameDuration += dt;
        checkIsFrameExpired();
    }
    
    public void setTextureFileName(String textureFileName) {
        this.textureFileName = textureFileName;
    }
    
    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }
    
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }
    
    public void nextFrame() {
        currentFrame = (currentFrame + 1) % frameCount;
        currentFrameDuration = 0;
    }
    
    private void checkIsFrameExpired() {
        if (currentFrameDuration > frameDuration) {
            isFrameExpired = true;
        } else {
            isFrameExpired = false;
        }
    }
}
