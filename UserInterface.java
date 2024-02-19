package blackjack;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class UserInterface {

    private UserDriver ud;
    private Table mainTable;


    public UserInterface(){
        //TODO: Change this ruleset to take user input for the ruleset
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please choose a ruleset:\n1 - Beginner\n2 - Intermediate\n3 - Advanced");
        String playerChoice = scanner.nextLine();
        RuleSet ruleset = new RuleSet(playerChoice);

        //TODO: The user driver should already be working, without modification, but you can if you need too.
        ud= new UserDriver(ruleset, this);
        Vector<Player> playerVector= new Vector<>();
        playerVector.add(new Player(ruleset, ud));

        //Adds 7 players beyond the first, be careful with the smaller deck rulesets.
        //TODO: BONUS:  If you have the time, try to change the names to something more readable lmao, its in the base player constructor.
        for(int i=0; i<7; i++){
            playerVector.addElement(new Player(ruleset));
        }

        mainTable= new Table(ruleset, playerVector);
    }


    public void startGame(int numRounds){
        //TODO: modify this to run a few rounds or whatever you think is best.
        for (int i = 0; i < numRounds; i++) {
            System.out.println("Round " + (i + 1));
            mainTable.startRound();
        }
    }


    public RuleSet.PlayerAction getChoice(List<Card> dealerHand, List<Card> currentHand){
        //TODO:  implement I/O for the player.  You get information about the table from mainTable.  Any calls you need already exist.
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your hand: " + currentHand);
        System.out.println("Dealer's hand: " + dealerHand.get(0) + ", face down card");
        System.out.println("Choose an action:\n1 - Hit\n2 - Stand");
        if (mainTable.getRuleset().getRuleChoice() > 1) {
            System.out.println("3 - Double down");
        }
        if (mainTable.getRuleset().getRuleChoice() > 2 && currentHand.get(0).getCardVal() == currentHand.get(1).getCardVal()) {
            System.out.println("4 - Split");
        }
        int playerChoice = scanner.nextInt();
        switch(playerChoice) {
            case 1:
                return RuleSet.PlayerAction.HIT;
            case 2:
                return RuleSet.PlayerAction.STAND;
            case 3:
                if (mainTable.getRuleset().getRuleChoice() > 1) {
                    return RuleSet.PlayerAction.DOUBLE_DOWN;
                }
            case 4:
                if (mainTable.getRuleset().getRuleChoice() > 2 && currentHand.get(0).getCardVal() == currentHand.get(1).getCardVal()) {
                    return RuleSet.PlayerAction.SPLIT;
                }
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.startGame(5);
    }

}