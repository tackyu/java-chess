package chess.domain.chesspiece;

import chess.domain.position.Direction;
import chess.domain.position.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static chess.domain.chesspiece.Role.*;

public class Pawn extends Piece {

    public Pawn(Team team) {
        super(team);
    }

    @Override
    public List<Position> getRoute(Position source, Position target) {
        List<Position> route = new ArrayList<>(super.getRoute(source, target));
        Direction direction = Direction.findDirection(source, target);
        if (direction.isUpDown()) { //이동
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
        int columnDistance = source.calculatePawnColumnDistance(target, team);
        return source.isPawnStartPosition(team) && source.isSameRow(target) && columnDistance == 2;
    }

    private boolean canMoveForward(Position source, Position target) {
        int columnDistance = source.calculatePawnColumnDistance(target, team);
        return source.isSameRow(target) && columnDistance == 1;
    }

    private boolean canAttack(Position source, Position target) {
        int rowDistance = source.calculateRowDistance(target);
        int colDistance = source.calculatePawnColumnDistance(target, team);
        return rowDistance == 1 && colDistance == 1;
    }

    @Override
    public Role getRole() {
        if (team.isWhite()) {
            return WHITE_PAWN;
        }
        return BLACK_PAWN;
    }
}
