package dk.sdu.mmmi.cbse.common.data.entityparts;

import java.util.HashMap;
import dk.sdu.mmmi.cbse.commonanimation.Animation;

public class AnimationPart implements EntityPart {
    private HashMap<String, Animation> animations;
    private String currentAnimationName;
    private boolean isAnimated;
    private boolean isAnimationDone;
   
    public AnimationPart() {
        animations = new HashMap<>();
    }
    
    public void addAnimation(
        String animationName,
        String textureFileName,
        int frameCount,
        float frameDuration,
        boolean isInterruptbile
    ) {
        Animation animation = new Animation(
            textureFileName,
            frameCount,
            frameDuration,
            isInterruptbile
        );
       
        animations.put(animationName, animation);
    }
    
    public void setCurrentAnimation(String currentAnimationName) {
        this.currentAnimationName = currentAnimationName;
        animations.get(currentAnimationName).resetLoopCounter();
    }
    
    public void setIsAnimated(boolean isAnimated) {
        Animation animation = animations.get(currentAnimationName);
        
        if (!animation.getIsInterruptible()) {   
            if (!isAnimated && animation.getCurrentFrame() < (animation.getFrameCount() - 1)) {
                this.isAnimated = true;
            } else {
                this.isAnimated = isAnimated;
            }
        } else {
            this.isAnimated = isAnimated;
        }
        
        if (!this.isAnimated) {
             animation.resetAnimation();
             isAnimationDone = true;
        } else {
            isAnimationDone = false;
        }
    }
    
    public boolean hasCurrentAnimationLooped() {
        Animation animation = animations.get(currentAnimationName);
        return animation.getLoopCounter() > 0;
    }
    
    public boolean isCurrentAnimationInterruptible() {
        Animation animation = animations.get(currentAnimationName);
        return animation.getIsInterruptible();
    }
    
    public void setIsAnimationDone(boolean isAnimationDone) {
        this.isAnimationDone = isAnimationDone;
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
    
    public boolean getIsAnimationDone() {
        return isAnimationDone;
    }
}
