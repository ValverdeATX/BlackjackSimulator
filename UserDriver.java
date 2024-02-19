package blackjack;

import java.util.Collection;
import java.util.List;

public class UserDriver extends Driver {

    //Remember you can use the super protected variables
    //availibleActions to get all the actions that you are allowed to take.

    private UserInterface user;

    @Override
    public RuleSet.PlayerAction getChoice(List<Card> dealerHand) {
        super.optionFilter(dealerHand);
        RuleSet.PlayerAction[] actions = currentAvailableActions.toArray(new RuleSet.PlayerAction[0]);
        //Feel free to modify this, but I don't think you'll need too.

        return user.getChoice(dealerHand, super.showMyhand());
    }

    UserDriver(RuleSet ruleset, UserInterface ui) {
        super(ruleset);
    }

}