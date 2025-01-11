package games.awele;

import iialib.games.model.IMove;

/** A move consists in choosing a square for a player : 0 for the leftmost one (from his point of view) to N-1 */
public class AweleMove implements IMove {
	
	private final AweleRole r;
	private final int idx;
    
	/** The role (TOP or BOTTOM) and the index of the square (0 à N) */
    AweleMove(AweleRole r, int index){
        this.r = r;
        this.idx = index;
    }

    /** Display of a move */
    @Override
    public String toString() {
        return "\"" + (r==AweleRole.BOTTOM?"BOTTOM":"TOP") + " player pick up its square n°"+(idx+1)+"\"";
    }
    
    /** Getter on the index */
    public int getIndex() { return idx; }

    /** Getter on the role */
    public AweleRole getRole() { return r; }
}
