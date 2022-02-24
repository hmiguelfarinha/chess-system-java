package chess;

import boardgame.Board;
import boardgame.Piece;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){ //para na aplicação poder imprimir as posições possiveis a partir de uma posição de origem
		Position position = sourcePosition.toPosition(); //conversão da posição de xadrez para uma posição de matriz normal
		validateSourcePosition(position); //validação da posição de origem
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); //conversão da posição para posição da matriz
		Position target = targetPosition.toPosition();//conversão da posição para posição da matriz
		validateSourcePosition(source); //chamada do método para validar se realmente na posição de origem exisita uma peça 
		validateTargetPosition(source, target); //chamada do método para validar a posição de destino 
		Piece capturedPiece = makeMove(source, target); //o capturedPiece recebe o resultado da operação makeMove, operação responsavel por fazer o movimento da peça, já vem no formato matrix
		return (ChessPiece)capturedPiece; //retorna a peça capturada, necessário fazer downcasting porque a peça capturada era do tipo piece
	}
	
	private Piece makeMove(Position source, Position target) { //operação de movimento, recebe uma posição de origem e uma posição de destino
		Piece p = board.removePiece(source); // p recebe a peça retirada da posição de origem
		Piece capturedPiece = board.removePiece(target); //o capturedPiece recebe a possível peça que esteja no posição de destino, se ela existir
		board.placePiece(p, target); // colocação da peça na posição de destino
		return capturedPiece; //retorna a peça capturada
	}
	
	private void validateSourcePosition(Position position) { //método para validar se na posição exisite uma peça 
		if (!board.thereIsAPiece(position)) { 
			throw new ChessException("There is no piece on source position"); //se não existir peça na posição manda uma exceção 
		}	
		if (!board.piece(position).isThereAnyPossibleMove()) { // verifica se existe movimento possivel para uma determinada peça 
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) { //para validar se a posição de destino é valida em relação à posição de origem basta testar se a posição de destino é um movimento possível que estiver na posição de origem
		if (!board.piece(source).possibleMove(target)) { //se para a peça de origem a posição de destino não é um movimento possível então não se pode mover para o destino
			throw new ChessException("The chosen piece can't move to target position"); 
		}
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //método para informar o initialSteup das posição das peças no sistema do xadrez em vez do sistema da matrix
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // .toPosition() é para converter para a posição de matrix
	}
	
	private void initialSetup() { //método responsavel por iniciar a partida colocando as peças no tabuleiro
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}
