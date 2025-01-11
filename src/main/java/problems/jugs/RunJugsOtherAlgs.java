package problems.jugs;

import java.util.ArrayList;
import java.util.Iterator;

import iialib.stateSpace.algs.ASearchAlgorithmStats;
import iialib.stateSpace.algs.Solution;
import iialib.stateSpace.algs.implementation.*;
import iialib.stateSpace.model.Problem;

public class RunJugsOtherAlgs{

	public static void main(String[] args) {
		
		// -- Data for problems --
		JugsState initial = new JugsState(); // Empty jugs
		JugsState initial2 = new JugsState(0,5); // Empty jugs
		JugsState initial3 = new JugsState(5,0); // Empty jugs
		JugsState initial4 = new JugsState(5,7); // Empty jugs
		JugsState initial5 = new JugsState(0,2); // Empty jugs
		
		// -- Problem --
		// Defining a problem as an anonymous Object implementing abstract methods
		Problem<JugsState> p = new Problem<JugsState>(initial,"From state "+initial+" to any state with 4l in one jug") {
			
			@Override
			public boolean isTerminal(JugsState s) {
					return (s.getSmall() == 4 || s.getLarge() == 4 );
			}
		};
	/*	
		ArrayList<JugsState> s = new ArrayList<JugsState>();
		s.add(initial);
		s.add(initial2);
		s.add(initial3);
		s.add(initial4);
		s.add(initial5);
		for(JugsState state : s) {
			System.out.println("For the state " + state);
			Iterator<JugsOperator> it  = state.applicableOperators();
			while(it.hasNext()) {			
				JugsOperator op = it.next();
				System.out.println("Operator " + op + " successor " + op.successor(state));
			}
		}
*/


		// -- Testing non informed search Algorithms --
		ASearchAlgorithmStats<JugsState,JugsOperator> alg1 = new DepthFirstSearchCycleDetectStats<>();

		Solution<JugsState,JugsOperator> s1 = alg1.solve(p);
		System.out.println();
	   
		
		ASearchAlgorithmStats<JugsState,JugsOperator> alg2 = new BoundedDepthFirstSearchCycleDetectStats<>(12);

		Solution<JugsState,JugsOperator> s2= alg2.solve(p);
		System.out.println();

		ASearchAlgorithmStats<JugsState,JugsOperator> alg3 = new BoundedDepthFirstSearchCycleDetectStats<>(8);

		Solution<JugsState,JugsOperator> s3= alg3.solve(p);
		System.out.println();
	
		
		ASearchAlgorithmStats<JugsState,JugsOperator> alg4 = new BoundedDepthFirstSearchCycleDetectStats<>(3);

		Solution<JugsState,JugsOperator> s4= alg4.solve(p);
		System.out.println();


		ASearchAlgorithmStats<JugsState,JugsOperator> alg5 = new BreadthFirstSearchStats<>();

		Solution<JugsState,JugsOperator> s5= alg5.solve(p);
		System.out.println();

	}
    

}
