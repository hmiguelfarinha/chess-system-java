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
	public String toString() { // onde estiver a peça aparece R de Rook
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0); // posição auxiliar, em 0 0 só para ter um valor incial

		// verificação above
		p.setValues(position.getRow() - 1, position.getColumn()); // linha acima da posição da peça e a mesma coluna da peça. o position é a posição da peça que é o atributo da classe Piece
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // equanto a posição p existir e não tiver nenuma peça
			mat[p.getRow()][p.getColumn()] = true; //marca como verdadeiro as posições acima da peça
			p.setRow(p.getRow() - 1);// decrementar a linha
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //verifica se a última posição existe e se encontrou uma peça adversária então também é uma jogada possível
			mat[p.getRow()][p.getColumn()] = true; //marca como verdadeira a posição
		}

		// verificação left
		p.setValues(position.getRow(), position.getColumn() -1); // linha acima da posição da peça e a mesma coluna da peça. o position é a posição da peça que é o atributo da classe Piece
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // equanto a posição p existir e não tiver nenuma peça
			mat[p.getRow()][p.getColumn()] = true; //marca como verdadeiro as posições acima da peça
			p.setColumn(p.getColumn() - 1);// decrementar a linha
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //verifica se a última posição existe e se encontrou uma peça adversária então também é uma jogada possível
			mat[p.getRow()][p.getColumn()] = true; //marca cmo verdadeira a posição
		}

		// verificação right
		p.setValues(position.getRow(), position.getColumn() + 1); // linha acima da posição da peça e a mesma coluna da peça. o position é a posição da peça que é o atributo da classe Piece
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // equanto a posição p existir e não tiver nenuma peça
			mat[p.getRow()][p.getColumn()] = true; //marca como verdadeiro as posições acima da peça
			p.setColumn(p.getColumn() + 1);// incrementa a coluna
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //verifica se a última posição existe e se encontrou uma peça adversária então também é uma jogada possível
			mat[p.getRow()][p.getColumn()] = true; //marca cmo verdadeira a posição
		}

		// verificação below
		p.setValues(position.getRow() + 1, position.getColumn()); // linha acima da posição da peça e a mesma coluna da peça. o position é a posição da peça que é o atributo da classe Piece
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { // equanto a posição p existir e não tiver nenuma peça
			mat[p.getRow()][p.getColumn()] = true; //marca como verdadeiro as posições acima da peça
			p.setRow(p.getRow() + 1);// incrementa a coluna
		}
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //verifica se a última posição existe e se encontrou uma peça adversária então também é uma jogada possível
			mat[p.getRow()][p.getColumn()] = true; //marca cmo verdadeira a posição
		}
		
		return mat;
	}

}
