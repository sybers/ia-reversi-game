package reversi;

public class Program {

	public static void main(String[] args) {
		HumanPlayer player1 = new HumanPlayer();
		HumanPlayer player2 = new HumanPlayer();

		ReversiGame g = new ReversiGame(player1, player2);
		
		g.startGame();
		
		System.out.println("Fin de la partie");
		
	}

}
