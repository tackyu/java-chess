package chess.controller;

import chess.domain.chessboard.ChessBoard;
import chess.view.Command;
import chess.domain.position.Position;
import chess.view.InputView;
import chess.view.OutputView;

import java.util.List;

public class Controller {
    public void run() {
        OutputView.printStartMessage();
        Command command = Command.getStartCommand(InputView.readCommand());
        ChessBoard chessBoard = ChessBoard.initializeChessBoard();
        while (!command.isEnd()) {
            OutputView.printChessBoard(chessBoard.getChessBoard());
            command = Command.getProcessCommand(InputView.readCommand());
            processGame(command, chessBoard);
        }
    }

    private void processGame(Command command, ChessBoard chessBoard) {
        if (command.isMove()) {
            List<String> positions = InputView.readPositions();
            chessBoard.move(Position.of(positions.get(0)), Position.of(positions.get(1)));
        }
    }
}
