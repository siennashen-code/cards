import processing.core.PApplet;

public class ClickableRectangle {
    int x;
    int y;
    int width;
    int height;

    boolean isClicked(int mouseX, int mouseY) {
        return mouseX >= x-width/2 && mouseX <= x + width/2 &&
               mouseY >= y-height/2 && mouseY <= y + height/2;
    }

    public void draw(PApplet app) {
        app.rect(x, y, width, height);
    } 
}
