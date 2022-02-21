package boardgame;

public class Piece {

	protected Position position; //posição não visivel na camada de xadrez 
	private Board board; //associação com o tabuleiro 

	public Piece(Board board) { //apenas se passa o tabuleiro para o contrutor porque uma peça recem criada é nula porque ainda não foi colocada no tabuleiro 
		this.board = board;
		position = null; //podia-se não colocar porque o java já dava a posição como nula
	}

	protected Board getBoard() { //protegido para apenas classes do mesmo pacote e subclasses da peça podem aceder ao tabuleiro. assim o tabuleiro não é acedido pela classe xadrez
		return board;
	}

}
