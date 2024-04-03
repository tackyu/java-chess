package chess.domain.chessboard;

import chess.domain.position.Direction;
import chess.domain.chesspiece.*;
import chess.domain.position.Position;

import java.util.*;

import static chess.domain.chessboard.State.*;
import static chess.domain.chesspiece.Role.*;

public class ChessBoard {
    private final Map<Position, Piece> chessBoard;
    private State state;

    ChessBoard(Map<Position, Piece> chessBoard) {
        this.chessBoard = chessBoard;
        this.state = GAME_ONGOING;
    }

    public void move(Position source, Position target) {
        Piece piece = findChessPiece(source);
        piece.getRoute(source, target)
                .forEach(this::validateObstacle);

        if (piece.willAttack(Direction.findDirection(source, target), findChessPiece(target))) {
            attack(source, target, piece);
            return;
        }
        validateSameTeam(target, piece);//이동~ 같은 팀이면 예외
        updateChessBoard(source, target, piece);
    }

    private void attack(Position source, Position target, Piece piece) {
        Piece enemy = findChessPiece(target);
        if (enemy.getRole() == BLACK_KING) {
            state = WHITE_WIN;
        }
        if (enemy.getRole() == WHITE_KING) {
            state = BLACK_WIN;
        }
        updateChessBoard(source, target, piece);
    }

    private void updateChessBoard(Position source, Position target, Piece piece) {
        chessBoard.put(source, new Empty());
        chessBoard.put(target, piece);
    }

    private void validateObstacle(Position position) {
        Piece obstacle = findChessPiece(position);
        if (obstacle.getRole() != EMPTY) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
    }

    private void validateSameTeam(Position target, Piece piece) {
        Piece targetPiece = findChessPiece(target);
        if (piece.isTeam(targetPiece)) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
    }

    public Piece findChessPiece(Position source) {
        return chessBoard.get(source);
    }

    public State getState() {
        return state;
    }

    public Map<Position, Piece> getChessBoard() {
        return Collections.unmodifiableMap(chessBoard);
    }

    public List<Piece> getPiecesByColumn(Position startPosition) {
        List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            pieces.add(findChessPiece(startPosition.move(0, i)));
        }
        return Collections.unmodifiableList(pieces);
    }
}
