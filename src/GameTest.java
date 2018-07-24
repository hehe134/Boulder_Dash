public class GameTest {
    public static void main(String[] args) {

        Game myGame = new Game();
        myGame.getMap();
        myGame.turnLeft();
        myGame.turnLeft();
        myGame.turnDown();
        myGame.turnLeft();
        System.out.println("Robot: (" + myGame.robot.getX() + " , " + myGame.robot.getY() + ")");

        if (myGame.win) System.out.println("You win");
        System.out.println("Score: "+myGame.score);
        for (int j = 0; j < myGame.n; j++) {
            for (int i = 0; i < myGame.m; i++) {
                System.out.print(myGame.map[i][j]);
            }
            System.out.println();
        }
    }
}
