package blackjack;
import java.util.Scanner;
import java.util.Vector;

public class CardSet {
    //cardSet provides the cards available to the current session
    //The availability of these cards are determined by Ruleset

    private Vector<Card> cardDeck;

    public CardSet(int ruleChoice) {
        createDeck(ruleChoice);
    }

    private void createDeck(int ruleChoice) {
        cardDeck = new Vector<>();
        String[] cardType = {"Hearts", "Diamonds", "Spades", "Clubs"};
        String[] cardValue = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        if (ruleChoice == 1) {

            int cardTypeI=0;
            for (String newType:cardType) {

                int cardValueI=0;
                for (String newValue:cardValue) {

                    cardDeck.add(new Card(newValue + " of " + newType, cardValueI, cardTypeI));
                    cardValueI++;

                }
                cardTypeI++;

            }

        } else if(ruleChoice == 2){
            for(int i=0; i< 4; i++) {
                int cardTypeI = 0;
                for (String newType : cardType) {

                    int cardValueI = 0;
                    for (String newValue : cardValue) {

                        cardDeck.add(new Card(newValue + " of " + newType, cardValueI, cardTypeI));
                        cardValueI++;

                    }
                    cardTypeI++;

                }
            }

        }
        else if (ruleChoice == 3) {
            //Unlimited deck option for advanced ruleset is still a work-in-progress.
            System.out.println("Playing with 4 decks");
            int numOfDecks = 4;
            for (int currDeck = 1; currDeck < numOfDecks; currDeck++) {

                int cardTypeI=0;
                for(String newType:cardType) {

                    int cardValueI=0;
                    for(String newValue:cardValue) {
                        cardDeck.add(new Card(newValue + " of " + newType, cardValueI, cardTypeI));
                        cardValueI++;

                    }

                    cardTypeI++;
                }
            }
        } else {
            System.out.println("Invalid Ruleset");
        }
    }

    public Vector<Card> generateDeck() {
        return cardDeck;
    }

}