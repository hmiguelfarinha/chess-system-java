package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch { //coração do sistema de xadrez

	private int turn;
	private Color currentPlayer;
	private Board board; //uma partida de xadrez tem de ter um tabuleiro
	private boolean check;
	private boolean checkMate;
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() { 
		board = new Board (8, 8); //quem tem de saber a dimensão de um tabuleiro de xadrez é a classe chessmatch 
		turn = 1;
		currentPlayer = Color.WHITE; //é sempre o primeiro a começar
		check = false; // uma propriedade boolean é sempre iniciada como false, foi colocado aqui por questão didática
		initialSetup(); //método que inicia a partida
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
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
		
		if (testCheck(currentPlayer)) { //teste se a jogada faz com que o jogador se autocoloque em check
			undoMove(source, target, capturedPiece);
			throw new ChessException("You  can't put yourself in check");
		}
		check = (testCheck(opponent(currentPlayer))) ? true : false; //expressão condicional ternária para saber se o oponente ficou em check, se ficar atualizada a variável de check para true
		
		if(testCheckMate(opponent(currentPlayer))) { //testar se o jogo acabou para o oponente da peça que se mexeu 
			checkMate = true;
		}
		else {
			nextTurn(); //troca o turno
		}
		
		return (ChessPiece)capturedPiece; //retorna a peça capturada, necessário fazer downcasting porque a peça capturada era do tipo piece
	}
	
	private Piece makeMove(Position source, Position target) { //operação de movimento, recebe uma posição de origem e uma posição de destino
		ChessPiece p = (ChessPiece)board.removePiece(source); // p recebe a peça retirada da posição de origem
		p.increseMoveCount();
		Piece capturedPiece = board.removePiece(target); //o capturedPiece recebe a possível peça que esteja no posição de destino, se ela existir
		board.placePiece(p, target); // colocação da peça na posição de destino
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		return capturedPiece; //retorna a peça capturada
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { //desfazer a jogada
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreseMoveCount();
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

	}
	
	private void validateSourcePosition(Position position) { //método para validar se na posição exisite uma peça 
		if (!board.thereIsAPiece(position)) { 
			throw new ChessException("There is no piece on source position"); //se não existir peça na posição manda uma exceção 
		}	
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { //verificação se o jogador está a pegar nussa peça sua. foi necessário fazer downcasting porque o getColor é uma propriedade do CHessPiece e o Board.Piece é da classe mais genérica 
			throw new ChessException("The chosen piece is not yours");
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
	
	private void nextTurn() {
		turn++; //incrementação do turno
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; //função ternária para mudar de jogador
	}
	
	private Color opponent(Color color) { //método para retornar apenas a cor do oponente
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) { //metodo para localizar o rei de uma determinada cor, varrendo as peças do jogo
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //feito downcasting
		for(Piece p : list) {
			if(p instanceof King) { //se a peça p for uma instancia da classe rei, o instanceof é uma porpriedade de herança
				return (ChessPiece)p; //necessário downcasting
			}
		}
		throw new IllegalStateException("There is no " + color + "king on the board"); //não se vai tratar esta excecao no programa principal porque não é suposto ela nunca acontecer, se acontecer significa que o sistema de xadrez está mal construido, portanto deixa-se estoirar para depois resolver o problema
	}
	
	private boolean testCheck(Color color) { //percorrer para cada uma das peças adversárias se existe um movimento possível que vá dar à casa do rei
		Position kingPosition = king(color).getChessPosition().toPosition(); // o kingPosition fica com a posição do rei no formato matrix
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList()); //lista das peças do adversário 
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) { //se na posição do rei é verdadeiro retorna true
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //list para todas as peças da cor
		for(Piece p : list) {//for para percorrer todas as peças da lista
			boolean [][] mat = p.possibleMoves(); //matriz que vai ser percorrida todas as peças para ver se alguma retira o check, se existir retorna falso para o check mate
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					if(mat[i][j]) { //para cada elemento da matriz e exista um movimento possivel verfica-se se tira do check
						Position source = ((ChessPiece)p).getChessPosition().toPosition(); //para a verificação movemos a peça p da sua posição para o movimento possível, aqui foi necessário fazer o downcasting porque o Position não é da classe ChessPiece e é necessáiro para fazer o toPosition
						Position target = new Position(i, j); //target é a posição que estamos a testar
						Piece capturedPiece = makeMove(source, target); //fazemos o movimento
						boolean testCheck = testCheck(color); //testemos o check
						undoMove(source, target, capturedPiece); //desfazemos logo o movimento para voltar à forma incial
						if(!testCheck) { // verificamos como tinha ficado a variavel auxiliar para o testcheck e se há checkmate segundo a jogada possivel
							return false;
						}
						
					}
				}
			}
		}
		return true; //se esgotar o for e não houver nenhuma movimento possivel que retire do check então está em checkamte
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //método para informar o initialSteup das posição das peças no sistema do xadrez em vez do sistema da matrix
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // .toPosition() é para converter para a posição de matrix
		piecesOnTheBoard.add(piece); //peça colocada no lista de peças no tabuleiro 
	}
	
	private void initialSetup() { //método responsavel por iniciar a partida colocando as peças no tabuleiro
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK)); 
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK));
	}
}
