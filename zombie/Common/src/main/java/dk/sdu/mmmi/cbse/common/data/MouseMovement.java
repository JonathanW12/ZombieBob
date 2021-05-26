package dk.sdu.mmmi.cbse.common.data;

public class MouseMovement {

    private float x;
    private float y;

    private static boolean[] keys;
    private static boolean[] pkeys;

    private static final int NUM_KEYS = 3;
    public static final int LEFTCLICK = 0;
    public static final int RIGHTCLICK = 1;
    public static final int MIDDLECLICK = 2;
    private int scroll = 0;

    public MouseMovement() {
        keys = new boolean[NUM_KEYS];
        pkeys = new boolean[NUM_KEYS];
    }

    public void setMousePosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            pkeys[i] = keys[i];
        }
    }

    public void setKey(int k, boolean b) {
        keys[k] = b;
    }

    public boolean isDown(int k) {
        return keys[k];
    }

    public boolean isPressed(int k) {
        return keys[k] && !pkeys[k];
    }

    public int getScroll() {
        return scroll;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
    }
}
