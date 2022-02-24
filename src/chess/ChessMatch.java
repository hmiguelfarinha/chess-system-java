package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){ //para na aplica��o poder imprimir as posi��es possiveis a partir de uma posi��o de origem
		Position position = sourcePosition.toPosition(); //convers�o da posi��o de xadrez para uma posi��o de matriz normal
		validateSourcePosition(position); //valida��o da posi��o de origem
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition(); //convers�o da posi��o para posi��o da matriz
		Position target = targetPosition.toPosition();//convers�o da posi��o para posi��o da matriz
		validateSourcePosition(source); //chamada do m�todo para validar se realmente na posi��o de origem exisita uma pe�a 
		validateTargetPosition(source, target); //chamada do m�todo para validar a posi��o de destino 
		Piece capturedPiece = makeMove(source, target); //o capturedPiece recebe o resultado da opera��o makeMove, opera��o responsavel por fazer o movimento da pe�a, j� vem no formato matrix
		return (ChessPiece)capturedPiece; //retorna a pe�a capturada, necess�rio fazer downcasting porque a pe�a capturada era do tipo piece
	}
	
	private Piece makeMove(Position source, Position target) { //opera��o de movimento, recebe uma posi��o de origem e uma posi��o de destino
		Piece p = board.removePiece(source); // p recebe a pe�a retirada da posi��o de origem
		Piece capturedPiece = board.removePiece(target); //o capturedPiece recebe a poss�vel pe�a que esteja no posi��o de destino, se ela existir
		board.placePiece(p, target); // coloca��o da pe�a na posi��o de destino
		return capturedPiece; //retorna a pe�a capturada
	}
	
	private void validateSourcePosition(Position position) { //m�todo para validar se na posi��o exisite uma pe�a 
		if (!board.thereIsAPiece(position)) { 
			throw new ChessException("There is no piece on source position"); //se n�o existir pe�a na posi��o manda uma exce��o 
		}	
		if (!board.piece(position).isThereAnyPossibleMove()) { // verifica se existe movimento possivel para uma determinada pe�a 
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void validateTargetPosition(Position source, Position target) { //para validar se a posi��o de destino � valida em rela��o � posi��o de origem basta testar se a posi��o de destino � um movimento poss�vel que estiver na posi��o de origem
		if (!board.piece(source).possibleMove(target)) { //se para a pe�a de origem a posi��o de destino n�o � um movimento poss�vel ent�o n�o se pode mover para o destino
			throw new ChessException("The chosen piece can't move to target position"); 
		}
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //m�todo para informar o initialSteup das posi��o das pe�as no sistema do xadrez em vez do sistema da matrix
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // .toPosition() � para converter para a posi��o de matrix
	}
	
	private void initialSetup() { //m�todo responsavel por iniciar a partida colocando as pe�as no tabuleiro
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
