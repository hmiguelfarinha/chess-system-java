package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) { // constrututor passa para a superclass
		super(board, color);
	}

	@Override
	public String toString() { // onde estiver a pe�a aparece R de Rook
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0); // posi��o auxiliar, em 0 0 s� para ter um valor incial

		// verifica��o above
		p.setValues(position.getRow() - 1, position.getColumn()); // linha acima da posi��o da pe�a e a mesma coluna da pe�a. o position � a posi��o da pe�a que � o atributo da classe Piece
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // equanto a posi��o p existir e n�o tiver nenuma pe�a
			mat[p.getRow()][p.getColumn()] = true; //marca como verdadeiro as posi��es acima da pe�a
			p.setRow(p.getRow() - 1);// decrementar a linha
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //verifica se a �ltima posi��o existe e se encontrou uma pe�a advers�ria ent�o tamb�m � uma jogada poss�vel
			mat[p.getRow()][p.getColumn()] = true; //marca como verdadeira a posi��o
		}

		// verifica��o left
		p.setValues(position.getRow(), position.getColumn() -1); // linha acima da posi��o da pe�a e a mesma coluna da pe�a. o position � a posi��o da pe�a que � o atributo da classe Piece
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // equanto a posi��o p existir e n�o tiver nenuma pe�a
			mat[p.getRow()][p.getColumn()] = true; //marca como verdadeiro as posi��es acima da pe�a
			p.setColumn(p.getColumn() - 1);// decrementar a linha
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //verifica se a �ltima posi��o existe e se encontrou uma pe�a advers�ria ent�o tamb�m � uma jogada poss�vel
			mat[p.getRow()][p.getColumn()] = true; //marca cmo verdadeira a posi��o
		}

		// verifica��o right
		p.setValues(position.getRow(), position.getColumn() + 1); // linha acima da posi��o da pe�a e a mesma coluna da pe�a. o position � a posi��o da pe�a que � o atributo da classe Piece
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // equanto a posi��o p existir e n�o tiver nenuma pe�a
			mat[p.getRow()][p.getColumn()] = true; //marca como verdadeiro as posi��es acima da pe�a
			p.setColumn(p.getColumn() + 1);// incrementa a coluna
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //verifica se a �ltima posi��o existe e se encontrou uma pe�a advers�ria ent�o tamb�m � uma jogada poss�vel
			mat[p.getRow()][p.getColumn()] = true; //marca cmo verdadeira a posi��o
		}

		// verifica��o below
		p.setValues(position.getRow() + 1, position.getColumn()); // linha acima da posi��o da pe�a e a mesma coluna da pe�a. o position � a posi��o da pe�a que � o atributo da classe Piece
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // equanto a posi��o p existir e n�o tiver nenuma pe�a
			mat[p.getRow()][p.getColumn()] = true; //marca como verdadeiro as posi��es acima da pe�a
			p.setRow(p.getRow() + 1);// incrementa a coluna
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //verifica se a �ltima posi��o existe e se encontrou uma pe�a advers�ria ent�o tamb�m � uma jogada poss�vel
			mat[p.getRow()][p.getColumn()] = true; //marca cmo verdadeira a posi��o
		}
		
		return mat;
	}

}
