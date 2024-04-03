package chess.domain.chesspiece;

import chess.domain.position.Position;

import java.util.List;

import static chess.domain.chesspiece.Team.NOTHING;
import static chess.domain.chesspiece.Role.EMPTY;

public class Empty extends Piece {
    private static final double SCORE = 0;

    public Empty() {
        super(NOTHING);
    }

    @Override
    public List<Position> getRoute(Position source, Position target) {
        throw new IllegalArgumentException("이동할 체스 말이 없습니다.");
    }

    @Override
    protected void validateMovingRule(Position source, Position target) {
    }

    @Override
    public Role getRole() {
        return EMPTY;
    }

    @Override
    public double getScore() {
        return SCORE;
    }
}
