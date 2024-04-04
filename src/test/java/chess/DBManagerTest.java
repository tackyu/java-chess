package chess;

import chess.dao.ChessGameDao;
import chess.domain.chessboard.BoardInitializer;
import chess.domain.chessboard.ChessBoard;
import chess.domain.chesspiece.Pawn;
import chess.domain.chesspiece.Piece;
import chess.domain.chesspiece.Role;
import chess.dao.ChessBoardDao;
import chess.domain.chesspiece.Team;
import chess.domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DBManagerTest {
    private final DBManager dbManager = new DBManager();
    private final ChessBoardDao chessBoardDao = new ChessBoardDao();
    private final BoardInitializer boardInitializer = new BoardInitializer();

    @Test
    @DisplayName("chessBoard 갱신을 확인한다.")
    void DBManager_test_updateBoard() {
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        chessBoardDao.save(-1, chessBoard.getChessBoard());
        dbManager.updateBoard(new Pawn(Team.WHITE), List.of("a2", "a4"));

        Map<Position, Piece> data = chessBoardDao.load(-1);
        assertThat(data.get(Position.of("a2")).getRole()).isEqualTo(Role.EMPTY);
        assertThat(data.get(Position.of("a4")).getRole()).isEqualTo(Role.WHITE_PAWN);
        chessBoardDao.delete(-1);
    }
}
