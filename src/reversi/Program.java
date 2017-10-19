package reversi;

public class Program {

	public static void main(String[] args) {
		ReversiGame g = new ReversiGame();
		
		System.out.println(g.toString());
		
		boolean isBlackTurn = false;
		
		while(true) {
			g.setPiece(isBlackTurn);
			System.out.println(g.toString());
			isBlackTurn = !isBlackTurn;
		}
		
	}

}
