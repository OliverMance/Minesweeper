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

        for (int y = 0; y < this.height; y++) {
            List<Tile> row = new ArrayList<Tile>();
            for(int x = 0; x < this.width; x++) {
                row.add(new EmptyTile());
            }
            this.field.add(row);
        }

        // add mines to board and find neighbours
        addMines();
        findNeighbours();
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

    // method to find neighbour Tiles for each Tile
    public void findNeighbours() {
        // loop through field, processing each Tile
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.get(i).size(); j++) {
                // only process Tile if non-MineTile
                // set neighbours if non-mine Tile
                if (field.get(i).get(j).getClass() != MineTile.class) {
                    List<Tile> neighbours = new ArrayList<>();
                    try {
                        // add tiles in all adjacent directions
                        // don't look for above tiles if tile on top row
                        if (i != 0) {
                            // don't look for left tiles if in first column
                            if (j != 0) {
                                // top left
                                neighbours.add(field.get(i - 1).get(j - 1));
                            }
                            // top centre
                            neighbours.add(field.get(i - 1).get(j));
                            // don't look for right tiles if in last column
                            if (j != (field.get(i).size() - 1)) {
                                // top right
                                neighbours.add(field.get(i - 1).get(j + 1));
                            }
                        }

                        // don't look for below tiles if tile on bottom row
                        if (i != (field.size() - 1)) {
                            // don't look for left tiles if in first column
                            if (j != 0) {
                                // bottom left
                                neighbours.add(field.get(i + 1).get(j - 1));
                            }
                            // bottom centre
                            neighbours.add(field.get(i + 1).get(j));
                            // don't look for right tiles if in last column
                            if (j != (field.get(i).size() - 1)) {
                                // bottom right
                                neighbours.add(field.get(i + 1).get(j + 1));
                            }
                        }

                        // don't look for left tiles if in first column
                        if (j != 0) {
                            // left
                            neighbours.add(field.get(i).get(j - 1));
                        }
                        // don't look for right tiles if in last column
                        if (j != (field.get(i).size() - 1)) {
                            // right
                            neighbours.add(field.get(i).get(j + 1));
                        }

                        // set the neighbours list for the Tile
                        field.get(i).get(j).setNeighbours(neighbours);
                        // count the number of mines adjacent to the Tile
                        field.get(i).get(j).countMines();
                        // handle non-index errors
                    } catch (Exception e) {
                        System.out.println("Encountered error: " + e.getMessage());
                        System.exit(0);
                    }
                }
            }
        }
    }

    // method to handle clearing of a Tile and/or floodfill clearing via recursion
    public void clearTile(Tile t) {
        // base case if Tile is a MineTile, flagged or already cleared
        if (t.getClass() == MineTile.class || t.isFlagged() || t.isVisible()) {
            t.select();
            return;
        }

        // Reveal the current tile before recursion
        t.select();

        // only search neighbours if Tile has no adjacent mines
        if (((EmptyTile) t).getAdjMineCount() == 0) {
            for (Tile n : ((EmptyTile) t).getNeighbours()) {
                // only process Tile if not already cleared (Check visibility before recursion)
                if (!n.isVisible()) {
                    // check/clear adjacent tile and its neighbours via recursion
                    clearTile(n);
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
