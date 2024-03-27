package chess.domain.chessboard;

import chess.domain.position.Direction;
import chess.domain.chesspiece.*;
import chess.domain.position.Column;
import chess.domain.position.Position;
import chess.domain.position.Row;

import java.util.*;

import static chess.domain.chessboard.State.*;
import static chess.domain.chesspiece.Team.BLACK;
import static chess.domain.chesspiece.Team.WHITE;
import static chess.domain.chesspiece.Role.*;

public class ChessBoard {
    private final Map<Column, Line> chessBoard;
    private State state;

    private ChessBoard(Map<Column, Line> chessBoard) {
        this.chessBoard = chessBoard;
        this.state = GAME_ONGOING;
    }

    //TODO: 한칸 생각해보기
    public static ChessBoard initializeChessBoard() {
        Map<Column, Line> board = new LinkedHashMap<>();
        board.put(Column.from("8"), new Line(List.of(new Rook(BLACK), new Knight(BLACK),
                new Bishop(BLACK), new Queen(BLACK), new King(BLACK),
                new Bishop(BLACK), new Knight(BLACK), new Rook(BLACK))));
        board.put(Column.from("7"), new Line(List.of(new Pawn(BLACK), new Pawn(BLACK),
                new Pawn(BLACK), new Pawn(BLACK), new Pawn(BLACK),
                new Pawn(BLACK), new Pawn(BLACK), new Pawn(BLACK))));
        board.put(Column.from("6"), new Line(List.of(new Empty(), new Empty(),
                new Empty(), new Empty(), new Empty(),
                new Empty(), new Empty(), new Empty())));
        board.put(Column.from("5"), new Line(List.of(new Empty(), new Empty(),
                new Empty(), new Empty(), new Empty(),
                new Empty(), new Empty(), new Empty())));
        board.put(Column.from("4"), new Line(List.of(new Empty(), new Empty(),
                new Empty(), new Empty(), new Empty(),
                new Empty(), new Empty(), new Empty())));
        board.put(Column.from("3"), new Line(List.of(new Empty(), new Empty(),
                new Empty(), new Empty(), new Empty(),
                new Empty(), new Empty(), new Empty())));
        board.put(Column.from("2"), new Line(List.of(new Pawn(WHITE), new Pawn(WHITE),
                new Pawn(WHITE), new Pawn(WHITE), new Pawn(WHITE),
                new Pawn(WHITE), new Pawn(WHITE), new Pawn(WHITE))));
        board.put(Column.from("1"), new Line(List.of(new Rook(WHITE), new Knight(WHITE),
                new Bishop(WHITE), new Queen(WHITE), new King(WHITE),
                new Bishop(WHITE), new Knight(WHITE), new Rook(WHITE))));

        return new ChessBoard(board);
    }

    public Map<Column, Line> getChessBoard() {
        return Collections.unmodifiableMap(chessBoard);
    }

    public void move(Position source, Position target) {
        Piece piece = findChessPiece(source);
        piece.getRoute(source, target)
                .forEach(this::checkObstacle); //공통~ 경로에 장애물 확인

        if (piece.willAttack(Direction.findDirection(source, target), findChessPiece(target))) {//공격
            attack(source, target, piece);
            return;
        }
        checkTeam(target, piece);//이동~ 같은 팀이면 예외
        updateChessBoard(source, target, piece);
    }

    private void attack(Position source, Position target, Piece piece) {
        Piece enemy = findChessPiece(target);
        if (enemy.getRole() == BLACK_KING || enemy.getRole() == WHITE_KING) {
            state = GAME_END;
        }
        updateChessBoard(source, target, piece);
    }

    private void updateChessBoard(Position source, Position target, Piece piece) {
        chessBoard.put(source.getColumn(), updateLine(source, new Empty()));
        chessBoard.put(target.getColumn(), updateLine(target, piece));
    }

    private void checkObstacle(Position position) {
        Piece obstacle = findChessPiece(position);
        if (obstacle.getRole() != EMPTY) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
    }

    private void checkTeam(Position target, Piece piece) {
        Piece targetPiece = findChessPiece(target);
        if (piece.isTeam(targetPiece)) {
            throw new IllegalArgumentException("이동할 수 없습니다.");
        }
    }

    private Line updateLine(Position source, Piece piece) {
        return chessBoard.get(source.getColumn()).update(source.getRow(), piece);
    }

    private Piece findChessPiece(Position source) {
        Column column = source.getColumn();
        Row row = source.getRow();
        Line chessPieces = chessBoard.get(column);
        return chessPieces.getChessPiece(row);
    }

    public State getState() {
        return state;
    }
}
