package problems.jugs;

import iialib.stateSpace.model.ApplicableOpsIterator;
import iialib.stateSpace.model.IState;

import java.util.Iterator;

public class JugsState implements IState<JugsOperator> {

	public static int SMALL_CAP = 5;
	public static int LARGE_CAP = 7;
	
	
	/**
	 * content of the small jug
	 */
	private int small;
	/**
	 * content of the large jug
	 */
	private int large;

	// ------------ Constructors -------------------

	public JugsState() {
		super();	// By default  (0,0)
	}

	public JugsState(int small,int large) {
		this.small = small;
		this.large = large;
		}
	
	public JugsState(JugsState js) {
		this.small = js.small;
		this.large = js.large;
		}

	// ------------ getter / setters-------------------

	protected int getSmall() {
		return small;
	}

	protected int getLarge() {
		return large;
	}


	public void setLarge(int large) {
		this.large = large;
	}

	public void setSmall(int small) {
		this.small = small;
	}


	// ------------ IState interface methods -----------------

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JugsState) {
			JugsState jugs = (JugsState) obj;
			return (this.small == jugs.small) && (this.large == jugs.large);
		}
		return false;
	}

	@Override
	public String toString() {
		return  "(" + small + "," + large + ")";
	}
	

	@Override 
	 public Iterator<JugsOperator> applicableOperators() {
		 // For this problem domain all operators are always applicable 
		 // we simply get the standard iterator of the List of all operators 
		 return new ApplicableOpsIterator<JugsState,JugsOperator>(JugsOperator.JUG_OPS,this); 
	}

	
	@Override
	public int hashCode() {
		return LARGE_CAP*large+ small;
	}


}
