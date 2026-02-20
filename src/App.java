import processing.core.PApplet;
import processing.core.PConstants;

public class App extends PApplet {
    public static int width = 750;
    public static int height = 600;

    CardGame cardGame = new Blackjack();
    private int timer;

    public static void main(String[] args) {
        PApplet.main("App");
    }
    
    @Override
    public void settings() {
        size(width, height);  
    }

    @Override
    public void draw() {
        background(0, 100, 0);
        cardGame.initializeGame();
        cardGame.drawChoices(this);
        
        
    }

    //     cardGame.playerOneHand.draw(this);
    //     // Draw computer hand
    //     cardGame.playerTwoHand.draw(this);
        
    //     // Draw draw button
    //     fill(200);
    //     System.out.println(cardGame.dealButton);
    //     fill(0);
    //     textAlign(CENTER, CENTER);
    //     text("Draw", cardGame.drawButton.x + cardGame.drawButton.width / 2, cardGame.drawButton.y + cardGame.drawButton.height / 2);

    //     // Display current player
    //     fill(0);
    //     textSize(16);
    //     text("Current Player: " + cardGame.getCurrentPlayer(), width / 2, 20);

    //     // Display deck size
    //     text("Deck Size: " + cardGame.getDeckSize(), width / 2,
    //             height - 20);
    //     // Display last played card
    //     if (cardGame.getLastPlayedCard() != null) {
    //         cardGame.getLastPlayedCard().setPosition(width / 2 - 40, height / 2 - 60, 80, 120);
    //         cardGame.getLastPlayedCard().draw(this);
    //     }
    //     if (cardGame.getCurrentPlayer() == "Player Two") {
    //         fill(0);
    //         textSize(16);
    //         text("Computer is thinking...", width / 2, height / 2 + 80);
    //         timer++;
    //         if (timer == 100) {
    //             cardGame.handleComputerTurn();
    //             timer = 0;
    //         }
    //     }

    //     cardGame.drawChoices(this);
    // }

    
    // @Override
    // public void mousePressed() {
    //     cardGame.handleDrawButtonClick(mouseX, mouseY);
    //     cardGame.handleCardClick(mouseX, mouseY);
    // }


}
