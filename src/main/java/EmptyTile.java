import java.util.List;

public class EmptyTile extends Tile {

    // List of neighbour Tiles
    List<Tile> neighbours;

    // the number of mines in the neighbour tiles
    int mineCount;

    public EmptyTile() {
        this.icon = 'x';
        this.visible = false;
        this.flagged = false;
    }

    // method to find the number of MineTiles in the Tile's neighbours
    public void countMines() {
        int count = 0;
        for (Tile n : this.neighbours) {
            if (n.getClass() == MineTile.class) {
                count++;
            }
        }
        // set attribute to the number of MineTiles found
        this.mineCount = count;
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
