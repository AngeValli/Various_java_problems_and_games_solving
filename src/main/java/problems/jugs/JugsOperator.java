package problems.jugs;

import java.util.Arrays;
import java.util.List;
import iialib.stateSpace.model.IOperator;

public enum JugsOperator implements IOperator<JugsState> {
		EMPTY_S("Empty small"),
		EMPTY_L("Empty large"),
		FILL_S("Fill small"),
		FILL_L("Fill large"),
		POUR_STOL("Pour small into large"),
		POUR_LTOS("Pour large into small");
	
	  // ------------ List with all operators -------------------
    public static final List<JugsOperator> JUG_OPS =
    		Arrays.asList(JugsOperator.values());					// gather all operators into a Collection

    
    // ------------ Attributes -------------------
    String name;
    
    // ------------ Constructor -------------------
    JugsOperator (String name)   {
     	this.name = name;
    }
 
     // ------------ Methods from IOperator interface -------------------
    
	@Override
	public String getName() {
	   return name;
    }

	@Override
   public boolean isApplicable(JugsState s){
		boolean result = false;
        switch(this) {
   			case EMPTY_S : result = (s.getSmall() > 0); break;
			case EMPTY_L : result  = (s.getLarge() > 0); break;
    		case FILL_S : result  = (s.getSmall() < JugsState.SMALL_CAP); break;
    		case FILL_L : result  = (s.getLarge() < JugsState.LARGE_CAP); break;
			case POUR_STOL : result  = (s.getSmall() > 0 && s.getLarge() < JugsState.LARGE_CAP); break;
			default : result  = (s.getLarge() > 0 && s.getSmall() < JugsState.SMALL_CAP); break;
       }
        return result;
	}

	@Override
    public JugsState successor(JugsState state) {
		int s = state.getSmall();
		int l = state.getLarge();
		int S = JugsState.SMALL_CAP;
		int L = JugsState.LARGE_CAP;
		JugsState result;	
	    switch(this) {
	   			case EMPTY_S : result = new JugsState(0,l); break;
				case EMPTY_L : result = new JugsState(s,0); break;
	    		case FILL_S :  result = new JugsState(S,l); break;
	    		case FILL_L :  result = new JugsState(s,L); break;
				case POUR_STOL : result = new JugsState(Math.max(0,s+l-L),Math.min(L,s+l));  break;
				default : result = new JugsState(Math.min(S,s+l),Math.max(0,s+l-S)); 
	       }
	    return result;
    }

	@Override
    public String toString() {
    	return this.getName();
       }
	
	

}
