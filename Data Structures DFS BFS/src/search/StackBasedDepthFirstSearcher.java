package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * An implementation of a Searcher that performs an iterative search,
 * storing the list of next states in a Stack. This results in a
 * depth-first search.
 * 
 */
public class StackBasedDepthFirstSearcher<T> extends Searcher<T> 
{
	private ArrayList<T> predecessors;
	private ArrayList<T> states;
	private Stack<T> stack;
	public StackBasedDepthFirstSearcher(SearchProblem<T> searchProblem) 
	{
		super(searchProblem);
		predecessors = new ArrayList<T>();
		states = new ArrayList<T>();
		stack = new Stack<T>();
	}

	@Override
	public List<T> findSolution() 
	{
       if(solution != null)
       {
    	   return solution;
       }
       T initialState = searchProblem.getInitialState();
       stack.push(initialState);
       predecessors.add(null); // predecessor of initial state is null
       states.add(initialState);
       visited.add(initialState);
       T goalState = null;
       while(!stack.isEmpty())
       {
    	   T curr = stack.pop();
    	   if(searchProblem.isGoal(curr))
    	   {
    		   goalState = curr;
    		   break;
    	   }
    	   if(!visited.contains(curr))
    	   {
    		   visited.add(curr);
    	   }
    	   for(T neighbor : searchProblem.getSuccessors(curr))
    	   {
    		   if(!visited.contains(neighbor) && !states.contains(neighbor))
    		   {
    			   stack.push(neighbor);
    			   visited.add(neighbor);
    			   states.add(neighbor);
        		   predecessors.add(curr);
    		   }
    	   }
       }
       final List<T> path = new ArrayList<T>();
       if(goalState != null)
       {
    	   T temp = goalState;
    	   path.add(goalState);
    	   while(!temp.equals(searchProblem.getInitialState()))
    	   {
    		   T predecessor = predecessors.get(states.indexOf(temp));
    		   path.add(predecessor);
    		   temp = predecessor;
    	   }
    	   Collections.reverse(path);
       }
       if (path.size() > 0) 
       {
			if (!isValidSolution(path)) 
			{
				throw new RuntimeException(
						"searcher should never find an invalid solution!");
			}
		}
		return path;
   	}
}
