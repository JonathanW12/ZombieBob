package dk.sdu.mmmi.cbse.core.coreprocessors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.AudioPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.EntityPart;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AudioProcessor {
    
    public enum MusicState {
        MENUMUSIC,
        GAMEMUSIC
    }
    
    private final World world;
    private final HashMap<String, Sound> sounds;
    private final Music menuMusic;
    private final Music gameMusic;
    private MusicState currentMusicState;
    
    public AudioProcessor(World world) {
        this.world = world;
        sounds = new HashMap<>();
        menuMusic = getMusic("menu-music.mp3");
        gameMusic = getMusic("game-music.mp3");
        
        menuMusic.setLooping(true);
        gameMusic.setLooping(true);
        menuMusic.setVolume(1f);
        gameMusic.setVolume(1f);
        
        currentMusicState = MusicState.MENUMUSIC;
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
        
        if (currentMusicState == MusicState.MENUMUSIC) {
            gameMusic.stop();      
            menuMusic.play();
        } else if (currentMusicState == MusicState.GAMEMUSIC) {
            menuMusic.stop();
            gameMusic.play();
        }
    }
    
    public void setMusicState(MusicState musicState) {
        this.currentMusicState = musicState;
    }
    
    private Sound getSound(String fileName) {        
        if (sounds.get(fileName) == null) {
            Sound sound;
            
            try {
                sound = Gdx.audio.newSound(Gdx.files.local("audio/" + fileName));
            } catch (GdxRuntimeException e) {
                sound = Gdx.audio.newSound(Gdx.files.local("../../audio/" + fileName));
            }
            
            sounds.put(fileName, sound);
            return sound;
        } else {
            return sounds.get(fileName);
        }
    }
    
    private Music getMusic(String fileName) {
        Music music;
        
        try {
            music = Gdx.audio.newMusic(Gdx.files.local("audio/" + fileName));
        } catch (GdxRuntimeException e) {
            music = Gdx.audio.newMusic(Gdx.files.local("../../audio/" + fileName));
        }
        
        return music;
    }
    
    public void dispose() {
        for (Map.Entry<String, Sound> sound: sounds.entrySet()) {
            sound.getValue().dispose();
        }
    }
}
