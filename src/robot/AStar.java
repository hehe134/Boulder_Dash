package robot;

import static java.lang.Math.abs;

public class AStar {
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
            result += s;
        }
        return result;
    }

    public void findPath(Game myGame, Point start, Point end) {

        Game test;
        myTree.insert(distance(start, end), ' ', true, null, myGame);
        circle(start, end, myGame, myTree.root);
        beClose(start, end);
    }

    public void circle(Point start, Point end, Game myGame, Tree.TreeNode newN) {
        Game test;
        Point newP = new Point(start.getX(), start.getY());

//        newP.setX(start.getX());
//        newP.setY(start.getY());
        if (myGame.canMove('R') && myGame.isSafe(myGame.robot.x + 1, myGame.robot.y)) {
            newP.x++;
            test = myGame;
            test.RightOrLeft(1);
            myTree.insert(distance(newP, end), 'R', false, newN, test);
        }
        newP.setX(start.getX());
        newP.setY(start.getY());
        if (myGame.canMove('L') && myGame.isSafe(myGame.robot.x - 1, myGame.robot.y)) {
            newP.x--;
            test = myGame;
            test.RightOrLeft(-1);
            myTree.insert(distance(newP, end), 'L', false, newN, test);
        }
        newP.setX(start.getX());
        newP.setY(start.getY());
        if (myGame.canMove('U') && myGame.isSafe(myGame.robot.x, myGame.robot.y - 1)) {
            newP.y--;
            test = myGame;
            test.UpOrDown(-1);
            myTree.insert(distance(newP, end), 'U', false, newN, test);
        }
        newP.setX(start.getX());
        newP.setY(start.getY());
        if (myGame.canMove('D') && myGame.isSafe(myGame.robot.x, myGame.robot.y + 1)) {
            newP.y++;
            test = myGame;
            test.UpOrDown(1);
            myTree.insert(distance(newP, end), 'D', false, newN, test);
        }
    }

    Tree.TreeNode newN1;
    StringBuilder sb;
    String s = "";

    public void beClose(Point start, Point end) {
        newN1 = myTree.MinNode();
        circle(start, end, newN1.getMyGame(), newN1);
        myTree.MinNode().setClose();
        System.out.printf("(" + myTree.MinNode().getMyGame().robot.getX() + "," + myTree.MinNode().getMyGame().robot.getY() + ")");
        if (newN1.getMyGame().robot == end) {
            s = "";
            sb = new StringBuilder(s);
            while (newN1.getParent() != null) {
                sb.insert(0, newN1.getDirection());
            }
        } else {
            beClose(start, end);
        }
    }

    public int distance(Point start, Point end) {
        return (abs(start.x - end.x) + abs(start.y - end.y));
    }
}

