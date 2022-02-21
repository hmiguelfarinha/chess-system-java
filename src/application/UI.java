package application;

import chess.ChessPiece;

public class UI {

	public static void printBoard(ChessPiece[][] pieces) { //m�todo para imprimir o tabuleiro, � est�tico! recebe o chesspiece chamando pieces
		for (int i = 0; i<pieces.length; i++) {
			System.out.print((8 - i) + " "); // imprime o 8 , 7 , 6 por a� fora da primeira coluna
			for (int j = 0; j<pieces.length; j++) { // j<pieces.length considera-se que a matriz � quadrada ent�o pode ser assim
				printPiece(pieces[i][j]); //imprime a pe�a
			}
			System.out.println(); //saltar de linha
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPiece(ChessPiece piece) { //m�todo auxiliar para imprimir uma pe�a
		if (piece == null) { //verifica��o se existe pe�a na posi��o do tabuleiro
			System.out.print("-"); //imprime como se fosse vazio
		}
		else {
			System.out.print(piece); // se existe pe�a ent�o imprime
		}
		System.out.print(" "); //impress�o de um espa�o em branco para as pe�as n�o ficarem demasido juntas
	}
	
	
}
