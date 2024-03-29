package chess.controller;

import chess.domain.chessboard.BoardInitializer;
import chess.domain.chessboard.ChessBoard;
import chess.view.Command;
import chess.domain.position.Position;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.List;

import static chess.domain.chessboard.State.*;

public class Controller {
    public void run() {
        OutputView.printStartMessage();
        Command command = Command.getStartCommand(InputView.readCommand());
        BoardInitializer boardInitializer = new BoardInitializer();
        ChessBoard chessBoard = boardInitializer.initializeChessBoard();
        while (isGameOnGoing(chessBoard, command)) {
            OutputView.printChessBoard(chessBoard.getChessBoard());
            command = Command.getProcessCommand(InputView.readCommand());
            processGame(command, chessBoard);
        }
    }

    private boolean isGameOnGoing(ChessBoard chessBoard, Command command) {
        return chessBoard.getState() == GAME_ONGOING && !command.isEnd();
    }

    private void processGame(Command command, ChessBoard chessBoard) {
        if (command.isMove()) {
            List<String> positions = InputView.readPositions();
            chessBoard.move(Position.of(positions.get(0)), Position.of(positions.get(1)));
        }
    }
}
