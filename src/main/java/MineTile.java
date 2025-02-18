/**
 * The subclass of Tile implementing the logic for a Tile containing a mine
 * @author Oliver Mance
 */
public class MineTile extends Tile {

    /**
     * The constructor for the MineTile object
     */
    public MineTile() {
        this.icon = "*";
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

    public void setIcon(String icon) { this.icon = icon; }
}
