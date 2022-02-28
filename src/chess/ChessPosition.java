package chess;

import boardgame.Position;

public class ChessPosition {

	private char column;
	private int row;

	public ChessPosition(char column, int row) {
		if (column < 'a' || column > 'h' || row < 1 || row > 8) { //programação defensiva
			throw new ChessException("Error instantiating ChessPostion. Valid values are from a1 to h8.");
		}
		this.column = column;
		this.row = row;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	protected Position toPosition() { //vai converter a chessposition para a posição normal Logia -> matrix_row = 8 - chess_row e matrix_column = chess_column - unicode 'a'
		return new Position(8 - row, column - 'a');
	}
	
	protected static ChessPosition fromPosition(Position position) { //vai converter de posição normal para chesspostion 
		return new ChessPosition((char)('a'+ position.getColumn()), 8 - position.getRow()); //é preciso fazer o casting porque não converte automaticamente para char
	}
	
	@Override
	
	public String toString() {
		return "" + column + row; // as "" é para forçar o compilador a perceber que é uma concatenação de string, sem as aspas dá erro
	}
}
