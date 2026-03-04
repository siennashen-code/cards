import processing.core.PApplet;

public class App extends PApplet {

    Blackjack cardGame = new Blackjack();
    int timer = 0;

    public static void main(String[] args) {
        PApplet.main("App");
    }

    @Override
    public void settings() {
        size(Blackjack.width, Blackjack.height);
        Blackjack.deckImg = loadImage("deckImg.png");
        Blackjack.backgroundImg = loadImage("background.jpg");
        cardGame.initializeGame();
        for (Card c : cardGame.deck) {
            c.img = loadImage("cardFaces/" + c.value + "_of_" + c.suit + ".png");
        }

    }

    @Override
    public void draw() {

        imageMode(CENTER);
        image(Blackjack.backgroundImg, width / 2, height / 2, 750, 700);

        if (cardGame.page == 0) {
            push();
            textAlign(CENTER, CENTER);
            textSize(50);
            if (cardGame.newGame) {
                text("LET'S PLAY BLACKJACK", width / 2, 100);
            }
            pop();

            image(Blackjack.deckImg, width / 2, height / 2, 160, 200);
            cardGame.dealButton.draw(this);
        } else {
            cardGame.dealerHand.draw(this);
            cardGame.playerOneHand.draw(this);
            
            if (cardGame.page == 1) {
                imageMode(CENTER);
                image(Blackjack.deckImg, width / 2, height / 2, 160, 200);
                gamePlay();

            } else if (cardGame.page == 2) {
                push();
                fill(100, 100, 100, 200);
                rect(0,0, 750, 700);
                pop();

                roundOver();
            } else if (cardGame.page == 3) {
                endPage();
            }
        }
    }

    public void gamePlay() {

        if (cardGame.playerOneTurn) {
            if (cardGame.gameActive) {
                cardGame.hitButton.draw(this);
                cardGame.standButton.draw(this);
            }

            if (cardGame.playerOneHand.calculateScore() > 21) {
                cardGame.gameActive = false;
                if (timer == 60) {
                    timer = 0;
                    cardGame.page = 2;
                    cardGame.playerOneTurn = false;
                }
                timer++;
            }

        } else {
            push();
            textAlign(CENTER, CENTER);
            text("Dealer's turn...", width / 2, 50);
            pop();
            if (timer == 60) {
                timer = 0;
                if (cardGame.dealerHand.calculateScore() < 17) {
                    cardGame.handleComputerTurn();
                } else {
                    cardGame.page = 2;
                }
            }
            timer++;
        }

    }

    public void roundOver() {
        push();
        textAlign(CENTER, CENTER);

        if (!cardGame.roundOver) {
            cardGame.updateBalance();
            cardGame.roundOver = true;
        }

        textSize(30);
        text(cardGame.determineWinner(), width / 2, height / 2);

        cardGame.againButton.draw(this);
        cardGame.endButton.draw(this);

        push();
        textAlign(RIGHT, TOP);
        if (cardGame.playerMoney < 0) {
            text("Balance: -$" + cardGame.playerMoney * -1, width - 10, 10);
        } else {
            text("Balance: $" + cardGame.playerMoney, width - 10, 10);
        }
        pop();

        pop();
    }

    public void endPage() {
        cardGame.startButton.draw(this);

        push();
        textAlign(CENTER, CENTER);
        if (cardGame.playerMoney > 0) {
            text("You're a pro gambler! You earned $" + cardGame.playerMoney + "!", width / 2, height / 2);
        } else if (cardGame.playerMoney == 0) {
            text("You earned nothing!", width / 2, height / 2);
        } else {
            text("Better quit gambling! You lost $" + -1 * cardGame.playerMoney + "!", width / 2, height / 2);
        }

        pop();
    }

    @Override
    public void mousePressed() {
        cardGame.handleButtonClick(mouseX, mouseY);
    }

}
