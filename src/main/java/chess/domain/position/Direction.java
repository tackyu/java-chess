package chess.domain.position;

import java.util.Arrays;

public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1);

    private final int rowDirection;

    private final int columnDirection;

    Direction(int rowDirection, int columnDirection) {
        this.rowDirection = rowDirection;
        this.columnDirection = columnDirection;
    }

    public static Direction findDirection(Position source, Position target) {
        int rowDirection = source.compareRow(target);
        int columnDirection = source.compareColumn(target);

        return Arrays.stream(values())
                .filter(value -> value.rowDirection == rowDirection && value.columnDirection == columnDirection)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 방향입니다."));
    }

    public static boolean isUpDown(Position source, Position target) {
        Direction direction = findDirection(source, target);
        return direction == UP || direction == DOWN;
    }

    public static boolean isDiagonal(Position source, Position target) {
        Direction direction = findDirection(source, target);
        return direction == UP_RIGHT || direction == UP_LEFT || direction == DOWN_LEFT || direction == DOWN_RIGHT;
    }

    public Position move(Position source) {
        return source.move(rowDirection, columnDirection);
    }
}
