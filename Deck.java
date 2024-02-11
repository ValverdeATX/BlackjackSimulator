package blackjack;

import java.util.*;

public class Deck {
    private List<Card> cards;
    private Vector<Card> originalCards;

    public Deck(Vector<Card> cards) {
        originalCards=new Vector<Card>(cards);
        this.cards = new ArrayList<>(cards);
    }

    public void returnCards(Hand handToReturn){
        cards.addAll(handToReturn.clearHand());
    }

    public Card getCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0);  // Get and remove the card from the front of the list
        } else {
            return null;  // Or throw an exception, depending on your design
        }
    }

    public void shuffle() {
        Collections.shuffle(cards, new Random());
    }
}