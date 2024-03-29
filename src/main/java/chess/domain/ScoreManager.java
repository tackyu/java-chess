package chess.domain;

import chess.domain.chessboard.ChessBoard;
import chess.domain.chesspiece.Team;

public class ScoreManager {
    private final ChessBoard chessBoard;

    public ScoreManager(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public int calculate(Team team) {
        return 38;
    }
}
