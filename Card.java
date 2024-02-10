package blackjack;

public class Card {

    //I've adapted this class to fit with the cardSet so that I can easily get integer values
    //While it's not ideal for ambiguous types to be here, I don't want to make
    //a set of dummy variables.

    //cardVal of 2-10 for non-face cards, 10 for face cards, 11 for Ace (Special case)
    private final int cardVal;

    //cardType of 0-3 as Hearts Diamonds Spades Clubs respectively
    private final int cardType;
    private final String cardString;

    public boolean compareFirstPart(Card cardToCompare) {

        // Split both strings by spaces
        String[] parts1 = this.cardString.split("\\s+");
        String[] parts2 = cardToCompare.getCardString().split("\\s+");

        // Check if both strings have at least one part, and compare the first parts
        if (parts1.length > 0 && parts2.length > 0) {
            return parts1[0].equals(parts2[0]);
        }

        return false;
    }


    Card (final String cardString, final int cardVal, final int cardType){
        this.cardString = cardString;

        //cardVal of 2-10 for non-face cards, 10 for face cards, 11 for Ace (Special case)
        if(cardVal < 8) { // 0-7 in the input range corresponds to 2-9 in the deck
            this.cardVal = cardVal + 2;
        } else if(cardVal <= 11) { // 8-10 in the input range corresponds to 10 in the deck (face cards)
            this.cardVal = 10;
        } else { // 11-12 in the input range corresponds to Ace in the deck
            this.cardVal = 11;
        }

        this.cardType = cardType;
    }

    public int getCardType() {
        return cardType;
    }

    public int getCardVal(){
        return cardVal;
    }

    public String getCardString (){
        return cardString;
    }

    public String toString() {
        return getCardString();
    }
}