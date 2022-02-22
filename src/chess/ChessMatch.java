package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch { //cora��o do sistema de xadrez

	private Board board; //uma partida de xadrez tem de ter um tabuleiro
	
	public ChessMatch() { 
		board = new Board (8, 8); //quem tem de saber a dimens�o de um tabuleiro de xadrez � a classe chessmatch 
		initialSetup(); //m�todo que inicia a partida
	}
	
	public ChessPiece[][] getPieces(){ //retorna uma matriz de pe�as de xadrez corrrespondentes � partida, n�o correspondente �s pe�as, tem haver com a camada do programa onde se est� 
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; //
		for (int i = 0; i<board.getRows(); i++) {			//percorrer a matriz de pe�as do tabuleiro e fazer um downcasting para chesspiece
			for (int j = 0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); //feito o downcasting para ele interpretar como uma pe�a de xadrez e n�o como uma pe�a comum
			}
		}
	return mat;	
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //m�todo para informar o initialSteup das posi��o das pe�as no sistema do xadrez em vez do sistema da matrix
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // .toPosition() � para converter para a posi��o de matrix
	}
	private void initialSetup() { //m�todo responsavel por iniciar a partida colocando as pe�as no tabuleiro
		placeNewPiece('b', 6, new Rook(board, Color.WHITE));
		placeNewPiece('e', 8, new King(board, Color.BLACK));
		placeNewPiece('e', 1, new King(board, Color.WHITE));
	}
}
