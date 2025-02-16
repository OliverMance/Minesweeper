import java.util.List;

abstract class Tile {

    // the ASCII character representing the Tile type
    protected String icon;

    // boolean to determine whether icon should be shown
    protected boolean visible;

    // boolean to determine whether Tile is flagged
    protected boolean flagged;

    // method to process behaviour if Tile selected
    public abstract void select();

    // method to count number of adjacent Mines
    public void countMines() {
        // to be overridden by EmptyTile
    }

    // method to toggle the flag status of the tile
    public void toggleFlag() {
        // check if tile has already been selected
        if (!visible) {
            // toggle flag with bitwise XOR
            this.flagged ^= true;
            System.out.println("\nTile flag toggled!");
        } else {
            System.out.println("\nTile already cleared!");
        }
    }

    // method to display the tile
    public abstract void display();

    // setter method for adding neighbours
    public abstract void setNeighbours(List<Tile> neighbours);
}
