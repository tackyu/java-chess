package chess.domain.chesspiece;

import java.util.Arrays;

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

    public static Team findTeam(String teamData) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(teamData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 팀입니다"));
    }
}
