package problems.turtle;

import iialib.stateSpace.algs.ASearchAlgorithmStats;
import iialib.stateSpace.algs.Solution;
import iialib.stateSpace.algs.implementation.*;
import iialib.stateSpace.model.Problem;

public class RunTurtleOtherAlgs{

	public static void main(String[] args) {
		
		// -- Data for problems --
		TurtleState initial = new TurtleState();
		initial.setTurtle(1,true);
		initial.setTurtle(2,true);
				
		TurtleState terminal = new TurtleState(true);
		
		// -- A simple problem for testing --
		//
		Problem<TurtleState> p = Problem.defineProblem(initial,terminal);


		// -- Testing non informed search Algorithms --
		// ASearchAlgorithmStats<TurtleState,TurtleOperator> alg1 = new DepthFirstSearchCycleDetectStats<>();

		// Solution<TurtleState,TurtleOperator> s1 = alg1.solve(p);
		// System.out.println();
	   
		
		ASearchAlgorithmStats<TurtleState,TurtleOperator> alg2 = new BoundedDepthFirstSearchCycleDetectStats<>(12);

		Solution<TurtleState,TurtleOperator> s2= alg2.solve(p);
		System.out.println();

		
		ASearchAlgorithmStats<TurtleState,TurtleOperator> alg3 = new BoundedDepthFirstSearchCycleDetectStats<>(8);

		Solution<TurtleState,TurtleOperator> s3= alg3.solve(p);
		System.out.println();
	
		
		ASearchAlgorithmStats<TurtleState,TurtleOperator> alg4 = new BoundedDepthFirstSearchCycleDetectStats<>(3);

		Solution<TurtleState,TurtleOperator> s4= alg4.solve(p);
		System.out.println();


		ASearchAlgorithmStats<TurtleState,TurtleOperator> alg5 = new BreadthFirstSearchStats<>();

		Solution<TurtleState,TurtleOperator> s5= alg5.solve(p);
		System.out.println();

	}
    

}
