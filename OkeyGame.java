import java.util.Arrays;
import java.util.Random;

public class OkeyGame {

    Player[] players;
    
    Tile[] tiles;

    Tile lastDiscardedTile;
    int currentTileIndex = 0;

    int currentPlayerIndex = 0;

    public OkeyGame() {
        players = new Player[4];
    }

    public void createTiles() {
        tiles = new Tile[112];
        int currentTile = 0;

        // two copies of each color-value combination, no jokers
        for (int i = 1; i <= 7; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[currentTile++] = new Tile(i,'Y');
                tiles[currentTile++] = new Tile(i,'B');
                tiles[currentTile++] = new Tile(i,'R');
                tiles[currentTile++] = new Tile(i,'K');
            }
        }
    }
    
    /**
     * This method first check if there's any more tiles left in the tiles arraylist
     * then it picks the top tile from the tiles arraylist and adds it to the current player's hand
     * then it increments the currentTileIndex
     * then it returns the toString method of the picked tile so that we can print what we picked
     * @return the toString method of the picked tile
     */
    public String getTopTile() 
    {
        if (currentTileIndex >= tiles.length) // Check if there are tiles left
        {
            System.out.println("Error: No more tiles in the stack.");
            return "No more tiles to draw.";
        }

        // Pick the top tile from the stack
        Tile pickedTile = tiles[currentTileIndex];
        currentTileIndex++;

        // Check if player's hand is full before adding the tile
        if (players[currentPlayerIndex].getNumberOfTiles() >= 15) 
        {
            System.out.println("Cannot pick up: Hand is full.");
            return "Cannot pick up: Hand is full.";
        }

        players[currentPlayerIndex].addTile(pickedTile);
        System.out.println(players[currentPlayerIndex].getName() + " picked up " + pickedTile.toString());

        return pickedTile.toString();
    }


   /*
     * DONE: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    /** Resets lastDiscardedTile to null after picking.  Returns the tile as a string for display. */
    public void distributeTilesToPlayers() 
    {
        // Check array is initialized
        if (players == null || players.length < 4) 
        {
            System.out.println("Error: Players array is not initialized properly."); 
            return;
        }
    
        // Check if tiles array is initialized and has enough tiles
        if (tiles == null || tiles.length < 112) 
        {
            System.out.println("Error: Tiles array is not initialized or has missing tiles."); 
            return;
        }
    
        // Ensure all players are initialized
        for (int j = 0; j < players.length; j++) 
        {
            if (players[j] == null) 
            {
                System.out.println("Error: Player at index " + j + " is not initialized."); 
                return;
            }
        }
    
        int index = 0;  
    
        // First player gets 15 tiles
        for (int i = 0; i < 15; i++) {
            if (index >= tiles.length) // Check if there are enough tiles
            { 
                System.out.println("Error: Not enough tiles to distribute."); 
                return;
            }
            players[0].addTile(tiles[index]);
            index++;
        }
    
        // Other players get 14 tiles 
        for (int j = 1; j < players.length; j++) 
        {
            for (int i = 0; i < 14; i++) 
            {
                if (index >= tiles.length) // Check if there are enough tiles
                { 
                    System.out.println("Error: Not enough tiles to distribute."); 
                    return;
                }
                players[j].addTile(tiles[index]);
                index++;
            }
        }
    
        currentTileIndex = index; 
    }
 

    /*
     * DONE: get the last discarded tile for the current player
     * (this simulates picking up the tile discarded by the previous player)
     * it should return the toString method of the tile so that we can print what we picked
     */
    /** Resets lastDiscardedTile to null after picking.  Returns the tile as a string for display. */
    public String getLastDiscardedTile() 
    {
        if (lastDiscardedTile == null)  // Check if there is a discarded tile
    {
        return "No discarded tile to pick up.";
    }

    // Check if player's hand is full (should not exceed 15 tiles)
    if (players[currentPlayerIndex].getNumberOfTiles() >= 15) 
    {
        return "Cannot pick up: Hand is full.";
    }


        Tile tile = lastDiscardedTile;
        players[currentPlayerIndex].addTile(tile);
        lastDiscardedTile = null;
        return tile.toString();

        }

    /**
     * This method shuffles the current tiles array which consists of 112 tiles which were sorted in order
     */
    public void shuffleTiles() {
        Random random = new Random();
        for(int i = 0; i < 10 * tiles.length; i++){
            int firstIndex = random.nextInt(112);
            int secondIndex = random.nextInt(112);
            Tile temp = tiles[firstIndex];
            tiles[firstIndex] = tiles[secondIndex];
            tiles[secondIndex] = temp;
        }
    }

    /*
     * Done by Ali: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {
        return players[currentPlayerIndex].isWinningHand();
    }

    /**
     * Picks a tile for the current computer player considering the following rules:
     * - If the lastDiscardedTile is considered beneficial, computer player picks it using getLastDiscardedTile()
     * - If the lastDiscardedTile is considered non-beneficial, computer player picks the top tile using getTopTile()
     * Prints whether computer picks from tiles or discarded ones.
     * @author Mert Uzun
     */
    public void pickTileForComputer() {
        Player currentPlayer = players[currentPlayerIndex];
        if (lastDiscardedTileIsBeneficial()) {
            getLastDiscardedTile();
            System.out.println("Player " + (currentPlayerIndex + 1) + "takes the last discarded tile.");
        }
        else{
            getTopTile();
            System.out.println("Player " + (currentPlayerIndex + 1) + "takes the top tile.");
        }
    }

    /**
     * Helper method to check if picking last discarded tile would be beneficial
     * Method considers this process beneficial if last discarded tile can match with 2 or more tiles in players current hand
     * Considers it non-beneficial if last discarded tiles is duplicate of one of tiles in players hand or only has one possible match
     * @return true if beneficial, false if not
     * @author Mert Uzun
     */
    private boolean lastDiscardedTileIsBeneficial(){
        int countForPossibleMatches = 0;
        boolean hasDuplicate = false;
        Player currentPlayer = players[currentPlayerIndex];

        for(int i = 0; i < currentPlayer.getNumberOfTiles(); i++){
            if (lastDiscardedTile.equals(currentPlayer.getTiles()[i])) {
                hasDuplicate = true;
            }
        }

        if (!hasDuplicate) {
            for(int i = 0; i < currentPlayer.getNumberOfTiles(); i++){
                if (lastDiscardedTile.canFormChainWith(currentPlayer.getTiles()[i])) {
                    countForPossibleMatches++;
                }
            }
        }

        if (hasDuplicate || countForPossibleMatches < 2) {
            return false;
        }
        else {
            return true;
        }  
    }

    /**
     * Current computer player will discard the least useful tile.
     * Prints what tile is discarded to make other players aware of this action.
     * First looks for tiles with duplicates and if there is any, discards it.
     * Secondly, looks for a tile with minimum matchables, if there is a tile such as this, discards it.
     * @author Mert Uzun
     */
    public void discardTileForComputer() {
        int index = getCurrentPlayerIndex();
        Player currentPlayer = players[index];
        Tile[] handOfPlayer = currentPlayer.getTiles();

        //First, look for duplicates, makes discarded index null
        for(int i = 0; i < handOfPlayer.length; i++){
            Tile currentTile = handOfPlayer[i];
            for(int j = i + 1; j < handOfPlayer.length; j++){
                Tile toBeCompared = handOfPlayer[j];
                if (currentTile.equals(toBeCompared)) {
                    System.out.println(currentPlayer.getName() + " discards " + currentTile + " from its hand.");
                    handOfPlayer[i] = null;
                    sortTilesWithNullAtLastIndex(handOfPlayer);
                    return;
                }
            }
        }

        //Secondly, look for tiles without minimum pairs
        for(int x = 0; x < 4; x++){
            for(int i = 0; i < handOfPlayer.length; i++){
                Tile currentTile = handOfPlayer[i];
                int countForPairables = 0;
                for(int j = 0; j < handOfPlayer.length; j++){
                    Tile toBeCompared = handOfPlayer[j];
                    if (currentTile.canFormChainWith(toBeCompared)) {
                        countForPairables++;
                    }
                }
    
                //If current tile has minimum matches with any other, discard it
                if (countForPairables == x) {
                    System.out.println(currentPlayer.getName() + " discards " + currentTile + " from its hand.");
                    handOfPlayer[i] = null;
                    sortTilesWithNullAtLastIndex(handOfPlayer);
                    return;
                }
            }
        }
    }

    /**
     * Sorts the tiles array based on compareTo method in Tile class, in a way to put null at last index
     * @param tiles tiles array to be sorted
     * @author Mert Uzun
     */
    public void sortTilesWithNullAtLastIndex(Tile[] tiles){
        int nullIndex = -1;

        for(int i = 0; i < tiles.length; i++){
            if (tiles[i] == null) {
                nullIndex = i;
                break;
            }
        }

        if (nullIndex != -1 && nullIndex != 14) {
            tiles[nullIndex] = tiles[14];
            tiles[14] = null;
        }

        Arrays.sort(tiles, 0, 14);
    }

    /**
     * This method first checks the validity of the index range then
     * removes the tile from the current player's hand and sets it as the last discarded tile
     * then prints the name of the player and the tile that is discarded
     * @param tileIndex the index of the tile to be discarded
     */
    public void discardTile(int tileIndex) 
    {
        // Validate index range
        if (tileIndex < 0 || tileIndex >= players[currentPlayerIndex].getNumberOfTiles()) 
        {
            System.out.println("Invalid tile index. Must be between 0 and " + (players[currentPlayerIndex].getNumberOfTiles() - 1));
            return;
        }

        // Remove the tile and set it as last discarded tile
        lastDiscardedTile = players[currentPlayerIndex].getAndRemoveTile(tileIndex);
        
        System.out.println(players[currentPlayerIndex].getName() + " discarded " + lastDiscardedTile.toString());
    }

    public void displayDiscardInformation() {
        if(lastDiscardedTile != null) {
            System.out.println("Last Discarded: " + lastDiscardedTile.toString());
        }
    }

    public void displayCurrentPlayersTiles() {
        players[currentPlayerIndex].displayTiles();
    } 

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

      public String getCurrentPlayerName() {
        return players[currentPlayerIndex].getName();
    }

    public void passTurnToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
    }

    public void setPlayerName(int index, String name) {
        if(index >= 0 && index <= 3) {
            players[index] = new Player(name);
        }
    }
}
