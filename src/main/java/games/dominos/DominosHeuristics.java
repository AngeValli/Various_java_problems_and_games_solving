package games.dominos;

import iialib.games.algs.IHeuristic;

public class DominosHeuristics {
	
	/**
	 * Heuristic from vertical player point of view.
	 * @param board the board to evaluate
	 * @param role tue player who has the move
	 * @return Heuristic value of (board,role)
	 */
	public static IHeuristic<DominosBoard,DominosRole>  hVertical = (board,role) -> {
        final int nbV = board.nbVerticalMoves();
        final int nbH = board.nbHorizontalMoves();
        // If there is not any possible move,
        // the player who was about to play has lost
        if (nbV == 0 && nbH == 0) {
        	if (role == DominosRole.VERTICAL)
        		return IHeuristic.MIN_VALUE;
        	else
        		return IHeuristic.MAX_VALUE;
        }
        // If the vertical player can't place a tile,
        // then he has lost
        else if (nbV == 0) {
        	return IHeuristic.MIN_VALUE;
        }
        // If the horizontal player can't place a tile,
        // then he has lost
        else if (nbH == 0) {
        	return IHeuristic.MAX_VALUE;
        }
        else {
        	return  nbV - nbH;
        }
    };
    
    /**
	 * Heuristic from horizontal player point of view.
	 * @param board the board to evaluate
	 * @param role tue player who has the move
	 * @return Heuristic value of (board,role)
	 */
	public static IHeuristic<DominosBoard,DominosRole> hHorizontal = (board,role) -> {
        final int nbV = board.nbVerticalMoves();
        final int nbH = board.nbHorizontalMoves();
        // If there is not any possible move,
        // the player who was about to play has lost
        if (nbV == 0 && nbH == 0) {
        	if (role == DominosRole.VERTICAL)
        		return IHeuristic.MAX_VALUE;
        	else
        		return IHeuristic.MIN_VALUE;
        }
        // If the vertical player can't place a domino,
        // then he has lost
        else if (nbV == 0) {
        	return IHeuristic.MIN_VALUE;
        }
        // If the horizontal player can't place a domino,
        // then he has lost
        else if (nbH == 0) {
        	return IHeuristic.MIN_VALUE;
        }
        else {
        	return  nbH - nbV;
        }
    };
   
}
	