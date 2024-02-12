package blackjack;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RuleSet {
    //ruleSet is the class in which the user will be able to choose
    //between several options for available rule-sets.

    //currently:
    //Beginner: one deck + hit or stand
    //Intermediate: one deck + hit, stand, double-down, split
    //Advanced: any deck size + hit, stand, double-down, split (w.i.p.)
    private CardSet newSet;
    private int ruleChoice;

    public enum PlayerAction {
        HIT, STAND, DOUBLE_DOWN, SPLIT, INSURANCE
    }

    private Set<PlayerAction> availableActions = new HashSet<>();

    public RuleSet(String playerChoice) {
        int ruleChoice = Integer.parseInt(playerChoice);
        newSet = new CardSet(ruleChoice);
        try{
            //System.out.println(ruleChoice + " is a valid integer.");
            if(ruleChoice == 1) {
                System.out.println("Generating beginner rule-set.");
                inGameOptions(ruleChoice);
                //Following lines are placeholders to test successful deck creation:
                System.out.println("Here are your cards for this game");
                for(Card card:newSet.generateDeck()) {
                    System.out.println(card);
                }
            }
            else if(ruleChoice == 2) {
                System.out.println("Generating intermediate rule-set.");
                inGameOptions(ruleChoice);
                //Following lines are placeholders to test successful deck creation:
                System.out.println("Here are your cards for this game");
                for(Card card:newSet.generateDeck()) {
                    System.out.println(card);
                }
            }
            else if(ruleChoice == 3) {
                System.out.println("Generating advanced rule-set.");
                inGameOptions(ruleChoice);
                //Following lines are placeholders to test successful deck creation:
                //System.out.println("Here are your cards for this game");

            }
            else {
                System.out.println("Sorry, your choice is currently not available. Please try again.");
            }
        } catch (NumberFormatException ex) {
            //ex.printStackTrace();
            System.out.println("That was not one of the available choices. Please try again.");
        }
    }

    public int getRuleChoice() {
        return ruleChoice;
    }

    private void inGameOptions(int ruleChoice) {
        // Clear previous actions
        availableActions.clear();
        if (ruleChoice == 1) {
            availableActions.add(PlayerAction.HIT);
            availableActions.add(PlayerAction.STAND);
            System.out.println("  Beginner rule-set settings have been applied.");
        }
        else if (ruleChoice == 2) {
            availableActions.add(PlayerAction.HIT);
            availableActions.add(PlayerAction.STAND);
            availableActions.add(PlayerAction.DOUBLE_DOWN);
            availableActions.add(PlayerAction.SPLIT);
            System.out.println("  Intermediate rule-set settings have been applied.");
        }
        else if(ruleChoice == 3){
            // All actions are allowed
            availableActions.addAll(Arrays.asList(PlayerAction.values()));
            System.out.println("  Advanced rule-set settings have been applied.");
        }
        else {
            System.out.println("Error on initializing in-game options.");
        }
    }

    public Set<PlayerAction> getAvailableActions() {
        return this.availableActions;
    }

    public Deck getDeck(){
        return new Deck(newSet.generateDeck());
    }

}