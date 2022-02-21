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
	
	 
}
