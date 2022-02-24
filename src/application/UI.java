package application;

import java.util.InputMismatchException;
import java.util.Scanner;

import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static ChessPosition readChessPosition(Scanner sc) { // para ler a posição do usuário aproveitamos o scanner
																// do programa principal
		try {
			String s = sc.nextLine(); // lemos o valor indicado pelo utilizador, tipo c3
			char column = s.charAt(0); // para sabermos a coluna
			int row = Integer.parseInt(s.substring(1)); // para sabermos a linha
			return new ChessPosition(column, row); // retorna coluna e linha
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
		}
	}

	public static void printBoard(ChessPiece[][] pieces) { // método para imprimir o tabuleiro, é estático! recebe o chesspiece chamando pieces
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " "); // imprime o 8 , 7 , 6 por aí fora da primeira coluna
			for (int j = 0; j < pieces.length; j++) { // j<pieces.length considera-se que a matriz é quadrada então pode ser assim
				printPiece(pieces[i][j], false); // imprime a peça
			}
			System.out.println(); // saltar de linha
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) { //sobrecarga do printBoard para imprimir as jogadas possíveis
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " "); // imprime o 8 , 7 , 6 por aí fora da primeira coluna
			for (int j = 0; j < pieces.length; j++) { // j<pieces.length considera-se que a matriz é quadrada então pode ser assim
				printPiece(pieces[i][j], possibleMoves[i][j]); // imprime a peça com a cor se o movimento for possível 
			}
			System.out.println(); // saltar de linha
		}
		System.out.println("  a b c d e f g h");
	}

	private static void printPiece(ChessPiece piece, boolean background) {
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} 
		else {
			if (piece.getColor() == Color.WHITE) { // verificar se a peça a imprimir é preta ou branca, a preta está a ser imprimida a amarelo
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} 
			else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
}
