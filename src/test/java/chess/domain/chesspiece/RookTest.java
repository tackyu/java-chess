package chess.domain.chesspiece;

import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static chess.domain.chesspiece.Team.BLACK;
import static chess.domain.chesspiece.Team.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RookTest {
    @Test
    @DisplayName("목적지 제외 세로로 갈 수 있는 위치들을 반환한다.")
    void Rook_Check_vertical_route() {
        Piece piece = new Rook(WHITE);
        List<Position> route = piece.getRoute(Position.of("a1"), Position.of("e1"));
        List<Position> positions = List.of(Position.of("b1"), Position.of("c1"),
                Position.of("d1"));
        assertThat(route).isEqualTo(positions);
    }
    @Test
    @DisplayName("목적지 제외 가로로 갈 수 있는 위치들을 반환한다.")
    void Rook_Check_horizontal_route() {
        Piece piece = new Rook(WHITE);
        List<Position> route = piece.getRoute(Position.of("a1"), Position.of("a5"));
        List<Position> positions = List.of(Position.of("a2"), Position.of("a3"),
                Position.of("a4"));
        assertThat(route).isEqualTo(positions);
    }

    @Test
    @DisplayName("목적지 제외 갈 수 있는 위치들이 아니면 예외를 발생한다.")
    void Rook_Validate_route() {
        Piece piece = new Rook(WHITE);
        assertThatThrownBy(() -> {
            piece.getRoute(Position.of("a1"), Position.of("c4"));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("같은 팀인지 확인한다.")
    void Rook_Validate_team() {
        Piece piece=new Rook(WHITE);
        assertThat(piece.isTeam(new King(WHITE))).isTrue();
        assertThat(piece.isTeam(new King(BLACK))).isFalse();
    }

    @Test
    @DisplayName("자신이 폰인지 확인한다.")
    void Rook_Check_pawn() {
        Piece piece = new Rook(WHITE);
        assertThat(piece.isPawn()).isFalse();
    }
}
