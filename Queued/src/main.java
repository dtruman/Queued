
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		int c=30;
		int b=35;
		int a=40;
		int d=50;
		int e=45;
		int f=60;
		int g=47;
		int h=46;
		int i=49;
		int j=48;
		
		AVLBasedPriorityQueue BPQ=new AVLBasedPriorityQueue();
		BPQ.offer(c);
		BPQ.offer(b);
		BPQ.offer(a);
		BPQ.offer(d);
		BPQ.offer(e);
		BPQ.offer(f);
		BPQ.offer(g);
		BPQ.offer(h);
		BPQ.offer(i);
		BPQ.offer(j);
		BPQ.inOrder();
		System.out.println("BREAK");
				
		while(BPQ.peek()!=null)
		{
			System.out.println(BPQ.poll());
			System.out.println("PRINT");
			BPQ.inOrder();
			System.out.println("BREAK");
		}
	}

}
