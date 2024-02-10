package blackjack;
import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        //Temporary placeholder main just to include the player making
        //the choice of selecting a rule-set for their game.
        /*
        System.out.println("Greetings!");
        System.out.println("The currently available rule-sets are:");
        System.out.println("1. Beginner:" + "\n" + "  A single deck containing fifty-two cards." +
                "\n" + "  The player may stand or hit.");
        System.out.println("2. Intermediate:" + "\n" + "  A single deck containing fifty-two cards." +
                "\n" + "  The player may stand, hit, double-down, and split.");
        System.out.println();
        System.out.println("Please make your choice from our available options:");
        Scanner input = new Scanner(System.in);
        String playerChoice = input.nextLine();

         */


        String playerChoice="3";
        RuleSet newChoice = new RuleSet(playerChoice);

        //The choiceSet class is where the decision is made.
        //The ruleSet class is where different rule-sets are compartmentalized.
        //The cardSet class is where the cards for the deck are generated.
        //^^ the cardSet cards are called upon by generateDeck().

        Vector<Player> playerVector= new Vector<>();
        for(int i=0; i<8; i++){
            playerVector.addElement(new Player(newChoice));
        }

        Table t= new Table(newChoice, playerVector);
        Vector<Integer> playerPositions=t.getPlayers();
        t.startRound();

        System.out.println("End of program.");
        //System.out.printf("Hello and welcome!");
        /*

        UserInterface ui= new UserInterface();
        ui.startGame();

         */
    }
}