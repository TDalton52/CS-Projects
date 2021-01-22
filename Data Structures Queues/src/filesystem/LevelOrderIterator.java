package filesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.NoSuchElementException;

import structures.Queue;
/**
 * An iterator to perform a level order traversal of part of a 
 * filesystem. A level-order traversal is equivalent to a breadth-
 * first search.
 */
public class LevelOrderIterator extends FileIterator<File> 
{
	private File rootNode;
	private Queue<File> queue;
	/**
	 * Instantiate a new LevelOrderIterator, rooted at the rootNode.
	 * @param rootNode
	 * @throws FileNotFoundException if the rootNode does not exist
	 */
	public LevelOrderIterator(File rootNode) throws FileNotFoundException 
	{
       	if(!rootNode.exists())
       	{
       		throw new FileNotFoundException();
       	}
       	this.rootNode = rootNode;
       	queue = new Queue<File>();
       	queue.enqueue(rootNode);
	}
	
	@Override
	public boolean hasNext() 
	{
        return !queue.isEmpty();
	}

	@Override
	public File next() throws NoSuchElementException 
	{
        if(!hasNext())
       	{
       		throw new NoSuchElementException();
       	}
       	File node = queue.dequeue();
       	if(node.isDirectory())
       	{
       		File[] children = node.listFiles();
       		for(int i = 0; i < children.length; i++)
       		{
       			Arrays.sort(children);
       			queue.enqueue(children[i]);
       		}
       	}
       	return node;
	}

	@Override
	public void remove() {
		// Leave this one alone.
		throw new UnsupportedOperationException();		
	}

}
