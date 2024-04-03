package chess.domain.chesspiece;

import chess.domain.position.Direction;
import chess.domain.position.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static chess.domain.chesspiece.Role.*;
import static chess.domain.chesspiece.Team.*;

public class Pawn extends Piece {
    private static final double SCORE = 1;

    public Pawn(Team team) {
        super(team);
    }

    @Override
    public List<Position> getRoute(Position source, Position target) {
        List<Position> route = new ArrayList<>(super.getRoute(source, target));
        Direction direction = Direction.findDirection(source, target);
        if (direction.isUpDown()) {
            route.add(target);
        }
        return Collections.unmodifiableList(route);
    }

    @Override
    public boolean willAttack(Direction direction, Piece opponent) {
        if (direction.isDiagonal() && !super.willAttack(direction, opponent)) {
            throw new IllegalArgumentException("공격 대상이 없습니다.");
        }
        return super.willAttack(direction, opponent) && direction.isDiagonal();
    }

    @Override
    protected void validateMovingRule(Position source, Position target) {
        if (!isMovable(source, target)) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
    }

    private boolean isMovable(Position source, Position target) {
        return canMoveForwardTwice(source, target)
                || canMoveForward(source, target)
                || canAttack(source, target);
    }

    private boolean canMoveForwardTwice(Position source, Position target) {
        if (team == WHITE) {
            return isStartPosition(source, team) && source.isSameRow(target)
                    && target.subtractColumn(source) == 2;
        }
        return isStartPosition(source, team) && source.isSameRow(target)
                && source.subtractColumn(target) == 2;
    }

    private boolean isStartPosition(Position source, Team team) {
        if (team == WHITE) {
            return source.getColumn().getIndex() == 1;
        }
        return source.getColumn().getIndex() == 6;
    }

    private boolean canMoveForward(Position source, Position target) {
        if (team == WHITE) {
            return source.isSameRow(target) && target.subtractColumn(source) == 1;
        }
        return source.isSameRow(target) && source.subtractColumn(target) == 1;
    }

    private boolean canAttack(Position source, Position target) {
        int rowDistance = source.calculateRowDistance(target);
        if (team == WHITE) {
            return rowDistance == 1 && target.subtractColumn(source) == 1;
        }
        return rowDistance == 1 && source.subtractColumn(target) == 1;
    }

    @Override
    public Role getRole() {
        if (team.isWhite()) {
            return WHITE_PAWN;
        }
        return BLACK_PAWN;
    }

    @Override
    public double getScore() {
        return SCORE;
    }
}
