package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //matriz de meças 
	
	public Board(int rows, int columns) { 
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public Piece piece (int row, int column) { //método para retornar a peça dada uma linha e uma coluna
		return pieces[row][column];
	}
	
	public Piece piece (Position position) { // sobrecarga do método para a posição
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) { //na matriz de peças do tabuleiro na linha e coluna atribui a peça que veio como argumento, a matriz do tabuleiro
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; //informação que a peça deixa de estar na posição nula mas sim na posição informada no método. a posição é acedida livremente porque está declarada na class piece como protected e é do mesmo pacote da Board
	}
}
