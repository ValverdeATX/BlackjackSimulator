package blackjack;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class Player {
    //TODO: implement betting
    private int position;  // Position by distance from dealer's right.
    private boolean isPositionSet = false;
    private final String id;  // Final player ID
    private final RuleSet ruleSet;
    private Hand myHand;
    private List<Hand> specialHand;
    private Driver driver;

    public Player(RuleSet ruleSet) {
        this.ruleSet = ruleSet;
        this.id = UUID.randomUUID().toString();  // Generates a unique ID for each player
        driver = new AutoDriver(ruleSet);
    }

    public Player(RuleSet ruleSet, Driver customDriver) {
        this.ruleSet = ruleSet;
        this.id = UUID.randomUUID().toString();  // Generates a unique ID for each player
        driver = customDriver;
    }

    public void setPosition(int position) throws PositionAlreadySetException {
        if (!isPositionSet) {
            this.position = position;
            isPositionSet = true;
        } else {
            throw new PositionAlreadySetException("Position has already been set. Cannot modify it again.");
        }
    }

    public void startRound(Hand hand){
        myHand=hand;
        driver.readyTurn(myHand);
    }

    public int takeTurn(Table table) { //returns the integer value of the players hand after the turn is complete

        Hand thisHand;
        if(specialHand != null){ //Special case to handle a split hand.
            driver.readyTurn(specialHand.get(specialHand.size()-1));
            thisHand=specialHand.get(specialHand.size()-1);
        } else {
            thisHand=myHand;
        }

        List<Card> dealerHand = table.getHand();

        RuleSet.PlayerAction playerChoice;

        while(true) {
            playerChoice = driver.getChoice(dealerHand);
            if(playerChoice == RuleSet.PlayerAction.STAND){
                break;
            } else {
                switch (playerChoice){
                    case HIT:
                        thisHand.hit(table.drawCard());
                        break;
                    case SPLIT:

                        if(specialHand == null){
                            specialHand= new Vector<>();
                        }

                        specialHand.add(thisHand.split()); //If split is called, a special hand is created for another turn.
                        break;
                    case DOUBLE_DOWN:
                        thisHand.doubleDown(table.drawCard());
                        break;
                    case INSURANCE:
                        System.out.println("Insurance not yet implemented");
                        System.exit(1);
                        break;
                }
            }

            if(myHand.getSum() == 0){
                return -1;
                //Returning -1 because the dealer always beats anyone who busts
                //Even if they bust themselves

            }

        }

        if(specialHand != null){ //Special return value for a split hand
            return -myHand.getSum();
        }


        return myHand.getSum();
    }

    public Hand win(){
        //TODO: implement betting
        if(myHand.getCards().isEmpty()){
            myHand=specialHand.remove(0);
        }

        return myHand;
    }

    public Hand tie(){
        //TODO: implement betting
        if(myHand.getCards().isEmpty()){
            myHand=specialHand.remove(0);
            specialHand.remove(0);
        }

        return myHand;
    }

    public Hand lose(){
        //TODO: implement betting
        if(myHand.getCards().isEmpty()){
            myHand=specialHand.remove(0);
        }

        return myHand;
    }

    public int getPosition(){
        return position;
    }

    public String getID() {
        return this.id;
    }

    public Hand getHand(){
        if(specialHand != null){
            return null;
        }
        return myHand;
    }

    public Vector<Hand> getAllHand(){

        Vector<Hand> returnVec = new Vector<>(specialHand);
        returnVec.add(myHand);

        return returnVec;
    }

}