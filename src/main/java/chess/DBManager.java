package chess;

import chess.dao.ChessBoardDao;
import chess.dao.ChessGameDao;
import chess.domain.chessboard.ChessBoard;
import chess.domain.chesspiece.Empty;
import chess.domain.chesspiece.Piece;
import chess.domain.position.Position;

import java.util.List;
import java.util.Map;

public class DBManager {
    private final ChessBoardDao chessBoardDao;
    private final ChessGameDao chessGameDao;

    public DBManager() {
        this.chessBoardDao = new ChessBoardDao();
        this.chessGameDao = new ChessGameDao();
    }

    public int findGameId() {
        return chessGameDao.findGameId();
    }

    public Map<Position, Piece> loadChessBoard() {
        return chessBoardDao.load(findGameId());
    }

    public void initialize(ChessBoard board) {
        chessGameDao.addGame();
        chessBoardDao.save(chessGameDao.findGameId(), board.getChessBoard());
    }

    public void updateBoard(Piece sourcePiece, List<String> positions) {
        chessBoardDao.update(sourcePiece, Position.of(positions.get(1)));
        chessBoardDao.update(new Empty(), Position.of(positions.get(0)));
    }

    public void clean() {
        int gameId = chessGameDao.findGameId();
        chessGameDao.delete();
        chessBoardDao.delete(gameId);
    }

}
