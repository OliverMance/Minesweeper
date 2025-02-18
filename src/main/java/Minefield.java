import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Minefield class contains the game board structure and contains methods for updating and checking its state
 * @author Oliver Mance
 */
public class Minefield {

    private int height;
    private int width;
    private int mineCount;
    private List<List<Tile>> field;

    /**
     * The constructor for a Minefield utilising a pre-defined difficulty
     * @param difficulty The string used to determine the Minefield attributes
     */
    public Minefield(String difficulty) {
        // set board attributes based on difficulty
        switch (difficulty) {
            case ("veryeasy"):
                this.height = 5;
                this.width = 5;
                this.mineCount = 3;
                break;
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

    /**
     * Overloaded constructor for a Minefield with custom attributes
     * @param height The height of the Minefield (in Tiles)
     * @param width The width of the Minefield (in Tiles)
     * @param mineCount The number of mines to be placed in the Minefield
     */
    public Minefield(int height, int width, int mineCount) {
        this.height = height;
        this.width = width;
        this.mineCount = mineCount;

        // run the local method to initialise the game board
        intialise();
    }

    /**
     * Method to generate a field of EmptyTiles with the dimension attributes determining the size
     */
    private void intialise() {

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
    }

    /**
     * Method to place the MineTiles in the initialised board
     * @param userX The x-coordinate of the user's first move
     * @param userY The y-coordinate of the user's first move
     */
    public void addMines(int userX, int userY) {

        // loop depending on number of mines to add
        for (int i = 0; i < this.mineCount; i++) {
            // loop until mine successfully placed
            boolean placed = false;
            while (!placed) {
                // generate random coordinates
                Random rand = new Random();
                int y = rand.nextInt(this.height);
                int x = rand.nextInt(this.width);

                // check Tile has not been selected as the user's first move
                if (y != userY && x != userX) {
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
    }

    /**
     * Method to find the neighbouring Tiles of each EmptyTile
     */
    public void findNeighbours() {
        // loop through field, processing each Tile
        for (int i = 0; i < field.size(); i++) {
            for (int j = 0; j < field.get(i).size(); j++) {
                // set neighbours for EmptyTile instances only
                if (field.get(i).get(j).getClass() == EmptyTile.class) {
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
                        EmptyTile currentTile = (EmptyTile) field.get(i).get(j);
                        currentTile.setNeighbours(neighbours);
                        // count the number of mines adjacent to the Tile
                        currentTile.countMines();

                        // handle non-index errors
                    } catch (Exception e) {
                        System.out.println("Encountered error: " + e.getMessage());
                        System.exit(0);
                    }
                }
            }
        }
    }

    /**
     * Method to handle clearing of a Tile and/or floodfill clearing via recursion
     * @param t The current Tile instance
     * @return The gameOver status as a boolean, true if a MineTile is selected, indicating gameOver
     */
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

        // Reveal the current tile before recursion (prevents infinite recursion vs after)
        t.select();

        // can only possibly be an EmptyTile at this point, but perform a check to rule out ClassCastException
        if (t.getClass() == EmptyTile.class) {
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
        // default return
        return false;
    }

    /**
     * Method to check the win condition of the field being successfully cleared
     * @return false if EmptyTiles remain uncleared, true if all EmptyTiles are cleared
     */
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

    /**
     * Method to display the current state of the Minefield board to the console
     */
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

    /**
     * Overloaded method to display the Minefield board with all mine locations visible
     * @param mineFlag The parameter to determine how the mine icons will be set
     */
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
                // set mines to be visible
                if (t.getClass() == MineTile.class) {
                    // if win condition, print mines with different icon
                    if (mineFlag.equals("W")) {
                        ((MineTile) t).setIcon("+");
                    }
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
