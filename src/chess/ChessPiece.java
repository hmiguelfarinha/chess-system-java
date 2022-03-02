package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {
	
	private Color color;
	private int moveCount; //como � inteiro por padr�o come�a com zero, ent�o n�o � necess�rio colocar no construtor  

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() { //foi apagado o set para proteger que a cor n�o seja mudada, apenas o get para aceder � cor da pe�a
		return color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	public void increseMoveCount() {
		moveCount++;
	}
	
	public void decreseMoveCount() {
		moveCount--;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position); //convers�o de posi��o normal para posi��o de xadrez
	}
	
	protected boolean isThereOpponentPiece(Position position) { //m�todo para saber se existe uma pe�a advers�ria numa determinada posi��o. � na classe gen�rica ChessPiece para ser reaproveitada em todas as outras pe�as
		ChessPiece p = (ChessPiece)getBoard().piece(position); //p recebe a pe�a que estiver na posi��o. necess�rio fazer o downcasting
		return p != null && p.getColor() != color; //verifica que se p � diferente de nula e se � a cor da pe�a � diferente da cor da pe�a em quest�o 
	}

}
