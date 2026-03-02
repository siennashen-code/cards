import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import processing.core.PImage;

public class Blackjack extends CardGame {
    Hand dealerHand; 
    Boolean dealed = false;

    Button dealButton;
    Button hitButton;
    Button standButton;

    public static int width = 750;
    public static int height = 700;

    static PImage deckImg;

    Blackjack(){
        super();
    }

    @Override
    protected void initializeGame() {
        // Initialize buttons
        float[] dealButtonColor = { 255, 0, 0 };
        float[] dealTextColor = { 255, 255, 255 };
        dealButton = new Button(dealButtonColor, dealTextColor, "Deal");
        dealButton.width = 100;
        dealButton.height = 50;
        dealButton.x = width/2;
        dealButton.y = height/2;

        float[] hitButtonColor = { 255, 0, 0 };
        float[] hitTextColor = { 255, 255, 255 };
        hitButton = new Button(hitButtonColor, hitTextColor, "Hit");
        hitButton.width = 100;
        hitButton.height = 50;
        hitButton.x = width/2 - 60;
        hitButton.y = height-50;

        float[] standButtonColor = { 255, 0, 0 };
        float[] standTextColor = { 255, 255, 255 };
        standButton = new Button(standButtonColor, standTextColor, "Stand");
        standButton.width = 100;
        standButton.height = 50;
        standButton.x = width/2 + 60;
        standButton.y = height-50;

        // Initialize decks and hands
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
        playerOneHand = new Hand();
        dealerHand = new Hand();
        gameActive = true;

        createDeck();
        Collections.shuffle(deck);
        
    }

    @Override
    protected void createDeck() { // This Blackjack will be played with 3 decks
        for (int i = 0; i < 3; i++) {
            super.createDeck();
        }
    }

    public void handleButtonClick(int mouseX, int mouseY) {
        if (dealButton.isClicked(mouseX, mouseY) && !dealed) {
            initialDeal();
            dealed = true;
        } else if (hitButton.isClicked(mouseX, mouseY) && dealed && playerOneTurn) {
            dealCards(1, playerOneHand);
            // if (calculateScore(playerOneHand) > 21) {
                
            //     gameActive = false;
            // }
        } else if (standButton.isClicked(mouseX, mouseY) && dealed && playerOneTurn) {
            playerOneTurn = false;
        }
    }

    public void initialDeal() { //Deals 2 cards to player and dealer
        playerOneHand = dealCards(2, playerOneHand);
        dealerHand = dealCards(2, dealerHand);
    }

    protected Hand dealCards(int numCards, Hand targetHand) { //Deals 1 card to somebody
        for (int i = 0; i < numCards; i++) {
            if (!deck.isEmpty()) {
                targetHand.addCard(deck.remove(0));
            }
        }

        int targetStart = 750/2-(80*targetHand.getSize()+10*(targetHand.getSize()-1))/2;
        if (targetHand.equals(playerOneHand)){
            targetHand.positionCards(targetStart, height-230, 80, 120, 90);
        } else if (targetHand.equals(dealerHand)){
            targetHand.positionCards(targetStart, 110, 80, 120, 90);
        }

        return targetHand;
    }

    @Override
    public void handleComputerTurn() {
        dealCards(1,dealerHand);

    }

    int calculateScore(Hand hand) { //Calculates sum of all card values
        int score = 0;

        for (Card c : hand.getCards()) {
            String value = c.value; 
            if (value.equals("J") || value.equals("Q") || value.equals("K")) {
                score += 10;
            } else if (value.equals("A")) {
                if (score + 11 < 21) { //Ace can either be worth 1 or 11
                    score += 11;
                } else {
                    score += 1;
                }
            } else {
                score += Integer.valueOf(value);
            }
        }

        return score;

    }

    String determineWinner() {
        int pScore = calculateScore(playerOneHand);
        int dScore = calculateScore(dealerHand);

        if (pScore > 21)
            return "Dealer wins!";
        else if (dScore > 21)
            return "You win!";
        else if (pScore > dScore)
            return "You win!";
        else if (dScore > pScore)
            return "Dealer Wins!";
        else
            return "You tied!";
    }

    @Override
    public void drawChoices(PApplet app){

        dealButton.draw(app);
        hitButton.draw(app);
        standButton.draw(app);
    }

}
