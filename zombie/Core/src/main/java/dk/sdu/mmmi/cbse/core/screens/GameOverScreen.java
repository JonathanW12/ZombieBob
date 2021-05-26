package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import dk.sdu.mmmi.cbse.common.data.MouseMovement;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GameOverScreen extends MenuScreenTemplate implements Screen {

    private BitmapFont title;
    private final SpriteBatch secondaryBatch;
    private Label mainMenuButton, submitHighscoreBtn, score;
    private TextField nameField;
    private ArrayList<Label[]> highscoreList = new ArrayList<>();

    public GameOverScreen(ZombieBobGame game) {
        super(game);
        secondaryBatch = new SpriteBatch();

        setupUI();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        secondaryBatch.begin();

        float estimatedTitleWidth = 635;
        title.setColor(0.541f, 0.011f, 0.011f, 1);
        title.draw(
                secondaryBatch,
                "Game Over",
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
        handleSubmitScoreButton();
    }

    private void setupUI() {
        float buttonWidth = 275;
        float buttonHeight = 75;

        title = getTitleFont();

        // Name text field
        nameField = new TextField("John Doe", getSkin(), "default");
        nameField.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() / 2 + 100,
                buttonWidth,
                buttonHeight
        );
        nameField.setAlignment(Align.center);
        getStage().addActor(nameField);

        // Score label
        score = new Label(
                "Score: " + getGameData().getLevelInformation().getEnemiesKilled() * 100,
                getSkin(),
                "title-plain"
        );
        score.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() / 2 + 30,
                buttonWidth,
                buttonHeight
        );
        score.setAlignment(Align.center);
        getStage().addActor(score);

        // Submit button
        submitHighscoreBtn = new Label("Submit Highscore", getSkin(), "title");
        submitHighscoreBtn.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                200,
                buttonWidth,
                buttonHeight
        );
        submitHighscoreBtn.setAlignment(Align.center);
        getStage().addActor(submitHighscoreBtn);

        // Create main menu button
        mainMenuButton = new Label("Main Menu", getSkin(), "title");
        mainMenuButton.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                100,
                buttonWidth,
                buttonHeight
        );
        mainMenuButton.setAlignment(Align.center);

        getStage().addActor(mainMenuButton);
        drawScoreLabels();
    }

    private void handleSubmitScoreButton() {
        if (isMouseOnActor(submitHighscoreBtn) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK)) {
            try {
                addHighscore();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addHighscore() throws MalformedURLException, ProtocolException, IOException {
        String name = URLEncoder.encode(nameField.getText(), StandardCharsets.UTF_8);
        String score = "" + getGameData().getLevelInformation().getEnemiesKilled() * 100;

        if (name.equals("")) {
            name = "Anonymous";
        }

        URL url = new URL("https://zombiebob-map-generator.herokuapp.com/add-highscore?name=" + name + "&score=" + score);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        connection.getInputStream();
        connection.disconnect();

        getGame().setScreen(new HighscoreScreen(getGame()));
    }

    private void drawScoreLabels() {
        float buttonWidth = 275;
        float buttonHeight = 75;

        int i = 0;
        for (Label[] scoreLabel : highscoreList) {
            // Add name label
            getStage().addActor(scoreLabel[0]);
            scoreLabel[0].setBounds(
                    getStage().getWidth() / 2 - buttonWidth / 2 - 100,
                    getStage().getHeight() - 500 - buttonHeight * i,
                    buttonWidth,
                    buttonHeight
            );
            scoreLabel[0].setAlignment(Align.left);

            // Add score label
            getStage().addActor(scoreLabel[1]);
            scoreLabel[1].setBounds(
                    getStage().getWidth() / 2 - buttonWidth / 2 + 100,
                    getStage().getHeight() - 500 - buttonHeight * i,
                    buttonWidth,
                    buttonHeight
            );
            scoreLabel[1].setAlignment(Align.center);

            i++;
        }
    }

    private void handleMainMenuButton() {
        if ((isMouseOnActor(mainMenuButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            getGame().setScreen(new MainMenuScreen(getGame()));
        }
    }

}
