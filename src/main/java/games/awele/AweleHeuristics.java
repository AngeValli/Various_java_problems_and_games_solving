package games.awele;

import iialib.games.algs.IHeuristic;

/** The heuristic used here in Awalé for the bottom player is the following :
 * - If the BOTTOM player does not have any more stones in its field (sumBOTTOM==0), h = -inf
 * - If the TOP player does not have any more stones in its field (sumTOP==0), h = +inf
 * - Otherwise, h = (scoreBottom-scoreTop)*100 + (sumTOP-sumBOTTOM)*10 + (nbMovesBOTTOM - nbMovesTOP)
 * 
 * The heuristic is symmetric for the other player.
 * 
 * @author nico
 *
 */
public class AweleHeuristics {
	public static IHeuristic<AweleBoard,AweleRole>  hBottom = (board,role) -> {
		return scoreBottom(board);
    };

    private static int scoreBottom(AweleBoard board) {
    	int sumBOTTOM = board.somme(AweleRole.BOTTOM);
		int sumTOP = board.somme(AweleRole.TOP);
		if (sumBOTTOM==0)
			return Integer.MIN_VALUE;
		if (sumTOP==0)
			return Integer.MAX_VALUE;
		return 100*(board.getscoreBottom()-board.getscoreTop())
				+10*(sumBOTTOM-sumTOP)
				+(board.possibleMoves(AweleRole.BOTTOM).size()-board.possibleMoves(AweleRole.TOP).size());
    }
    
	public static IHeuristic<AweleBoard,AweleRole>  hTop = (board,role) -> {
		return -scoreBottom(board);		
    };

}
