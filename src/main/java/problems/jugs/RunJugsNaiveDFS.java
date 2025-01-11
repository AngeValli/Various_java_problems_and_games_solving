package problems.jugs;

import iialib.stateSpace.algs.ASearchAlgorithmStats;
import iialib.stateSpace.algs.Solution;
import iialib.stateSpace.algs.implementation.DepthFirstSearchNaiveStats;
import iialib.stateSpace.model.Problem;


public class RunJugsNaiveDFS{

	public static void main(String[] args) {
		
		// -- Data for problems --
		JugsState initial = new JugsState(); // Empty jugs
		
		// -- Problems --
		// Defining a problem as an anonymous Object this implemented abstract methods
		Problem<JugsState> p = new Problem<JugsState>(initial,"From state "+initial+" to any state with 4l in one jug") {
			
			@Override
			public boolean isTerminal(JugsState s) {
					return (s.getSmall() == 4 || s.getLarge() == 4 );
			}
		};
		

		ASearchAlgorithmStats<JugsState,JugsOperator>  alg = new DepthFirstSearchNaiveStats<>();

		// This will loop since the state graph has cycle (=> StackOverflow)
		Solution<JugsState,JugsOperator> s = alg.solve(p);
		
		
		
	}
    

}
