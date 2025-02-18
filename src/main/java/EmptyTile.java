import java.util.List;

/**
 * The subclass of Tile implementing the logic for an empty Tile (no mine)
 * @author Oliver Mance
 */
public class EmptyTile extends Tile {

    // List of neighbour Tiles
    private List<Tile> neighbours;

    // the number of mines in the neighbour tiles
    private int adjMineCount;

    /**
     * The constructor for the EmptyTile object
     */
    public EmptyTile() {
        this.icon = " ";
        this.visible = false;
        this.flagged = false;
    }

    /**
     * Method implementing logic to count the number of MineTiles in this instance's neighbours
     */
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

    /**
     * Definition of the abstract method, implementing the behaviour if an EmptyTile is selected for clearing
     */
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
    
    public void setNeighbours(List<Tile> neighbours) { this.neighbours = neighbours; }

    public List<Tile> getNeighbours() { return neighbours; }

    public int getAdjMineCount() { return adjMineCount; }
}
