package problems.taquin;

public class Run {
	
	
	
	
	public static void main(String[] args) {
		
		int[][] g = new int[][]{{1,2,3},{8,0,4},{7,6,5}};
		
		TaquinState s = new TaquinState(g,1,1);
		System.out.println("Initial grid :\n" + s);
		
		/*
		TaquinState sl = TaquinOperator.LEFT.successor(s);
		System.out.println("after LEFT:\n" + sl);
		TaquinState sr = TaquinOperator.RIGHT.successor(s);
		System.out.println("after RIGHT:\n" + sr);
		TaquinState su = TaquinOperator.UP.successor(s);
		System.out.println("after UP:\n" + su);
		TaquinState sd = TaquinOperator.DOWN.successor(s);
		System.out.println("after DOWN:\n" + sd);
		*/
		
		for (int i = 1; i<20 ; i++) {
			System.out.println("Iteration " + i + " : ");
			System.out.println(s.randomSuccessor(i));			
		}
		
	}
		

}