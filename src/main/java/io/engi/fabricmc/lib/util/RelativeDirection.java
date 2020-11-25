package io.engi.fabricmc.lib.util;

import net.minecraft.util.math.Direction;

public enum RelativeDirection {
    FRONT,
    BACK,
    LEFT,
    RIGHT,
    UP,
    DOWN;

    public Direction toAbsolute(Direction normal) {
        switch (this) {
            case FRONT:
                return normal;
            case BACK:
                return normal.getOpposite();
            case LEFT:
                return normal.rotateYCounterclockwise();
            case RIGHT:
                return normal.rotateYClockwise();
            case UP:
                return Direction.UP;
            case DOWN:
                return Direction.DOWN;
        }
        return normal;
    }

    public static RelativeDirection fromAbsolute(Direction dir, Direction normal) {
        if (dir.equals(normal.getOpposite())) return BACK;
        if (dir.equals(normal.rotateYClockwise())) return RIGHT;
        if (dir.equals(normal.rotateYCounterclockwise())) return LEFT;
        if (dir.equals(Direction.UP)) return UP;
        if (dir.equals(Direction.DOWN)) return DOWN;
        return FRONT;
    }
}
