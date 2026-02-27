import processing.core.PApplet;
import processing.core.PConstants;

public class App extends PApplet {
    public static int width = 750;
    public static int height = 600;

    Blackjack cardGame = new Blackjack();

    public static void main(String[] args) {
        PApplet.main("App");
    }

    @Override
    public void settings() {
        size(width, height);
        cardGame.initializeGame();
    }

    @Override
    public void draw() {
        background(0, 100, 0);
        cardGame.drawChoices(this);

        if (cardGame.dealed) {
            cardGame.dealerHand.draw(this);
            cardGame.playerOneHand.draw(this);
        }

    }
   

    @Override
    public void mousePressed() {
        // if (cardGame.dealButton.isClicked(mouseX, mouseY)) {
        //     cardGame.initialDeal();
        //     dealed = true;
        // }
        cardGame.handleButtonClick(mouseX, mouseY);
    
    }

}
