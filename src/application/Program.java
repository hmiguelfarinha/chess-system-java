package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ChessMatch chessMatch = new ChessMatch(); //criada partida de xadrez
		List<ChessPiece> captured = new ArrayList<>();
		
		while(!chessMatch.getCheckMate()) { // retiramos o loop constante e passamos que � enquanto a partida n�o estiver em checkamte
			try {
				UI.clearScreen();
				UI.printMatch(chessMatch, captured); //fun��o para imprimir as pe�as da partida (UI - User interface) e o lista de pe�as capturadas
				System.out.println();
				System.out.print("Source: ");
				ChessPosition source = UI.readChessPosition(sc);
				
				boolean[][] possibleMoves = chessMatch.possibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(), possibleMoves);
				
				System.out.println();
				System.out.print("Target: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source, target);		
				
				if (capturedPiece != null) {
					captured.add(capturedPiece);
				}
				
				if(chessMatch.getPormoted() != null) { //se o getPromoted � diferente de nula significa que um pe�a foi promovida
					System.out.print("Enter piece for promotion (B/N/R/Q): ");
					String type = sc.nextLine();
					chessMatch.replacePromotedPiece(type);
				}
			}
			catch (ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}
		UI.clearScreen();
		UI.printMatch(chessMatch, captured); //terminado o checkamte imprimesse para ter a vis�o da partida finalizada
	}
	
}
