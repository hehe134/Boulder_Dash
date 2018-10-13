package robot;

import com.sun.jdi.Value;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

import static java.lang.Math.abs;
import static java.util.Arrays.*;


public class Game implements Cloneable {
    public char[][] map;
    boolean[][] theRobotHasBeen;
    //    int[][] times;
    public Point robot;
    public int lamda = 0;
    public int allLamda = 0;
    public Point lift;
    ArrayList<Point> point_lamda = new ArrayList<Point>();
    int m;
    int n;
    int move;
    int score;
    public boolean canPlay = true;
    public boolean win = false;
    HashMap<Character, Point> teleporter = new HashMap();
    HashMap<Character, Point> teleporterOut = new HashMap();
    int water;
    int aliveUnderWater = 10;
    //    R — робот
//    * — камень
//    L — закрытый выход
//    . — земля
//    # — стена
//    \ — λ
//    O — открытый выход
//    " " — пробел, пустая клетка


    public void fall() {
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                if (map[x][y] == '*' || map[x][y] == '@') {
                    switch (map[x][y + 1]) {
                        //Под камнем пустота — камень падает на 1 клетку вниз.
                        case ' ':
//                            map[x][y] = ' ';
                            if (map[x][y] == '*') {
                                map[x][y] = ' ';
                                map[x][y + 1] = '*';
                            } else {
                                map[x][y] = ' ';
                                if (map[x][y + 2] != ' ') {
                                    map[x][y + 1] = '\\';
                                } else {
                                    map[x][y + 1] = '@';
                                }
                            }
                            if (map[x][y + 2] == 'R') {
                                canPlay = false;
                            } //dead
                            break;
                        //Под камнем — камень, справа пусто и справа внизу пусто — камень падает по диагонали вправо.
                        case '*':
                            if (map[x + 1][y + 1] == ' ' && map[x + 1][y] == ' ') {
                                if (map[x][y] == '*') {
                                    map[x][y] = ' ';
                                    map[x + 1][y + 1] = '*';
                                } else {
                                    map[x][y] = ' ';
                                    if (map[x + 1][y + 2] != ' ') {
                                        map[x + 1][y + 1] = '\\';
                                    } else {
                                        map[x + 1][y + 1] = '@';
                                    }
                                }
                                if (map[x + 1][y + 2] == 'R') {
                                    canPlay = false;
                                } //dead
                            }
                            // //Под камнем — камень, слева пусто и слева внизу пусто — камень падает по диагонали влево.
                            else if (map[x - 1][y + 1] == ' ' && map[x - 1][y] == ' ') {
                                if (map[x][y] == '*') {
                                    map[x][y] = ' ';
                                    map[x - 1][y + 1] = '*';
                                } else {
                                    map[x][y] = ' ';
                                    if (map[x - 1][y + 2] != ' ') {
                                        map[x - 1][y + 1] = '\\';
                                    } else map[x - 1][y + 1] = '@';
                                }
                                if (map[x - 1][y + 2] == 'R') {
                                    canPlay = false;
                                } //dead
                            }
                            break;
                        //Под камнем — λ, справа пусто и справа внизу пусто — камень падает по диагонали вправо.
                        case '\\':
                            if (map[x + 1][y] == ' ' && map[x + 1][y + 1] == ' ') {
                                if (map[x][y] == '*') {
                                    map[x][y] = ' ';
                                    map[x + 1][y + 1] = '*';
                                } else {
                                    map[x][y] = ' ';
                                    if (map[x + 1][y + 2] != ' ') {
                                        map[x + 1][y + 1] = '\\';
                                    } else map[x + 1][y + 1] = '@';
                                }
                                if (map[x + 1][y + 2] == 'R') {
                                    canPlay = false;
                                } //dead
                            }
                            break;

                    }
                }
            }
        }

    }

    void step() {
        fall();
        move++;
        score--;
    }

    void RightOrLeft(int flag) {
//        if (flag) n = 1;
//        else n = -1;
        if (canPlay) {
            if (teleporter.containsKey(map[robot.x + flag][robot.y])) {
                teleport(map[robot.x + flag][robot.y]);
            } else {
                switch (map[robot.x + flag][robot.y]) {
                    case ' ':
                        map[robot.x][robot.y] = ' ';
                        robot.x += flag;
                        map[robot.x][robot.y] = 'R';
                        step();
                        break;
                    case '.':
                        map[robot.x][robot.y] = ' ';
                        robot.x += flag;
                        map[robot.x][robot.y] = 'R';
                        step();
                        break;
                    case '*':
                        if (map[robot.x + 2 * flag][robot.y] == ' ') {
                            map[robot.x][robot.y] = ' ';
                            robot.x += flag;
                            map[robot.x][robot.y] = 'R';
                            map[robot.x + flag][robot.y] = '*';
                            step();
                        }
                        break;
                    case '@':
                        if (map[robot.x + 2 * flag][robot.y] == ' ') {
                            map[robot.x][robot.y] = ' ';
                            robot.x += flag;
                            map[robot.x][robot.y] = 'R';
                            map[robot.x + flag][robot.y] = '@';
                            step();
                        }
                        break;
                    case '\\':
                        lamda++;
                        score += 25;
                        openTheLift();
                        map[robot.x][robot.y] = ' ';
                        robot.x += flag;
                        map[robot.x][robot.y] = 'R';
                        step();
                        removeLamda(robot);
                        break;
                    case '0':
                        win = true;
                        map[robot.x][robot.y] = ' ';
                        robot.x = robot.x + flag;
                        map[robot.x][robot.y] = 'R';
                        score += 50;
                        canPlay = false;
                        step();
                        break;
                }
            }
            theRobotHasBeen[robot.x][robot.y] = true;
//            printMap();
        }
    }

    void UpOrDown(int flag) {
        if (canPlay) {
            if (teleporter.containsKey(map[robot.x][robot.y + flag])) {
                teleport(map[robot.x][robot.y + flag]);
            } else {
                switch (map[robot.x][robot.y + flag]) {
                    case ' ':
                        map[robot.x][robot.y] = ' ';
                        robot.y += flag;
                        map[robot.x][robot.y] = 'R';
                        step();
                        break;
                    case '\\':
                        lamda++;
                        score += 25;
                        openTheLift();
                        map[robot.x][robot.y] = ' ';
                        robot.y += flag;
                        map[robot.x][robot.y] = 'R';
                        step();
                        removeLamda(robot);
                        break;
                    case '.':
                        map[robot.x][robot.y] = ' ';
                        robot.y += flag;
                        map[robot.x][robot.y] = 'R';
                        step();
                        break;
                    case '0':
                        win = true;
                        map[robot.x][robot.y] = ' ';
                        robot.y = robot.y + flag;
                        map[robot.x][robot.y] = 'R';
                        score += 50;
                        canPlay = false;
                        step();
                        break;
                }
            }
            theRobotHasBeen[robot.x][robot.y] = true;
//            printMap();
        }
    }

    void openTheLift() {
        if (allLamda == lamda) {
            map[lift.x][lift.y] = '0';
        }
    }

    /*public void turnRight() {
        int flag = 1;
        if (canPlay) {
            RightO rLeft(flag);
            fall();
            move++;
            score--;
        }
    }

    public void turnLeft() {
        int flag = -1;
        if (canPlay) {
            RightOrLeft(flag);
            fall();
            move++;
            score--;
        }
    }
*/
/*
    public void turnUp() {
        if (canPlay) {
            UpOrDown(false);
            fall();
            move++;
            score--;
        }
    }

    public void turnDown() {
        if (canPlay) {
            UpOrDown(true);
            fall();
            move++;
            score--;
        }
    }
*/

    public void commandA() {
        if (canPlay) {
            canPlay = false;
            win = true;
            score += 25;
        }
        printMap();
    }

    public void commandW() {
        if (canPlay) {
            fall();
            move++;
            score--;
        }
        printMap();
    }

    public char getObject(Point p) {
        return map[p.x][p.y];
    }

    public void getMap() {
        try {
            String pathname = "map/input1";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            int j = 0;
            int maxlength = 0;
            while (line != null) {
                if (line.length() > maxlength) maxlength = line.length();
                line = br.readLine();
                j++;
            }
            m = maxlength;
            n = j;
            reader = new InputStreamReader(
                    new FileInputStream(filename));
            br = new BufferedReader(reader);
            line = "";
            line = br.readLine();
            j = 0;
            map = new char[m][n];
            while (line != null) {
                for (int i = 0; i < line.length(); i++) {
                    map[i][j] = line.charAt(i);
                    if (line.charAt(i) == 'R') {
                        robot = new Point(i, j);
                    }
                    if (line.charAt(i) == 'L') {
                        lift = new Point(i, j);
                    }
                    if (line.charAt(i) == '\\' || line.charAt(i) == '@') {
                        allLamda++;
                    }
                    if (line.charAt(i) == '\\') {
                        point_lamda.add(new Point(i, j));
                    }
                    if (line.charAt(i) >= 'A' && line.charAt(i) <= 'I') {
                        teleporter.put(line.charAt(i), new Point(i, j));
                    }
                    if (line.charAt(i) >= '1' && line.charAt(i) <= '9') {
                        teleporterOut.put(line.charAt(i), new Point(i, j));
                    }
                }
                line = br.readLine();
                j++;
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        theRobotHasBeen = new boolean[m][n];
        newMapB();
    }

    public boolean canMove(char a) {
        if (a == 'R') {
            char sth = map[robot.x + 1][robot.y];
            if (sth != '*' && sth != '@' && sth != '#' && sth != 'L') {
                return true;
            } else if ((sth == '*' || sth == '@') && map[robot.x + 2][robot.y] == ' ') {
                return true;
            } else return false;
        } else if (a == 'L') {
            char sth = map[robot.x - 1][robot.y];
            if (sth != '*' && sth != '@' && sth != '#' && sth != 'L') {
                return true;
            } else if ((sth == '*' || sth == '@') && map[robot.x - 2][robot.y] == ' ') {
                return true;
            } else return false;
        } else if (a == 'U') {
            char sth = map[robot.x][robot.y - 1];
            if (sth != '*' && sth != '@' && sth != '#' && sth != 'L') {
                return true;
            } else return false;
        } else {
            char sth = map[robot.x][robot.y + 1];
            if (sth != '*' && sth != '@' && sth != '#' && sth != 'L') {
                return true;
            } else return false;
        }
    }

    public boolean alive(int x, int y) {
        boolean flag = true;
        if (map[x][y - 1] == ' ' && (map[x][y - 2] == '*' || map[x][y - 2] == '@')) {
            flag = false;
        }
        if (map[x][y - 1] == ' ' && (map[x - 1][y - 1] == '*' || map[x - 1][y - 1] == '@' || map[x - 1][y - 1] == '\\') &&
                (map[x - 1][y - 2] == '*' || map[x - 1][y - 2] == '@')) {
            flag = false;
        }
        if (map[x][y - 1] == ' ' && (map[x + 1][y - 1] == '*' || map[x + 1][y - 1] == '@') &&
                (map[x + 1][y - 2] == '*' || map[x + 1][y - 2] == '@') &&
                (map[x + 2][y - 2] != ' ' || map[x + 2][y - 1] != ' ')) {
            flag = false;
        }
        return flag;
    }

    public void teleport(char from) {
        Point a = teleporterOut.get((char) (from - 16));
        Point b = teleporter.get(from);
        map[a.x][a.y] = 'R';
        map[robot.x][robot.y] = ' ';
        robot.x = a.x;
        robot.y = a.y;
        map[b.x][b.y] = ' ';
    }

    public Point findNextLamda(Point current) {
        int y1;
        int x1;
        Point lamda = null;
        for (int i = 1; i < max(current.x, m - 1 - current.x, current.y, n - 1 - current.y); i++) {
            for (x1 = current.x - i; x1 <= current.x + i; x1++) {
                y1 = i + current.y;
                if (lamdaInMap(x1, y1)) {
                    lamda = new Point(x1, y1);
                }
                y1 = current.y - i;
                if (lamdaInMap(x1, y1)) {
                    lamda = new Point(x1, y1);
                }
            }
            for (y1 = current.y - i; y1 <= current.y + i; y1++) {
                x1 = current.x + i;
                if (lamdaInMap(x1, y1)) {
                    lamda = new Point(x1, y1);
                }
                x1 = current.x - i;
                if (lamdaInMap(x1, y1)) {
                    lamda = new Point(x1, y1);
                }
            }
        }
        return lamda;
    }

    Point findNextLamda2(Point current) {
        Iterator<Point> it = point_lamda.iterator();
        int MINdis = Integer.MAX_VALUE;
        int dis;
        Point MIN_Point = null;
        Point now = null;
        while (it.hasNext()) {
            now = it.next();
            dis = (abs(current.x - now.x) + abs(current.y - now.y));
            if (dis < MINdis) {
                MINdis = dis;
                MIN_Point = now;
            }
        }
        return MIN_Point;
    }

    void removeLamda(Point p) {
        Iterator<Point> it = point_lamda.iterator();
        while (it.hasNext()) {
            Point e = it.next();
            if (e.getX() == p.getX() && e.getY() == p.getY()) {
                it.remove();
            }
        }
    }

    int max(int a, int b, int c, int d) {
        int max = a;
        if (max < b) max = b;
        if (max < c) max = c;
        if (max < d) max = d;
        return max;
    }

    public boolean lamdaInMap(int x1, int y1) {
        if (x1 >= 0 && y1 >= 0 && x1 < m && y1 < n && map[x1][y1] == '\\') {
            return true;
        } else return false;
    }

    void printMap() {
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                System.out.print(map[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    void water(int nStart) {
        int nNow = nStart + move / 20;
        if (n - robot.y <= nNow) {
            aliveUnderWater--;
            if (aliveUnderWater == 0) canPlay = false;
        } else aliveUnderWater = 10;

    }

    char[][] copyMap(char[][] Map) {
        char[][] newMap = new char[m][n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                newMap[i][j] = Map[i][j];
            }
        }
        return newMap;
    }

//    List copyList(List<Point> PL){
//        List newPL=new ArrayList();
//        for (int i=0;i<PL.size();i++){
//            newPL.add(PL[i]);
//        }
//    }

    boolean[][] copyMap(boolean[][] Map) {
        boolean[][] newMap = new boolean[m][n];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                newMap[i][j] = Map[i][j];
            }
        }
        return newMap;
    }

    void newMapB() {
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                if (i == robot.x && j == robot.y)
                    theRobotHasBeen[i][j] = true;
                else theRobotHasBeen[i][j] = false;
            }
        }
    }

    boolean hasBeen(int x, int y) {
        if (theRobotHasBeen[x][y] == true) return true;
        else return false;
    }

    Point R() {
        return new Point(robot.x + 1, robot.y);
    }

    Point L() {
        return new Point(robot.x - 1, robot.y);
    }

    Point U() {
        return new Point(robot.x, robot.y - 1);
    }

    Point D() {
        return new Point(robot.x, robot.y + 1);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Game newGame = (Game) super.clone();
        this.map = copyMap(newGame.map);
        this.theRobotHasBeen = copyMap(newGame.theRobotHasBeen);
//        System.out.println(newGame.map == this.map);
        newGame.robot = (Point) this.robot.clone();
        newGame.point_lamda=(ArrayList<Point>) this.point_lamda.clone();
//        System.out.println(newGame.robot == this.robot);
        return newGame;
    }
}
