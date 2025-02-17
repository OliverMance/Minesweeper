import java.util.List;

public class EmptyTile extends Tile {

    // List of neighbour Tiles
    private List<Tile> neighbours;

    // the number of mines in the neighbour tiles
    private int adjMineCount;

    public EmptyTile() {
        this.icon = " ";
        this.visible = false;
        this.flagged = false;
    }

    // method to find the number of MineTiles in the Tile's neighbours
    @Override
    public void countMines() {
        int count = 0;
        for (Tile n : this.neighbours) {
            if (n.getClass() == MineTile.class) {
                count++;
            }
        }
        // set attribute to the number of MineTiles found
        this.adjMineCount = count;

        // if no mines adjacent, empty icon, otherwise the number of adjacent mines
        if (this.adjMineCount == 0) {
            this.icon = "_";
        } else {
            this.icon = String.valueOf(this.adjMineCount);
        }
    }

    // method to process behaviour if Tile selected
    @Override
    public void select() {
        // check if tile is already visible
        if (!this.visible) {
            // check if Tile is flagged
            if (this.flagged) {
                System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("\nThis tile is flagged! Please remove the flag if you wish to select this tile!");
            } else {
                // Empty tile scenario
                // set icon to be visible (cleared)
                this.visible = true;
            }
        } else {
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("\nTile already cleared!");
        }
    }

    // method to display the tile
    @Override
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

    @Override
    public void setNeighbours(List<Tile> neighbours) { this.neighbours = neighbours; }

    public List<Tile> getNeighbours() { return neighbours; }

    public int getAdjMineCount() { return adjMineCount; }
}
