import processing.core.PApplet;

public class BlackjackHand extends Hand {  // Class for the special point system of cards in Blackjack

    int calculateScore() { // Calculates sum of hand
        int score = 0;
        
        for (Card c : getCards()) {
            String value = c.value; 
            if (value.equals("jack") || value.equals("queen") || value.equals("king")) { // J, Q, K worth 10 
                score += 10;
            } else if (value.equals("ace")) {
                if (score + 11 <= 21) { //Ace can either be worth 1 or 11
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

    @Override
    void draw(PApplet sketch) {
        for (Card card : getCards()) {
            sketch.push();
            sketch.imageMode(sketch.CORNER);
            sketch.image(card.img, card.x, card.y, 80, 120);
            sketch.pop();

            if (card.equals(getCards().get(getSize()-1))){
                sketch.textSize(20);
                sketch.fill(255);
                sketch.text(calculateScore(), card.x + 95, card.y + 120); // Print sum of all cards
            }
        }
    }  
}
