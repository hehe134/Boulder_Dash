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
            if (x == 0 && myGame.canMove('R') && myGame.alive(x1 + 1, y1)) {
                myGame.RightOrLeft(1);
//                System.out.print('R');
                s += 'R';
            } else if (x == 1 && myGame.canMove('L') && myGame.alive(x1 - 1, y1)) {
                myGame.RightOrLeft(-1);
//                System.out.print('L');
                s += 'L';
            } else if (x == 2 && myGame.canMove('U') && myGame.alive(x1, y1 - 1)) {
                myGame.UpOrDown(-1);
//                System.out.print('U');
                s += 'U';
            } else if (x == 3 && myGame.canMove('D') && myGame.alive(x1, y1 + 1)) {
                myGame.UpOrDown(1);
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
