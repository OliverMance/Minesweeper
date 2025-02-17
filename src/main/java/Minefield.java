import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minefield {

    private int height;
    private int width;
    private int mineCount;
    private List<List<Tile>> field;

    // fixed difficulty constructor
    public Minefield(String difficulty) {
        // set board attributes based on difficulty
        switch (difficulty) {
            case ("easy"):
                this.height = 9;
                this.width = 9;
                this.mineCount = 10;
                break;
            case ("medium"):
                this.height = 15;
                this.width = 15;
                this.mineCount = 40;
                break;
            case ("hard"):
                this.height = 16;
                this.width = 30;
                this.mineCount = 99;
                break;
            default:
                break;
        }
        // run the local method to initialise the game board
        intialise();
    }

    // overloaded custom difficulty constructor
    public Minefield(int height, int width, int mineCount) {
        this.height = height;
        this.width = width;
        this.mineCount = mineCount;

        // run the local method to initialise the game board
        intialise();
    }

    // method to generate a board of empty tiles
    public void intialise() {

        // define the board
        this.field = new ArrayList<List<Tile>>();

        // populate the board with EmptyTile objects
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
                // set neighbours for Tile only processing Tile if non-MineTile
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
    public boolean clearTile(Tile t) {
        // base case if MineTile is initially selected, gameOver if not flagged
        if (t.getClass() == MineTile.class && !t.isFlagged()) {
            t.select();
            return true;
        }

        // base case if Tile is flagged or already cleared, no gameOver
        if (t.isFlagged() || t.isVisible()) {
            t.select();
            return false;
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
        // default return
        return false;
    }

    // method to check if the field has been successfully cleared
    public boolean fieldCleared() {
        boolean cleared = true;
        // iterate through each Tile
        for (List<Tile> row : this.field) {
            for (Tile t : row) {
                // if the Tile is not visible (not cleared) return false
                if (t.getClass() == EmptyTile.class && !t.isVisible()) {
                    cleared = false;
                    break;
                }
            }
        }
        // true if no uncleared Tiles are found
        return cleared;
    }

    // method to display the current state of the Minefield board
    public void display() {
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        // print column numbers
        System.out.print("\n   ");
        for (int i = 0; i < this.width; i++) {
            // space number appropriately depending on digit
            if (i < 10) {
                System.out.print(" " + i + " ");
            } else {
                System.out.print(" " + i);
            }
        }
        System.out.print("\n");

        // loop through rows
        for (int i = 0; i < this.field.size(); i++) {
            // print row numbers
            if (i < 10) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
            // display each Tile in row
            for (Tile t : this.field.get(i)) {
                t.display();
            }
            System.out.println("\n");
        }
    }

    // overloaded method to display the Minefield board with mine locations
    public void display(String mineFlag) {
        System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        // print column numbers
        System.out.print("\n   ");
        for (int i = 0; i < this.width; i++) {
            // space number appropriately depending on digit
            if (i < 10) {
                System.out.print(" " + i + " ");
            } else {
                System.out.print(" " + i);
            }
        }
        System.out.print("\n");

        // loop through rows
        for (int i = 0; i < this.field.size(); i++) {
            // print row numbers
            if (i < 10) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
            // display each Tile in row
            for (Tile t : this.field.get(i)) {
                // print mine location
                if (t.getClass() == MineTile.class) {
                    t.setVisible(true);
                }
                t.display();
            }
            System.out.println("\n");
        }
    }

    public int getHeight() { return height; }

    public int getWidth() { return width; }

    public List<List<Tile>> getField() { return field; }
}
