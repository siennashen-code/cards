import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import processing.core.PImage;

public class Blackjack extends CardGame {
    // Player features
    BlackjackHand dealerHand;
    BlackjackHand playerOneHand;
    int playerMoney;

    // Buttons
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
    boolean gameOver = true;

    int page = 0; // Which page on the interface you are on

    PApplet app = new PApplet();

    @Override
    protected void initializeGame() {

        float[] buttonColor = { 255, 0, 0 }; // Initialize buttons
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

        dealerHand = new BlackjackHand(); // Initialize hands
        playerOneHand = new BlackjackHand();

        gameActive = true;
        playerOneTurn = true;

        createDeck(app);
    }

    void createDeck(PApplet app) {
        deck = new ArrayList<>();

        for (int i = 0; i < 3; i++) { // This game will be played with 3 decks
            String[] suits = { "hearts", "diamonds", "clubs", "spades" };
            String[] values = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace" };
            for (String suit : suits) {
                for (String value : values) {
                    deck.add(new Card(value, suit));
                }
            }
        }

    }

    public void initialDeal() { // Deals 2 cards to player and dealer
        drawCard(this.playerOneHand);
        drawCard(this.playerOneHand);
        drawCard(dealerHand);
        drawCard(dealerHand);
    }

    public void drawCard(BlackjackHand hand) { // Draw a card for a hand, and reposition that hand
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
            if (dealButton.isClicked(mouseX, mouseY)) { // Deal button
                initialDeal();
                page = 1;
            }
        
        } else if (page == 1) {
            if (hitButton.isClicked(mouseX, mouseY) && gameActive) { // Hit button
                drawCard(playerOneHand);
            } else if (standButton.isClicked(mouseX, mouseY) && gameActive) { // Stand button
                gameActive = false; // gameActive: whether or not player can hit or stand
                playerOneTurn = false; // playerOneTurn: Determines whether or not it's the dealer's turn to play. This
                                       // is different from gameActive because if you bust, there is 1 second where
                                       // gameActive is false but playerOneTurn is still true
            }
       
        } else if (page == 2) {
            if (againButton.isClicked(mouseX, mouseY)) { // Another round button
                playerOneHand = new BlackjackHand();
                dealerHand = new BlackjackHand();
                shuffleDeckBeginning();
                playerOneTurn = true;
                page = 0;
                roundOver = false;
                gameActive = true;
            } else if (endButton.isClicked(mouseX, mouseY)) { // End game button
                page = 3;
            }

        } else if (page == 3) {
            if (startButton.isClicked(mouseX, mouseY)) { // New game button
                shuffleDeckBeginning();
                playerOneHand = new BlackjackHand();
                dealerHand = new BlackjackHand();
                page = 0;
                playerMoney = 0;
                roundOver = false;
                playerOneTurn = true;
                gameActive = true;
                gameOver = true;
            }
        }
    }

    void shuffleDeckBeginning() { // Reset deck + shuffle
        for (int i = 0; i < playerOneHand.getSize(); i = i + 1) {
            Card reshuffle = playerOneHand.getCard(i);
            deck.add(reshuffle);
            playerOneHand.removeCard(reshuffle);
        }
        for (int i = 0; i < dealerHand.getSize(); i = i + 1) {
            Card reshuffle = dealerHand.getCard(i);
            deck.add(reshuffle);
            dealerHand.removeCard(reshuffle);
        }
        
        Collections.shuffle(deck);
    }

    @Override
    public void handleComputerTurn() {
        drawCard(dealerHand);
    }

    String determineWinner() { // Determine final message after round
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

    void updateBalance() {
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

        roundOver = true;

    }

}
