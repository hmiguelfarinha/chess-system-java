package application;

import chess.ChessMatch;

public class Program {

	public static void main(String[] args) {

		ChessMatch chessMatch = new ChessMatch(); //criada partida de xadrez
		UI.printBoard(chessMatch.getPieces()); //função para imprimir as peças da partida (UI - User interface)

	}

}
