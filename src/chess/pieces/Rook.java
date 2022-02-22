package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) { //constrututor passa para a superclass
		super(board, color);
	}

	@Override
	public String toString() { //onde estiver a peça aparece R de Rook
		return "R";
	}
}
