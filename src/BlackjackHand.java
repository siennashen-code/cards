import processing.core.PApplet;

public class BlackjackHand extends Hand { 

    BlackjackHand(String player){
        super();
    }

    int calculateScore() { //Calculates sum of all card values
        int score = 0;
        for (Card c : getCards()) {
            String value = c.value; 
            if (value.equals("jack") || value.equals("queen") || value.equals("king")) {
                score += 10;
            } else if (value.equals("ace")) {
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
                sketch.text(calculateScore(), card.x + 90, card.y + 120); //print sum of all cards
            }
        }
    }  
}
