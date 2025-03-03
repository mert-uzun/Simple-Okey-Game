import java.util.Scanner;

public class ApplicationMain {

    private static final Scanner sc = new Scanner(System.in);
    private static final OkeyGame game = new OkeyGame();
    private static boolean developerMode = false;

    public static void main(String[] args) {
        initializePlayers();
        setupGame();
        chooseDeveloperMode();
        startGame();
    }

    private static void initializePlayers() {
        System.out.print("Please enter your name: ");
        String playerName = sc.nextLine().trim();

        game.setPlayerName(0, playerName);
        game.setPlayerName(1, "John");
        game.setPlayerName(2, "Jane");
        game.setPlayerName(3, "Ted");
    }

    private static void setupGame() {
        game.createTiles();
        game.shuffleTiles();

        // Distribute tiles to players (2 times according to original logic)
        game.distributeTilesToPlayers();
        game.distributeTilesToPlayers();
    }

    private static void chooseDeveloperMode() {
        System.out.print("Play in Developer Mode (see computer tiles)? [Y/N]: ");
        String devChoice = sc.nextLine().trim().toUpperCase();
        developerMode = devChoice.equals("Y");
    }

    private static void startGame() {
        boolean firstTurn = true;
        boolean gameContinues = true;

        while (gameContinues) {
            int currentPlayer = game.getCurrentPlayerIndex();
            String currentName = game.getCurrentPlayerName();
            System.out.println("\n--- " + currentName + "'s Turn ---");

            if (currentPlayer == 0) {
                gameContinues = handleHumanTurn(firstTurn);
                firstTurn = false; // After first iteration, firstTurn always false
            } else {
                gameContinues = handleComputerTurn();
            }

            if (game.didGameFinish()) {
                System.out.println("\n🏆 " + currentName + " WINS THE GAME! 🏆");
                gameContinues = false;
            }
        }
    }

    private static boolean handleHumanTurn(boolean firstTurn) {
        if (!firstTurn) {
            humanPickTile();
        } else {
            System.out.println("It's your first turn; you don't pick up a tile.");
        }

        game.displayCurrentPlayersTiles();
        game.displayDiscardInformation();

        humanDiscardTile();
        game.passTurnToNextPlayer();

        return true;
    }

    private static void humanPickTile() {
        System.out.println("Choose an action:");
        System.out.println("[1] Pick from Tile Stack");
        System.out.println("[2] Pick from Discard Pile");

        int choice;
        do {
            System.out.print("Your choice (1-2): ");
            choice = readIntSafely();
        } while (choice != 1 && choice != 2);

        String pickedTile = (choice == 1) ? game.getTopTile() : game.getLastDiscardedTile();
        System.out.println("You picked up: " + pickedTile);
    }

    private static void humanDiscardTile() {
        System.out.println("\nWhich tile would you like to discard?");
        int index;
        do {
            System.out.print("Tile index (0-14): ");
            index = readIntSafely();
            if (index < 0 || index > 14) {
                System.out.println("🚫 Invalid index. Please enter a number between 0 and 14.");
            }
        } while (index < 0 || index > 14);

        game.discardTile(index);
        System.out.println("Tile discarded successfully.");
    }

    private static boolean handleComputerTurn() {
        if (developerMode) {
            System.out.println("🛠️ Developer Mode: Showing computer tiles.");
            game.displayCurrentPlayersTiles();
        }

        game.pickTileForComputer();
        if (!game.didGameFinish()) {
            game.discardTileForComputer();
            game.passTurnToNextPlayer();
            pause(1000); // Short pause to enhance UX
            return true;
        }
        return false;
    }

    private static int readIntSafely() {
        while (!sc.hasNextInt()) {
            System.out.print("🚫 Please enter a valid integer: ");
            sc.next(); // Clear invalid input
        }
        int value = sc.nextInt();
        sc.nextLine(); // Consume the newline character
        return value;
    }

    private static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ignored) {
        }
    }
}
