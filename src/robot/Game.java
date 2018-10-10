package robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


public class Game implements Cloneable {
    public char[][] map;
    int[][] times;
    public Point robot;
    public int lamda = 0;
    public int allLamda = 0;
    public Point lift;
    int m;
    int n;
    int move;
    int score;
    public boolean canPlay = true;
    public boolean win = false;
    HashMap<Character, Point> teleporter = new HashMap();
    HashMap<Character, Point> teleporterOut = new HashMap();

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


    void RightOrLeft(int flag) {
//        if (flag) n = 1;
//        else n = -1;
        if (canPlay) {
            switch (map[robot.x + flag][robot.y]) {
                case ' ':
                    map[robot.x][robot.y] = ' ';
                    robot.x += flag;
                    map[robot.x][robot.y] = 'R';
                    break;
                case '.':
                    map[robot.x][robot.y] = ' ';
                    robot.x += flag;
                    map[robot.x][robot.y] = 'R';
                    break;
                case '*':
                    if (map[robot.x + 2 * flag][robot.y] == ' ') {
                        map[robot.x][robot.y] = ' ';
                        robot.x += flag;
                        map[robot.x][robot.y] = 'R';
                        map[robot.x + flag][robot.y] = '*';
                    }
                    break;
                case '@':
                    if (map[robot.x + 2 * flag][robot.y] == ' ') {
                        map[robot.x][robot.y] = ' ';
                        robot.x += flag;
                        map[robot.x][robot.y] = 'R';
                        map[robot.x + flag][robot.y] = '@';
                    }
                    break;
                case '\\':
                    lamda++;
                    score += 25;
                    openTheLift();
                    map[robot.x][robot.y] = ' ';
                    robot.x += flag;
                    map[robot.x][robot.y] = 'R';
                    break;
                case '0':
                    win = true;
                    score += 50;
                    canPlay = false;
                    break;
            }
            if (teleporter.containsKey(map[robot.x + flag][robot.y])) {
                teleport(map[robot.x + flag][robot.y]);
            }
            fall();
            move++;
            score--;
        }
    }

    void UpOrDown(int flag) {
        if (canPlay) {
            switch (map[robot.x][robot.y + flag]) {
                case ' ':
                    map[robot.x][robot.y] = ' ';
                    robot.y += flag;
                    map[robot.x][robot.y] = 'R';
                    break;
                case '\\':
                    lamda++;
                    score += 25;
                    openTheLift();
                    map[robot.x][robot.y] = ' ';
                    robot.y += flag;
                    map[robot.x][robot.y] = 'R';
                    break;
                case '.':
                    map[robot.x][robot.y] = ' ';
                    robot.y += flag;
                    map[robot.x][robot.y] = 'R';
                    break;
                case '0':
                    win = true;
                    score += 50;
                    canPlay = false;
                    break;
            }
            if (teleporter.containsKey(map[robot.x][robot.y + flag])) {
                teleport(map[robot.x][robot.y + flag]);
            }
            fall();
            move++;
            score--;
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
        canPlay = false;
        win = true;
        score += 25;
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
    }

    public boolean canMove(char a) {
        if (a == 'R') {
            char sth = map[robot.x + 1][robot.y];
            if (sth != '*' && sth != '@' && sth != '#') {
                return true;
            } else if ((sth == '*' || sth == '@') && map[robot.x + 2][robot.y] == ' ') {
                return true;
            } else return false;
        } else if (a == 'L') {
            char sth = map[robot.x - 1][robot.y];
            if (sth != '*' && sth != '@' && sth != '#') {
                return true;
            } else if ((sth == '*' || sth == '@') && map[robot.x - 2][robot.y] == ' ') {
                return true;
            } else return false;
        } else if (a == 'U') {
            char sth = map[robot.x][robot.y - 1];
            if (sth != '*' && sth != '@' && sth != '#') {
                return true;
            } else return false;
        } else {
            char sth = map[robot.x][robot.y + 1];
            if (sth != '*' && sth != '@' && sth != '#') {
                return true;
            } else return false;
        }
    }

    public boolean isSafe(int x, int y) {
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
                ;
                if (lamdaInMap(x1, y1)) {
                    lamda = new Point(x1, y1);
                }
            }
        }
        return lamda;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        Game newGame = (Game) super.clone();
        newGame.robot = (Point) this.robot.clone();
        return newGame;
    }
}
