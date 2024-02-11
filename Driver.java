package blackjack;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Driver {  //This is the decision driver for each "player" and also the user.
    //  It is constructed using the ruleset.

    private int actionCount;

    protected Set<RuleSet.PlayerAction> availableActions;

    protected Set<RuleSet.PlayerAction> currentAvailableActions;

    protected Hand myHand;

    protected List<Card> showMyhand(){
        return myHand.getCards();
    }
    protected void optionFilter(List<Card> dealerHand){ //Filters Options.
        currentAvailableActions.remove(RuleSet.PlayerAction.SPLIT);

        if(dealerHand.get(0).getCardVal() != 11){
            currentAvailableActions.remove(RuleSet.PlayerAction.INSURANCE);
        }

        List<Card>currentCards = myHand.getCards();

        if(myHand.getCards().size() != 2){
            currentAvailableActions.remove(RuleSet.PlayerAction.SPLIT);
        }

        if(myHand.getCards().size() == 2){
            Card card1=myHand.getCards().get(0);
            Card card2=myHand.getCards().get(1);
            if( card1.compareFirstPart(card2) && availableActions.contains(RuleSet.PlayerAction.SPLIT)){
                currentAvailableActions.add(RuleSet.PlayerAction.SPLIT);
            }
        }

        actionCount++;
        if(actionCount > 1){
            currentAvailableActions.remove(RuleSet.PlayerAction.DOUBLE_DOWN);
        }
    }

    public abstract RuleSet.PlayerAction getChoice(List<Card> dealerHand);

    public void readyTurn(Hand playerHand){
        actionCount=0;
        myHand=playerHand;
        currentAvailableActions= new HashSet<>(availableActions);
    }

    Driver(RuleSet ruleset){

        //TODO: reimplement other actions
        availableActions= new HashSet<>(ruleset.getAvailableActions());
        availableActions.remove(RuleSet.PlayerAction.INSURANCE);

    }

}