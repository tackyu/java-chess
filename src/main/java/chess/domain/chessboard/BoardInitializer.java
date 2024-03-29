package chess.domain.chessboard;

import chess.domain.chesspiece.*;
import chess.domain.position.Position;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static chess.domain.chesspiece.Team.BLACK;
import static chess.domain.chesspiece.Team.WHITE;

public class BoardInitializer {
    public ChessBoard initializeChessBoard() {
        Map<Position, Piece> board = new LinkedHashMap<>();
        initializeRow(board, makeChessPiece(BLACK), Position.of("a8"));
        initializeRow(board, makePawn(BLACK), Position.of("a7"));
        for (int i = 0; i < 4; i++) {
            initializeRow(board, makeEmpty(), Position.of("a6").move(0, -i));
        }
        initializeRow(board, makePawn(WHITE), Position.of("a2"));
        initializeRow(board, makeChessPiece(WHITE), Position.of("a1"));
        return new ChessBoard(board);
    }

    private void initializeRow(Map<Position, Piece> board, List<Piece> pieces, Position startPosition) {
        for (int i = 0; i <= 7; i++) {
            board.put(startPosition.move(i, 0), pieces.get(i));
        }
    }

    private List<Piece> makeChessPiece(Team team) {
        return List.of(new Rook(team), new Knight(team),
                new Bishop(team), new Queen(team), new King(team),
                new Bishop(team), new Knight(team), new Rook(team));
    }

    private List<Piece> makePawn(Team team) {
        return List.of(new Pawn(team), new Pawn(team),
                new Pawn(team), new Pawn(team), new Pawn(team),
                new Pawn(team), new Pawn(team), new Pawn(team));
    }

    private List<Piece> makeEmpty() {
        return List.of(new Empty(), new Empty(),
                new Empty(), new Empty(), new Empty(),
                new Empty(), new Empty(), new Empty());
    }

}
