import java.util.Scanner;

public class Game {

    private Minefield board;
    private Scanner inp;
    private int currentX, currentY;
    private boolean gameOver;
    private boolean winCondition;

    public Game() {
        this.inp = new Scanner(System.in);
        this.gameOver = false;
        this.winCondition = false;
        this.board = createField();
    }

    // method to get difficulty level user input and create the game board/field
    public Minefield createField() {
        // loop until valid user input
        while(true) {
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print("\nPlease enter your chosen difficulty (easy, medium, hard or custom): ");
            String ans = inp.nextLine().toLowerCase();
            switch (ans) {
                // create board if valid preset value
                case "easy", "medium", "hard":
                    return new Minefield(ans);
                // create board with custom values
                case "custom":
                    return createCustom();
                // otherwise prompted to re-enter
                default:
                    System.out.println("Invalid input, please re-enter!");
                    break;
            }
        }
    }

    // method to get user-defined attributes and create a new Minefield
    public Minefield createCustom() {
        // loop until valid user input
        boolean valid = false;
        while(!valid) {
            try {
                // prompt user inputs
                System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.print("\nPlease enter the height of the Minefield: ");
                int height = Integer.parseInt(inp.nextLine());
                System.out.print("\nPlease enter the width of the Minefield: ");
                int width = Integer.parseInt(inp.nextLine());
                System.out.print("\nPlease enter the number of mines in the Minefield: ");
                int numMines = Integer.parseInt(inp.nextLine());

                // check if dimension inputs are positive and in a sensible range
                if (height >= 0 && height <= 50 && width >= 0 && width <= 50) {
                    // ensure mine count is positive and less than the total number of tiles
                    if (numMines > 0 && numMines < (height * width)) {
                        return new Minefield(height, width, numMines);
                    } else {
                        System.out.println("Please enter a mine count greater than 0 and less " +
                                "than (height x width), in this case " + (height * width) + "!");
                    }
                } else {
                    System.out.println("Please enter height and width values between 1-50!");
                }
            // handle non-integer inputs
            } catch (Exception e) {
                System.out.println("\nInvalid input type, please only use integers!");
                System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
        // default return
        return null;
    }

    // method implementing main game loop
    public void playGame() {
        // loop player moves until game has reached a finished state
        while (!this.gameOver) {
            // get user input and process their move
            inputMove();
            processMove();

            // check for win condition
            if (board.fieldCleared()) {
                this.winCondition = true;
                this.gameOver = true;
            }
        }

        // if game won, display message
        if (this.winCondition) {
            this.board.display();
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("\nCongratulations, you won!");
        } else {
            this.board.display("L");
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("\nSorry, you lost!");
        }
    }

    // method to handle the inputs for a user move
    public void inputMove() {

        this.board.display();

        int x, y;
        // loop until valid user input
        boolean valid = false;
        while(!valid) {
            try {
                // get row
                System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.print("\nPlease enter the row number: ");
                y = Integer.parseInt(inp.nextLine());
                // get columns
                System.out.print("\nPlease enter the column number: ");
                x = Integer.parseInt(inp.nextLine());

                // ensure input values are in range of game board
                if (y >= 0 && y < board.getHeight() && x >= 0 && x < board.getWidth()) {
                    this.currentX = x;
                    this.currentY = y;
                    valid = true;
                } else {
                    System.out.println("\nCoordinate value out of range!");
                    System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                }

                // catch invalid input type
            } catch (Exception e) {
                System.out.println("\nInvalid input type, please only use integers!");
                System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
    }

    // method for processing the player move
    public void processMove() {
        // establish whether stepping or flagging
        // loop until valid input is given
        boolean valid = false;
        while (!valid) {
            System.out.print("\nDo you want to step on this tile or flag it? (s/f): ");
            String ans = inp.nextLine().toLowerCase();

            // perform action based on input
            switch (ans) {
                // case for 'stepping' on a Tile
                case ("s"):
                    // update gameOver state based on returned value (true if 'step' on MineTile)
                    this.gameOver = this.board.clearTile(this.board.getField().get(this.currentY).get(this.currentX));
                    valid = true;
                    break;
                // case for 'flagging' a Tile
                case ("f"):
                    this.board.getField().get(this.currentY).get(this.currentX).toggleFlag();
                    valid = true;
                    break;
                // case for invalid input (repeats prompt)
                default:
                    System.out.println("\nInvalid input, please try again!");
                    System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }
    }
}
