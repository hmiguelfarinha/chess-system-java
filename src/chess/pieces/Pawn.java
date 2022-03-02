package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	public Pawn(Board board, Color color) {
		super(board, color);

	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0); // posi��o auxiliar, em 0 0 s� para ter um valor incial

		if (getColor() == Color.WHITE) { //tem de ser feito em separado para as brancas e para as pretas porque para as brancas retirados uma posi��o da matrix, para as pretas adicionamos
			p.setValues(position.getRow() - 1, position.getColumn()); //para uma posi��o acima na matriz,
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //se a posi��o acima dele exitir e estiver vazia
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() - 2, position.getColumn()); //para a primeira jogada, porque na primeira jogada ele pode andar duas casas
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) { //se a posi��o p duas casas acima dele exitir e estiver vazia, se a posi��o p2 uma casa acima dele exitir e estiver vazia e ainda for a primeira jogada
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() - 1, position.getColumn() - 1); //para a posi��o acima � esquerda, noroeste,
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posi��o existe e existe l� uma pe�a advers�ria
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() - 1, position.getColumn() + 1); //para a posi��o acima � esquerda, nordeste,
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posi��o existe e existe l� uma pe�a advers�ria
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

		}
		else { //para a pe�a preta
			p.setValues(position.getRow() + 1, position.getColumn()); //para uma posi��o acima na matriz,
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //se a posi��o acima dele exitir e estiver vazia
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() + 2, position.getColumn()); //para a primeira jogada, porque na primeira jogada ele pode andar duas casas
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) { //se a posi��o p duas casas acima dele exitir e estiver vazia, se a posi��o p2 uma casa acima dele exitir e estiver vazia e ainda for a primeira jogada
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() + 1, position.getColumn() - 1); //para a posi��o acima � esquerda, noroeste,
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posi��o existe e existe l� uma pe�a advers�ria
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() + 1, position.getColumn() + 1); //para a posi��o acima � esquerda, nordeste,
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posi��o existe e existe l� uma pe�a advers�ria
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}

