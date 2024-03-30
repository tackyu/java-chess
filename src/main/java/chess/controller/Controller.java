package chess.controller;

import chess.domain.ScoreManager;
import chess.domain.chessboard.BoardInitializer;
import chess.domain.chessboard.ChessBoard;
import chess.domain.dao.ChessBoardDao;
import chess.domain.dao.ChessGameDao;
import chess.view.Command;
import chess.domain.position.Position;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.List;

import static chess.domain.chessboard.State.*;
import static chess.domain.chesspiece.Team.*;

public class Controller {
    public void run() {
        OutputView.printStartMessage();
        Command command = Command.getStartCommand(InputView.readCommand());
        ChessBoard chessBoard = loadChessBoard(new BoardInitializer());
        ScoreManager scoreManager = new ScoreManager(chessBoard);
        while (isGameOnGoing(chessBoard, command)) {
            OutputView.printChessBoard(chessBoard.getChessBoard(), Position.of("a8"));
            command = Command.getProcessCommand(InputView.readCommand());
            processGame(command, chessBoard, scoreManager);
        }
    }

    private ChessBoard loadChessBoard(BoardInitializer boardInitializer) {
        ChessGameDao chessGameDao = new ChessGameDao();
        ChessBoardDao chessBoardDao = new ChessBoardDao();
        int gameID = chessGameDao.findGameId();
        if (gameID == -1) {
            chessGameDao.addGame();
            ChessBoard board = boardInitializer.initializeChessBoard();
            chessBoardDao.save(chessGameDao.findGameId(), board.getChessBoard());
            return board;
        }
        return boardInitializer.initializeChessBoard(chessBoardDao.load(gameID));
    }

    private boolean isGameOnGoing(ChessBoard chessBoard, Command command) {
        return chessBoard.getState() == GAME_ONGOING && !command.isEnd();
    }

    private void processGame(Command command, ChessBoard chessBoard, ScoreManager scoreManager) {
        if (command.isMove()) {
            List<String> positions = InputView.readPositions();
            chessBoard.move(Position.of(positions.get(0)), Position.of(positions.get(1)));
        }
        if (command.isStatus()) {
            OutputView.printScore(WHITE, scoreManager.calculate(WHITE));
            OutputView.printScore(BLACK, scoreManager.calculate(BLACK));
            OutputView.printWinner(scoreManager.findWinner());
        }
    }

}
