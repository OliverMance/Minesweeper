import java.util.List;

public abstract class Tile {

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
    public void display() {
        // only display icon if visible
        if (this.visible) {
            System.out.print("[" + this.icon + "]");
        } else if (this.flagged) {
            System.out.print("[f]");
        } else {
            System.out.print("[ ]");
        }
    }

    // setter method for adding neighbours
    public abstract void setNeighbours(List<Tile> neighbours);

    // method to set visible status manually
    public void setVisible(boolean visible) { this.visible = visible; }

    // return visibility status of the Tile
    public boolean isVisible() { return visible; }

    // return flagged status of the Tile
    public boolean isFlagged() { return flagged; }
}
