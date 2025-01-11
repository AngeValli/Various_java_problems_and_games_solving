package problems.turtle;

import iialib.stateSpace.algs.ASearchAlgorithmStats;
import iialib.stateSpace.algs.Solution;
import iialib.stateSpace.algs.implementation.DepthFirstSearchNaiveStats;
import iialib.stateSpace.model.Problem;

public class RunTurtleNaiveDFS{

	public static void main(String[] args) {
		
		// -- Data for problems --
		TurtleState initial = new TurtleState();
		initial.setTurtle(1,true);
		initial.setTurtle(2,true);
				
		TurtleState terminal = new TurtleState(true);
		
		// -- Problems --
		// Defining a problem as an anonymous Object this implemented abstract methods
		Problem<TurtleState> p = new Problem<TurtleState>(initial,"From state "+initial+" to state " + terminal) {
			
			@Override
			public boolean isTerminal(TurtleState s) {
					return (s.equals(terminal));
			}
		};
		

		ASearchAlgorithmStats<TurtleState,TurtleOperator> alg = new DepthFirstSearchNaiveStats<>();

		// This will loop since the state graph has cycle (=> StackOverflow)
		Solution<TurtleState,TurtleOperator> s = alg.solve(p);
		
		
	}
    

}
