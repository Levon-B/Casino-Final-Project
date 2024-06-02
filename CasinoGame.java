// Levon Babayan: Casino Games
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class CasinoGame {
    private static int userMoney = 1000;
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Welcome to the Casino!");
        while (true) {
            System.out.println("Your current balance: $" + userMoney);
            System.out.println("Choose a game: Slots, Roulette, Blackjack, Baccarat, Craps, High-Low (War), or type 'quit' to exit");
            String gameChoice = scanner.nextLine().toLowerCase();

            if (gameChoice.equals("quit")) {
                System.out.println("Thank you for playing!");
                break;
            }

            switch (gameChoice) {
            case "slots":
            playSlots();
            break;
            case "roulette":
            playRoulette();
            break;
            case "blackjack":
            playBlackjack();
            break;
            case "baccarat":
            playBaccarat();
            break;
            case "craps":
            playCraps();
            break;
            case "high-low":
            case "war":
            playHighLow();
            break;
            default:
                System.out.println("Invalid choice. Please choose a valid game.");
                break;
    }
    }
    }

    private static void playSlots() {
    int betAmount = getBetAmount("Enter the amount you want to bet:");
    
    int[] slotResults = generateSlotResults();
    displaySlotResults(slotResults);

    int multiplier = calculateSlotMultiplier(slotResults);
    userMoney += betAmount * multiplier;

    askToContinue("slots");
    }

    private static void playRoulette() {
        int betAmount = getBetAmount("Enter the amount you want to bet:");
        int selectedNumber = -1;
        String selectedColor = "";

        while (true) {
        System.out.println("Choose a number (0-36) or type 'even', 'odd', 'red', or 'black'");
        String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("even") || choice.equals("odd") || choice.equals("red") || choice.equals("black")) {
                selectedColor = choice;
                break;
            } else {
                try {
                selectedNumber = Integer.parseInt(choice);
                if (selectedNumber >= 0 && selectedNumber <= 36) {
                    break;
            }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a valid number or color.");
        }
        }
        }

        int result = generateRouletteResult();
        String resultColor = (result == 0) ? "green" : ((result % 2 == 0) ? "red" : "black");
        System.out.println("Roulette result: " + result + " (" + resultColor + ")");

        if ((selectedColor.equals("red") && resultColor.equals("red"))
                || (selectedColor.equals("black") && resultColor.equals("black"))
                || (selectedColor.equals("even") && result % 2 == 0 && result != 0)
                || (selectedColor.equals("odd") && result % 2 != 0)) {
            System.out.println("Congratulations! You win!");
            userMoney += betAmount * 2;
        } else {
            System.out.println("Sorry, you lose.");
    }

    askToContinue("roulette");
    }

    private static void playBlackjack() {
        int betAmount = getBetAmount("Enter the amount you want to bet:");

        ArrayList<Integer> playerHand = new ArrayList<>();
        ArrayList<Integer> dealerHand = new ArrayList<>();

        dealInitialCards(playerHand, dealerHand);

        boolean playerBust = playerTurn(playerHand);
        if (playerBust) {
            System.out.println("Player Busts! You lose.");
            askToContinue("blackjack");
            return;
        }

        boolean dealerBust = dealerTurn(dealerHand);
        if (dealerBust) {
            System.out.println("Dealer Busts! You win double your bet.");
            userMoney += betAmount * 2;
            askToContinue("blackjack");
            return;
        }

        int playerTotal = calculateTotal(playerHand);
        int dealerTotal = calculateTotal(dealerHand);

        if (playerTotal > dealerTotal) {
            System.out.println("Player wins! You win double your bet.");
            userMoney += betAmount * 2;
        } else if (playerTotal < dealerTotal) {
            System.out.println("Dealer wins! You lose.");
        } else {
            System.out.println("It's a tie! Your bet is returned.");
            userMoney += betAmount;
        }

        askToContinue("blackjack");
    }

    private static void playBaccarat() {
        int betAmount = getBetAmount("Enter the amount you want to bet:");
        String choice = "";

        while (true) {
            System.out.println("Bet on 'player', 'banker', or 'tie':");
            choice = scanner.nextLine().toLowerCase();
            if (choice.equals("player") || choice.equals("banker") || choice.equals("tie")) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter 'player', 'banker', or 'tie'.");
            }
        }

        int playerScore = random.nextInt(9) + 1;
        int bankerScore = random.nextInt(9) + 1;
        System.out.println("Player's score: " + playerScore);
        System.out.println("Banker's score: " + bankerScore);

        String winner = "";
        if (playerScore > bankerScore) {
            winner = "player";
        } else if (bankerScore > playerScore) {
            winner = "banker";
        } else {
            winner = "tie";
        }

        if (choice.equals(winner)) {
            System.out.println("Congratulations! You win!");
            userMoney += betAmount * (choice.equals("tie") ? 8 : 2);
        } else {
            System.out.println("Sorry, you lose.");
        }

        askToContinue("baccarat");
    }

    private static void playCraps() {
        int betAmount = getBetAmount("Enter the amount you want to bet:");
        int point = rollDice();
        System.out.println("You rolled: " + point);

        if (point == 7 || point == 11) {
            System.out.println("You win!");
            userMoney += betAmount * 2;
        } else if (point == 2 || point == 3 || point == 12) {
            System.out.println("You lose.");
        } else {
            System.out.println("Point is set to: " + point);
            boolean keepRolling = true;
            while (keepRolling) {
                int newRoll = rollDice();
                System.out.println("You rolled: " + newRoll);
                if (newRoll == point) {
                    System.out.println("You win!");
            userMoney += betAmount * 2;
            keepRolling = false;
            } else if (newRoll == 7) {
            System.out.println("You lose.");
            keepRolling = false;
    }
    }
    }
    askToContinue("craps");
    }

    private static void playHighLow() {
        int betAmount = getBetAmount("Enter the amount you want to bet:");
        int playerCard = getRandomCard();
        int dealerCard = getRandomCard();
        System.out.println("Your card: " + playerCard);
        System.out.println("Dealer's card: " + dealerCard);

        if (playerCard > dealerCard) {
            System.out.println("You win!");
            userMoney += betAmount * 2;
        } else if (playerCard < dealerCard) {
            System.out.println("You lose.");
        } else {
            System.out.println("It's a tie! Your bet is returned.");
            userMoney += betAmount;
        }

        askToContinue("high-low");
    }

    private static int getBetAmount(String message) {
        int betAmount;
        do {
            System.out.println(message);
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
            betAmount = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
        } while (betAmount <= 0 || betAmount > userMoney);

        userMoney -= betAmount;
        return betAmount;
    }

    private static int[] generateSlotResults() {
        int[] results = new int[3];
        for (int i = 0; i < 3; i++) {
            results[i] = random.nextInt(9) + 1;
        }
        return results;
    }

    private static void displaySlotResults(int[] results) {
        System.out.println("Slot Results: " + results[0] + " " + results[1] + " " + results[2]);
    }

    private static int calculateSlotMultiplier(int[] slotResults) {
        if (slotResults[0] == slotResults[1] && slotResults[1] == slotResults[2]) {
            return 3;
        } else if (slotResults[0] == slotResults[1] || slotResults[1] == slotResults[2] || slotResults[0] == slotResults[2]) {
            return 2;
        } else {
            return 0;
    }
    }

    private static int generateRouletteResult() {
        return random.nextInt(37);
    }

    private static void dealInitialCards(ArrayList<Integer> playerHand, ArrayList<Integer> dealerHand) {
        for (int i = 0; i < 2; i++) {
            playerHand.add(getRandomCard());
            dealerHand.add(getRandomCard());
        }
        System.out.println("Your cards: " + playerHand);
        System.out.println("Dealer's card: " + dealerHand.get(0));
    }

    private static boolean playerTurn(ArrayList<Integer> playerHand) {
        while (true) {
            System.out.println("Do you want to hit or stand? (hit/stand)");
            String choice = scanner.nextLine().toLowerCase();

            if (choice.equals("hit")) {
                playerHand.add(getRandomCard());
                System.out.println("Your cards: " + playerHand);

                int playerTotal = calculateTotal(playerHand);
                if (playerTotal > 21) {
                    return true; // Player busts
        } else if (playerTotal == 21) {
                System.out.println("You got Blackjack! You win!");
                userMoney += userMoney + userMoney / 2;
                askToContinue("blackjack");
                return false;
    }
    } else if (choice.equals("stand")) {
        return false; // Player stands
    } else {
        System.out.println("Invalid choice. Please enter 'hit' or 'stand'.");
    }
    }
    }

    private static boolean dealerTurn(ArrayList<Integer> dealerHand) {
        while (true) {
            int dealerTotal = calculateTotal(dealerHand);
            System.out.println("Dealer's cards: " + dealerHand);

            if (dealerTotal >= 17) {
                break;
            }
            dealerHand.add(getRandomCard());
        }

    int dealerTotal = calculateTotal(dealerHand);
    return dealerTotal > 21;
    }

    private static int getRandomCard() {
    return random.nextInt(10) + 1;
    }

    private static int calculateTotal(ArrayList<Integer> hand) {
    int total = 0;
    int aceCount = 0;

    for (int card : hand) {
        if (card == 1) {
        aceCount++;
        total += 11;
        } else if (card > 10) {
        total += 10;
        } else {
        total += card;
        }
        }

        while (total > 21 && aceCount > 0) {
            total -= 10;
            aceCount--;
        }

        return total;
    }

    private static int rollDice() {
        return random.nextInt(6) + 1 + random.nextInt(6) + 1;
    }

    private static void askToContinue(String currentGame) {
        System.out.println("Your current balance: $" + userMoney);
        System.out.println("Do you want to continue playing this game? (yes/no)");
        String choice;
        do {
            choice = scanner.nextLine().toLowerCase();
            if (choice.equals("no")) {
                System.out.println("Returning to the main menu.");
                return;
        } else if (choice.equals("yes")) {
            switch (currentGame) {
                case "slots":
                playSlots();
                break;
                case "roulette":
                playRoulette();
                break;
                case "blackjack":
                playBlackjack();
                break;
                case "baccarat":
                playBaccarat();
                break;
                case "craps":
                playCraps();
                break;
                case "high-low":
                playHighLow();
                break;
                default:
                System.out.println("Invalid game choice.");
                break;
}
    System.out.println("Your current balance: $" + userMoney);
} else {
    System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
}
} while (!choice.equals("yes"));
}
}
