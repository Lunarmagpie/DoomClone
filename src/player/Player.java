package player;

public class Player {
    int x;
    int y;

    public Player() {
        x = 0;
        y = 0;
    }

    public void moveRight()  { x += 10; }
    public void moveLeft()   { x -= 10; }
    public void moveUp()     { y += 10; }
    public void moveDown()   { y -= 10; }
}
