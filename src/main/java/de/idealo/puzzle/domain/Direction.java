package de.idealo.puzzle.domain;

/**
 * Robot directions and rotating logic.
 *
 * @author <a href=mailto:eupakhomov@gmail.com>Eugene Pakhomov</a>
 */
public enum Direction {
    NORTH (0, 1),
    SOUTH (0, -1),
    EAST (1, 0),
    WEST (-1, 0);

    public final static Direction DEFAULT_DIRECTION = Direction.NORTH;

    private final int dirX;
    private final int dirY;

    Direction(int dirX, int dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public int getDirX() {
        return dirX;
    }

    public int getDirY() {
        return dirY;
    }

    public Direction left() {
        return findByXY(-dirY, dirX);
    }

    public Direction right() {
        return findByXY(dirY, -dirX);
    }

    private Direction findByXY(int dirX, int dirY) {
        for(Direction dir : Direction.values()) {
            if(dir.dirX == dirX && dir.dirY == dirY) {
                return dir;
            }
        }

        throw new IllegalArgumentException("Direction with vector [" + dirX + "," + dirY + "] does not exist");
    }
}
