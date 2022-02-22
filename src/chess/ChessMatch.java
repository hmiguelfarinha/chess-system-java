package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch { //coração do sistema de xadrez

	private Board board; //uma partida de xadrez tem de ter um tabuleiro
	
	public ChessMatch() { 
		board = new Board (8, 8); //quem tem de saber a dimensão de um tabuleiro de xadrez é a classe chessmatch 
		initialSetup(); //método que inicia a partida
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
	
	private void initialSetup() { //método responsavel por iniciar a partida colocando as peças no tabuleiro
		board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
		board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
		board.placePiece(new King(board, Color.WHITE), new Position(7, 4));
	}
}
