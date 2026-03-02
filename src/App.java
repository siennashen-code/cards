import processing.core.PApplet;
import processing.core.PConstants;

public class App extends PApplet {

    Blackjack cardGame = new Blackjack();
    int timer = 0;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    @Override
    public void settings() {
        // size(750, 700);
        size(Blackjack.width, Blackjack.height);
        Blackjack.deckImg = loadImage("deckImg.png");
        cardGame.initializeGame();
    }

    @Override
    public void draw() {
        background(0, 100, 0);

        if (cardGame.gameActive) {
            imageMode(CENTER);
            image(Blackjack.deckImg, width / 2, height / 2, 160, 200);
            gamePlay();
        } else {
            roundOver();
        }

    }

    public void gamePlay() {
        if (!cardGame.dealed) {
            cardGame.dealButton.draw(this);
        } else {
            cardGame.dealerHand.draw(this);
            cardGame.playerOneHand.draw(this);

            if (cardGame.playerOneTurn) {
                cardGame.hitButton.draw(this);
                cardGame.standButton.draw(this);
               
                if (cardGame.calculateScore(cardGame.playerOneHand) > 21) {
                    cardGame.playerOneTurn = false;
                    if (timer == 120){
                        timer = 0;
                        cardGame.gameActive = false;
                    }
                    timer ++;
                }
        
            } else {
                if (timer == 60) {
                    timer = 0;
                    if (cardGame.calculateScore(cardGame.dealerHand) < 17) {
                        System.out.println("Dealer Drawing...");
                        cardGame.dealCards(1, cardGame.dealerHand);

                    } else {
                        cardGame.gameActive = false;
                    }
                }
                timer++;
            }

        }
    }

    public void roundOver() {
        push();
        textAlign(CENTER, CENTER);
        text(cardGame.determineWinner(), width / 2, height / 2);
        text("Your score: " + cardGame.calculateScore(cardGame.playerOneHand), width / 2, height / 2 - 20);
        text("Dealer score: " + cardGame.calculateScore(cardGame.dealerHand), width / 2, height / 2 - 40);
        pop();
    }

    @Override
    public void mousePressed() {
        cardGame.handleButtonClick(mouseX, mouseY);
    }

}
