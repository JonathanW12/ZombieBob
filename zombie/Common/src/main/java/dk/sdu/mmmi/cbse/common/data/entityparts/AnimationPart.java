package dk.sdu.mmmi.cbse.common.data.entityparts;

import java.util.HashMap;
import dk.sdu.mmmi.cbse.commonanimation.Animation;

public class AnimationPart implements EntityPart {
    private HashMap<String, Animation> animations;
    private String currentAnimationName;
    private boolean isAnimated;
   
    public AnimationPart() {
        animations = new HashMap<>();
    }
    
    public void addAnimation(String animationName, String textureFileName, int frameCount, float frameDuration) {
        Animation animation = new Animation(textureFileName, frameCount, frameDuration);
       
        animations.put(animationName, animation);
    }
    
    public void setCurrentAnimation(String currentAnimationName) {
        this.currentAnimationName = currentAnimationName;
    }
    
    public void setIsAnimated(boolean isAnimated) {
        this.isAnimated = isAnimated;
    }
    
    public Animation getAnimationByName(String animationName) {
        return animations.get(animationName);
    }
    
    public String getCurrentAnimationName() {
        return currentAnimationName;
    }
    
    public Animation getCurrentAnimation() {
        return animations.get(currentAnimationName);
    }
    
    public boolean isAnimated() {
        return isAnimated;
    }
}
