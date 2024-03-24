package chess.domain.chesspiece;

import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static chess.domain.chesspiece.Team.BLACK;
import static chess.domain.chesspiece.Team.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class QueenTest {
    @Test
    @DisplayName("여왕은 앞뒤로 움직일 수 있다.")
    void Queen_Move_forward_and_backward() {
        Piece piece = new Queen(WHITE);
        List<Position> route = piece.getRoute(Position.of("a1"), Position.of("a4"));
        List<Position> positions = List.of(Position.of("a2"), Position.of("a3"));
        assertThat(route).isEqualTo(positions);
    }

    @Test
    @DisplayName("여왕은 좌우로 움직일 수 있다.")
    void Queen_Move_side() {
        Piece piece = new Queen(WHITE);
        List<Position> route = piece.getRoute(Position.of("b4"), Position.of("e4"));
        List<Position> positions = List.of(Position.of("c4"), Position.of("d4"));
        assertThat(route).isEqualTo(positions);
    }

    @Test
    @DisplayName("여왕은 대각선으로 움직일 수 있다.")
    void Queen_Move_diagonal() {
        Piece piece = new Queen(WHITE);
        List<Position> route = piece.getRoute(Position.of("b2"), Position.of("e5"));
        List<Position> positions = List.of(Position.of("c3"), Position.of("d4"));
        assertThat(route).isEqualTo(positions);
    }

    @Test
    @DisplayName("목적지 제외 갈 수 있는 위치들이 아니면 예외를 발생한다.")
    void Bishop_Validate_route() {
        Piece piece = new Queen(WHITE);
        assertThatThrownBy(() -> piece.getRoute(Position.of("a1"), Position.of("b4")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없습니다.");
    }

    @Test
    @DisplayName("같은 팀인지 확인한다.")
    void Queen_Validate_team() {
        Piece piece = new Queen(WHITE);
        assertThat(piece.isTeam(new King(WHITE))).isTrue();
        assertThat(piece.isTeam(new King(BLACK))).isFalse();
    }

}
