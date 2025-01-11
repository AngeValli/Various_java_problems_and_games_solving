package problems.turtle;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import iialib.stateSpace.model.IOperator;

public class TurtleOperator implements IOperator<TurtleState> {
	
	
    // ------------ List with all operators -------------------
    public static final List<TurtleOperator> TURTLE_OPERATORS =
    			IntStream.rangeClosed(0,TurtleState.N_TURTLES-2)		// create a stream of int from 0 to N_TURTLES-2
    				   .mapToObj(i -> new TurtleOperator(i))			// create an operator flip(i) of each of these int
    				   .collect(Collectors.toList());					// gather all operators into a List of Operators

    
    // ------------ Attributes -------------------
    private int 	leftMostFlipped;
    
    // ------------ Constructor -------------------
    TurtleOperator (int i)   {
     	if (i < TurtleState.N_TURTLES-1) 
    		 leftMostFlipped = i;
     	else
     		new RuntimeException("invalid operator : argument should be less than : "+(TurtleState.N_TURTLES-2));
    }
 
     // ------------ Methods from IOperator interface -------------------
    
	@Override
	public String getName() {
	   return "flip(" + leftMostFlipped + ")";
    }

	@Override
   public boolean isApplicable(TurtleState s){
        return true;
    }

	@Override
    public TurtleState successor(TurtleState s) {
		TurtleState successor = new TurtleState(s);
		successor.flip(leftMostFlipped);
		successor.flip(leftMostFlipped+1);
        return successor;
    }

	@Override
    public String toString() {
    	return this.getName();
       }

}
