package robot;

public class Search {

    int n = 2147483647;
    String s = "";
    String MinS = "";

    void fd(Game myGame) {
        double x;
        while (myGame.canPlay) {
            x = Math.random();
            if (x < 0.25 && myGame.canMove(x)) {
                myGame.turnRight();
//                System.out.print('R');
                s += 'R';
            } else if (x >= 0.25 && x < 0.5 && myGame.canMove(x)) {
                myGame.turnLeft();
//                System.out.print('L');
                s += 'L';
            } else if (x >= 0.5 && x < 0.75 && myGame.canMove(x)) {
                myGame.turnUp();
//                System.out.print('U');
                s += 'U';
            } else if (x >= 0.75 && myGame.canMove(x)) {
                myGame.turnDown();
//                System.out.print('D');
                s += 'D';
            }
            if (myGame.move > n) break;
        }
        if (myGame.move < n && myGame.win) {
            n = myGame.move;
            MinS = s;
        }
    }

    void fd2(int count) {
        Game myGame;
        for (int i = 0; i < count; i++) {
            myGame = new Game();
            myGame.getMap();
            fd(myGame);
            s = "";
        }
    }
}
