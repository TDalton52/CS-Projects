package structures;

import java.util.NoSuchElementException;

/**************************************************************************************
 * NOTE: before starting to code, check support/structures/UnboundedQueueInterface.java
 * for detailed explanation of each interface method, including the parameters, return
 * values, assumptions, and requirements
 ***************************************************************************************/
public class Queue<T> implements UnboundedQueueInterface<T> 
{
	public Node<T> head;
	public Node<T> tail;
	private int size;
	
	public Queue() 
	{		
            head = null;
            tail = null;
    }
	
	public Queue(Queue<T> other)
	{
		for(int i = 0; i < other.size(); i++)
		{
			this.enqueue(other.peek());
			other.enqueue(other.dequeue());
		}
	}
	
	@Override
	public boolean isEmpty() 
	{
		return head == null;
	}

	@Override
	public int size() 
	{
         return size;
	}

	@Override
	public void enqueue(T element) 
	{
			size++;
            Node<T> next = new Node<T>(element, null);
            if(tail == null)
            {
            	head = next;
            }
            else
            {
            	tail.next = next;
            }
            tail = next;
	}

	@Override
	public T dequeue() throws NoSuchElementException 
	{
         if(isEmpty())
         {
        	 throw new NoSuchElementException();
         }
         size--;
         T data = head.data;
         head = head.next;
         if(head == null)
         {
        	 tail = null;
         }
         return data;
	}

	@Override
	public T peek() throws NoSuchElementException 
	{
		 if(isEmpty())
         {
        	 throw new NoSuchElementException();
         }
		 return head.data;
	}

	
	@Override
	public UnboundedQueueInterface<T> reversed() 
	{
		Queue<T> reversed = new Queue<T>(this);
		Node<T> current = reversed.head;
		Node<T> next = null;
		Node<T> prev = null;
		while(current != null)
		{
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		reversed.head = prev;
		return reversed;
	}
}

class Node<T> 
{
	public T data;
	public Node<T> next;
	public Node(T data) { this.data=data;}
	public Node(T data, Node<T> next) {
		this.data = data; this.next=next;
	}
}

