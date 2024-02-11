package blackjack;

import java.util.List;
import java.util.Random;

public class AutoDriver extends Driver {

    @Override
    public RuleSet.PlayerAction getChoice(List<Card> dealerHand) {

        super.optionFilter(dealerHand);

        //TODO: modify to follow the wikipedia basic blackjack strategy
        RuleSet.PlayerAction[] actions = currentAvailableActions.toArray(new RuleSet.PlayerAction[0]);
        int randomIndex = new Random().nextInt(actions.length);
        RuleSet.PlayerAction action= actions[randomIndex];

        if(action == RuleSet.PlayerAction.INSURANCE){
            currentAvailableActions.remove(RuleSet.PlayerAction.INSURANCE);
        }

        return action;
    }

    AutoDriver(RuleSet ruleset) {
        super(ruleset);
    }
}