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
    if (players[currentPlayerIndex].numberOfTiles >= 15) 
    {
        return "Cannot pick up: Hand is full.";
    }


        Tile tile = lastDiscardedTile;
        players[currentPlayerIndex].addTile(tile);
        lastDiscardedTile = null;
        return tile.toString();

        }

 

    /*
     * TODO: get the top tile from tiles array for the current player
     * that tile is no longer in the tiles array (this simulates picking up the top tile)
     * it should return the toString method of the tile so that we can print what we picked
     */
    public String getTopTile() {
        return null;
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
     * TODO: check if game still continues, should return true if current player
     * finished the game, use isWinningHand() method of Player to decide
     */
    public boolean didGameFinish() {
        return players[currentPlayerIndex].isWinningHand();
    }

    /*
     * TODO: Pick a tile for the current computer player using one of the following:
     * - picking from the tiles array using getTopTile()
     * - picking from the lastDiscardedTile using getLastDiscardedTile()
     * You should consider if the discarded tile is useful for the computer in
     * the current status. Print whether computer picks from tiles or discarded ones.
     */
    public void pickTileForComputer() {

    }

    /*
     * TODO: Current computer player will discard the least useful tile.
     * this method should print what tile is discarded since it should be
     * known by other players. You may first discard duplicates and then
     * the single tiles and tiles that contribute to the smallest chains.
     */
    public void discardTileForComputer() {

    }

    /*
     * TODO: discards the current player's tile at given index
     * this should set lastDiscardedTile variable and remove that tile from
     * that player's tiles
     */
    public void discardTile(int tileIndex) {

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
