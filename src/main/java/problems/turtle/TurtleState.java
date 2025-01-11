package problems.turtle;

import iialib.stateSpace.model.IState;

import java.util.Arrays;
import java.util.Iterator;

public class TurtleState implements IState<TurtleOperator> {

	/** problem size (number of turtles) */
	public static int N_TURTLES = 6;

	/**
	 * turtle positions 1 = on their feets 0 = on their back
	 */
	private boolean[] turtles;

	// ------------ Constructors -------------------

	public TurtleState() {
		turtles = new boolean[N_TURTLES];
	}

	public TurtleState(boolean[] tab) {
		turtles = tab;
	}

	public TurtleState(boolean b) {
		turtles = new boolean[N_TURTLES];
		for (int i = 0; i < N_TURTLES; i++)
			turtles[i] = b;
	}

	public TurtleState(TurtleState s) {
		this();
		System.arraycopy(s.turtles, 0, this.turtles, 0, N_TURTLES);
	}

	// ------------ getter / setters-------------------
	protected boolean[] getTurtles() {
		return turtles;
	}

	protected boolean getTurtle(int i) {
		return turtles[i];
	}

	protected void setTurtle(int i, boolean b) {
		turtles[i] = b;
	}
	
	protected void flip(int i) {
		turtles[i] = ! turtles[i];
	}


	// ------------ IState interface methods -----------------

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TurtleState) {
			TurtleState et = (TurtleState) obj;
			return Arrays.equals(et.getTurtles(), turtles);
		}
		return false;
	}

	@Override
	public String toString() {
		String s = "(";
		for (int i = 0; i < N_TURTLES - 1; i++) {
			if (turtles[i])
				s = s + "1 ";
			else
				s = s + "0 ";

		}
		if (turtles[N_TURTLES - 1])
			s = s + "1";
		else
			s = s + "0";
		s = s + ")";
		return s;
	}

	@Override
	public Iterator<TurtleOperator> applicableOperators() {
		// For this problem domain all operators are always applicable
		// we simply get the standard iterator of the List of all operators
		return TurtleOperator.TURTLE_OPERATORS.iterator();
	}

}
