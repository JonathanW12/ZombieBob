package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.MouseMovement;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;

import java.util.ArrayList;

public class GameOverScreen extends MenuScreenTemplate implements Screen {

    private BitmapFont title;
    private final SpriteBatch secondaryBatch;
    private Label mainMenuButton,submitHighscoreBtn, score;
    private TextField nameField;
    private ArrayList<Label[]> highscoreList = new ArrayList<>();
    private final static String highscoreURL = "https://zombiebob-map-generator.herokuapp.com/get-highscores";
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
                "GameOver",
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

        addHighscore();



        nameField = new TextField("John Doe", getSkin(), "default");
        nameField.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() / 2,
                buttonWidth,
                buttonHeight
        );
        nameField.setAlignment(Align.center);
        getStage().addActor(nameField);

        String enemiesKilled = "" + getGameData().getLevelInformation().getEnemiesKilled();

        score = new Label(enemiesKilled, getSkin(), "title-plain");
        score.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() / 3,
                buttonWidth,
                buttonHeight
        );
        score.setAlignment(Align.center);
        getStage().addActor(score);

        submitHighscoreBtn = new Label("Submit Highscore", getSkin(), "title");
        submitHighscoreBtn.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() / 84,
                buttonWidth,
                buttonHeight
        );
        submitHighscoreBtn.setAlignment(Align.center);

        // Create main menu button
        mainMenuButton = new Label("Main Menu", getSkin(), "title");
        mainMenuButton.setBounds(
                getStage().getWidth() / 2 - buttonWidth / 2,
                getStage().getHeight() / 8,
                buttonWidth,
                buttonHeight
        );
        mainMenuButton.setAlignment(Align.center);

        getStage().addActor(mainMenuButton);
        drawScoreLabels();
    }

    private void addHighscore() {

    }

    private void drawScoreLabels() {
        float buttonWidth = 275;
        float buttonHeight = 75;

        int i = 0;
        for (Label[] scoreLabel: highscoreList) {
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

    /*
    // From https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject getHighscoresJSON() throws IOException, JSONException {
        InputStream inputStream = new URL(highscoreURL).openStream();

        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            inputStream.close();
        }
    }
*/
    private void handleMainMenuButton() {
        if ((isMouseOnActor(mainMenuButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            getGame().setScreen(new MainMenuScreen(getGame()));
        }
    }

}
