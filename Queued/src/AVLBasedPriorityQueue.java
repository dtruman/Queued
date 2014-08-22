
public class AVLBasedPriorityQueue<T extends Comparable<T>>
{
	Node root;
	private class Node
	{
		Node less, greater;
		T data;

		public Node(T data)
		{
			this.data=data;
		}
	}
	
	public boolean offer(T data)
	{	
		if(root==null)
		{
			root=new Node(data);
			return true;
		}
		
		return offerHelp(data, root);
	}
	
	private boolean offerHelp(T data, Node node)
	{
		if(node.data.compareTo(data)<0)
		{
			if(node.greater==null)
			{
				node.greater=new Node(data);
				checkForBalance(null, root);
				return true;
			}
			return offerHelp(data, node.greater);
		}
		else if(node.data.compareTo(data)>0)
		{
			if(node.less==null)
			{
				node.less=new Node(data);
				checkForBalance(null, root);
				return true;
			}
			return offerHelp(data, node.less);
		}
		return false;
	}
	
	public T peek()
	{
		if(root==null)
			return null;
		return peekHelp(root);
	}
	private T peekHelp(Node node)
	{
		if(node.less!=null)
			return peekHelp(node.less);
		
		if(node.less==null)
		{
			return node.data;
		}
		
		if(node.greater!=null)
			return peekHelp(node.greater);
		
		return null;
	}
	
	public T poll()
	{
		return inOrder(null, root);
	}
	
	private T inOrder(Node parent, Node node)
	{
		T ret=null;
		if(node.less!=null)
		{
			ret=inOrder(node, node.less);
			if(ret==node.less.data)
			{
				T temp=node.less.data;
				remove(node);
				checkForBalance(parent, node);
				return temp;
			}
		}
		
		if(node.less==null)
		{
			T temp=node.data;
			if(root.data==node.data)
				root=root.greater;
			return temp;
		}
		
		if(node.greater!=null && ret==null)
		{
			ret=inOrder(node, node.greater);
			if(ret==node.greater.data)
			{
				T temp=node.greater.data;
				remove(node);
				checkForBalance(parent, node);
				return temp;
			}
		}
		
		return ret;
	}
	private void remove(Node key)
	{
		if(key.less==null)
		{
			key.less=null;
			return ;
		}
		if(key.less.greater==null && key.less.less==null)
		{
			key.less=null;
			return ;
		}
		if(key.less.greater==null)
		{
			key.less=key.less.less;
			return;
		}
		if(key.less.less==null)
		{
			key.less=key.less.greater;
			return;
		}
		if(key.less!=null)
		{
			Node smallestKey=largest(key.less.less);
			key.less.data=smallestKey.data;
			key.less.less=key.less.greater;
			key.less.greater=null;
		}
		
		if(key.greater==null)
		{
			key.greater=null;
			return ;
		}
		if(key.greater.greater==null && key.greater.less==null)
		{
			key.greater=null;
			return ;
		}
		if(key.greater.greater==null)
		{
			key.greater=key.greater.less;
			return;
		}
		if(key.greater.less==null)
		{
			key.greater=key.greater.greater;
			return;
		}
		if(key.greater!=null)
		{
			Node smallestKey=largest(key.greater.less);
			key.greater.data=smallestKey.data;
			key.greater.less=key.greater.greater;
			key.greater.greater=null;
		}
	}
	private Node largest(Node node)
	{
		if(node.greater==null)
			return node;
		return largest(node.less);
	}
	
	private int depth(Node node)
	{
		if(node==null)
			return 0;
		return 1+Math.max(depth(node.less), depth(node.greater));
	}
	
	private void checkForBalance(Node parent, Node node)
	{
		if(node.less==null && node.greater==null)
		{
			return ;
		}
		if(node.less!=null)
		{
			checkForBalance(node, node.less);
		}
		
		balance(parent, node);
		
		if(node.greater!=null)
		{
			checkForBalance(node, node.greater);
		}
	}
	
	private void balance(Node parent, Node node)
	{
		switch(balanceNumber(node))
		{
		case 1:
			if(parent==null)
			{
				if(node.greater!=null && balanceNumber(node.greater)<0)
					node.greater=rotateRight(node.greater);
				root=rotateLeft(node);
			}
			if(parent!=null)
			{
				if(node.greater!=null && balanceNumber(node.greater)<0)
				{
					node.greater=rotateRight(node.greater);
				}
				if(parent.greater==node)
					parent.greater=rotateLeft(node);
				else if(parent.less==node)
					parent.less=rotateLeft(node);
			}
			break;
		case -1:
			if(parent==null)
			{
				if(node.less!=null && balanceNumber(node.less)>0)
					node.less=rotateLeft(node.less);
				root=rotateRight(node);
			}
			if(parent!=null)
			{
				if(node.less!=null && balanceNumber(node.less)>0)
				{
					node.less=rotateLeft(node.less);
				}
				if(parent.greater==node)
					parent.greater=rotateRight(node);
				else if(parent.less==node)
					parent.less=rotateRight(node);
			}
			break;
		default:
			break;
		}
	}
	
	public void inOrder()
	{
		inOrderHelp(root, 0);
	}
	private void inOrderHelp(Node root, int indent)
	{
		if(root!=null)
		{
			if(root.greater!=null)
			{
				inOrderHelp(root.greater, indent+4);
			}
			
			for(int i=0; i<indent; i++)
				System.out.print(" ");
			System.out.println(root.data);
			
			if(root.less!=null)
			{
				inOrderHelp(root.less, indent+4);
			}
		}
	}
	
	private int balanceNumber(Node node)
	{
		int L=depth(node.less);
		int R=depth(node.greater);
		if(L-R>=2)
			return -1;
		else if(L-R<=-2)
			return 1;
		else if(L-R==1)
			return -2;
		else if(L-R==-1)
			return 2;
		return 0;
	}
	
	private Node rotateLeft(Node node)
	{
		Node ret=node.greater;
		if(node.greater!=null)
			node.greater=ret.less;
		ret.less=node;
		return ret;
	}
	private Node rotateRight(Node node)
	{
		Node ret=node.less;
		if(node.less!=null)
			node.less=ret.greater;
		ret.greater=node;
		return ret;
	}
}
