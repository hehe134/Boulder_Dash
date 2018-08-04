package robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Game {
    public char[][] map = new char[50][50];
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
    //    R — робот
//    * — камень
//    L — закрытый выход
//    . — земля
//    # — стена
//    \ — λ
//    O — открытый выход
//    " " — пробел, пустая клетка

    public class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }

    public void fall() {
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                if (map[x][y] == '*' || map[x][y] == '@') {
                    switch (map[x][y + 1]) {
                        //Под камнем пустота — камень падает на 1 клетку вниз.
                        case ' ':
                            map[x][y] = ' ';
                            if (map[x][y] == '*') {
                                map[x][y + 1] = '*';
                            } else {
                                if (map[x][y + 2] != ' ') {
                                    map[x][y + 1] = '\\';
                                } else{
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
                                map[x][y] = ' ';
                                if (map[x][y] == '*') {
                                    map[x + 1][y + 1] = '*';
                                } else {
                                    if (map[x + 1][y + 2] != ' ') {
                                        map[x + 1][y + 1] = '\\';
                                    } else{
                                        map[x + 1][y + 1] = '@';
                                    }
                                }
                                if (map[x + 1][y + 2] == 'R') {
                                    canPlay = false;
                                } //dead
                            }
                            // //Под камнем — камень, слева пусто и слева внизу пусто — камень падает по диагонали влево.
                            else if (map[x - 1][y + 1] == ' ' && map[x - 1][y] == ' ') {
                                map[x][y] = ' ';
                                if (map[x][y] == '*') {
                                    map[x - 1][y + 1] = '*';
                                } else {
                                    if (map[x - 1][y + 2] != ' ') {
                                        map[x - 1][y + 1] = '\\';
                                    }else map[x - 1][y + 1] = '@';
                                }
                                if (map[x - 1][y + 2] == 'R') {
                                    canPlay = false;
                                } //dead
                            }
                            break;
                        //Под камнем — λ, справа пусто и справа внизу пусто — камень падает по диагонали вправо.
                        case '\\':
                            if (map[x + 1][y] == ' ' && map[x + 1][y + 1] == ' ') {
                                map[x][y] = ' ';
                                if (map[x][y] == '*') {
                                    map[x + 1][y + 1] = '*';
                                } else {
                                    if (map[x+1][y+2]!=' '){
                                        map[x+1][y+1]='\\';
                                    }else map[x + 1][y + 1] = '@';
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


    void RightOrLeft(boolean flag) {
        int n;
        if (flag) n = 1;
        else n = -1;
        switch (map[robot.x + n][robot.y]) {
            case ' ':
                map[robot.x][robot.y] = ' ';
                robot.x += n;
                map[robot.x][robot.y] = 'R';
                break;
            case '.':
                map[robot.x][robot.y] = ' ';
                robot.x += n;
                map[robot.x][robot.y] = 'R';
                break;
            case '*':
                if (map[robot.x + 2 * n][robot.y] == ' ') {
                    map[robot.x][robot.y] = ' ';
                    robot.x += n;
                    map[robot.x][robot.y] = 'R';
                    map[robot.x + n][robot.y] = '*';
                }
                break;
            case '@':
                if (map[robot.x + 2 * n][robot.y] == ' ') {
                    map[robot.x][robot.y] = ' ';
                    robot.x += n;
                    map[robot.x][robot.y] = 'R';
                    map[robot.x + n][robot.y] = '@';
                }
                break;
            case '\\':
                lamda++;
                score += 25;
                openTheLift();
                map[robot.x][robot.y] = ' ';
                robot.x += n;
                map[robot.x][robot.y] = 'R';
                break;
            case '0':
                win = true;
                score += 50;
                canPlay = false;
                break;
        }
    }

    void UpOrDown(boolean flag) {
        int n;
        if (flag) n = 1;
        else n = -1;
        switch (map[robot.x][robot.y + n]) {
            case ' ':
                map[robot.x][robot.y] = ' ';
                robot.y += n;
                map[robot.x][robot.y] = 'R';
                break;
            case '\\':
                lamda++;
                score += 25;
                openTheLift();
                map[robot.x][robot.y] = ' ';
                robot.y += n;
                map[robot.x][robot.y] = 'R';
                break;
            case '.':
                map[robot.x][robot.y] = ' ';
                robot.y += n;
                map[robot.x][robot.y] = 'R';
                break;
            case '0':
                win = true;
                score += 50;
                canPlay = false;
                break;
        }
    }

    void openTheLift() {
        if (allLamda == lamda) {
            map[lift.x][lift.y] = '0';
        }
    }

    public void turnRight() {
        if (canPlay) {
            RightOrLeft(true);
            fall();
            move++;
            score--;
        }
    }

    public void turnLeft() {
        if (canPlay) {
            RightOrLeft(false);
            fall();
            move++;
            score--;
        }
    }

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

    public void commandA() {
        canPlay = false;
        win = true;
        score += 25;
    }

    public void getMap() {
        try {
            String pathname = "map/input";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(
                    new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            int j = 0;
            int maxlength = 0;
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
                }
                if (line.length() > maxlength) maxlength = line.length();
                line = br.readLine();
                j++;
            }
            m = maxlength;
            n = j;
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean canMove(double a) {
        if (a < 0.25) {
            char sth = map[robot.x + 1][robot.y];
            if (sth == ' ' || sth == '.' || sth == '0' || sth == '\\') {
                return true;
            } else if (sth == '*' && map[robot.x + 2][robot.y] == ' ') {
                return true;
            } else return false;
        } else if (a >= 0.25 && a < 0.5) {
            char sth = map[robot.x - 1][robot.y];
            if (sth == ' ' || sth == '.' || sth == '0' || sth == '\\') {
                return true;
            } else if (sth == '*' && map[robot.x - 2][robot.y] == ' ') {
                return true;
            } else return false;
        } else if (a >= 0.5 && a < 0.75) {
            char sth = map[robot.x][robot.y - 1];
            if (sth == ' ' || sth == '.' || sth == '0' || sth == '\\') {
                return true;
            } else return false;
        } else {
            char sth = map[robot.x][robot.y + 1];
            if (sth == ' ' || sth == '.' || sth == '0' || sth == '\\') {
                return true;
            } else return false;
        }
    }
}
