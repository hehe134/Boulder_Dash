package robot;

import java.util.Scanner;

public class GameTest {
    public static void main(String[] args) {
        GameTest m = new GameTest();
//        m.playGame();
        m.getResult1();
//        m.getResult2();
    }


    void playGame() {
        Game myGame = new Game();
        myGame.getMap();
        myGame.printMap();
        Scanner scan = new Scanner(System.in);
        c1:
        while (scan.hasNext()) {
            String str = scan.next();
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == 'R'||str.charAt(i) == 'r') myGame.RightOrLeft(1);
                else if (str.charAt(i) == 'L'||str.charAt(i) == 'l') myGame.RightOrLeft(-1);
                else if (str.charAt(i) == 'U'||str.charAt(i) == 'u') myGame.UpOrDown(-1);
                else if (str.charAt(i) == 'D'||str.charAt(i) == 'd') myGame.UpOrDown(1);
                else if (str.charAt(i) == 'A'||str.charAt(i) == 'a') myGame.commandA();
                else if (str.charAt(i) == 'W'||str.charAt(i) == 'w') myGame.commandW();
                System.out.println("Score: " + myGame.score + "\nMoves: " + myGame.move);
                if (myGame.win) {
                    System.out.println("You win");
                    break c1;
                } else if (!myGame.canPlay) {
                    System.out.println("You lose");
                    break c1;
                }
                myGame.printMap();
            }
        }
    }

    void getResult1() {
        AStar myAstar = new AStar();
        System.out.println("result2: " + myAstar.getWholePath());
    }

    void getResult2() {
        Search mysearch = new Search();
        mysearch.fd2(10000);
        System.out.println("result :" + mysearch.MaxS);
    }


}
