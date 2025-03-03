import java.net.SocketImplFactory;
import java.util.Arrays;

public class Player {
    private String playerName;
    private Tile[] playerTiles;
    private int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /**
     * Removes the tile in the given index by making all elements greater than the given index move 1 index to the left
     */
    public Tile getAndRemoveTile(int index) {
        Tile tile = playerTiles[index];
        if (this.playerTiles[index] != null) {
            for (int i = index; i < numberOfTiles - 1; i++) {
                playerTiles[i] = playerTiles[i + 1];
            }
            playerTiles[numberOfTiles - 1] = null;
            numberOfTiles--;
            return tile;
        }
        return null;
    }

    /**
     * Adds the given tile to the array of tiles kept in the player class and increments numberOfTiles by 1.
     * Finds the index the tile should be inserted without sorting the array. 
     * Finds the index it should be inserted to and shifts all elements greater than the index to the right by 1
     * @author Elif Bozkurt, Ali Sevindi, Mert Uzun
     */
    public void addTile(Tile t) {
        if (this.numberOfTiles >= 15) return; //if the player has 15 or more tiles return
        int index = this.numberOfTiles; // if there is no tiles with the value of t, t is added to the end of the array.
    
        if (index > 0){
            for (int i = 0; i < numberOfTiles; i++) {
                if (t.compareTo(getTiles()[i]) < 1) {
                    index = i;
                    break;
                }
            }
            for (int j = this.numberOfTiles ; j > index; j--) {
                playerTiles[j] = playerTiles[j - 1];
            }
        }
     
        playerTiles[index] = t;
        numberOfTiles ++; 
    }
    

    /*
     * DONE: This method works for chains of length 4 as after sorting the array it only checks the 3 tiles after that tile
     * if we have ...4-4-4-4-4... it should count the first four 4s as a chain and not the last 4. By the game logic the player 
     * should only have chains of length 4 since there cant be tiles of same color in the same chain. It returns chainsOfFour == 3 and not
     * chainsOfFour >= 3 since there cannot be more than 3 chains. If the player is winning there should be 3 chains of length 4 and 2 extra tiles
     * updated by Utku
     */
    public boolean isWinningHand() {
        int chainsOfFour = 0;
        
        // Remove elements those are not null
        Tile[] tilesCopy = Arrays.stream(this.playerTiles)
                                 .filter(tile -> tile != null)
                                 .toArray(Tile[]::new);
        
        Arrays.sort(tilesCopy);
    
        for (int i = 0; i < tilesCopy.length - 3; i++) {
            if (tilesCopy[i].canFormChainWith(tilesCopy[i + 1]) &&
                tilesCopy[i + 1].canFormChainWith(tilesCopy[i + 2]) &&
                tilesCopy[i + 2].canFormChainWith(tilesCopy[i + 3])) {
                chainsOfFour++;
                i += 3; 
            }
        }
    
        return chainsOfFour == 3; 
    }

    public int findPositionOfTile(Tile t) {
        int tilePosition = -1;
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i].compareTo(t) == 0) {
                tilePosition = i;
            }
        }
        return tilePosition;
    }

    /**
     * Counts the number of pairs in a player's hand.
     * A pair is four tiles with the same value but different colors.
     * @author Mert Uzun
     */
    public int countPairs() {
        int pairs = 0;
        int[] colorCount = new int[8]; // Since values are in range [1,7], index 0 is unused.

        // Count occurrences of each value
        for (int i = 0; i < numberOfTiles; i++) {
            if (playerTiles[i] != null) {
                colorCount[playerTiles[i].getValue()]++;
            }
        }

        // Count valid pairs
        for (int i = 1; i <= 7; i++) {
            pairs += colorCount[i] / 4;
        }

        return pairs;
    }

    /**
     * Display players current hand with the indexes on top and each tile at the bottom,
     * @author Elif Bozkurt, Mert Uzun
     */
    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            if(playerTiles[i] == null){
                continue;
            }
            else{
                System.out.printf("%-3d",i);
            }
        }

        System.out.println();

        for (int i = 0; i < numberOfTiles; i++) {
            if (playerTiles[i] == null) {
                continue;
            }
            else{
                System.out.print(playerTiles[i].toString() + " ");
            }
        }
        System.out.println();
    }

    public Tile[] getTiles() {
        return playerTiles;
    }

    public void setName(String name) {
        playerName = name;
    }

    public String getName() {
        return playerName;
    }

    public int getNumberOfTiles(){
        return numberOfTiles;
    }
}
