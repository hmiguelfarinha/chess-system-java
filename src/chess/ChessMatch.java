package chess;

import boardgame.Board;

public class ChessMatch { //coração do sistema de xadrez

	private Board board; //uma partida de xadrez tem de ter um tabuleiro
	
	public ChessMatch() { 
		board = new Board (8, 8); //quem tem de saber a dimensão de um tabuleiro de xadrez é a classe chessmatch 
	}
	
	public ChessPiece[][] getPieces(){ //retorna uma matriz de peças de xadrez corrrespondentes à partida, não correspondente às peças, tem haver com a camada do programa onde se está 
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()]; //
		for (int i = 0; i<board.getRows(); i++) {			//percorrer a matriz de peças do tabuleiro e fazer um downcasting para chesspiece
			for (int j = 0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); //feito o downcasting para ele interpretar como uma peça de xadrez e não como uma peça comum
			}
		}
	return mat;	
	}
}
