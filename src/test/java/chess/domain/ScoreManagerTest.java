package chess.domain;

import chess.domain.chessboard.BoardInitializer;
import chess.domain.chessboard.ChessBoard;
import chess.domain.chesspiece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static chess.domain.chesspiece.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ScoreManagerTest {
    private final BoardInitializer boardInitializer = new BoardInitializer();

    @Test
    @DisplayName("점수를 계산")
    void ScoreManager_calculate_score_by_team() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        ScoreManager scoreManager = new ScoreManager(chessBoard);

        assertThat(scoreManager.calculate(BLACK)).isEqualTo(38);
        assertThat(scoreManager.calculate(WHITE)).isEqualTo(38);

    }
}
