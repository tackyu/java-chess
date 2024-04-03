package chess.domain.chesspiece;

import chess.domain.position.Direction;
import chess.domain.position.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Piece {
    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public List<Position> getRoute(Position source, Position target) {
        validateMovingRule(source, target);
        List<Position> route = new ArrayList<>();
        Direction direction = Direction.findDirection(source, target);
        Position movingPosition = direction.move(source);

        while (!movingPosition.equals(target)) {
            route.add(movingPosition);
            movingPosition = direction.move(movingPosition);
        }
        return Collections.unmodifiableList(route);
    }

    protected abstract void validateMovingRule(Position source, Position target);

    public boolean isTeam(Piece piece) {
        return team == piece.team;
    }

    public boolean isTeam(Team team) {
        return this.team == team;
    }

    public boolean willAttack(Direction direction, Piece opponent) {
        return team.reverse() == opponent.team;
    }

    public abstract Role getRole();

    public abstract double getScore();
}
