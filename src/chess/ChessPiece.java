package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece {
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() { //foi apagado o set para proteger que a cor não seja mudada, apenas o get para aceder à cor da peça
		return color;
	}

}
