package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch; //para o rei ter acesso à partida 
	
	public King(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) { //método auxiliar para saber se o rei pode mover para uma determinada posição
		ChessPiece p = (ChessPiece)getBoard().piece(position); //para saber se o rei pode mover para a posição primeiro sabemos a peça p que estiver nessa posição
		return p == null || p.getColor() != getColor(); //se p diferente de nulo e a cor de p diferente da por em questão
	}
	
	private boolean testRookCastling(Position position) { //teste de a torre está apta para roque
		ChessPiece p = (ChessPiece)getBoard().piece(position); //adquirida a peça da posição p
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0; //se a peça p for diferente de nulo, se é uma torre, se a cor é igual à cor do rei e se ainda não fez movimentos
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0); //posição auxiliar
		
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
		if(getMoveCount() == 0 && !chessMatch.getCheck()) { //se é a primeira jogada e não está em check
			//specialmove castling ring side rook
			Position posT1 = new Position(position.getRow(), position.getColumn() + 3); //posição onde deve estar a torre em relação ao rei, tanto para peças pretas como brancas
			if(testRookCastling(posT1)) { //se der verdadeiro ainda falta testar se as duas casas entre eles estão vazias
				Position p1 = new Position(position.getRow(), position.getColumn() + 1); //casa à direita do rei 
				Position p2 = new Position(position.getRow(), position.getColumn() + 2); //duas casas à direita do rei
				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getColumn() + 2] = true;
				}
			}
			
			//specialmove castling ring side rook
			Position posT2 = new Position(position.getRow(), position.getColumn() - 4); //posição onde deve estar a torre em relação ao rei, tanto para peças pretas como brancas
			if(testRookCastling(posT2)) { //se der verdadeiro ainda falta testar se as duas casas entre eles estão vazias
				Position p1 = new Position(position.getRow(), position.getColumn() - 1); //casa à esquerda do rei 
				Position p2 = new Position(position.getRow(), position.getColumn() - 2); //duas casas à esquerda do rei
				Position p3 = new Position(position.getRow(), position.getColumn() - 3); //três casas à esquerda do rei

				if(getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getColumn() - 2] = true;
				}
			}
		}
		return mat;
	}
}
