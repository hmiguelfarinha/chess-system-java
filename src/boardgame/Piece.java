package boardgame;

public class Piece {

	protected Position position; //posi��o n�o visivel na camada de xadrez 
	private Board board; //associa��o com o tabuleiro 

	public Piece(Board board) { //apenas se passa o tabuleiro para o contrutor porque uma pe�a recem criada � nula porque ainda n�o foi colocada no tabuleiro 
		this.board = board;
		position = null; //podia-se n�o colocar porque o java j� dava a posi��o como nula
	}

	protected Board getBoard() { //protegido para apenas classes do mesmo pacote e subclasses da pe�a podem aceder ao tabuleiro. assim o tabuleiro n�o � acedido pela classe xadrez
		return board;
	}

}
