package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //matriz de meças 
	
	public Board(int rows, int columns) { 
		if (rows < 1 || columns < 1) { // como não faz sentuido um tabuleiro menor que uma coluna e uma linha cria-se uma excecao defensivamente
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

	public Piece piece (int row, int column) { //método para retornar a peça dada uma linha e uma coluna
		if (!positionExists(row, column)) { //para o caso da posição inserida não existir 
			throw new BoardException("Position not on the board"); 
		}
		return pieces[row][column];
	}
	
	public Piece piece (Position position) { // sobrecarga do método para a posição
		if (!positionExists(position)) { //para o caso da posição inserida não existir 
			throw new BoardException("Position not on the board"); 
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) { //na matriz de peças do tabuleiro na linha e coluna atribui a peça que veio como argumento, a matriz do tabuleiro
		if (thereIsAPiece(position)) { //verifica se já existe uma peça na posição indicada
			throw new BoardException("There is already a piece on position " + position); 			
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; //informação que a peça deixa de estar na posição nula mas sim na posição informada no método. a posição é acedida livremente porque está declarada na class piece como protected e é do mesmo pacote da Board
	}
	
	public Piece removePiece(Position position) {
		if (!positionExists(position)) { //programação defensiva para o caso da posição não existir 
			throw new BoardException("Position not on the board");
		}
		if(piece(position) == null) { //verificação se existe peça na posição do tabuleiro 
			return null;
		}
		Piece aux = piece(position); //a variável aux vai receber a peça que estiver na posição 
		aux.position = null; // a posição da peça aux passa a ser nula
		pieces[position.getRow()][position.getColumn()] = null; // na matrix de peças a posição onde se está remover a peça fica a nulo
		return aux; // a variável aux fica com a peça que foi retirada e é retornada
	}
	
	private boolean positionExists(int row, int column) { // método auxiliar para saber se a posição existe. porque dentro da classe vai ser mais fácil testar pela linha e pela coluna do que pela posião
		return row >= 0 && row < rows && column >= 0 && column < columns; //logica para saber se a posição existe através de linhas e colunas
	}
	public boolean positionExists (Position position) { // método para saber se a posição existe
		return positionExists(position.getRow(), position.getColumn()); //auxiliado pelo método anterior 
	}
	
	public boolean thereIsAPiece(Position position) {
		if (!positionExists(position)) { //para o caso da posição inserida não existir 
			throw new BoardException("Position not on the board"); 
		}
		return piece(position) != null;
	}
	
	
}
