package dk.sdu.mmmi.cbse.animationsystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AnimationPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.commonanimation.Animation;
import java.util.Map;
import java.util.UUID;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class AnimationSystem implements IEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        for (Map.Entry<UUID, EntityPart> entry: world.getMapByPart("AnimationPart").entrySet()) {
            AnimationPart animationPart = (AnimationPart) entry.getValue();
            Animation animation = animationPart.getAnimationByName(animationPart.getCurrentAnimationName());
            
            animation.increaseCurrentFrameDuration(gameData.getDelta());
            
            if (animation.getIsFrameExpired()) {
                animation.nextFrame();
            }
        }
    }
}
