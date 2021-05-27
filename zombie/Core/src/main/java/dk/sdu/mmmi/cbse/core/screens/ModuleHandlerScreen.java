package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;
import java.util.HashMap;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import dk.sdu.mmmi.cbse.common.data.MouseMovement;
import dk.sdu.mmmi.cbse.core.main.UpdatesEditor;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

class ModuleHandlerScreen extends MenuScreenTemplate implements Screen {

    private UpdatesEditor updatesEditor = UpdatesEditor.getInstance();
    private BitmapFont title;
    private Label mainMenuButton;
    private HashMap<String, Label> labels = new HashMap<String, Label>();
    private HashMap<String, Image> removedModules = new HashMap<String, Image>();
    private final SpriteBatch secondaryBatch;
    private float buttonWidth = 275;
    private float buttonHeight = 75;
    private float xOffSet = 360;
    private float yOffSet = 100;
    private Image redX;

    public ModuleHandlerScreen(ZombieBobGame game) {
        super(game);
        secondaryBatch = new SpriteBatch();
        setup();
    }

    @Override
    public void update() {
        super.update();
        handleMainMenu();
        labels.forEach((String moduleName, Label label) -> {
            handleModuleClick(moduleName);
        });
        if (getGameData().getKeys().isPressed(GameKeys.ESCAPE)) {
            getGame().setScreen(new PauseMenuScreen(getGame()));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        secondaryBatch.begin();
        float estimatedTitleWidth = 500;
        title.setColor(0.541f, 0.011f, 0.011f, 1);
        title.draw(
                secondaryBatch,
                "Settings",
                getStage().getWidth() / 2 - estimatedTitleWidth / 2,
                getStage().getHeight() - 150
        );
        secondaryBatch.end();
        update();
    }

    private void setup() {
        title = getTitleFont();
        mainMenuButton = new Label("Main Manu", getSkin(), "title");
        mainMenuButton.setBounds(
                (getStage().getWidth() / 2) - (buttonWidth / 2),
                100,
                buttonWidth,
                buttonHeight
        );
        mainMenuButton.setAlignment(Align.center);
        getStage().addActor(mainMenuButton);

        AtomicInteger counter = new AtomicInteger(-1);
        updatesEditor.getModulesStatus().forEach((String name, Boolean value) -> {
            setUpModule(name, counter.addAndGet(1), value);
        });
    }

    private void setUpModule(String moduleName, int index, boolean value) {
        float xPos = 100 + xOffSet * (float) Math.floor(index / 6);
        float yPos = 100 + yOffSet + yOffSet * (index % 6);
        Label button = new Label(moduleName, getSkin(), "title");
        button.setBounds(xPos, yPos, buttonWidth, buttonHeight);
        button.setAlignment(Align.center);
        getStage().addActor(button);
        labels.put(moduleName, button);

        redX = new Image(getButtonTexture("redX.png"));
        redX.setPosition((-1 * redX.getWidth() / 2) + labels.get(moduleName).getX() + buttonWidth / 2, labels.get(moduleName).getY());
        redX.setVisible(!value);
        getStage().addActor(redX);
        removedModules.put(moduleName, redX);

    }

    private void handleModuleClick(String moduleName) {
        if ((isMouseOnActor(labels.get(moduleName)) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            if (updatesEditor.getModulesStatus().get(moduleName)) {
                System.out.println("Deactive");
                activateRedX(moduleName, true);
                updatesEditor.deactivate(moduleName);
                return;
            }
            activateRedX(moduleName, false);
            updatesEditor.activate(moduleName);

        }
    }

    private void handleMainMenu() {
        if ((isMouseOnActor(mainMenuButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            getGame().setScreen(new PauseMenuScreen(getGame()));
        }
    }

    private void activateRedX(String moduleName, boolean activated) {
        if (activated) {
            removedModules.get(moduleName).setVisible(true);
            return;
        }
        removedModules.get(moduleName).setVisible(false);
    }

    public Texture getButtonTexture(String fileName) {
        Texture buttonTexture;
        try {
            buttonTexture = new Texture(Gdx.files.local("raw-assets/" + fileName));
        } catch (GdxRuntimeException e) {
            buttonTexture = new Texture(Gdx.files.local("../../raw-assets/" + fileName));
        }
        return buttonTexture;
    }
}
