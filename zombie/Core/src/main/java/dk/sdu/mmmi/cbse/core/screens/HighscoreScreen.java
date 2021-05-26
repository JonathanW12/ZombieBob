package dk.sdu.mmmi.cbse.core.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import dk.sdu.mmmi.cbse.common.data.MouseMovement;
import dk.sdu.mmmi.cbse.core.main.ZombieBobGame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import org.json.JSONArray;

public class HighscoreScreen extends MenuScreenTemplate implements Screen {

    private BitmapFont title;
    private final SpriteBatch secondaryBatch;
    private Label mainMenuButton, highscoreName, highscoreScore;
    private ArrayList<Label[]> highscoreList = new ArrayList<>();
    private final static String highscoreURL = "https://zombiebob-map-generator.herokuapp.com/get-highscores";

    public HighscoreScreen(ZombieBobGame game) {
        super(game);
        secondaryBatch = new SpriteBatch();

        setupUI();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        secondaryBatch.begin();

        float estimatedTitleWidth = 660;
        title.setColor(0.541f, 0.011f, 0.011f, 1);
        title.draw(
                secondaryBatch,
                "Highscores",
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

        addScoreLabels();

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

    private void addScoreLabels() {
        try {
            JSONObject j = getHighscoresJSON();
            JSONArray highscores = j.getJSONArray("highscores");

            int highScoreLength = highscores.length();

            if (highScoreLength > 5) {
                highScoreLength = 5;
            }

            for (int i = 0; i < highScoreLength; i++) {
                String name = highscores.getJSONObject(i).getString("name");
                String score = highscores.getJSONObject(i).getString("score");

                highscoreName = new Label("" + name, getSkin(), "title-plain");
                highscoreScore = new Label("" + score, getSkin(), "title-plain");

                Label[] scoreLabels = {highscoreName, highscoreScore};
                highscoreList.add(scoreLabels);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
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
            scoreLabel[1].setAlignment(Align.right);

            i++;
        }
    }

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

    private void handleMainMenuButton() {
        if ((isMouseOnActor(mainMenuButton) && getGameData().getMouse().isPressed(MouseMovement.LEFTCLICK))) {
            getGame().setScreen(new MainMenuScreen(getGame()));
        }
    }

}
