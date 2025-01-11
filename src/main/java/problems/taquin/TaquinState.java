package problems.taquin;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import iialib.stateSpace.model.ApplicableOpsIterator;
import iialib.stateSpace.model.IState;

public class TaquinState implements IState<TaquinOperator>{

	public static int ORDER = 3;

	// Attributes

	// ---------------------- Attributes ----------------------
	/**
	 * tiles positions
	 * Tiles are numbered from 1 to ORDER^2-1 and their respective positions is represented 
	 * by a matrix where grid[i][j]=k iff the tile k is on line i /column i (except for
	 * grid[emptyX][emptyY], which denotes the empty position in the grid (whatever its value in the grid).
	 *  */
	int[][] grid;
	int emptyX;
	int emptyY;

	// ---------------------- Constructors ----------------------
	/**
	 * Retun
	 */
	TaquinState() {
		grid = new int[ORDER][ORDER];
		int c = 0;
		for (int i = 0; i < ORDER; i++)
			for (int j = 0; j < ORDER; i++) {
				grid[i][j] = c;
				c++;
			}
		emptyX = 0;
		emptyY = 0;
	}

	TaquinState(int[][] m, int eX, int eY) {
			if (m.length != ORDER)
				throw new RuntimeException("ERROR TaquinState(m,eX,eY) : matrix argument  is not of size " + ORDER);
			else {				
				this.grid = new int[ORDER][ORDER];
				for(int i= 0 ; i < ORDER; i++) {
					if (m[i].length != ORDER)
							throw new RuntimeException("ERROR TaquinState(m,eX,eY) : m is not a square matrix of size " + ORDER);
					else {
						for(int j= 0 ; j < ORDER; j++) 
							this.grid[i][j] = m[i][j];
					}
				}
				this.emptyX = eX;
				this.emptyY = eY;
			}
		}
	
	TaquinState(TaquinState s) {
		this(s.getGrid(),s.getEmptyX(),s.getEmptyY());
	}

	// ---------------------- Getters/Setters ----------------------

	public int[][] getGrid() {
		return grid;
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

	public int getEmptyX() {
		return emptyX;
	}

	public void setEmptyX(int emptyX) {
		this.emptyX = emptyX;
	}

	public int getEmptyY() {
		return emptyY;
	}

	public void setEmptyY(int emptyY) {
		this.emptyY = emptyY;
	}

	public void setOrder(int n) {
		ORDER = n;
	}
	
	public int getIJ(int i,int j) {
		if (i == emptyX && j == emptyY)
			return 0;
		return this.grid[i][j];
	}

	public void setIJ(int i,int j,int val) {
		this.grid[i][j] = val;
	}

	// ---------------------- Methods form IState ----------------------
	@Override
	public Iterator<TaquinOperator> applicableOperators() {
		 return new ApplicableOpsIterator<TaquinState,TaquinOperator>(TaquinOperator.ALL_OPS,this); 
	}

	@Override
	public String toString() {
		String sgrid = "[";
		for(int i = 0; i<ORDER;i++) {
			if (i > 0)
				sgrid = sgrid + "  "; 
			for(int j = 0; j<ORDER;j++) {
				sgrid = sgrid + grid[i][j];
				if (j < ORDER-1)
					sgrid = sgrid + " "; 
			}
			if (i < ORDER-1)
				sgrid = sgrid + "\n" ;
		}
		sgrid = sgrid + "]";
		return "("+ sgrid + "," + emptyX + "," + emptyY + ")";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaquinState other = (TaquinState) obj;
		if (emptyX != other.emptyX)
			return false;
		if (emptyY != other.emptyY)
			return false;
		if (!Arrays.deepEquals(grid, other.grid))
			return false;
		return true;
	}
	// ---------------------- HashCode for Collections  ----------------------

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + emptyX;
		result = prime * result + emptyY;
		result = prime * result + Arrays.deepHashCode(grid);
		return result;
	}


	// ---------------------- Methods for heuristics ----------------------
	/**
	 * return the number of tiles in this state that do not match their expected position in the goal state
	 * @param goal	the expected goal state
	 * @return
	 */
	public int nbOfUnmatchedTiles(TaquinState goal) {
		int count = 0;
		int eXGoal = goal.getEmptyX();
		int eYGoal = goal.getEmptyY();
		for(int i = 0;i<ORDER;i++)
			for(int j = 0;j<ORDER;i++) 
				if ((i != eXGoal || j != eYGoal) && goal.getIJ(i, j) != this.grid[i][j])
					count++;
		return count;
	}
	
	/**
	 * Compute the Manhattan distance of the state with respect to  a given goal state
	 * @param goal the goal state do be reached
	 * @return
	 */
	public int manhantanDistance(TaquinState goal) {
		int count = 0;
		// get the empty slot position in the goal
		int eXGoal = goal.getEmptyX(); 
		int eYGoal = goal.getEmptyY();
		Coord[] goalpos = goal.position();
		for(int i = 0;i<ORDER;i++)
			for(int j = 0;j<ORDER;i++) {
				int goalij = goal.getIJ(i,j);			
				if ((i != eXGoal || j != eYGoal) && this.grid[i][j] != goalij)
					count = count + Math.abs(i - goalpos[goalij].x)+ Math.abs(j - goalpos[goalij].y);
			}
		return count;
	}
	
	private Coord[] position() {
		Coord[] position = new Coord[ORDER*ORDER];
		for(int i = 0;i<ORDER;i++)
		  for(int j = 0;j<ORDER;i++) 
			if (i != emptyX || j != emptyY)
				position[grid[i][j]] = new Coord(i,j);
			else
				position[0] = new Coord(i,j);
		return position;
	}
	
		
	class Coord {
		int x;
		int y;
		
		Coord(int i, int j) {
			this.x = i;
			this.y = j;
		}
	}

		
	/**
	 * Generate a state obtained by a set of randomly chosen operators
	 * @param goal		The goal to be reached
	 * @param distance	The number of operators randomly applied
	 * @return the obtained state
	 */
	public TaquinState randomSuccessor(int distance) {
		TaquinState s = new TaquinState(this);
		Random rnd = new Random(System.currentTimeMillis());
		int i;
		while (distance>0) {
			i = rnd.nextInt(4); // 
			//System.out.println("DEBUG (randomState) i =  " + i);
			TaquinOperator op = TaquinOperator.ALL_OPS.get(i);
			//System.out.println("DEBUG (randomState) op =  " + op);
			while (!op.isApplicable(s)) {
				i = (i + 1) % 4;
				op = TaquinOperator.ALL_OPS.get(i);
				//System.out.println("DEBUG (randomState-loop) op =  " + op);
			}
			s = op.successor(s);	
			//System.out.println("DEBUG (randomState) successor =  \n" + s);
			distance--;
		}
		return s;
	}
	
		
}
