package problems.taquin;

import iialib.stateSpace.model.IHeuristic;


// Constants ----------------------------------------------------------


public class Heuristics {
	
	
	
	/**
	 * returns a heuristic corresponding to t
	 * @param goal	the goal state
	 * @return the heuristic with respect to the goal state
	 */
	public static IHeuristic<TaquinState>  nbOfUnmatchedTiles(TaquinState goal){
		
		return new IHeuristic<TaquinState>() {
			@Override
			public double apply(TaquinState state) {
				return state.nbOfUnmatchedTiles(goal);			
			}
			@Override
			public String toString() {
				return  "number of misplaced tiles wrt goal"; 		
			}
			};
	}
	
	
	
	public static IHeuristic<TaquinState>  manhattanDistance(TaquinState goal){
		
		return new IHeuristic<TaquinState>() {
			@Override
			public double apply(TaquinState state) {
				return state.manhantanDistance(goal);			
			}
			@Override
			public String toString() {
				return  "Manhattan distance wrt goal"; 		
			}
		};

	}
	
}
