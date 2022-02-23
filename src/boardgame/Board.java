package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //matriz de me�as 
	
	public Board(int rows, int columns) { 
		if (rows < 1 || columns < 1) { // como n�o faz sentuido um tabuleiro menor que uma coluna e uma linha cria-se uma excecao defensivamente
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public Piece piece (int row, int column) { //m�todo para retornar a pe�a dada uma linha e uma coluna
		if (!positionExists(row, column)) { //para o caso da posi��o inserida n�o existir 
			throw new BoardException("Position not on the board"); 
		}
		return pieces[row][column];
	}
	
	public Piece piece (Position position) { // sobrecarga do m�todo para a posi��o
		if (!positionExists(position)) { //para o caso da posi��o inserida n�o existir 
			throw new BoardException("Position not on the board"); 
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) { //na matriz de pe�as do tabuleiro na linha e coluna atribui a pe�a que veio como argumento, a matriz do tabuleiro
		if (thereIsAPiece(position)) { //verifica se j� existe uma pe�a na posi��o indicada
			throw new BoardException("There is already a piece on position " + position); 			
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; //informa��o que a pe�a deixa de estar na posi��o nula mas sim na posi��o informada no m�todo. a posi��o � acedida livremente porque est� declarada na class piece como protected e � do mesmo pacote da Board
	}
	
	public Piece removePiece(Position position) {
		if (!positionExists(position)) { //programa��o defensiva para o caso da posi��o n�o existir 
			throw new BoardException("Position not on the board");
		}
		if(piece(position) == null) { //verifica��o se existe pe�a na posi��o do tabuleiro 
			return null;
		}
		Piece aux = piece(position); //a vari�vel aux vai receber a pe�a que estiver na posi��o 
		aux.position = null; // a posi��o da pe�a aux passa a ser nula
		pieces[position.getRow()][position.getColumn()] = null; // na matrix de pe�as a posi��o onde se est� remover a pe�a fica a nulo
		return aux; // a vari�vel aux fica com a pe�a que foi retirada e � retornada
	}
	
	private boolean positionExists(int row, int column) { // m�todo auxiliar para saber se a posi��o existe. porque dentro da classe vai ser mais f�cil testar pela linha e pela coluna do que pela posi�o
		return row >= 0 && row < rows && column >= 0 && column < columns; //logica para saber se a posi��o existe atrav�s de linhas e colunas
	}
	public boolean positionExists (Position position) { // m�todo para saber se a posi��o existe
		return positionExists(position.getRow(), position.getColumn()); //auxiliado pelo m�todo anterior 
	}
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) { //para o caso da posi��o inserida n�o existir 
			throw new BoardException("Position not on the board"); 
		}
		return piece(position) != null;
	}
	
	
}
