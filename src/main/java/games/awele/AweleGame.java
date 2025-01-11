package games.awele;

import java.util.ArrayList;

import iialib.games.algs.AIPlayer;
import iialib.games.algs.AbstractGame;
import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.algorithms.MiniMax;
import iialib.games.algs.algorithms.RandomChoice;

public class AweleGame extends AbstractGame<AweleMove, AweleRole, AweleBoard> {

	AweleGame(ArrayList<AIPlayer<AweleMove, AweleRole, AweleBoard>> players, AweleBoard board) {
		super(players, board);
	}

	public static void main(String[] args) {

		AweleRole roleTOP = AweleRole.TOP;
		AweleRole roleBOTTOM = AweleRole.BOTTOM;

		GameAlgorithm<AweleMove, AweleRole, AweleBoard> algTOP =
				//new RandomChoice<AweleMove, AweleRole, AweleBoard>(roleTOP);
				new MiniMax<AweleMove, AweleRole, AweleBoard>(roleTOP, roleBOTTOM, AweleHeuristics.hTop, 4); // Minimax depth 4

		GameAlgorithm<AweleMove, AweleRole, AweleBoard> algBOTTOM =
				//new RandomChoice<AweleMove, AweleRole, AweleBoard>(roleBOTTOM);
				new MiniMax<AweleMove, AweleRole, AweleBoard>(roleBOTTOM, roleTOP, AweleHeuristics.hBottom, 2); // Minimax depth 2

		AIPlayer<AweleMove, AweleRole, AweleBoard> playerTOP = new AIPlayer<AweleMove, AweleRole, AweleBoard>(roleTOP, algTOP);

		AIPlayer<AweleMove, AweleRole, AweleBoard> playerBOTTOM = new AIPlayer<AweleMove, AweleRole, AweleBoard>(roleBOTTOM, algBOTTOM);

		ArrayList<AIPlayer<AweleMove, AweleRole, AweleBoard>> players = new ArrayList<AIPlayer<AweleMove, AweleRole, AweleBoard>>();

		players.add(playerBOTTOM); // First Player = Bottom player
		players.add(playerTOP); // Second Player

		// Setting the initial Board
		AweleBoard initialBoard = new AweleBoard();

		AweleGame game = new AweleGame(players, initialBoard);
		game.runGame();
	}

}
