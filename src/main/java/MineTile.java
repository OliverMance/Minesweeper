import java.util.List;

public class MineTile extends Tile {

    public MineTile() {
        this.icon = "X";
        this.visible = false;
        this.flagged = false;
    }

    // method to process behaviour if Tile selected
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

    @Override
    public void setNeighbours(List<Tile> neighbours) {
        // do nothing for MineTile
    }
}
