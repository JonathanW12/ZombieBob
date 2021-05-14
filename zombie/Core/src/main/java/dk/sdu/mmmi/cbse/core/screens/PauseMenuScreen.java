package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import dk.sdu.mmmi.cbse.common.data.MouseMovement;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;

public class PauseMenuScreen extends MenuScreenTemplate implements Screen {
    
    private BitmapFont title;
    private final SpriteBatch secondaryBatch; 
    private Label resumeButton, settingsButton, mainMenuButton, highscoresButton, exitButton;
    
    public PauseMenuScreen(ZombieBobGame game) {
        super(game);
        secondaryBatch = new SpriteBatch();
        
        setupUI();
    }
    
    @Override
    public void render(float delta) {
        super.render(delta);

        secondaryBatch.begin();

        float estimatedTitleWidth = 750;
        title.setColor(0.541f, 0.011f, 0.011f, 1);
        title.draw(
                secondaryBatch,
                "Game Paused",
                getStage().getWidth() / 2 - estimatedTitleWidth / 2,
                getStage().getHeight() - 150
        );

        secondaryBatch.end();

        update();
    }
    
    @Override
    public void update() {
        super.update();
        handleResumeButton();
        handleSettingsButton();
        handleMainMenuButton();
        handleHighscoresButton();
        handleExitButton();
    }
    
    private void setupUI() {
        float buttonWidth = 275;
        float buttonHeight = 75;
        
        title = getTitleFont();
        
        // Create resume button
        resumeButton = new Label("Resume Game", getSkin(), "title");
        resumeButton.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() - 500,
                buttonWidth,
                buttonHeight
        );
        resumeButton.setAlignment(Align.center);
        
        // Create settings button
        settingsButton = new Label("Settings", getSkin(), "title");
        settingsButton.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() - 600,
                buttonWidth,
                buttonHeight
        );
        settingsButton.setAlignment(Align.center);
        
        // Create highscores button
        highscoresButton = new Label("Highscores", getSkin(), "title");
        highscoresButton.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() - 800,
                buttonWidth,
                buttonHeight
        );
        highscoresButton.setAlignment(Align.center);
        
        // Create main menu button
        mainMenuButton = new Label("Main Manu", getSkin(), "title");
        mainMenuButton.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() - 700,
                buttonWidth,
                buttonHeight
        );
        mainMenuButton.setAlignment(Align.center);

        // Create exit button
        exitButton = new Label("Exit Game", getSkin(), "title");
        exitButton.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() - 900,
                buttonWidth,
                buttonHeight
        );
        exitButton.setAlignment(Align.center);
        
        // Add buttons to stage
        getStage().addActor(resumeButton);
        getStage().addActor(settingsButton);
        getStage().addActor(highscoresButton);
        getStage().addActor(mainMenuButton);
        getStage().addActor(exitButton);
    }
    
    private void handleResumeButton() {
        if ((isMouseOnActor(resumeButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            getGame().setScreen(new GameScreen(getGame()));
        }
    }
    
    private void handleSettingsButton() {
        if ((isMouseOnActor(settingsButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
             getGame().setScreen(new ModuleHandlerScreen(getGame()));
        }
    }
    
    private void handleHighscoresButton() {
        if ((isMouseOnActor(highscoresButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            getGame().setScreen(new HighscoreScreen(getGame()));
        }
    }
    
    private void handleMainMenuButton() {
        if ((isMouseOnActor(mainMenuButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            getGame().setScreen(new MainMenuScreen(getGame()));
        }
    }
    
    private void handleExitButton() {
        if ((isMouseOnActor(exitButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            getGame().dispose();
            Gdx.app.exit();
        }
    }
    
}
