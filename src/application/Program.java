package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {

		ChessMatch chessMatch = new ChessMatch(); //criada partida de xadrez
		UI.printBoard(chessMatch.getPieces()); //fun��o para imprimir as pe�as da partida (UI - User interface)

	}

}
