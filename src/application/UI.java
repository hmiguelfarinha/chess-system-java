package application;

import chess.ChessPiece;

public class UI {

	public static void printBoard(ChessPiece[][] pieces) { //método para imprimir o tabuleiro, é estático! recebe o chesspiece chamando pieces
		for (int i = 0; i<pieces.length; i++) {
			System.out.print((8 - i) + " "); // imprime o 8 , 7 , 6 por aí fora da primeira coluna
			for (int j = 0; j<pieces.length; j++) { // j<pieces.length considera-se que a matriz é quadrada então pode ser assim
				printPiece(pieces[i][j]); //imprime a peça
			}
			System.out.println(); //saltar de linha
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPiece(ChessPiece piece) { //método auxiliar para imprimir uma peça
		if (piece == null) { //verificação se existe peça na posição do tabuleiro
			System.out.print("-"); //imprime como se fosse vazio
		}
		else {
			System.out.print(piece); // se existe peça então imprime
		}
		System.out.print(" "); //impressão de um espaço em branco para as peças não ficarem demasido juntas
	}
	
	
}
