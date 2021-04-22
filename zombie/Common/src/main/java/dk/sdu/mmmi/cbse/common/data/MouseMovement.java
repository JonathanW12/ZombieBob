package dk.sdu.mmmi.cbse.common.data;

public class MouseMovement {
    private float x;
    private float y;

    private boolean leftClick;
    private boolean rightClick;
    private boolean middleClick;
    private int scroll = 0;

    public void setMousePosition(float x,float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isLeftClick() {
        return leftClick;
    }

    public void setLeftClick(boolean leftClick) {
        this.leftClick = leftClick;
    }

    public boolean isRightClick() {
        return rightClick;
    }

    public void setRightClick(boolean rightClick) {
        this.rightClick = rightClick;
    }

    public boolean isMiddleClick() {
        return middleClick;
    }

    public void setMiddleClick(boolean middleClick) {
        this.middleClick = middleClick;
    }

    public int getScroll() {
        return scroll;
    }

    public void setScroll(int scroll) {
        this.scroll = scroll;
    }
}
