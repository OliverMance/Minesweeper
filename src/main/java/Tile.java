import java.util.List;

/**
 * The abstract class to template the differing Tile subclasses with some definitions being implemented
 * @author Oliver Mance
 */
public abstract class Tile {

    /**
     * The String representing the Tile output when displaying the Minefield
     */
    protected String icon;

    /**
     * Boolean to determine whether the Tile has been cleared and the icon should be shown
     */
    protected boolean visible;

    /**
     * Boolean to determine whether Tile is flagged
     */
    protected boolean flagged;

    /**
     * Abstract method to process behaviour if Tile selected to be cleared
     */
    public abstract void select();

    /**
     * Method to count number of adjacent MineTiles, only to be overridden by EmptyTile
     */
    public void countMines() {
        // to be overridden by EmptyTile
    }

    /**
     * Method to toggle the flag status of the Tile, no need to be overridden in subclasses
     */
    public void toggleFlag() {
        // check if tile has already been selected
        if (!visible) {
            // toggle flag with bitwise XOR
            this.flagged ^= true;
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("\nTile flag toggled!");
        } else {
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("\nTile already cleared!");
        }
    }

    /**
     * Method to print the Tile to the console, no need to be overridden in subclasses
     */
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

    /**
     * Abstract setter method for adding a List of neighbour Tiles, definition only needed in EmptyTile
     * @param neighbours The List of neighbour Tiles
     */
    public abstract void setNeighbours(List<Tile> neighbours);

    // method to set visible status manually
    public void setVisible(boolean visible) { this.visible = visible; }

    // return visibility status of the Tile
    public boolean isVisible() { return visible; }

    // return flagged status of the Tile
    public boolean isFlagged() { return flagged; }
}
