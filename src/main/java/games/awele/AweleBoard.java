package games.awele;

import iialib.games.model.IBoard;
import iialib.games.model.Score;
import iialib.games.model.Score.Status;

import java.util.ArrayList;

/** We represent the board of the game such as :
 *
 *    TOP PLAYER
 *  +---+---+---+---+---+---+
 *  |   |   |...|   |N+2|N+1|
 *  +---+---+---+---+---+---+
 *  +---+---+---+---+---+---+
 *  | 0 | 1 |   |...|   | N |
 *  +---+---+---+---+---+---+
 *    BOTTOM PLAYER
 *
 */

public class AweleBoard implements IBoard<AweleMove, AweleRole, AweleBoard> {

	// ---------------------- Attributes ---------------------
	// Attributes

	/* Number of squares by player (by default, 6) */
	private int n = 6;
	/* The board */
	private int[] board;
	/* The score of each player */
	private int scoreTop = 0;
	private int scoreBottom = 0;

	// ---------------------- Constructor ---------------------
	
	/** Default constructor */
	public AweleBoard() {
		init();
	}
	
	/** Constructor with the number of squares as parameter */
	public AweleBoard(int n) {
		this.n = n;
		init();
	}
	
	/** Initialisation */
	private void init() {
		board = new int[2*n];
		for(int i=0;i<2*n;i++)
			board[i] = 4;
	}
	
	/** Copy constructor */
	public AweleBoard(AweleBoard b) {
		n = b.n;
		board = new int[2*n];
		for(int i=0;i<2*n;i++)
			board[i] = b.board[i];
		this.scoreTop = b.scoreTop;
		this.scoreBottom = b.scoreBottom;
	}

	// --------------------- IBoard Methods ---------------------

	@Override
	public ArrayList<AweleMove> possibleMoves(AweleRole playerRole) {
		ArrayList<AweleMove> moves = new ArrayList<AweleMove>();
		/** One possible move (if valid) for each square of the player */
		for(int i=0;i<n;i++) {
			AweleMove m = new AweleMove(playerRole, i);
			if (isValidMove(m, playerRole))
				moves.add(m);
		}
		return moves;
	}

	@Override
	public AweleBoard play(AweleMove move, AweleRole playerRole) {
		/* Creating copy */
		AweleBoard result = new AweleBoard(this);
		/* Sowing */
		int start = (playerRole==AweleRole.BOTTOM)?move.getIndex():n+move.getIndex(); // Departure zone
		int residue = board[start]; // Positive value as the move is valid
		int i = 1; // current index
		int dest = -1; // target from start and current index, computed at each turn. We initialise at -1 as we need to first declare the 'dest' variable in Java, but its initial value is given within the loop below.
		while (residue>0) {
			dest = (start + i)%(2*n);
			if (dest!=start) {
				result.board[dest]++;
				residue--;
			}
			i++;
		}
		// Here, 'dest' is the square where we sowed the last stone
		result.board[start] = 0; // We empty the starting square
		
		/* We pick up the stones also if the current position is at an opponent's square, except when we collect all the stones */
		if ((playerRole==AweleRole.BOTTOM && dest<n) || (playerRole==AweleRole.TOP && dest>=n))
			return result;
		/* 1. Before picking up, we calculate how many squares we have to pick up and we do the sum */
		int sum = 0;
		int stop = dest>=n?n:0;
		i=0;
		while (dest-i>=stop && (result.board[dest-i]==2 || result.board[dest-i]==3)) { // while we can pick up
			sum += result.board[dest-i];
			i++;
		}
		/* 2. If this sum is equal to the amount of remaining stones of the opponent, we don't do anything */
		if (sum == result.somme(playerRole==AweleRole.BOTTOM?AweleRole.TOP:AweleRole.BOTTOM))
			return result;
		/* 3. Otherwise, we loop again to collect the stones */
		if (playerRole==AweleRole.TOP)
			result.scoreTop += sum;
		else
			result.scoreBottom += sum;
		i=0;
		while (dest-i>=stop && (result.board[dest-i]==2 || result.board[dest-i]==3)) {
			result.board[dest-i] = 0;
			i++;
		}
		return result;
	}
	
	
	@Override
	public boolean isValidMove(AweleMove move, AweleRole playerRole) {
		if (playerRole!=move.getRole()) /* We can't play for the other player */
			return false;
		int i = move.getIndex();
		int nb = (playerRole==AweleRole.BOTTOM)?board[i]:board[n+i]; // Number of stones in the square of the player
		if (nb==0) /* We can't play from an empty square */
			return false;
		if (somme(playerRole==AweleRole.BOTTOM?AweleRole.TOP:AweleRole.BOTTOM)==0 && nb<n-i) /* If an opponent does not have any stone, the move is valid only if we feed him */
			return false;
		return true;
	}

