public class BlackjackHand extends Hand { 

    BlackjackHand(String player){
        super();
    }

    int calculateScore() { //Calculates sum of all card values
        int score = 0;

        for (Card c : getCards()) {
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



    
}
