public class Tile implements Comparable {
    
    int value;
    char color;

    /*
     * Creates a tile using the given color and value, colors are represented
     * using the following letters: Y: Yellow, B: Blue, R: Red, K: Black
     * Values can be in the range [1,7]. There are four tiles of each color value
     * combination (7 * 4 * 4) = 112 tiles, false jokers are not included in this game.
     */
    public Tile(int value, char color) {
        this.value = value;
        this.color = color;
    }

    /*
     * Compares tiles so that they can be added to the hands in order
     */
    @Override
    public int compareTo(Object t) {
        Tile other = (Tile)t;
        if(getValue() < other.getValue()) {
            return -1;
        }
        else if(getValue() > other.getValue()) {
            return 1;
        }
        else{
             if(colorNameToInt() < other.colorNameToInt()) {
                return -1;
            }
            else if(colorNameToInt() > other.colorNameToInt()) {
                return 1;
            }
            else{
                return 0;
            }
        }
    }

    public int colorNameToInt() {
        if(color == 'Y') {
            return 0;
        }
        else if(color == 'B') {
            return 1;
        }
        else if(color == 'R') {
            return 2;
        }
        else {
            return 3;
        }
    }

    // determines if this tile can make a chain with the given tile
    public boolean canFormChainWith(Tile t) {

        // can make chain if same number but different color
        if(t.getColor() != color && t.getValue() == value) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return "" + value + color;
    }

    /**
     * Looks for two tiles if they are equal or not, based on both color and number matchings
     * @param other other tile to be considered
     * @return true or false if these two tiles are equal or not
     * @author Mert Uzun
     */
    public boolean equals(Tile other){
        return this.toString().equals(other.toString());
    }

    public int getValue() {
        return value;
    }

    public char getColor() {
        return color;
    }
    
}
