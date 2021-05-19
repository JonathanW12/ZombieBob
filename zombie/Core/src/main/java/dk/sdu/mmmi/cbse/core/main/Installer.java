package dk.sdu.mmmi.cbse.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    private static ApplicationListener g;

    @Override
    public void restored() {

        g = new ZombieBobGame();

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "ZombieBoB 0.4.1";
        cfg.width = 960;
        cfg.height = 540;
//      cfg.fullscreen = true;
        cfg.useGL30 = false;
        cfg.resizable = false;

        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        cfg.fullscreen = false;
//      https://stackoverflow.com/questions/38726801/libgdx-fullscreen-windowed-mode-has-border

        new LwjglApplication(g, cfg);
    }
}
