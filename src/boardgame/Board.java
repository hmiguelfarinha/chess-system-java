package boardgame;

public class Board {

	private int rows;
	private int columns;
	private Piece[][] pieces; //matriz de me�as 
	
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
	
	public Piece piece (int row, int column) { //m�todo para retornar a pe�a dada uma linha e uma coluna
		return pieces[row][column];
	}
	
	public Piece piece (Position position) { // sobrecarga do m�todo para a posi��o
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) { //na matriz de pe�as do tabuleiro na linha e coluna atribui a pe�a que veio como argumento, a matriz do tabuleiro
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; //informa��o que a pe�a deixa de estar na posi��o nula mas sim na posi��o informada no m�todo. a posi��o � acedida livremente porque est� declarada na class piece como protected e � do mesmo pacote da Board
	}
}
