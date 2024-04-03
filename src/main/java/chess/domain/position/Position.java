package chess.domain.position;

import java.util.Objects;

public class Position {
    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(String position) {
        Row row = Row.from(position.substring(0, 1));
        Column column = Column.from(position.substring(1));

        return new Position(row, column);
    }

    public static Position of(int rowIndex, int columnIndex) {
        Row row = Row.from(rowIndex);
        Column column = Column.from(columnIndex);

        return new Position(row, column);
    }

    public Column getColumn() {
        return column;
    }

    public Position move(int rowDirection, int columnDirection) {
        return new Position(row.update(rowDirection), column.update(columnDirection));
    }

    public int calculateRowDistance(Position target) {
        return Math.abs(row.getIndex() - target.row.getIndex());
    }

    public int calculateColumnDistance(Position target) {
        return Math.abs(column.getIndex() - target.getColumn().getIndex());
    }

    public int subtractColumn(Position target) {
        return column.subtractColumn(target.getColumn());
    }

    public boolean isSameRow(Position target) {
        return this.row == target.row;
    }

    public boolean isSameColumn(Position target) {
        return this.column == target.column;
    }

    public boolean isDifferentRow(Position target) {
        return !isSameRow(target);
    }

    public boolean isDifferentColumn(Position target) {
        return !isSameColumn(target);
    }

    public int compareRow(Position target) {
        return row.compare(target.row);
    }

    public int compareColumn(Position target) {
        return column.compare(target.column);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Position position = (Position) object;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public String getName() {
        return row.name().toLowerCase() + (column.getIndex() + 1);
    }
}
