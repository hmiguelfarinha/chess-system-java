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
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch { //cora��o do sistema de xadrez

	private int turn;
	private Color currentPlayer;
	private Board board; //uma partida de xadrez tem de ter um tabuleiro
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable; //por padr�o come�a como nula ent�o n�o � necessario inciar no contrutor
	
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	
	public ChessMatch() { 
		board = new Board (8, 8); //quem tem de saber a dimens�o de um tabuleiro de xadrez � a classe chessmatch 
		turn = 1;
		currentPlayer = Color.WHITE; //� sempre o primeiro a come�ar
		check = false; // uma propriedade boolean � sempre iniciada como false, foi colocado aqui por quest�o did�tica
		initialSetup(); //m�todo que inicia a partida
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
	
	public ChessPiece getEnPassantVulnerable() { 
		return enPassantVulnerable;
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
		
		if (testCheck(currentPlayer)) { //teste se a jogada faz com que o jogador se autocoloque em check
			undoMove(source, target, capturedPiece);
			throw new ChessException("You  can't put yourself in check");
		}
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target); //pegamos a pe�a que foi movida
		
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; //express�o condicional tern�ria para saber se o oponente ficou em check, se ficar atualizada a vari�vel de check para true
		
		if(testCheckMate(opponent(currentPlayer))) { //testar se o jogo acabou para o oponente da pe�a que se mexeu 
			checkMate = true;
		}
		else {
			nextTurn(); //troca o turno
		}
		
		//Specialmove en passant
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {//se a pe�a movida foi um pe�o e a dif de linhas foi 2 para mais ou para menos significa que foi um movimento de peao incial com duas casas
			enPassantVulnerable = movedPiece;
		}
		else {
			enPassantVulnerable = null;	
		}
		return (ChessPiece)capturedPiece; //retorna a pe�a capturada, necess�rio fazer downcasting porque a pe�a capturada era do tipo piece
	}
	
	private Piece makeMove(Position source, Position target) { //opera��o de movimento, recebe uma posi��o de origem e uma posi��o de destino
		ChessPiece p = (ChessPiece)board.removePiece(source); // p recebe a pe�a retirada da posi��o de origem
		p.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target); //o capturedPiece recebe a poss�vel pe�a que esteja no posi��o de destino, se ela existir
		board.placePiece(p, target); // coloca��o da pe�a na posi��o de destino
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}
		
		//Specialmove castling kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) { //se a pe�a p � uma instancia de Rei, e a posi��o de destino � a posi��o de origem + 2 para a coluna ent�o � o roque menor
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3); //pegamos a posi��o da torre
			Position targetT = new Position(source.getRow(), source.getColumn() + 1); //pegamos a posi��o de destino da torre
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		//Specialmove castling queen rook
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) { //se a pe�a p � uma instancia de Rei, e a posi��o de destino � a posi��o de origem - 2 para a coluna ent�o � o roque maior
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4); //pegamos a posi��o da torre
			Position targetT = new Position(source.getRow(), source.getColumn() - 1); //pegamos a posi��o de destino da torre
			ChessPiece rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}
		
		//specialmove en passant
		if(p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == null) { //verifica��o se andou na diagonal e n�o capturou pe�a, ent�o significa que foi o en passant
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) { //verifica��o se que lado do jogo estamos
					pawnPosition = new Position(target.getRow() + 1, target.getColumn());
				}
				else {
					pawnPosition = new Position(target.getRow() - 1, target.getColumn());
				}
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		
		return capturedPiece; //retorna a pe�a capturada
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) { //desfazer a jogada
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		//Specialmove castling kingside rook
		if(p instanceof King && target.getColumn() == source.getColumn() + 2) { //se a pe�a p � uma instancia de Rei, e a posi��o de destino � a posi��o de origem + 2 para a coluna ent�o � o roque menor
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3); //pegamos a posi��o da torre
			Position targetT = new Position(source.getRow(), source.getColumn() + 1); //pegamos a posi��o de destino da torre
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		//Specialmove castling queen rook
		if(p instanceof King && target.getColumn() == source.getColumn() - 2) { //se a pe�a p � uma instancia de Rei, e a posi��o de destino � a posi��o de origem - 2 para a coluna ent�o � o roque maior
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4); //pegamos a posi��o da torre
			Position targetT = new Position(source.getRow(), source.getColumn() - 1); //pegamos a posi��o de destino da torre
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		//specialmove en passant
		if(p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) { //verifica��o se andou na diagonal e se a pe�a capturada � o en passant
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				if(p.getColor() == Color.WHITE) { //verifica��o se que lado do jogo estamos
					pawnPosition = new Position(3, target.getColumn());
				}
				else {
					pawnPosition = new Position(4, target.getColumn());
				}
				
				board.placePiece(pawn, pawnPosition);
			}
		}
	}
	
	private void validateSourcePosition(Position position) { //m�todo para validar se na posi��o exisite uma pe�a 
		if (!board.thereIsAPiece(position)) { 
			throw new ChessException("There is no piece on source position"); //se n�o existir pe�a na posi��o manda uma exce��o 
		}	
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor()) { //verifica��o se o jogador est� a pegar nussa pe�a sua. foi necess�rio fazer downcasting porque o getColor � uma propriedade do CHessPiece e o Board.Piece � da classe mais gen�rica 
			throw new ChessException("The chosen piece is not yours");
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
	
	private void nextTurn() {
		turn++; //incrementa��o do turno
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE; //fun��o tern�ria para mudar de jogador
	}
	
	private Color opponent(Color color) { //m�todo para retornar apenas a cor do oponente
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) { //metodo para localizar o rei de uma determinada cor, varrendo as pe�as do jogo
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //feito downcasting
		for(Piece p : list) {
			if(p instanceof King) { //se a pe�a p for uma instancia da classe rei, o instanceof � uma porpriedade de heran�a
				return (ChessPiece)p; //necess�rio downcasting
			}
		}
		throw new IllegalStateException("There is no " + color + "king on the board"); //n�o se vai tratar esta excecao no programa principal porque n�o � suposto ela nunca acontecer, se acontecer significa que o sistema de xadrez est� mal construido, portanto deixa-se estoirar para depois resolver o problema
	}
	
	private boolean testCheck(Color color) { //percorrer para cada uma das pe�as advers�rias se existe um movimento poss�vel que v� dar � casa do rei
		Position kingPosition = king(color).getChessPosition().toPosition(); // o kingPosition fica com a posi��o do rei no formato matrix
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList()); //lista das pe�as do advers�rio 
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) { //se na posi��o do rei � verdadeiro retorna true
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList()); //list para todas as pe�as da cor
		for(Piece p : list) {//for para percorrer todas as pe�as da lista
			boolean [][] mat = p.possibleMoves(); //matriz que vai ser percorrida todas as pe�as para ver se alguma retira o check, se existir retorna falso para o check mate
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColumns(); j++) {
					if(mat[i][j]) { //para cada elemento da matriz e exista um movimento possivel verfica-se se tira do check
						Position source = ((ChessPiece)p).getChessPosition().toPosition(); //para a verifica��o movemos a pe�a p da sua posi��o para o movimento poss�vel, aqui foi necess�rio fazer o downcasting porque o Position n�o � da classe ChessPiece e � necess�iro para fazer o toPosition
						Position target = new Position(i, j); //target � a posi��o que estamos a testar
						Piece capturedPiece = makeMove(source, target); //fazemos o movimento
						boolean testCheck = testCheck(color); //testemos o check
						undoMove(source, target, capturedPiece); //desfazemos logo o movimento para voltar � forma incial
						if(!testCheck) { // verificamos como tinha ficado a variavel auxiliar para o testcheck e se h� checkmate segundo a jogada possivel
							return false;
						}
						
					}
				}
			}
		}
		return true; //se esgotar o for e n�o houver nenhuma movimento possivel que retire do check ent�o est� em checkamte
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) { //m�todo para informar o initialSteup das posi��o das pe�as no sistema do xadrez em vez do sistema da matrix
		board.placePiece(piece, new ChessPosition(column, row).toPosition()); // .toPosition() � para converter para a posi��o de matrix
		piecesOnTheBoard.add(piece); //pe�a colocada no lista de pe�as no tabuleiro 
	}
	
	private void initialSetup() { //m�todo responsavel por iniciar a partida colocando as pe�as no tabuleiro
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this)); //O diz � autoreferencia para a partida em quest�o, tem de ser passada quando se instancia o Rei porque o rei tem acesso � partida
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK)); 
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}
}
