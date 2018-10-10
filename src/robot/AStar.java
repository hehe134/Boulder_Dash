package robot;

import static java.lang.Math.abs;

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
        return result;
    }

    public void findPath(Game myGame, Point start, Point end) {

//        Game test;
        myTree.insert(distance(start, end), ' ', true, null, myGame);
        circle(start, end, myGame, myTree.root);
        Tree.TreeNode T= myTree.MinNode();
        T.setClose();
        beClose(start, end,T);
    }

    public void circle(Point start, Point end, Game myGame, Tree.TreeNode newN) {
        Game test;
        Point newP = new Point(start.getX(), start.getY());

//        newP.setX(start.getX());
//        newP.setY(start.getY());
        try {
            if (myGame.canPlay && myGame.canMove('R') && myGame.isSafe(myGame.robot.x + 1, myGame.robot.y)) {
                newP.x++;
                test = (Game) myGame.clone();
                test.RightOrLeft(1);
                myTree.insert(distance(newP, end), 'R', false, newN, test);
            }
            newP.setX(start.getX());
            newP.setY(start.getY());
            if (myGame.canPlay && myGame.canMove('L') && myGame.isSafe(myGame.robot.x - 1, myGame.robot.y)) {
                newP.x--;
                test =(Game) myGame.clone();
                test.RightOrLeft(-1);
                myTree.insert(distance(newP, end), 'L', false, newN, test);
            }
            newP.setX(start.getX());
            newP.setY(start.getY());
            if (myGame.canPlay && myGame.canMove('U') && myGame.isSafe(myGame.robot.x, myGame.robot.y - 1)) {
                newP.y--;
                test = (Game) myGame.clone();
                test.UpOrDown(-1);
                myTree.insert(distance(newP, end), 'U', false, newN, test);
            }
            newP.setX(start.getX());
            newP.setY(start.getY());
            if (myGame.canPlay && myGame.canMove('D') && myGame.isSafe(myGame.robot.x, myGame.robot.y + 1)) {
                newP.y++;
                test =(Game) myGame.clone();
                test.UpOrDown(1);
                myTree.insert(distance(newP, end), 'D', false, newN, test);
            }
        }catch (CloneNotSupportedException e){

        }
    }

    Tree.TreeNode newN1;
    StringBuilder sb;
    String s = "";

    public void beClose(Point start, Point end, Tree.TreeNode now) {
        circle(start, end,  now.getMyGame(), now);
       Tree.TreeNode T= myTree.MinNode();
       T.setClose();
        System.out.printf("(" + T.getMyGame().robot.getX() + "," + T.getMyGame().robot.getY() + ")");
        if (T.getMyGame().robot.x == end.x&&T.getMyGame().robot.y == end.y) {
            s = "";
            sb = new StringBuilder(s);
            while (T.getParent() != null) {
                sb.insert(0, T.getDirection());
                T=T.getParent();
            }
        } else {
            beClose(start, end,T);
        }
    }

    public int distance(Point start, Point end) {
        return (abs(start.x - end.x) + abs(start.y - end.y));
    }


}

