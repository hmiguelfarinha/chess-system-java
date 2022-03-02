package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

	private ChessMatch chessMatch; //depedencia para a partida devido à jogada especial do en passant
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;

	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		Position p = new Position(0, 0); // posição auxiliar, em 0 0 só para ter um valor incial

		if (getColor() == Color.WHITE) { //tem de ser feito em separado para as brancas e para as pretas porque para as brancas retirados uma posição da matrix, para as pretas adicionamos
			p.setValues(position.getRow() - 1, position.getColumn()); //para uma posição acima na matriz,
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //se a posição acima dele exitir e estiver vazia
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() - 2, position.getColumn()); //para a primeira jogada, porque na primeira jogada ele pode andar duas casas
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) { //se a posição p duas casas acima dele exitir e estiver vazia, se a posição p2 uma casa acima dele exitir e estiver vazia e ainda for a primeira jogada
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() - 1, position.getColumn() - 1); //para a posição acima à esquerda, noroeste,
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existe e existe lá uma peça adversária
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() - 1, position.getColumn() + 1); //para a posição acima à esquerda, nordeste,
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existe e existe lá uma peça adversária
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}
			
			//Specialmove en passant white
			if(position.getRow() == 3) { //para acontecer o en passant o peão tem de estar na linha 3 da matriz que é a linha 5 do xadrez
				Position left = new Position(position.getRow(), position.getColumn() - 1); //posição ao lado esquerdo do peão 
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) { //se a posição existe, se está la uma peça oponente e se a peça é a peça que está vulnerável n passant
					mat[left.getRow() - 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1); //posição ao lado esquerdo do peão 
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) { //se a posição existe, se está la uma peça oponente e se a peça é a peça que está vulnerável n passant
					mat[right.getRow() - 1][right.getColumn()] = true;
				}
			}
		}
		else { //para a peça preta
			p.setValues(position.getRow() + 1, position.getColumn()); //para uma posição acima na matriz,
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //se a posição acima dele exitir e estiver vazia
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() + 2, position.getColumn()); //para a primeira jogada, porque na primeira jogada ele pode andar duas casas
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) { //se a posição p duas casas acima dele exitir e estiver vazia, se a posição p2 uma casa acima dele exitir e estiver vazia e ainda for a primeira jogada
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() + 1, position.getColumn() - 1); //para a posição acima à esquerda, noroeste,
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existe e existe lá uma peça adversária
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}

			p.setValues(position.getRow() + 1, position.getColumn() + 1); //para a posição acima à esquerda, nordeste,
			if(getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se a posição existe e existe lá uma peça adversária
				mat[p.getRow()][p.getColumn()] = true; //pode mover
			}
			
			//Specialmove en passant black
			if(position.getRow() == 4) { //para acontecer o en passant o peão tem de estar na linha 3 da matriz que é a linha 5 do xadrez
				Position left = new Position(position.getRow(), position.getColumn() - 1); //posição ao lado esquerdo do peão 
				if(getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) { //se a posição existe, se está la uma peça oponente e se a peça é a peça que está vulnerável n passant
					mat[left.getRow() + 1][left.getColumn()] = true;
				}
				Position right = new Position(position.getRow(), position.getColumn() + 1); //posição ao lado esquerdo do peão 
				if(getBoard().positionExists(right) && isThereOpponentPiece(right) && getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) { //se a posição existe, se está la uma peça oponente e se a peça é a peça que está vulnerável n passant
					mat[right.getRow() + 1][right.getColumn()] = true;
				}
			}
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}

}

