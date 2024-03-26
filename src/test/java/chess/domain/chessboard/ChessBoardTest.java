package chess.domain.chessboard;

import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChessBoardTest {

    @Test
    @DisplayName("폰은 직진으로 전진할 때 어떠한 말도 있어선 안된다._비어있을 경우")
    void ChessBoard_Check_moveStraight_empty() {
        ChessBoard chessBoard = ChessBoard.initializeChessBoard();

        assertThatCode(() -> chessBoard.move(Position.of("a2"), Position.of("a4")))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("폰은 직진으로 전진할 때 어떠한 말도 있어선 안된다_적이 있을 경우")
    void ChessBoard_Check_moveStraight_enemy() {
        ChessBoard chessBoard = ChessBoard.initializeChessBoard();
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
        ChessBoard chessBoard = ChessBoard.initializeChessBoard();
        chessBoard.move(Position.of("a2"), Position.of("a4"));
        chessBoard.move(Position.of("a4"), Position.of("a5"));

        assertThatThrownBy(() -> chessBoard.move(Position.of("a5"), Position.of("b6")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공격 대상이 없습니다.");
    }

    @Test
    @DisplayName("폰은 전진하는 대각선 방향에 적이 있을 때만 이동할 수 있다_적이 있을 경우")
    void ChessBoard_Check_pawn_moveDiagonal_enemy() {
        ChessBoard chessBoard = ChessBoard.initializeChessBoard();
        chessBoard.move(Position.of("a2"), Position.of("a4"));
        chessBoard.move(Position.of("a4"), Position.of("a5"));
        chessBoard.move(Position.of("a5"), Position.of("a6"));

        assertThatCode(() -> chessBoard.move(Position.of("a6"), Position.of("b7")))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("목적지에 같은 팀이 위치한다면 이동할 수 없다.")
    void ChessBoard_Disallow_move_to_target_if_there_is_team() {
        ChessBoard chessBoard = ChessBoard.initializeChessBoard();

        assertThatThrownBy(() -> chessBoard.move(Position.of("a1"), Position.of("b1")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동할 수 없습니다.");
    }
}
