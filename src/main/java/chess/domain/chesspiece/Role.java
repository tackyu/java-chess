package chess.domain.chesspiece;

import java.util.Arrays;
import java.util.function.Function;

import static chess.domain.chesspiece.Team.*;

public enum Role {
    BLACK_KING(King::new),
    WHITE_KING(King::new),
    BLACK_QUEEN(Queen::new),
    WHITE_QUEEN(Queen::new),
    BLACK_ROOK(Rook::new),
    WHITE_ROOK(Rook::new),
    BLACK_BISHOP(Bishop::new),
    WHITE_BISHOP(Bishop::new),
    BLACK_KNIGHT(Knight::new),
    WHITE_KNIGHT(Knight::new),
    BLACK_PAWN(Pawn::new),
    WHITE_PAWN(Pawn::new),
    EMPTY(nothing -> new Empty());

    private final Function<Team, Piece> pieceMaker;

    Role(Function<Team, Piece> pieceMaker) {
        this.pieceMaker = pieceMaker;
    }

    public static Piece findType(String pieceData) {
        Role role = Arrays.stream(values())
                .filter(value -> value.name().equals(pieceData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 체스 말입니다"));
        return getPiece(role);
    }

    private static Piece getPiece(Role role) {
        if (role == EMPTY) {
            return role.pieceMaker.apply(NOTHING);
        }
        String[] piece = role.name().split("_");
        Team team = Team.findTeam(piece[0]);
        return role.pieceMaker.apply(team);
    }
}
