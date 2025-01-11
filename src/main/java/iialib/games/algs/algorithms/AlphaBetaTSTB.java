package iialib.games.algs.algorithms;

import java.util.ArrayList;

import iialib.games.algs.GameAlgorithm;
import iialib.games.algs.IHeuristic;
import iialib.games.model.IBoard;
import iialib.games.model.IMove;
import iialib.games.model.IRole;
import iialib.games.model.Player;

/*
 * "The Sooner The Better"
 * In some games (where the pieces can come back to their previous position for example), 
 * it is possible for a player with a winning strategy to never reach the end of the game. 
 * because the MiniMax algorithm is moving the target node of victory further in time :
 * 
 *         (A)
 *         / \
 *       [B] [C]+inf
 *       /
 *     (D)
 *     / \
 *   [E] [F]+inf
 *   
 *   FRIEND plays A -> B (as the player targets to reach F which is a state of victory)
 *   ENNEMY plays B -> D
 *   
 *         (D)
 *         / \
 *       [E] [F]+inf
 *       /
 *     (A)
 *     / \
 *   [B] [C]+inf
 *   
 *   FRIEND plays D -> E (as the player targets to reach C which is a state of victory)
 *   ENNEMY plays E -> A
 *   etc.
 * 
 * One way to solve this issue is to differentiale the values associated to endgames
 * to foster the shallower nodes.
 */

public class AlphaBetaTSTB<Move extends IMove,Role extends IRole,Board extends IBoard<Move,Role,Board>> implements GameAlgorithm<Move,Role,Board> {

	// Constants
	private final static int DEPTH_MAX_DEFAULT = 4;

	// Attributes
	private final Role playerMaxRole;
	private final Role playerMinRole;
	private int depthMax = DEPTH_MAX_DEFAULT;
	private IHeuristic<Board, Role> h;
	private int nbNodes;
	private int nbLeaves;

	// --------- Constructors ---------

	public AlphaBetaTSTB(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h) {
		this.playerMaxRole = playerMaxRole;
		this.playerMinRole = playerMinRole;
		this.h = h;
	}

	public AlphaBetaTSTB(Role playerMaxRole, Role playerMinRole, IHeuristic<Board, Role> h, int depthMax) {
		this(playerMaxRole, playerMinRole, h);
		this.depthMax = depthMax;
	}

	/*
	 * IAlgo METHODS =============
	 */

	@Override
	public Move bestMove(Board board, Role playerRole) {
		System.out.println("[AlphaBeta]");

		nbNodes = 1; // root node
		nbLeaves = 0;
		int alpha = IHeuristic.MIN_VALUE;
		int beta = IHeuristic.MAX_VALUE;
		int newAlpha;

		// Compute all possible moves for maxPlayer
		ArrayList<Move> allMoves = board.possibleMoves(playerMaxRole);
		System.out.println("    * " + allMoves.size() + " possible moves");
		Move bestMove = allMoves.get(0);
		
		for (Move move : allMoves) {
			newAlpha = minMax(board.play(move, playerMaxRole), alpha, beta, 1);
			//System.out.println("Le coup " + move + " a pour valeur minimax " + newAlpha);
			if (newAlpha > alpha) {
				alpha = newAlpha;
				bestMove = move;
			}
		}
		
		System.out.println("    * " + nbNodes + " nodes explored");
		System.out.println("    * " + nbLeaves + " leaves evaluated");
		System.out.println("Best value is: " + alpha);
		return bestMove;
	}

	/*
	 * PUBLIC METHODS ==============
	 */

	public String toString() {
		return "AlphaBeta(ProfMax=" + depthMax + ")";
	}

	/*
	 * PRIVATE METHODS ===============
	 */
	private int maxMin(Board board, int alpha, int beta, int depth) {
		if (depth == depthMax || board.isGameOver()) {
			nbLeaves++;
			return h.eval(board, playerMaxRole);
		}
		else {
			nbNodes++;
			ArrayList<Move> allMoves = board.possibleMoves(playerMaxRole);
			int newVal;
			
			for (Move move : allMoves) {
				newVal = minMax(board.play(move,playerMaxRole), alpha, beta, depth+1);
				if (newVal == IHeuristic.MAX_VALUE) {
					newVal -= depth;
				}
				
				alpha = Math.max(alpha, newVal);
				
				if (alpha >= beta) {
					return beta;
				}
			}
			
			return alpha;
		}
	}

	private int minMax(Board board, int alpha, int beta, int depth) {
		if (depth == depthMax || board.isGameOver()) {
			nbLeaves++;
			return h.eval(board, playerMinRole);
		}
		else {
			nbNodes++;
			ArrayList<Move> allMoves = board.possibleMoves(playerMinRole);
			int newVal;
			
			for (Move move : allMoves) {
				newVal = maxMin(board.play(move,playerMinRole), alpha, beta, depth+1);
				if (newVal == IHeuristic.MIN_VALUE) {
					newVal += depth;
				}
				
				beta = Math.min(beta, newVal);
				
				if (alpha >= beta) {
					return alpha;
				}
			}
			
			return beta;
		}
	}

}
