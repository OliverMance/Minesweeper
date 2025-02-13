public class EmptyTile extends Tile {

    // the ASCII character representing the Tile type
    private final char icon;

    // boolean to determine whether icon should be shown
    private boolean visible;

    // boolean to determine whether Tile is flagged
    private boolean flagged;

    public EmptyTile() {
        this.icon = 'x';
        this.visible = false;
        this.flagged = false;
    }

    // method to process behaviour if Tile selected
    public void select() {
        // check if Tile is flagged
        if (this.flagged) {
            System.out.println("This tile is flagged! Please remove the flag if you wish to select this tile!");
        } else {
            // Empty tile scenario
            // set icon to be visible
            this.visible = true;
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
}
