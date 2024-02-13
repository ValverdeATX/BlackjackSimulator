package blackjack;

import java.util.*;

public class Table {

    private RuleSet ruleset;
    private Vector<Player> players;
    private Hand dealerHand; // Assuming this is the internal private Hand
    private Deck deck;

    private Card getDealerSecondCard() throws DealerHandNotSet {
        if (dealerHand != null && dealerHand.getCards().size() >= 2) {
            return dealerHand.getCards().get(1);
        } else {
            throw new DealerHandNotSet("Dealer's hand not set or has less than two cards.");
        }
    }

    private int dealerTurn(){ //dealer always hits on 17.

        while(dealerHand.getSum() <= 17){

            if(dealerHand.getSum() == 0){
                return dealerHand.getSum();
            }

            dealerHand.hit(drawCard());
        }

        return dealerHand.getSum();
    }

    public Table(RuleSet ruleset, Collection<Player> players) {
        this.ruleset = ruleset;
        this.players = new Vector<>();
        this.deck=ruleset.getDeck();

        try {
            int playerPosition=0;
            for (Player player : players) {
                player.setPosition(playerPosition);
                this.players.add(player);  // Add player to the vector
                playerPosition++;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    public RuleSet getRuleset() {
        return this.ruleset;
    }

    // Returns a vector of player positions
    public Vector<Integer> getPlayers() {
        Vector<Integer> playerPositions = new Vector<>();
        for (Player player : players) {
            playerPositions.add(player.getPosition());
        }

        return playerPositions;
    }

    public List<Card> getHand(int playerPosition) { //USES MUST HANDLE CASE WHERE PLAYER HAS MULTIPLE HANDS
        if(players.get(playerPosition).getHand() != null){ //Standard return;
            return players.get(playerPosition).getHand().getCards();
        } else {
            return null;
        }
    }

    public Vector<List<Card>> getAllHand(int playerPosition){ //CALL THIS IF getHand(playerPosition) RETURNS NULL

        Vector<List<Card>> newVec= new Vector<>();

        for(Hand hand : players.get(playerPosition).getAllHand()){
            newVec.add(hand.getCards());
        }

        return newVec;
    }

    public List<Card> getHand() {
        try {
            Card secondCard = getDealerSecondCard();
            return Collections.unmodifiableList(Collections.singletonList(secondCard));
        } catch (DealerHandNotSet e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return null;
        }
    }

    public void startRound(){
        deck.shuffle();
        dealerHand= new Hand(drawCard(), drawCard());
        System.out.println("Dealer has revealed card " + this.getHand());

        for(Player player : this.players){
            player.startRound(new Hand(drawCard(), drawCard()));
        }

        //TODO: implement implement betting and insurance


        Vector<Pair<Integer, Integer>> playerValues= new Vector<>();
        for(Player player : this.players){
            playerValues.add(new Pair<Integer, Integer>(player.getPosition(), player.takeTurn(this)));
            if(playerValues.lastElement().getSecond() < -1){ //If a player splits, they will return a negative value less than 1.
                int lastIndex = playerValues.size() - 1;
                playerValues.lastElement().setSecond(playerValues.lastElement().getSecond() * -1); //reset the value.
                playerValues.add(new Pair<Integer, Integer>(player.getPosition(), player.takeTurn(this))); //Take the turn again.  The player will use their second hand.
            }
        }

        int dealerVal=dealerTurn();
        for(Pair<Integer, Integer> pair : playerValues){

            Player player=players.elementAt(pair.getFirst());
            int playerValue=(Integer)pair.getSecond();

            Hand playerHand=null;

            if(playerValue > dealerVal){
                playerHand=player.win();
                System.out.println("Player: " + player.getID() + " won their hand containing " + playerHand.getCards() + " with value " + playerHand.getSum());
            } else if(playerValue == dealerVal){
                playerHand=player.tie();
                System.out.println("Player: " + player.getID() + " tied their hand containing " + playerHand.getCards() + " with value " + playerHand.getSum());
            } else if(playerValue < dealerVal){
                playerHand=player.lose();
                System.out.println("Player: " + player.getID() + " lost their hand containing " + playerHand.getCards() + " with value " + playerHand.getSum());
            }

            deck.returnCards(playerHand);
        }

        System.out.println("Dealer had cards: " + dealerHand.getCards() + " value of " + dealerHand.getSum());
    }

    public Card drawCard(){
        return deck.getCard();
    }

}

class Pair<K, V> { //Special pair
    private K first;
    private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second){
        this.second=second;
    }
}


class PositionAlreadySetException extends Exception {
    public PositionAlreadySetException(String message) {
        super(message);
    }
}

class DealerHandNotSet extends Exception {
    public DealerHandNotSet(String message) {
        super(message);
    }
}