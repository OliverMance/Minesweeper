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
        this.board = new Minefield(getDifficulty());
    }

    // method to get difficulty level user input
    public String getDifficulty() {
        // loop until valid user input
        while(true) {
            System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.print("\nPlease enter your chosen difficulty (easy, medium, hard): ");
            String ans = inp.nextLine().toLowerCase();
            switch (ans) {
                // return string if valid
                case "easy", "medium", "hard":
                    return ans;
                // otherwise prompted to re-enter
                default:
                    System.out.println("Invalid input, please re-enter!");
                    break;
            }
        }
    }

    // TODO method for game loop
    // method to handle game main loop
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
