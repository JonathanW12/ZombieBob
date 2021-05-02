package dk.sdu.mmmi.cbse.core.coreprocessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AudioPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import java.util.Map;
import java.util.UUID;

public class AudioProcessor {
    
    private final World world;
    
    public AudioProcessor(World world) {
        this.world = world;
    }
    
    public void processAudio() {
        if (world.getMapByPart(AudioPart.class.getSimpleName()) != null) {
            for (Map.Entry<UUID, EntityPart> entry : world.getMapByPart(AudioPart.class.getSimpleName()).entrySet()) { 
                AudioPart audioPart = (AudioPart) entry.getValue();

                if (audioPart.getIsPlaying()) {
                    Sound sound = getSound(audioPart.getFileName());
                    audioPart.setIsPlaying(false);
                    long soundId = sound.play(0.1f);
                    sound.setLooping(soundId, false);
                }
            }
        }
    }
    
    private Sound getSound(String fileName) {
        Sound sound;
        
        try {
            sound = Gdx.audio.newSound(Gdx.files.local("audio/" + fileName));
        } catch (GdxRuntimeException e) {
            sound = Gdx.audio.newSound(Gdx.files.local("../../audio/" + fileName));
        }
        
        return sound;
    }
    
}
