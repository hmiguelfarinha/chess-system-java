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

	public Color getColor() { //foi apagado o set para proteger que a cor n�o seja mudada, apenas o get para aceder � cor da pe�a
		return color;
	}
	
	protected boolean isThereOpponentPiece(Position position) { //m�todo para saber se existe uma pe�a advers�ria numa determinada posi��o. � na classe gen�rica ChessPiece para ser reaproveitada em todas as outras pe�as
		ChessPiece p = (ChessPiece)getBoard().piece(position); //p recebe a pe�a que estiver na posi��o. necess�rio fazer o downcasting
		return p != null && p.getColor() != color; //verifica que se p � diferente de nula e se � a cor da pe�a � diferente da cor da pe�a em quest�o 
	}

}
