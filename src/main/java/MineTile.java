import java.util.List;

/**
 * The subclass of Tile implementing the logic for a Tile containing a mine
 * @author Oliver Mance
 */
public class MineTile extends Tile {

    /**
     * The constructor for the MineTile object
     */
    public MineTile() {
        this.icon = "X";
        this.visible = false;
        this.flagged = false;
    }

    /**
     * The implementation of the abstract method from Tile, defining the behaviour if a MineTile is selected for clearing
     */
    @Override
    public void select() {
        // check if Tile is flagged
        if (this.flagged) {
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("\nThis tile is flagged! Please remove the flag if you wish to select this tile!");
        } else {
            // GAME OVER scenario
            // set icon to be visible
            this.visible = true;
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("\nYou have selected a Mine!");
        }
    }

    /**
     * Empty override of the abstract method from superclass, empty as MineTile does not have a neighbours attribute but could be called when iterating through a Minefield
     * @param neighbours The List of neighbour Tiles
     */
    @Override
    public void setNeighbours(List<Tile> neighbours) {
        // do nothing for MineTile
    }
}
