package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;
import java.util.HashMap;
import com.badlogic.gdx.utils.Align;
import dk.sdu.mmmi.cbse.common.data.MouseMovement;

public class moduleHandlerScreen extends MenuScreenTemplate implements Screen{
    private BitmapFont title;
    private Label mainMenuButton, enemy, player;
    private HashMap<String,Label> labels = new HashMap<String,Label>();
    private final SpriteBatch secondaryBatch; 
    private float buttonWidth = 275;
    private float buttonHeight = 75;
    
    public moduleHandlerScreen(ZombieBobGame game) {
        super(game);
        secondaryBatch = new SpriteBatch();
        setup();
    }
    
    @Override
    public void update(){
        super.update();
        labels.forEach((String moduleName,Label label) ->{handleModuleClick(moduleName);});
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);
        secondaryBatch.begin();
        float estimatedTitleWidth = 750;
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
    
    private void setup(){
        mainMenuButton = new Label("Main Manu", getSkin(), "title");
        mainMenuButton.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() - 700,
                buttonWidth,
                buttonHeight
        );
        mainMenuButton.setAlignment(Align.center);
        // TODO for every module philip finds do setUpModule
    }
    private void setUpModule(String moduleName){    

        Label button = new Label(moduleName, getSkin(), "title");
        button.setBounds(100, 100, buttonWidth,buttonHeight);
        button.setAlignment(Align.center);
        getStage().addActor(button);
        labels.put(moduleName,button);
    }
    
    private void handleModuleClick(String moduleName){
        if ((isMouseOnActor(labels.get(moduleName)) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            // TODO remove or add module
        }
    }
    private void handleMainMenu() {
        if ((isMouseOnActor(mainMenuButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            getGame().setScreen(new MainMenuScreen(getGame()));
        }
    }
}
