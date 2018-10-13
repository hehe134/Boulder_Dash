package robot;

import java.util.Scanner;

public class GameTest {


    public static void main(String[] args) {
//        Game myGame = new Game();
//        myGame.getMap();
//        myGame.printMap();
//        Scanner scan = new Scanner(System.in);
//        c1:
//        while (scan.hasNext()) {
//            String str = scan.next();
//            for (int i = 0; i < str.length(); i++) {
//                if (str.charAt(i) == 'R') myGame.RightOrLeft(1);
//                else if (str.charAt(i) == 'L') myGame.RightOrLeft(-1);
//                else if (str.charAt(i) == 'U') myGame.UpOrDown(-1);
//                else if (str.charAt(i) == 'D') myGame.UpOrDown(1);
//                else if (str.charAt(i) == 'A') myGame.commandA();
//                else if (str.charAt(i) == 'W') myGame.commandW();
//                System.out.println("Score: " + myGame.score + "\nMoves: " + myGame.move);
//                if (myGame.win) {
//                    System.out.println("You win");
//                    break c1;
//                } else if (!myGame.canPlay) {
//                    System.out.println("You lose");
//                    break c1;
//                }
//            }
//        }

//        Search mysearch = new Search();
//        mysearch.fd2(10000);
//        System.out.println("result :" + mysearch.MaxS);

        AStar myAstar = new AStar();
        System.out.println("result2: " + myAstar.getWholePath());


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
