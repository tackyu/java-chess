package chess.domain.chesspiece;

import chess.domain.position.Position;

import static chess.domain.chesspiece.Role.*;

public class Queen extends Piece {
    private static final double SCORE = 9;

    public Queen(Team team) {
        super(team);
        score = SCORE;
    }

    @Override
    protected void validateMovingRule(Position source, Position target) {
        int rowDistance = source.calculateRowDistance(target);
        int columnDistance = source.calculateColumnDistance(target);
        if (source.isDifferentRow(target) && source.isDifferentColumn(target) && rowDistance != columnDistance) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
    }

    @Override
    public Role getRole() {
        if (team.isWhite()) {
            return WHITE_QUEEN;
        }
        return BLACK_QUEEN;
    }
}
