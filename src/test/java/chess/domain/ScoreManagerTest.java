package chess.domain;

import chess.domain.chessboard.BoardInitializer;
import chess.domain.chessboard.ChessBoard;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static chess.domain.chesspiece.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ScoreManagerTest {
    private final BoardInitializer boardInitializer = new BoardInitializer();

    @Test
    @DisplayName("초기 체스판 점수를 계산")
    void ScoreManager_calculate_initial_score_by_team() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        ScoreManager scoreManager = new ScoreManager(chessBoard);

        assertThat(scoreManager.calculate(BLACK)).isEqualTo(38);
        assertThat(scoreManager.calculate(WHITE)).isEqualTo(38);
    }

    @Test
    @DisplayName("폰이 한 열에 서있다면, 개당 0.5점씩으로 점수를 계산")
    void ScoreManager_calculate_score_by_team_when_pawn_stand_in_a_line() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        ScoreManager scoreManager = new ScoreManager(chessBoard);
        chessBoard.move(Position.of("a2"), Position.of("a4"));
        chessBoard.move(Position.of("a7"), Position.of("a5"));
        chessBoard.move(Position.of("b2"), Position.of("b4"));
        chessBoard.move(Position.of("b4"), Position.of("a5"));
        chessBoard.move(Position.of("c2"), Position.of("c4"));
        chessBoard.move(Position.of("c4"), Position.of("c5"));
        chessBoard.move(Position.of("b7"), Position.of("b6"));
        chessBoard.move(Position.of("c5"), Position.of("b6"));
        chessBoard.move(Position.of("a8"), Position.of("a7"));
        chessBoard.move(Position.of("b6"), Position.of("a7"));

        assertThat(scoreManager.calculate(BLACK)).isEqualTo(31.0);
        assertThat(scoreManager.calculate(WHITE)).isEqualTo(36.5);

    }

    @Test
    @DisplayName("점수를 기준으로 어느 진영이 더 우세한지 찾는다.")
    void ScoreManager_findWinner_white() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        ScoreManager scoreManager = new ScoreManager(chessBoard);
        chessBoard.move(Position.of("a2"), Position.of("a4"));
        chessBoard.move(Position.of("a7"), Position.of("a5"));
        chessBoard.move(Position.of("b2"), Position.of("b4"));
        chessBoard.move(Position.of("b4"), Position.of("a5"));
        chessBoard.move(Position.of("c2"), Position.of("c4"));
        chessBoard.move(Position.of("c4"), Position.of("c5"));
        chessBoard.move(Position.of("b7"), Position.of("b6"));
        chessBoard.move(Position.of("c5"), Position.of("b6"));
        chessBoard.move(Position.of("a8"), Position.of("a7"));
        chessBoard.move(Position.of("b6"), Position.of("a7"));

        assertThat(scoreManager.findWinner()).isEqualTo(WHITE);
    }

    @Test
    @DisplayName("점수를 기준으로 어느 진영이 더 우세한지 찾는다.")
    void ScoreManager_findWinner_black() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        ScoreManager scoreManager = new ScoreManager(chessBoard);
        chessBoard.move(Position.of("a7"), Position.of("a5"));
        chessBoard.move(Position.of("a5"), Position.of("a4"));
        chessBoard.move(Position.of("a4"), Position.of("a3"));
        chessBoard.move(Position.of("a3"), Position.of("b2"));
        chessBoard.move(Position.of("b2"), Position.of("a1"));

        assertThat(scoreManager.findWinner()).isEqualTo(BLACK);
    }

    @Test
    @DisplayName("점수를 기준으로 어느 진영이 더 우세한지 찾는다.")
    void ScoreManager_findWinner_draw() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        ScoreManager scoreManager = new ScoreManager(chessBoard);

        assertThat(scoreManager.findWinner()).isEqualTo(NOTHING);
    }
}
