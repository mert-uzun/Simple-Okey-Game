import java.util.Scanner;

public class ApplicationMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        OkeyGame game = new OkeyGame();

        System.out.print("Please enter your name: ");
        String playerName = sc.next();

        game.setPlayerName(0, playerName);
        game.setPlayerName(1, "John");
        game.setPlayerName(2, "Jane");
        game.setPlayerName(3, "Ted");

        game.createTiles();
        game.shuffleTiles();
        game.distributeTilesToPlayers();
        game.distributeTilesToPlayers();
        
        // Developer mode is used for seeing the computer players hands, to be used for debugging
        System.out.print("Play in developer's mode with other player's tiles visible? (Y/N): ");
        char devMode = sc.next().charAt(0);
        boolean devModeOn = devMode == 'Y';
        
        boolean firstTurn = true;
        boolean gameContinues = true;
        int playerChoice = -1;

        while(gameContinues) {
            
            int currentPlayer = game.getCurrentPlayerIndex();
            System.out.println(game.getCurrentPlayerName() + "'s turn.");
            
            if(currentPlayer == 0) {
                // This is the human player's turn
                game.displayCurrentPlayersTiles();
                game.displayDiscardInformation();

                System.out.println("What will you do?");

                if(!firstTurn) {
                    // After the first turn, player may pick from tile stack or last player's discard
                    System.out.println("1. Pick From Tiles");
                    System.out.println("2. Pick From Discard");
                }
                else{
                    // On first turn the starting player does not pick up new tile
                    System.out.println("1. Discard Tile");
                }
                
                // Below lines are updated by Utku Kabukçu to improve game logic
                System.out.print("Your choice: ");
                while (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.next(); // Discard invalid input
                }
                playerChoice = sc.nextInt();

                // If it's the first turn, only allow "1"
                if (firstTurn) {
                    while (playerChoice != 1) {
                        System.out.println("Invalid choice! On the first turn, you can only discard a tile.");
                        System.out.print("Your choice: ");
                        while (!sc.hasNextInt()) {
                            System.out.println("Invalid input! Please enter a number.");
                            sc.next(); // Discard invalid input
                        }
                        playerChoice = sc.nextInt();
                    }
                }
                // Otherwise, allow both "1" (Pick from Tiles) and "2" (Pick from Discard)
                else {
                    while (playerChoice < 1 || playerChoice > 2) {
                        System.out.println("Invalid choice! Please enter 1 or 2.");
                        System.out.print("Your choice: ");
                        while (!sc.hasNextInt()) {
                            System.out.println("Invalid input! Please enter a number.");
                            sc.next(); // Discard invalid input
                        }
                        playerChoice = sc.nextInt();
                    }
                }

                // After the first turn we can pick up
                if(!firstTurn) {
                    if(playerChoice == 1) {
                        System.out.println("You picked up: " + game.getTopTile());
                        firstTurn = false;
                    }
                    else if(playerChoice == 2) {
                        System.out.println("You picked up: " + game.getLastDiscardedTile()); 
                    }

                    // Display the hand after picking up new tile
                    game.displayCurrentPlayersTiles();
                }
                else{
                    // After first turn it is no longer the first turn
                    firstTurn = false;
                }

                gameContinues = !game.didGameFinish();

                if(gameContinues) {
                    // If game continues we need to discard a tile using the given index by the player
                    System.out.println("Which tile you will discard?");

                    // Done by Ali: make sure the given index is correct, should be 0 <= index <= 14
                    boolean correctIndex;
                    do{
                        System.out.print("Discard the tile in index: ");
                        playerChoice = sc.nextInt();
                        if (playerChoice < 0 || playerChoice > 14){
                            correctIndex = false ;
                            System.out.println("This is not a valid index. Try again!");
                        }
                        else {
                            correctIndex = true ;
                        }
                    } while (!correctIndex) ;
                    
                    game.discardTile(playerChoice);
                    game.passTurnToNextPlayer();
                }
                else{
                    // If we finish the hand we win
                    System.out.println("Congratulations, you win!");
                }
            }
            else{
                // This is the computer player's turn
                if(devModeOn) {
                    game.displayCurrentPlayersTiles();
                }

                // Computer picks a tile from tile stack or other player's discard
                game.pickTileForComputer();

                gameContinues = !game.didGameFinish();

                if(gameContinues) {
                    // If game did not end computer should discard
                    game.discardTileForComputer();
                    game.passTurnToNextPlayer();
                }
                else{
                    // Current computer character wins
                    System.out.println(game.getCurrentPlayerName() + " wins.");
                }
            }
        }
    }
}