package dk.sdu.mmmi.cbse.commonlevel;

public class LevelInformation {

    int currentLevel;
    int enemiesKilled;

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public void setEnemiesKilled(int enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    public void resetLevel() {
        currentLevel = 0;
        enemiesKilled = 0;
    }
}
