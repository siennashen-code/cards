import java.util.ArrayList;
import java.util.Collections;
import processing.core.PApplet;

public class BlackJack extends CardGame {
    
    PApplet app = new PApplet();
    Hand dealerHand; 
    Boolean dealed = false;
    Boolean playerOneDone = false;
    boolean dealerDone = false;

    Button dealButton;
    Button hitButton;
    Button standButton;

    
    public BlackJack() {
        initializeGame();
    }

    @Override
    protected void initializeGame() {
        // Initialize buttons
        float[] dealButtonColor = { 255, 0, 0 };
        float[] dealTextColor = { 255, 255, 255 };
        dealButton = new Button(dealButtonColor, dealTextColor, "Deal");
        dealButton.width = 100;
        dealButton.height = 50;
        dealButton.x = App.width/2;
        dealButton.y = App.height/2;

        float[] hitButtonColor = { 255, 0, 0 };
        float[] hitTextColor = { 255, 255, 255 };
        hitButton = new Button(hitButtonColor, hitTextColor, "Hit");
        hitButton.width = 100;
        hitButton.height = 50;
        hitButton.x = App.width/2 - 60;
        hitButton.y = App.height - 70;

        float[] standButtonColor = { 255, 0, 0 };
        float[] standTextColor = { 255, 255, 255 };
        standButton = new Button(standButtonColor, standTextColor, "Stand");
        standButton.width = 100;
        standButton.height = 50;
        standButton.x = App.width/2 + 60;
        standButton.y = App.height - 70;

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
        } else if (hitButton.isClicked(mouseX, mouseY) && dealed && !playerOneDone) {
            dealCards(1, playerOneHand);
            if (calculateScore(playerOneHand) > 21) {
                playerOneDone = true;
                determineWinner();
            }
        } else if (standButton.isClicked(mouseX, mouseY) && dealed && !playerOneDone) {
            playerOneDone = true;
            dealerTurn();
        }
    }

    public void initialDeal() { //Deals 2 cards to player and dealer
        dealCards(2, playerOneHand);
        dealCards(2, dealerHand);

        //Positions player and dealer cards
        playerOneHand.positionCards(50, 450, 80, 120, 20);
        dealerHand.positionCards(50, 50, 80, 120, 20);
    }

    protected void dealCards(int numCards, Hand targetHand) { //Deals 1 card to somebody
        for (int i = 0; i < numCards; i++) {
            if (!deck.isEmpty()) {
                targetHand.addCard(deck.remove(0));
            }
        }
    }

    private void dealerTurn() {
        // Standard House Rules: Dealer hits until 17 or higher
        while (calculateScore(dealerHand) < 17) {
            dealCards(1, dealerHand);
        }
        dealerDone = true;
        determineWinner();
    }

    private int calculateScore(Hand hand) { //Calculates sum of all card values
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

    private void determineWinner() {
        int pScore = calculateScore(playerOneHand);
        int dScore = calculateScore(dealerHand);

        if (pScore > 21)
            System.out.println("Player Busted! Dealer Wins.");
        else if (dScore > 21)
            System.out.println("Dealer Busted! Player Wins.");
        else if (pScore > dScore)
            System.out.println("Player Wins!");
        else if (dScore > pScore)
            System.out.println("Dealer Wins!");
        else
            System.out.println("It's a Push!");

        gameActive = false;
    }

    @Override
    public void drawChoices(PApplet app){
        dealButton.draw(app); //what does the this do?
        hitButton.draw(app);
        standButton.draw(app);
    }

}
