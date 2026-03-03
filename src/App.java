import processing.core.PApplet;

public class App extends PApplet {

    Blackjack cardGame = new Blackjack();
    int timer = 0;
    boolean roundOver = false;;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    @Override
    public void settings() {
        size(Blackjack.width, Blackjack.height);
        Blackjack.deckImg = loadImage("deckImg.png");
        cardGame.initializeGame();
    }

    @Override
    public void draw() {
        background(0, 100, 0);

        if (!roundOver) {
            imageMode(CENTER);
            image(Blackjack.deckImg, width / 2, height / 2, 160, 200);
            gamePlay();
        } else {
            roundOver();
        }

    }

    public void gamePlay() {
        System.out.println(cardGame.playerOneHand.calculateScore());
        if (!cardGame.dealed) {
            cardGame.dealButton.draw(this);
        } else {
            cardGame.dealerHand.draw(this);
            cardGame.playerOneHand.draw(this);

            if (cardGame.playerOneTurn) {
                if(cardGame.gameActive){
                    cardGame.hitButton.draw(this);
                    cardGame.standButton.draw(this);
                }

                if (cardGame.playerOneHand.calculateScore() > 21) {
                    cardGame.gameActive = false;
                    if (timer == 60) {
                        timer = 0;
                        roundOver = true;
                        cardGame.playerOneTurn = false;
                    }
                    timer++;
                }

            } else {
                if (timer == 60) {
                    timer = 0;
                    if (cardGame.dealerHand.calculateScore() < 17) {
                        cardGame.handleComputerTurn();
                    } else {
                        roundOver = true;
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
        text("Your score: " + cardGame.playerOneHand.calculateScore(), width / 2, height / 2 - 20);
        text("Dealer score: " + cardGame.dealerHand.calculateScore(), width / 2, height / 2 - 40);
        pop();
    }

    @Override
    public void mousePressed() {
        if (cardGame.gameActive) {
            cardGame.handleButtonClick(mouseX, mouseY);
        }
    }

}
