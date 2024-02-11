package blackjack;

import java.util.*;

public class Hand {
    private List<Card> cards;

    // Constructor
    public Hand(Card card1, Card card2) {
        this.cards = new ArrayList<>();
        this.cards.add(card1);
        this.cards.add(card2);
    }

    private Hand(Card card1){
        this.cards = new ArrayList<>();
        this.cards.add(card1);
    }

    // Add a card to the hand
    public void hit(Card card) {
        if(this.getSum()==0){
            System.out.println("Critical error, hit called on busted hand");
            System.exit(1);
        }
        this.cards.add(card);
    }

    public Hand split(){
        if(cards.size()!=2){
            System.out.println("Critical error, split called on incorrect hand size");
            System.exit(1);
        }
        return new Hand(cards.remove(1)); //This returns a new hand
    }

    public void doubleDown(Card card){
        hit(card);
    }

    // Get a copy of the card list
    public List<Card> getCards() {
        return Collections.unmodifiableList(this.cards);
    }

    public int getSum(){ //returns the value of the hand.  If it's a blackjack it returns 22.  If it's busted, it returns 0.

        int sum=0;
        int aceCount=0;
        for(Card card : cards){
            if(card.getCardVal() != 11){
                sum+=card.getCardVal();
            } else {
                aceCount+=1;
            }
        }

        if(sum==10 && cards.size()==2 && aceCount==1){
            return 22;
        }

        for(int i=0; i<aceCount; i++){
            if(sum + 11 <= 21){
                sum+=11;
            } else {
                sum+=1;
            }
        }

        if (sum > 21){
            sum=0;
        }

        return sum;
    }

    public List<Card> clearHand() {
        List<Card> oldCards = new ArrayList<>(this.cards);
        this.cards.clear();
        return oldCards;
    }
}