package problems.taquin;

import java.util.Arrays;
import java.util.List;
import iialib.stateSpace.model.IOperatorWithCost;

public enum TaquinOperator implements IOperatorWithCost<TaquinState>{
	
	UP,DOWN,LEFT,RIGHT;


	public static List<TaquinOperator> ALL_OPS = Arrays.asList(TaquinOperator.values());

	@Override
	public String getName() {
		switch(this) {
		case UP 	: return "UP";
		case DOWN 	: return "DOWN";
		case LEFT 	: return "LEFT";
		default 	: return "DEFAULT";
		}
	}

	@Override
	public boolean isApplicable(TaquinState s) {
		switch(this) {
		case UP 	: return s.getEmptyX() > 0;
		case DOWN 	: return s.getEmptyX() < TaquinState.ORDER-1;
		case LEFT 	: return s.getEmptyY() > 0;
		default  /*RIGHT*/	: return s.getEmptyY() < TaquinState.ORDER-1;
		}
	}

	@Override
	public TaquinState successor(TaquinState s) {
		int eX = s.getEmptyX();
		int eY = s.getEmptyY();
		int new_eX;
		int new_eY;
		TaquinState result = new TaquinState(s);
		switch(this) {
			case UP 	: new_eX = eX - 1; new_eY = eY; break;
			case DOWN 	: new_eX = eX + 1; new_eY = eY; break;	
			case LEFT 	: new_eX = eX ; new_eY = eY - 1; break;	
			default /*RIGHT*/ : new_eX = eX ; new_eY = eY + 1;
		}
		result.setIJ(eX,eY,s.getIJ(new_eX, new_eY));
		result.setEmptyX(new_eX);
		result.setEmptyY(new_eY);
		result.setIJ(new_eX, new_eY,0);	// Not strictly necessary but better for display
		return result;   
	}
	
	

	@Override
	public double getCost() {
		return 1;		
	}
	
}
