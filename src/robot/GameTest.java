package robot;

import java.util.Scanner;

public class GameTest {


    public static void main(String[] args) {
        Game myGame = new Game();
        myGame.getMap();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String str = scan.next();
            if (str.equals("R")) myGame.RightOrLeft(1);
            else if (str.equals("L")) myGame.RightOrLeft(-1);
            else if (str.equals("U")) myGame.UpOrDown(-1);
            else if (str.equals("D")) myGame.UpOrDown(1);
            if (myGame.win) {
                System.out.println("You win");
                break;
            }
        }
//        Search mysearch = new Search();
//        mysearch.fd2(10000);
//        System.out.println("result :" + mysearch.MaxS);

//        AStar myAstar = new AStar();
//        System.out.println("result2: " + myAstar.getWholePath());


//        myGame.turnLeft();
//        myGame.turnLeft();
//        myGame.turnDown();
//        myGame.turnLeft();
//        System.out.println("Robot: (" + myGame.robot.getX() + " , " + myGame.robot.getY() + ")");
//
//        if (myGame.win) System.out.println("You win");
//        System.out.println("Score: "+myGame.score);
//        for (int j = 0; j < myGame.n; j++) {
//            for (int i = 0; i < myGame.m; i++) {
//                System.out.print(myGame.map[i][j]);
//            }
//            System.out.println();
//        }
    }
}
