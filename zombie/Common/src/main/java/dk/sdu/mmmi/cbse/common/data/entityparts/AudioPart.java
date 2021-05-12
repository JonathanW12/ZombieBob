package dk.sdu.mmmi.cbse.common.data.entityparts;

import java.util.Random;

public class AudioPart implements EntityPart {
    
    private String fileName;
    private boolean isPlaying;
    private boolean isRandomlyReplaying;
    private long minReplayDelay;
    private long maxReplayDelay;
    private long standardDelay;
    private long actualDelay;
    private long lastReplayTime;
    private float volume;
    private final Random randomGenerator;
    
    public AudioPart(String fileName) {
        this.fileName = fileName;
        isPlaying = false;
        isRandomlyReplaying = false;
        minReplayDelay = 0L;
        maxReplayDelay = 0L;
        standardDelay = 0L;
        actualDelay = 0L;
        lastReplayTime = System.currentTimeMillis();
        volume = 0.1f;
        randomGenerator = new Random();
    }
    
    public AudioPart(String fileName, float volume) {
        this.fileName = fileName;
        isPlaying = false;
        isRandomlyReplaying = false;
        minReplayDelay = 0L;
        maxReplayDelay = 0L;
        standardDelay = 0L;
        actualDelay = 0L;
        lastReplayTime = System.currentTimeMillis();
        this.volume = volume;
        randomGenerator = new Random();
    }
    
    /**
     * Repeats the audio file at a random millisecond interval
     * between parameter 1 and 2
     * @param minReplayDelay Minimum replay delay in milliseconds
     * @param maxReplayDelay Maximum replay delay in milliseconds
     */
    public void playRandomly(long minReplayDelay, long maxReplayDelay) {
        this.minReplayDelay = minReplayDelay;
        this.maxReplayDelay = maxReplayDelay;
        
        isRandomlyReplaying = true;
        resetReplayingDelayTimer();
    }
    
    public void resetReplayingDelayTimer() {
        long newRandomDelay = randomGenerator.nextInt((int) (maxReplayDelay - minReplayDelay)) + minReplayDelay;
        lastReplayTime = System.currentTimeMillis();
        
        actualDelay = newRandomDelay;
    }
    
    public void resetStandardDelayTimer() {
        lastReplayTime = System.currentTimeMillis();
    }
            
    public String getFileName() {
        return fileName;
    }
    
    public boolean getIsPlaying() {
        return isPlaying;
    }
    
    public boolean getIsRandomlyReplaying() {
        return isRandomlyReplaying;
    }
    
    public long getMinReplayDelay() {
        return minReplayDelay;
    }
    
    public long getMaxReplayDelay() {
        return maxReplayDelay;
    }
    
    public long getLastReplayTime() {
        return lastReplayTime;
    }
    
    public long getActualDelay() {
        return actualDelay;
    }
    
    public long getStandardDelay() {
        return standardDelay;
    }
    
    public float getVolume() {
        return volume;
    }

    public void setStandardDelay(long standardDelay) {
        this.standardDelay = standardDelay;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
    
    public void setIsRandomlyPlaying(boolean isRandomlyPlaying) {
        this.isRandomlyReplaying = isRandomlyPlaying;
    }
    
    public void setMinReplayDelay(long minReplayDelay) {
        this.minReplayDelay = minReplayDelay;
    }
    
    public void setMaxReplayDelay(long maxReplayDelay) {
        this.maxReplayDelay = maxReplayDelay;
    }
    
    public void setVolume(float volume) {
        this.volume = volume;
    }
    
}