	@Override
	public boolean isGameOver() {
		/* If a player has 25 points */
		if (scoreTop>=25 || scoreBottom>=25)
			return true;
		/* The original rules, the game finishes by one of the following criteria :
		 * - The player which must play could not be fed and can't play anymore.
		 * - It is not possible to capture all the stones.
		 * Compared to the original rules, our stopping criteria is when there is less than 6 stones in the game.
		 */
		if (somme(null)<6)
			return true;
		if ((somme(AweleRole.BOTTOM)==0 && possibleMoves(AweleRole.TOP).size()==0) ||
				(somme(AweleRole.TOP)==0 && possibleMoves(AweleRole.BOTTOM).size()==0))
			return true;
		return false; 
	}

	@Override
	public ArrayList<Score<AweleRole>> getScores() {
		ArrayList<Score<AweleRole>> scores = new ArrayList<Score<AweleRole>>();
		if (!isGameOver())
			return scores;
		/* To start, we must collect the stones if we were in a situation where the game is blocked */
		if (somme(AweleRole.BOTTOM)==0 && possibleMoves(AweleRole.TOP).size()==0)
			scoreTop += somme(AweleRole.TOP);
		else if (somme(AweleRole.TOP)==0 && possibleMoves(AweleRole.BOTTOM).size()==0)
			scoreBottom += somme(AweleRole.BOTTOM);
		/* Then we send the scores */
		scores.add(new Score<AweleRole>(AweleRole.BOTTOM,scoreBottom>scoreTop?Status.WIN:scoreBottom<scoreTop?Status.LOOSE:Status.TIE,scoreBottom));
		scores.add(new Score<AweleRole>(AweleRole.TOP,scoreBottom>scoreTop?Status.LOOSE:scoreBottom<scoreTop?Status.WIN:Status.TIE,scoreTop));
		return scores;
	}

	// --------------------- Other Methods ---------------------
	
	/** Display the board */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		addLine(sb);
		for(int i=0;i<n;i++) {
			sb.append("| ");
			sb.append(board[2*n-1-i]);
			if (board[2*n-1-i]<10)
				sb.append(' ');
		}
		sb.append("|  --> ");
		sb.append(scoreTop);
		sb.append('\n');
		addLine(sb);
		addLine(sb);
		for(int i=0;i<n;i++) {
			sb.append("| ");
			sb.append(board[i]);
			if (board[i]<10)
				sb.append(' ');
		}
		sb.append("|  --> ");
		sb.append(scoreBottom);
		sb.append('\n');
		addLine(sb);
		return sb.toString();
	}

	/** Private method to avoid code repetition in toString */
	private void addLine(StringBuilder sb) {
		for(int i=0;i<n;i++)
			sb.append("+---");
		sb.append("+\n");		
	}
	
	/** Sum of the stones depending on the player's role (null to count all stones in the game) */
	public int somme(AweleRole r) {
		int s = 0;
		int debut,fin;
		if (r==null || r==AweleRole.BOTTOM)
			debut = 0;
		else
			debut = n;
		if (r==null || r==AweleRole.TOP)
			fin = 2*n;
		else
			fin = n;
		for(int i=debut ; i<fin; i++)
			s+=board[i];
		return s;
	}
	
	/** getter on the score of the bottom player */
	public int getscoreBottom() { return scoreBottom; }
	
	/** getter on the score of the top player */
	public int getscoreTop() { return scoreTop; }
}
