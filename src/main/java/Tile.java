abstract class Tile {

    // the ASCII character representing the Tile type
    private char icon;

    // boolean to determine whether icon should be shown
    private boolean visible;

    // boolean to determine whether Tile is flagged
    private boolean flagged;

    // method to process behaviour if Tile selected
    public abstract void select();

    // method to toggle the flag status of the tile
    public void toggleFlag() {
        // toggle flag with bitwise XOR
        this.flagged ^= true;
    }

    // method to display the tile
    public abstract void display();
}
