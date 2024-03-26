package chess.domain.chesspiece;

public enum Team {
    WHITE,
    BLACK,
    NOTHING;

    public boolean isWhite() {
        return this == WHITE;
    }

    public Team reverse() {
        return switch (this) {
            case WHITE -> BLACK;
            case BLACK -> WHITE;
            case NOTHING -> NOTHING;
        };
    }
}
