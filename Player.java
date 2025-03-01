import java.util.Arrays;

public class Player {
    String playerName;
    Tile[] playerTiles;
    int numberOfTiles;

    public Player(String name) {
        setName(name);
        playerTiles = new Tile[15]; // there are at most 15 tiles a player owns at any time
        numberOfTiles = 0; // currently this player owns 0 tiles, will pick tiles at the beggining of the game
    }

    /*
     * TODO: removes and returns the tile in given index
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

    /*
     * TODO: adds the given tile to the playerTiles in order
     * should also update numberOfTiles accordingly.
     * make sure playerTiles are not more than 15 at any time
     */
    public void addTile(Tile t) {
        if (this.numberOfTiles < 15) {
            this.playerTiles[numberOfTiles] = t;
            numberOfTiles++;
        }
    }

    /*
     * TODO: checks if this player's hand satisfies the winning condition
     * to win this player should have 3 chains of length 4, extra tiles
     * does not disturb the winning condition
     * @return
     */
    public boolean isWinningHand() {
        int chainsOfFour = 0;
        Tile[] tilesCopy = this.playerTiles.clone();
        Arrays.sort(tilesCopy);

        for (int i = 0; i < numberOfTiles - 3; i++) {
            if (tilesCopy[i].canFormChainWith(tilesCopy[i + 1]) &&
                tilesCopy[i + 1].canFormChainWith(tilesCopy[i + 2]) &&
                tilesCopy[i + 2].canFormChainWith(tilesCopy[i + 3])) {
                chainsOfFour++;
                i += 3; 
            }
        }

        return chainsOfFour == 3; // the player can have 14 tiles at most. 3 chains of 4 tiles each = 12 tiles. 
        //the player cannot have more than 3 chains of 4 tiles each. it is not necessary to check for extra tiles or extra chains
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

    public void displayTiles() {
        System.out.println(playerName + "'s Tiles:");
        for (int i = 0; i < numberOfTiles; i++) {
            System.out.print(playerTiles[i].toString() + " ");
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
}
