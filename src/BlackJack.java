import java.util.ArrayList;
import java.util.Collections;

import processing.core.PApplet;
import processing.core.PImage;

public class Blackjack extends CardGame {
    BlackjackHand dealerHand = new BlackjackHand("dealer"); 
    BlackjackHand playerOneHand = new BlackjackHand("player");
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

        // Initialize decks
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
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

    @Override
    public void drawCard(Hand hand){
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

        int start = 750/2-(80*hand.getSize()+10*(hand.getSize()-1))/2;
        if (hand.equals(playerOneHand)){
            hand.positionCards(start, 700-230, 80, 120, 90);
            System.out.println("positioned");
        } else if (hand.equals(dealerHand)){
            hand.positionCards(start, 110, 80, 120, 90);
        }
        
    }
    
    public void handleButtonClick(int mouseX, int mouseY) {
        if (dealButton.isClicked(mouseX, mouseY) && !dealed) {
            initialDeal();
            dealed = true;
        } else if (hitButton.isClicked(mouseX, mouseY) && dealed && playerOneTurn) {
            drawCard(playerOneHand);
        } else if (standButton.isClicked(mouseX, mouseY) && dealed && playerOneTurn) {
            playerOneTurn = false;
        }
    }

    public void initialDeal() { //Deals 2 cards to player and dealer
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
