package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	
	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() { //foi apagado o set para proteger que a cor não seja mudada, apenas o get para aceder à cor da peça
		return color;
	}
	
	protected boolean isThereOpponentPiece(Position position) { //método para saber se existe uma peça adversária numa determinada posição. É na classe genérica ChessPiece para ser reaproveitada em todas as outras peças
		ChessPiece p = (ChessPiece)getBoard().piece(position); //p recebe a peça que estiver na posição. necessário fazer o downcasting
		return p != null && p.getColor() != color; //verifica que se p é diferente de nula e se é a cor da peça é diferente da cor da peça em questão 
	}

}
