package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch; //para o rei ter acesso � partida 
	
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) { //m�todo auxiliar para saber se o rei pode mover para uma determinada posi��o
		ChessPiece p = (ChessPiece)getBoard().piece(position); //para saber se o rei pode mover para a posi��o primeiro sabemos a pe�a p que estiver nessa posi��o
		return p == null || p.getColor() != getColor(); //se p diferente de nulo e a cor de p diferente da por em quest�o
	}
	
	private boolean testRookCastling(Position position) { //teste de a torre est� apta para roque
		ChessPiece p = (ChessPiece)getBoard().piece(position); //adquirida a pe�a da posi��o p
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0; //se a pe�a p for diferente de nulo, se � uma torre, se a cor � igual � cor do rei e se ainda n�o fez movimentos
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0); //posi��o auxiliar
		
		//above
		p.setValues(position.getRow() - 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//below
		p.setValues(position.getRow() + 1, position.getColumn());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}		

		//left
		p.setValues(position.getRow(), position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}	

		//right
		p.setValues(position.getRow(), position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		//NW
		p.setValues(position.getRow() - 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//NE
		p.setValues(position.getRow() - 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		//SW
		p.setValues(position.getRow() + 1, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//SE
		p.setValues(position.getRow() + 1, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Specialmove castling
		if(getMoveCount() == 0 && !chessMatch.getCheck()) { //se � a primeira jogada e n�o est� em check
			//specialmove castling ring side rook
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3); //posi��o onde deve estar a torre em rela��o ao rei, tanto para pe�as pretas como brancas
			if(testRookCastling(posT1)) { //se der verdadeiro ainda falta testar se as duas casas entre eles est�o vazias
				Position p1 = new Position(position.getRow(), position.getColumn() + 1); //casa � direita do rei 
				Position p2 = new Position(position.getRow(), position.getColumn() + 2); //duas casas � direita do rei
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			//specialmove castling ring side rook
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4); //posi��o onde deve estar a torre em rela��o ao rei, tanto para pe�as pretas como brancas
			if(testRookCastling(posT2)) { //se der verdadeiro ainda falta testar se as duas casas entre eles est�o vazias
				Position p1 = new Position(position.getRow(), position.getColumn() - 1); //casa � esquerda do rei 
				Position p2 = new Position(position.getRow(), position.getColumn() - 2); //duas casas � esquerda do rei
				Position p3 = new Position(position.getRow(), position.getColumn() - 3); //tr�s casas � esquerda do rei

				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		return mat;
	}
}
