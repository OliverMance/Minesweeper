import java.util.Scanner;

public class Game {

    private Minefield board;
    private Scanner inp;
    private int currentX, currentY;

    public Game() {
        this.board = new Minefield("easy");
        this.inp = new Scanner(System.in);
    }

    // TODO method for game loop


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

            switch (ans) {
                case ("s"):
                    this.board.getField().get(this.currentY).get(this.currentX).select();
                    valid = true;
                    break;
                case ("f"):
                    this.board.getField().get(this.currentY).get(this.currentX).toggleFlag();
                    valid = true;
                    break;
                default:
                    System.out.println("\nInvalid input, please try again!");
                    System.out.print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }

        this.board.display();
    }
}
