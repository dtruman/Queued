
public class HeapBasedPriorityQueue<T extends Comparable<T>>
{
	T[] heap;
	int size;
	public HeapBasedPriorityQueue(int initialSize)
	{
		int powOfTwo=1;
		
		while(powOfTwo<initialSize)
		{
			powOfTwo=powOfTwo*2;
		}
		
		heap=(T[])(new Comparable[powOfTwo]);
		size=0;
		heap[0]=null;
	}
	
	public boolean offer(T data)
	{	
		if(size==heap.length)
			return false;
		
		heap[size+1]=data;
		size++;
		
		heapify(size);
		
		return true;
	}
	
	private void heapify(int index)
	{
		if(heap[index]==null)
			return;
		
		if(index*2<=size && !(heap[index*2]==null))
		{
			if(heap[index*2].compareTo(heap[index])<0 && ((index*2)+1>size || heap[index*2].compareTo(heap[(index*2)+1])<=0))
			{
				T temp=heap[index*2];
				heap[index*2]=heap[index];
				heap[index]=temp;
				heapify(index*2);
			}
			else if((index*2)+1<=size && heap[(index*2)+1].compareTo(heap[index])<0 && heap[index*2].compareTo(heap[(index*2)+1])>=0)
			{
				T temp=heap[(index*2)+1];
				heap[(index*2)+1]=heap[index];
				heap[index]=temp;
				heapify((index*2)+1);
			}
		}
		
		if(!(heap[index/2]==null))
		{
			if(heap[index/2].compareTo(heap[index])>0)
			{
				T temp=heap[index/2];
				heap[index/2]=heap[index];
				heap[index]=temp;
				heapify(index/2);
			}
		}
	}
	
	public T peek()
	{
		if(size<=0)
		{
			return null;
		}
		return heap[1];
	}
	
	public T poll()
	{
		if(size<=0)
		{
			return null;
		}
		T temp=heap[1];
		heap[1]=heap[size];
		heap[size]=temp;
		size--;
		
		heapify(1);
		
		return temp;
	}
}