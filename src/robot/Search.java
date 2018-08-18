package robot;

public class Search {

    int n = -2147483648;
    String s = "";
    String MaxS = "";

    void fd(Game myGame) {
        int x, x1, y1;
        while (myGame.canPlay) {
            x = (int) (Math.random() * 5);
            x1 = myGame.robot.x;
            y1 = myGame.robot.y;
            if (x == 0 && myGame.canMove(x) && myGame.isSafe(x1 + 1, y1)) {
                myGame.turnRight();
//                System.out.print('R');
                s += 'R';
            } else if (x == 1 && myGame.canMove(x) && myGame.isSafe(x1 - 1, y1)) {
                myGame.turnLeft();
//                System.out.print('L');
                s += 'L';
            } else if (x == 2 && myGame.canMove(x) && myGame.isSafe(x1, y1 - 1)) {
                myGame.turnUp();
//                System.out.print('U');
                s += 'U';
            } else if (x == 3 && myGame.canMove(x) && myGame.isSafe(x1, y1 + 1)) {
                myGame.turnDown();
//                System.out.print('D');
                s += 'D';
            }else if(x==5){
                myGame.fall();
            }
            if (myGame.score < n) break;
        }

        if (myGame.score > n && myGame.win == true && myGame.score > 0) {
            n = myGame.score;
            MaxS = s;
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
