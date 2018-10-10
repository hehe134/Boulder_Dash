package robot;

import static java.lang.Math.abs;
import static java.util.Arrays.copyOf;

public class AStar implements Cloneable {
//    public class node {
//        int g, h, sum;
//        Game.Point p;
//        char direction;
//
//        public node(Game.Point p, int g, int h, int sum) {
//            this.p = p;
//            this.g = g;
//            this.h = h;
//            this.sum = sum;
//        }
//    }

    Tree myTree = new Tree();
    TreeNode T2;

    public String getWholePath() {
        Game myGame;
        myGame = new Game();
        myGame.getMap();
        Point lamda;
        String result = "";
        while (myGame.findNextLamda(myGame.robot) != null) {
            lamda = myGame.findNextLamda(myGame.robot);
            findPath(myGame, myGame.robot, lamda);
            result += sb;
        }
        myTree = new Tree();
        findPath(T2.getMyGame(), T2.getMyGame().robot, T2.getMyGame().lift);
        result += sb;
        return result;
    }

    public void findPath(Game myGame, Point start, Point end) {

//        Game test;
        myTree.insert(distance(start, end), ' ', true, null, myGame);
        circle(start, end, myGame, myTree.root);
        TreeNode T = myTree.MinNode();
        T.setClose();
        beClose(start, end, T);
    }

    public void circle(Point start, Point end, Game myGame, TreeNode newN) {

        Point newP = new Point(start.getX(), start.getY());

//        newP.setX(start.getX());
//        newP.setY(start.getY());
        try {
            if (myGame.canPlay && myGame.canMove('R') && myGame.isSafe(myGame.robot.x + 1, myGame.robot.y)) {
                newP.x++;
                Game test = (Game) myGame.clone();
                for (int j = 0; j < test.n; j++) {
                    for (int i = 0; i < test.m; i++) {
                        test.map[i][j] = myGame.map[i][j];
                    }
                }
                test.RightOrLeft(1);
                myTree.insert(distance(newP, end), 'R', false, newN, test);
            }
            newP.setX(start.getX());
            newP.setY(start.getY());
            if (myGame.canPlay && myGame.canMove('L') && myGame.isSafe(myGame.robot.x - 1, myGame.robot.y)) {
                newP.x--;
                Game test = (Game) myGame.clone();
                for (int j = 0; j < test.n; j++) {
                    for (int i = 0; i < test.m; i++) {
                        test.map[i][j] = myGame.map[i][j];
                    }
                }
                test.RightOrLeft(-1);
                myTree.insert(distance(newP, end), 'L', false, newN, test);
            }
            newP.setX(start.getX());
            newP.setY(start.getY());
            if (myGame.canPlay && myGame.canMove('U') && myGame.isSafe(myGame.robot.x, myGame.robot.y - 1)) {
                newP.y--;
                Game test = (Game) myGame.clone();
                for (int j = 0; j < test.n; j++) {
                    test.map[j] = copyOf(myGame.map[j], test.m);
                }
                test.UpOrDown(-1);
                myTree.insert(distance(newP, end), 'U', false, newN, test);
            }
            newP.setX(start.getX());
            newP.setY(start.getY());
            if (myGame.canPlay && myGame.canMove('D') && myGame.isSafe(myGame.robot.x, myGame.robot.y + 1)) {
                newP.y++;
                Game test = (Game) myGame.clone();
                for (int j = 0; j < test.n; j++) {
                    test.map[j] = copyOf(myGame.map[j], test.m);
                }
                test.UpOrDown(1);
                myTree.insert(distance(newP, end), 'D', false, newN, test);
            }
        } catch (CloneNotSupportedException e) {

        }
    }

    TreeNode newN1;
    StringBuilder sb;
    String s = "";

    public void beClose(Point start, Point end, TreeNode now) {
        circle(start, end, now.getMyGame(), now);
        TreeNode T = myTree.MinNode();
        T.setClose();
        System.out.printf("(" + T.getMyGame().robot.getX() + "," + T.getMyGame().robot.getY() + ")");
        if (T.getMyGame().robot.x == end.x && T.getMyGame().robot.y == end.y) {
            try {
                s = "";
                sb = new StringBuilder(s);
                if (T.getMyGame().getObject(T.getMyGame().lift) == '0') {
                    T2 = (TreeNode) T.clone();
                }
                while (T.getParent() != null) {
                    sb.insert(0, T.getDirection());
                    T = T.getParent();
                }
            } catch (CloneNotSupportedException e) {
            }
        } else {
            beClose(start, end, T);
        }
    }

    public int distance(Point start, Point end) {
        return (abs(start.x - end.x) + abs(start.y - end.y));
    }


}

