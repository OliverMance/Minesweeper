import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minefield {

    private int height;
    private int width;
    private int mineCount;
    private List<List<Tile>> field;

    public Minefield(String difficulty) {
        // set board attributes based on difficulty
        switch (difficulty) {
            case ("easy"):
                this.height = 9;
                this.width = 9;
                this.mineCount = 10;
                break;
            case ("intermediate"):
                break;
            case ("hard"):
                break;
            case ("custom"):
                break;
            default:
                break;
        }

        intialise();
    }

    // method to generate a board of empty tiles
    public void intialise() {

        // define the board
        this.field = new ArrayList<List<Tile>>();

        for (int i = 0; i < this.height; i++) {
            List<Tile> row = new ArrayList<Tile>();
            for(int j = 0; j < this.width; j++) {
                row.add(new EmptyTile());
            }
            this.field.add(row);
        }

        addMines();
    }

    // helper method to add mine tiles when initalising board
    private void addMines() {

        // loop depending on number of mines to add
        for (int i = 0; i < this.mineCount; i++) {
            // loop until mine successfully placed
            boolean placed = false;
            while (!placed) {
                // generate random coordinates
                Random rand = new Random();
                int y = rand.nextInt(this.height);
                int x = rand.nextInt(this.width);

                // check if tile is already a mine
                if (this.field.get(y).get(x).getClass() != MineTile.class) {
                    this.field.get(y).set(x, new MineTile());

                    // use this to debug mine locations
                    //this.board.get(y).get(x).toggleFlag();

                    placed = true;
                }
            }
        }
    }

    // method to display the current state of the Minefield board
    public void display() {
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        // print column numbers
        System.out.print("\n   ");
        for (int i = 0; i < this.width; i++) {
            System.out.print(i + "  ");
        }
        System.out.print("\n");

        // loop through rows
        for (int i = 0; i < this.field.size(); i++) {
            // print row numbers
            System.out.print(i + " ");
            // display each Tile in row
            for (Tile t : this.field.get(i)) {
                t.display();
            }
            System.out.println("\n");
        }
    }

    public int getHeight() { return height; }

    public int getWidth() { return width; }

    public List<List<Tile>> getField() { return field; }
}
