package chess.domain.chessboard;

import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static chess.domain.chessboard.State.*;
import static org.assertj.core.api.Assertions.*;

class ChessBoardTest {
    private final BoardInitializer boardInitializer = new BoardInitializer();

    @Test
    @DisplayName("폰은 직진으로 전진할 때 어떠한 말도 있어선 안된다._비어있을 경우")
    void ChessBoard_Check_moveStraight_empty() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();

        assertThatCode(() -> chessBoard.move(Position.of("a2"), Position.of("a4")))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("폰은 직진으로 전진할 때 어떠한 말도 있어선 안된다_적이 있을 경우")
    void ChessBoard_Check_moveStraight_enemy() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        chessBoard.move(Position.of("a2"), Position.of("a4"));
        chessBoard.move(Position.of("a4"), Position.of("a5"));
        chessBoard.move(Position.of("a5"), Position.of("a6"));

        assertThatThrownBy(() -> chessBoard.move(Position.of("a6"), Position.of("a7")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없습니다.");

    }

    @Test
    @DisplayName("폰은 전진하는 대각선 방향에 적이 있을 때만 이동할 수 있다_비어있을 경우")
    void ChessBoard_Check_pawn_moveDiagonal_empty() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        chessBoard.move(Position.of("a2"), Position.of("a4"));
        chessBoard.move(Position.of("a4"), Position.of("a5"));

        assertThatThrownBy(() -> chessBoard.move(Position.of("a5"), Position.of("b6")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공격 대상이 없습니다.");
    }

    @Test
    @DisplayName("폰은 전진하는 대각선 방향에 적이 있을 때만 이동할 수 있다_적이 있을 경우")
    void ChessBoard_Check_pawn_moveDiagonal_enemy() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        chessBoard.move(Position.of("a2"), Position.of("a4"));
        chessBoard.move(Position.of("a4"), Position.of("a5"));
        chessBoard.move(Position.of("a5"), Position.of("a6"));

        assertThatCode(() -> chessBoard.move(Position.of("a6"), Position.of("b7")))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("목적지에 같은 팀이 위치한다면 이동할 수 없다.")
    void ChessBoard_Disallow_move_to_target_if_there_is_team() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();

        assertThatThrownBy(() -> chessBoard.move(Position.of("a1"), Position.of("b1")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없습니다.");
    }

    @Test
    @DisplayName("게임이 시작하면 게임 상태는 GAME_ONGOING이다.")
    void ChessBoard_check_game_state_when_the_game_starts() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        assertThat(chessBoard.getState()).isEqualTo(GAME_ONGOING);
    }

    @Test
    @DisplayName("흑팀 킹이 잡히면 게임 상태는 WHITE_WIN이다.")
    void ChessBoard_check_game_state_when_the_blackKing_is_gone() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        chessBoard.move(Position.of("e7"), Position.of("e5"));
        chessBoard.move(Position.of("e8"), Position.of("e7"));
        chessBoard.move(Position.of("e7"), Position.of("e6"));
        chessBoard.move(Position.of("e6"), Position.of("d5"));
        chessBoard.move(Position.of("c2"), Position.of("c4"));
        chessBoard.move(Position.of("c4"), Position.of("d5"));

        assertThat(chessBoard.getState()).isEqualTo(WHITE_WIN);
    }

    @Test
    @DisplayName("백팀 킹이 잡히면 게임 상태는 BLACK_WIN이다.")
    void ChessBoard_check_game_state_when_the_whiteKing_is_gone() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        chessBoard.move(Position.of("e2"), Position.of("e4"));
        chessBoard.move(Position.of("e1"), Position.of("e2"));
        chessBoard.move(Position.of("e2"), Position.of("e3"));
        chessBoard.move(Position.of("e3"), Position.of("d4"));
        chessBoard.move(Position.of("c7"), Position.of("c5"));
        chessBoard.move(Position.of("c5"), Position.of("d4"));

        assertThat(chessBoard.getState()).isEqualTo(BLACK_WIN);
    }
}
