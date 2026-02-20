import processing.core.PApplet;

public class Button extends ClickableRectangle {
    float[] rectColor = new float[3];
    float[] textColor = new float[3];
    String text;

    public Button(float[]rectColor, float[]textColor, String text){
        this.rectColor = rectColor;
        this.textColor = textColor;
        this.text = text;
    }

    @Override
    public void draw(PApplet app) {
        app.rectMode(app.CENTER);
        app.textAlign(app.CENTER, app.CENTER);

        app.fill(rectColor[0], rectColor[1], rectColor[2]);
        app.rect(x, y, width, height);

        app.textSize(25);
        app.fill(textColor[0], textColor[1], textColor[2]);
        app.text(text, x, y);
    } 
}
