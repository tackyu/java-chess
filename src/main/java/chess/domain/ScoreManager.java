package chess.domain;

import chess.domain.chessboard.ChessBoard;
import chess.domain.chesspiece.Piece;
import chess.domain.chesspiece.Team;
import chess.domain.position.Position;

import java.util.List;

import static chess.domain.chesspiece.Role.*;
import static chess.domain.chesspiece.Team.*;

public class ScoreManager {
    private final ChessBoard chessBoard;

    public ScoreManager(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public double calculate(Team team) {
        double score = 0;
        Position startPosition = Position.of("a1");
        for (int i = 0; i < 8; i++) {
            List<Piece> piecesByColumn = chessBoard.getPiecesByColumn(startPosition.move(i, 0));
            List<Piece> piecesByTeam = piecesByColumn.stream()
                    .filter(piece -> piece.isTeam(team))
                    .toList();
            score += calculateExceptPawn(piecesByTeam);
            score += calculatePawn(piecesByTeam);
        }
        return score;
    }

    private double calculateExceptPawn(List<Piece> piecesByTeam) {
        return piecesByTeam.stream()
                .filter(piece -> piece.getRole() != BLACK_PAWN && piece.getRole() != WHITE_PAWN)
                .toList()
                .stream()
                .mapToDouble(Piece::getScore)
                .sum();
    }

    private double calculatePawn(List<Piece> piecesByTeam) {
        int count = countPawn(piecesByTeam);
        if (count == 1) {
            return 1;
        }
        return 0.5 * count;
    }

    private int countPawn(List<Piece> piecesByTeam) {
        return (int) piecesByTeam.stream()
                .filter(piece -> piece.getRole() == BLACK_PAWN || piece.getRole() == WHITE_PAWN)
                .count();
    }

    public Team findWinner() {
        double black = calculate(BLACK);
        double white = calculate(WHITE);
        if (black > white) {
            return BLACK;
        }
        if (white > black) {
            return WHITE;
        }
        return NOTHING;
    }
}
