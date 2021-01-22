package sets;

import java.util.Iterator;

/**
 * A LinkedList-based implementation of Set
 */

  /********************************************************
   * NOTE: Before you start, check the Set interface in
   * Set.java for detailed description of each method.
   *******************************************************/
  
  /********************************************************
   * NOTE: for this project you must use linked lists
   * implemented by yourself. You are NOT ALLOWED to use
   * Java arrays of any type, or any Collection-based class 
   * such as ArrayList, Vector etc. You will receive a 0
   * if you use any of them.
   *******************************************************/ 

  /********************************************************
   * NOTE: you are allowed to add new methods if necessary,
   * but do NOT add new files (as they will be ignored).
   *******************************************************/

public class LinkedSet<E> implements Set<E> 
{
	private LinkedNode<E> head = null;
	private int size;

  // Constructors
  	public LinkedSet() 
  	{
	  
  	}

  	public LinkedSet(E e) 
  	{
	  	size = 1;
	  	head = new LinkedNode<E>(e, null);
  	}

  	private LinkedSet(LinkedNode<E> head) 
  	{
  		LinkedNode <E> temp = head;
  		while (temp != null)
  		{
  			temp = temp.getNext();
  			size++;
  		}
  		this.head = head;
  	}

  	@Override
  	public int size() 
  	{
	  return size;
  	}

  	@Override
  	public boolean isEmpty() 
  	{
  		return head == null;
  	}

  	@Override
  	public Iterator<E> iterator() 
  	{
	  return new LinkedNodeIterator<E>(head);
  	}
  
  	@Override
  	public boolean contains(Object o)
  	{
	  for (E current : this)
	  {
		  if(current.equals(o))
		  {
			  return true;
		  }
	  }
	  return false;
  	}

  	@Override
  	public boolean isSubset(Set<E> that) 
  	{
	  for (E current : this)
	  {
		  if(!that.contains(current))
		  {
			  return false;
		  }
	  }
	  return true;
  	}

  @Override
  public boolean isSuperset(Set<E> that) 
  {
    for (E current : that)
    {
    	if(!this.contains(current))
    	{
    		return false;
    	}
    }
    return true;
  }

  @Override
  public Set<E> adjoin(E e) 
  {
	  LinkedNode<E> add = new LinkedNode<E>(e, head);
	  return new LinkedSet<E>(add);
  }

  @Override
  public Set<E> union(Set<E> that) 
  {
	  LinkedSet<E> temp = this;
	  for(E current: that)
	  {
		 if(!temp.contains(current))
		 {
			 temp = (LinkedSet<E>) temp.adjoin(current);
		 }
	  }
	  return temp;
  }

  @Override
  public Set<E> intersect(Set<E> that) 
  {
	  LinkedNode<E> temp = null;
	  for(E current: that)
	  {
		  if(this.contains(current)) 
		  {
			  temp = new LinkedNode<E>(current, temp);
		  }
	  }
	  return new LinkedSet<E>(temp);
  }

  @Override
  public Set<E> subtract(Set<E> that) 
  {
    LinkedNode<E> temp = null;
    for(E current: this)
    {
    	if(!that.contains(current))
    	{
    		temp = new LinkedNode<E>(current, temp);
    	}
    }
    return new LinkedSet<E>(temp);
  }

  @Override
  public Set<E> remove(E e) 
  {
    LinkedNode<E> temp = null;
    for(E current: this)
    {
    	if(!current.equals(e))
    	{
    		temp = new LinkedNode<E>(current, temp);
    	}
    }
    return new LinkedSet<E>(temp);
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object o) 
  {
    if (! (o instanceof Set)) 
    {
      return false;
    }
    Set<E> that = (Set<E>)o;
    return this.isSubset(that) && that.isSubset(this);
  }

  @Override
    public int hashCode() 
  {
    int result = 0;
    for (E e : this) 
    {
      result += e.hashCode();
    }
    return result;
  }
}
