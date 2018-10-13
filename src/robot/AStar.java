package robot;

import static java.lang.Math.abs;


public class AStar implements Cloneable {

    Tree myTree = new Tree();
    Game robot_Game;

    public String getWholePath() {
        Game myGame;
        myGame = new Game();
        myGame.getMap();
        Point lamda;
        String result = "";
        lamda = myGame.findNextLamda2(myGame.robot);
        findPath(myGame, myGame.robot, lamda);
        result += sb;
        robot_Game.newMapB();
        while (robot_Game.findNextLamda2(robot_Game.robot) != null) {
            myTree = new Tree();
            lamda = robot_Game.findNextLamda2(robot_Game.robot);
            findPath(robot_Game, robot_Game.robot, lamda);
            result += sb;
            robot_Game.newMapB();
        }
        myTree = new Tree();
        findPath(robot_Game, robot_Game.robot, robot_Game.lift);
        result += sb;
        return result;
    }

    public void findPath(Game myGame, Point start, Point end) {

//        Game test;
        myTree.insert(distance(myGame.robot, start, end), ' ', true, null, myGame);
        aroundRobot(start, end, myGame, myTree.root);
        TreeNode T = myTree.MinNode();
        T.setClose();
        beClose(start, end, T);
    }

    public void aroundRobot(Point start, Point end, Game myGame, TreeNode newN) {


        Point newP = myGame.robot;
        try {
            if (myGame.canPlay && myGame.canMove('R') && myGame.alive(newP.x + 1, newP.y) && myGame.hasBeen(newP.x + 1, newP.y) == false) {

                Game test = (Game) myGame.clone();
                test.RightOrLeft(1);
                if (test.canPlay == true || test.win == true) {
                    myTree.insert(distance(test.robot, start, end), 'R', false, newN, test);
                }
            }

            if (myGame.canPlay && myGame.canMove('L') && myGame.alive(newP.x - 1, newP.y) && myGame.hasBeen(newP.x - 1, newP.y) == false) {

                Game test = (Game) myGame.clone();
                test.RightOrLeft(-1);
                if (test.canPlay == true || test.win == true) {
                    myTree.insert(distance(test.robot, start, end), 'L', false, newN, test);
                }
            }

            if (myGame.canPlay && myGame.canMove('U') && myGame.alive(newP.x, newP.y - 1) && myGame.hasBeen(newP.x, newP.y - 1) == false) {

                Game test = (Game) myGame.clone();
                test.UpOrDown(-1);
                if (test.canPlay == true || test.win == true) {
                    myTree.insert(distance(test.robot, start, end), 'U', false, newN, test);
                }
            }

            if (myGame.canPlay && myGame.canMove('D') && myGame.alive(newP.x, newP.y + 1) && myGame.hasBeen(newP.x, newP.y + 1) == false) {

                Game test = (Game) myGame.clone();
                test.UpOrDown(1);
                if (test.canPlay == true || test.win == true) {
                    myTree.insert(distance(test.robot, start, end), 'D', false, newN, test);
                }
            }
        } catch (CloneNotSupportedException e) {

        }
    }

    StringBuilder sb;
    String s = "";

    public void beClose(Point start, Point end, TreeNode now) {
        aroundRobot(start, end, now.getMyGame(), now);
        TreeNode T = myTree.MinNode();
        T.setClose();
        T.getMyGame().printMap();
//        System.out.printf("(" + T.getMyGame().robot.getX() + "," + T.getMyGame().robot.getY() + ")");
        if (T.getMyGame().robot.x == end.x && T.getMyGame().robot.y == end.y) {
            try {
                s = "";
                sb = new StringBuilder(s);
//                if (T.getMyGame().getObject(T.getMyGame().lift) == '0') {
                robot_Game = (Game) T.getMyGame().clone();
//                }
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

    public int distance(Point robot, Point start, Point end) {
        return (abs(start.x - robot.x) + abs(start.y - robot.y)
                + abs(end.x - robot.x) + abs(end.y - robot.y));
    }


}

