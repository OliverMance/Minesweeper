public class EmptyTile extends Tile {

    public EmptyTile() {
        this.icon = 'x';
        this.visible = false;
        this.flagged = false;
    }

    // method to process behaviour if Tile selected
    public void select() {
        // check if tile is already visible
        if (!this.visible) {
            // check if Tile is flagged
            if (this.flagged) {
                System.out.println("\nThis tile is flagged! Please remove the flag if you wish to select this tile!");
            } else {
                // Empty tile scenario
                // set icon to be visible
                this.visible = true;
            }
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

    public boolean isFlagged() { return flagged; }
}
