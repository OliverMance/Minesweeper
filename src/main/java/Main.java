public class Main {


    public static void main(String[] args) {
        MineTile testMine = new MineTile();
        MineTile mine3 = new MineTile();
        EmptyTile mine2 = new EmptyTile();

        testMine.display();
        mine2.display();
        mine3.display();

        testMine.toggleFlag();
        mine2.toggleFlag();

        testMine.display();
        mine2.display();
        mine3.display();

        testMine.select();
        mine2.select();

        testMine.display();
        mine2.display();
        mine3.display();

        testMine.toggleFlag();
        mine2.toggleFlag();
        mine3.toggleFlag();

        testMine.display();
        mine2.display();
        mine3.display();

        testMine.toggleFlag();
        mine2.select();

        testMine.display();
        mine2.display();
        mine3.display();

        testMine.toggleFlag();
        mine2.toggleFlag();

        testMine.display();
        mine2.display();
        mine3.display();

        mine2.select();

        testMine.display();
        mine2.display();
        mine3.display();
    }
}
