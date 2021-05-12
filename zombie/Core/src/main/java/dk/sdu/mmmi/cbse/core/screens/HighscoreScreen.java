package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import dk.sdu.mmmi.cbse.common.data.MouseMovement;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;

import java.util.ArrayList;

public class HighscoreScreen extends MenuScreenTemplate implements Screen {

    private BitmapFont title;
    private final SpriteBatch secondaryBatch;
    private Label mainMenuButton, highscore;
    private ArrayList<Label> highscores = new ArrayList<>();

    public HighscoreScreen(ZombieBobGame game) {
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
                "Highscore",
                getStage().getWidth() / 2 - estimatedTitleWidth / 2,
                getStage().getHeight() - 150
        );

        secondaryBatch.end();

        update();
    }

    @Override
    public void update() {
        super.update();
        handleMainMenuButton();
    }

    private void setupUI() {
        float buttonWidth = 275;
        float buttonHeight = 75;

        title = getTitleFont();


        highscore = new Label("Jeff\t\t5000", getSkin(), "title-plain");
        highscores.add(highscore);

        highscore = new Label("Jeff\t\t4000", getSkin(), "title-plain");
        highscores.add(highscore);

        highscore = new Label("Jeff\t\t3000", getSkin(), "title-plain");
        highscores.add(highscore);



        // Create main menu button
        mainMenuButton = new Label("Main Menu", getSkin(), "title");
        mainMenuButton.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight()/8,
                buttonWidth,
                buttonHeight
        );
        mainMenuButton.setAlignment(Align.center);
        /*
        highscore.setBounds(getStage().getWidth()/2-buttonWidth/2,
                getStage().getHeight()-300-buttonHeight,buttonWidth,buttonHeight);
        highscore.setAlignment(Align.center);
         */

        getStage().addActor(mainMenuButton);

        int i = 0;
        for (Label label:highscores){
            getStage().addActor(label);
            label.setBounds(getStage().getWidth()/2-buttonWidth/2,
                    getStage().getHeight()-400-buttonHeight*i,buttonWidth,buttonHeight);
            label.setAlignment(Align.center);
            i++;
        }


    }


    private void handleMainMenuButton() {
        if ((isMouseOnActor(mainMenuButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            getGame().setScreen(new MainMenuScreen(getGame()));
        }
    }


}
