package puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import search.SearchProblem;
import search.Solver;

/**
 * A class to represent an instance of the eight-puzzle.
 * 
 * The spaces in an 8-puzzle are opened as follows:
 * 
 * 0 | 1 | 2
 * --+---+---
 * 3 | 4 | 5
 * --+---+---
 * 6 | 7 | 8
 * 
 * The puzzle contains the eight numbers 1-8, and an empty space.
 * If we represent the empty space as 0, then the puzzle is solved
 * when the values in the puzzle are as follows:
 * 
 * 1 | 2 | 3
 * --+---+---
 * 4 | 5 | 6
 * --+---+---
 * 7 | 8 | 0
 * 
 * That is, when the space at open 0 contains value 1, the space 
 * at open 1 contains value 2, and so on.
 * 
 * From any given state, you can swap the empty space with a space 
 * adjacent to it (that is, above, below, left, or right of it,
 * without wrapping around).
 * 
 * For example, if the empty space is at open 2, you may swap
 * it with the value at open 1 or 5, but not any other open.
 * 
 * Only half of all possible puzzle states are solvable! See:
 * https://en.wikipedia.org/wiki/15_puzzle
 * for details.
 * 

 * @author liberato
 *
 */
public class EightPuzzle implements SearchProblem<List<Integer>> {

	/**
	 * Creates a new instance of the 8 puzzle with the given starting values.
	 * 
	 * The values are opened as described above, and should contain exactly the
	 * nine integers from 0 to 8.
	 * 
	 * @param startingValues
	 *            the starting values, 0 -- 8
	 * @throws IllegalArgumentException
	 *             if startingValues is invalid
	 */
	List<Integer> startingValues;
	List<Integer> solved = Arrays.asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 0});
	public EightPuzzle(List<Integer> startingValues) 
	{
		if(startingValues.size() != 9)
		{
			throw new IllegalArgumentException();
		}
		for(int i = 0; i <= 8; i++)
		{
			if(!startingValues.contains(i))
			{
				throw new IllegalArgumentException();
			}
		}
		this.startingValues = startingValues;
	}

	@Override
	public List<Integer> getInitialState() 
	{
		return startingValues;
	}

	@Override
	public List<List<Integer>> getSuccessors(List<Integer> currentState) 
	{
		List<List<Integer>> successors = new ArrayList<List<Integer>>();
		int open = currentState.indexOf(0);
		if(open==2 || open==5 || open==8 || open==1 || open==4 || open==7)
		{
			List<Integer> minus1 = new ArrayList<Integer>(currentState);
			minus1.set(open, currentState.get(open-1));
			minus1.set(open-1,0);
			successors.add(minus1);
		}
		
		if(open==0 || open==3 || open==6 || open==1 || open==4 || open==7)
		{
			List<Integer> plus1 = new ArrayList<Integer>(currentState);
			plus1.set(open, currentState.get(open+1));
			plus1.set(open+1,0);
			successors.add(plus1);
		}
		
		if(open==0 || open==1 || open==2 || open==3 || open==4 || open==5)
		{
			List<Integer> plus3 = new ArrayList<Integer>(currentState);
			plus3.set(open, currentState.get(open+3));
			plus3.set(open+3,0);
			successors.add(plus3);
		}
		
		if(open==6 || open==7 || open==8 || open==3 || open==4 || open==5)
		{
			List<Integer> minus3 = new ArrayList<Integer>(currentState);
			minus3.set(open, currentState.get(open-3));
			minus3.set(open-3,0);
			successors.add(minus3);
		}
		return successors;
	}


	@Override
	public boolean isGoal(List<Integer> state) 
	{
		return state.equals(solved);
	}

	public static void main(String[] args) {
		EightPuzzle e = new EightPuzzle(Arrays.asList(new Integer[] { 1, 2, 3,
				4, 0, 6, 7, 5, 8 }));

		List<List<Integer>> r = new Solver<List<Integer>>(e).solveWithBFS();
		for (List<Integer> l : r) {
			System.out.println(l);
		}
	}
}
