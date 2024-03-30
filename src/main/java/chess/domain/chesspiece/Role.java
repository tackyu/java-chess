package chess.domain.chesspiece;

import java.util.Arrays;

import static chess.domain.chesspiece.Team.*;

public enum Role {
    BLACK_KING(new King(BLACK)),
    WHITE_KING(new King(WHITE)),
    BLACK_QUEEN(new Queen(BLACK)),
    WHITE_QUEEN(new Queen(WHITE)),
    BLACK_ROOK(new Rook(BLACK)),
    WHITE_ROOK(new Rook(WHITE)),
    BLACK_BISHOP(new Bishop(BLACK)),
    WHITE_BISHOP(new Bishop(WHITE)),
    BLACK_KNIGHT(new Knight(BLACK)),
    WHITE_KNIGHT(new Knight(WHITE)),
    BLACK_PAWN(new Pawn(BLACK)),
    WHITE_PAWN(new Pawn(WHITE)),
    EMPTY(new Empty());

    private final Piece piece;

    Role(Piece piece) {
        this.piece = piece;
    }

    public static Piece findType(String pieceData) {
        Role role = Arrays.stream(values())
                .filter(value -> value.name().equals(pieceData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 체스 말입니다"));
        return role.piece;
    }
}
