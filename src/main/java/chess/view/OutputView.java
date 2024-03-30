package chess.view;

import chess.domain.chessboard.State;
import chess.domain.chesspiece.Role;
import chess.domain.chesspiece.Piece;
import chess.domain.chesspiece.Team;
import chess.domain.position.Position;

import java.util.EnumMap;
import java.util.Map;

import static chess.domain.chessboard.State.*;
import static chess.domain.chesspiece.Team.*;

public class OutputView {
    private static final EnumMap<Role, String> pieceBoard = initializePiece();

    public static void printChessBoard(Map<Position, Piece> chessBoard, Position position) {
        for (int i = 0; i < 8; i++) {
            printLine(chessBoard, position.move(0, -i));
            System.out.println();
        }
    }

    private static void printLine(Map<Position, Piece> chessBoard, Position position) {
        for (int i = 0; i < 7; i++) {
            Piece piece = chessBoard.get(position.move(i, 0));
            System.out.print(pieceBoard.get(piece.getRole()));
        }
    }

    public static void printStartMessage() {
        System.out.println("체스 게임을 시작합니다.");
        System.out.println("게임 시작은 start, 종료는 end 명령을 입력하세요.");
    }

    private static EnumMap<Role, String> initializePiece() {
        EnumMap<Role, String> pieceBoard = new EnumMap<>(Role.class);
        pieceBoard.put(Role.BLACK_KING, "K");
        pieceBoard.put(Role.WHITE_KING, "k");
        pieceBoard.put(Role.BLACK_QUEEN, "Q");
        pieceBoard.put(Role.WHITE_QUEEN, "q");
        pieceBoard.put(Role.BLACK_ROOK, "R");
        pieceBoard.put(Role.WHITE_ROOK, "r");
        pieceBoard.put(Role.BLACK_BISHOP, "B");
        pieceBoard.put(Role.WHITE_BISHOP, "b");
        pieceBoard.put(Role.BLACK_KNIGHT, "N");
        pieceBoard.put(Role.WHITE_KNIGHT, "n");
        pieceBoard.put(Role.BLACK_PAWN, "P");
        pieceBoard.put(Role.WHITE_PAWN, "p");
        pieceBoard.put(Role.EMPTY, ".");
        return pieceBoard;
    }

    public static void printScore(Team team, double score) {
        System.out.println(team + " 점수: " + score);
    }

    public static void printSuperiority(Team team) {
        if (team == NOTHING) {
            System.out.println("동점\n");
            return;
        }
        System.out.println(team + " 우세\n");
    }

    public static void printWinner(State state) {
        if (state == BLACK_WIN) {
            System.out.println("BLACK 승리");
            return;
        }
        System.out.println("WHITE 승리");
    }
}
