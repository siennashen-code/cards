import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import processing.core.PImage;

public class Blackjack extends CardGame {
    BlackjackHand dealerHand;
    BlackjackHand playerOneHand;
    int playerMoney;
    Boolean dealed = false;

    Button dealButton;
    Button hitButton;
    Button standButton;

    Button againButton;
    Button endButton;

    Button startButton;

    public static int width = 750;
    public static int height = 700;

    static PImage deckImg;
    static PImage backgroundImg;

    boolean roundOver = false;
    boolean newGame = true;

    int page = 0;

    @Override
    protected void initializeGame() {
        page = 0;
        roundOver = false;
        // Initialize buttons
        float[] buttonColor = { 255, 0, 0 };
        float[] textColor = { 255, 255, 255 };

        dealButton = new Button(buttonColor, textColor, "Deal");
        dealButton.width = 100;
        dealButton.height = 50;
        dealButton.x = width / 2;
        dealButton.y = height / 2;

        hitButton = new Button(buttonColor, textColor, "Hit");
        hitButton.width = 100;
        hitButton.height = 50;
        hitButton.x = width / 2 - 60;
        hitButton.y = height - 50;

        standButton = new Button(buttonColor, textColor, "Stand");
        standButton.width = 100;
        standButton.height = 50;
        standButton.x = width / 2 + 60;
        standButton.y = height - 50;

        againButton = new Button(buttonColor, textColor, "Another Round!");
        againButton.width = 200;
        againButton.height = 50;
        againButton.x = width / 2 - 100;
        againButton.y = 525;

        endButton = new Button(buttonColor, textColor, "End Game");
        endButton.width = 150;
        endButton.height = 50;
        endButton.x = width / 2 + 100;
        endButton.y = 525;

        startButton = new Button(buttonColor, textColor, "New Game");
        startButton.width = 150;
        startButton.height = 50;
        startButton.x = width / 2;
        startButton.y = 525;

        // Initialize decks
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
        dealerHand = new BlackjackHand("dealer");
        playerOneHand = new BlackjackHand("player");
        gameActive = true;

        playerOneTurn = true;

        deck = new ArrayList<>();
        createDeck();
        Collections.shuffle(deck);
    }

    @Override
    protected void createDeck() { // This Blackjack will be played with 3 decks
        for (int i = 0; i < 3; i++) {
            super.createDeck();
        }
    }

    public void drawCard(BlackjackHand hand) {
        if (deck != null && !deck.isEmpty()) {
            hand.addCard(deck.remove(0));
        } else if (discardPile != null && discardPile.size() > 1) {
            // Reshuffle discard pile into deck if deck is empty
            lastPlayedCard = discardPile.remove(discardPile.size() - 1);
            deck.addAll(discardPile);
            discardPile.clear();
            discardPile.add(lastPlayedCard);
            Collections.shuffle(deck);

            if (!deck.isEmpty()) {
                hand.addCard(deck.remove(0));
            }
        }

        int start = 750 / 2 - (80 * hand.getSize() + 10 * (hand.getSize() - 1)) / 2;
        if (hand.equals(playerOneHand)) {
            int y = 700 - 230;
            hand.positionCards(start, y, 80, 120, 90);
        } else if (hand.equals(dealerHand)) {
            hand.positionCards(start, 110, 80, 120, 90);
        }
    }

    public void handleButtonClick(int mouseX, int mouseY) {
        if (page == 0) {
            initializeGame();
            if (dealButton.isClicked(mouseX, mouseY)) {
                initialDeal();
                page = 1;
            }
        } else if (page == 1) {
            if (hitButton.isClicked(mouseX, mouseY) && playerOneTurn) {
                drawCard(playerOneHand);
            } else if (standButton.isClicked(mouseX, mouseY) && playerOneTurn) {
                playerOneTurn = false;
            }
        } else if (page == 2) {
            if (againButton.isClicked(mouseX, mouseY)) {
                initializeGame();
                newGame = false;
            } else if (endButton.isClicked(mouseX, mouseY)) {
                page = 3;
                System.out.println("got to page 3!");
            }
        } if (page == 3){
            if (startButton.isClicked(mouseX, mouseY)){
                initializeGame();
                playerMoney = 0;
                newGame = true;
            }
        }
    }

    public void initialDeal() { // Deals 2 cards to player and dealer
        drawCard(playerOneHand);
        drawCard(playerOneHand);
        drawCard(dealerHand);
        drawCard(dealerHand);
    }

    @Override
    public void handleComputerTurn() {
        drawCard(dealerHand);
    }

    String determineWinner() {
        int pScore = playerOneHand.calculateScore();
        int dScore = dealerHand.calculateScore();

        if (pScore > 21) {
            return "BUSTED!\nDealer wins";
        } else if (dScore > 21) {
            return "DEALER BUSTED!\nYou win";
        } else if (pScore > dScore) {
            return "Dealer is done." + "\n" +
                    "Your cards added to: " + playerOneHand.calculateScore() + "\n" +
                    "Dealer's cards added to: " + dealerHand.calculateScore() + "\n"
                    + "You win!";
        } else if (dScore > pScore) {
            return "Dealer is done." + "\n" +
                    "Your cards added to: " + playerOneHand.calculateScore() + "\n" +
                    "Dealer's cards added to: " + dealerHand.calculateScore() + "\n"
                    + "Dealer wins!";
        } else {
            return "PUSH!\nYou tied";
        }
    }

    void updateBalance(){
         int pScore = playerOneHand.calculateScore();
        int dScore = dealerHand.calculateScore();

        if (pScore > 21) {
                playerMoney -= 5;
        } else if (dScore > 21) {
            playerMoney += 5;
          
        } else if (pScore > dScore) {
            playerMoney += 5;
            
        } else if (dScore > pScore) {
            playerMoney -= 5;
           
        } 

    }

    @Override
    public void drawChoices(PApplet app) {
        dealButton.draw(app);
        hitButton.draw(app);
        standButton.draw(app);
    }

}
