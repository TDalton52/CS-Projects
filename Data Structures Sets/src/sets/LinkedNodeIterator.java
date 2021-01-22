package sets;

import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedNodeIterator<E> implements Iterator<E> 
{
   private LinkedNode<E> newNode;
  
  // Constructors
  public LinkedNodeIterator(LinkedNode<E> head) 
  {
      newNode = head;
  }

  @Override
  public boolean hasNext() 
  {
	  return newNode != null;
  }

  @Override
  public E next() 
  {
    if(!hasNext())
    {
    	throw new NoSuchElementException();
    }
    else
    {
    	E nextE = newNode.getData();
    	newNode = newNode.getNext();
    	return nextE;
    }
  }

  @Override
  public void remove() 
  {
    // Nothing to change for this method
    throw new UnsupportedOperationException();
  }
}
