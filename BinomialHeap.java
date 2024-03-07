/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap
{
	// circular, one direction.
	// TODO maintain rank


	public int size; // TODO Where is 'size' maintained?
	public HeapNode last;
	public HeapNode min;

	// Make Heap with one node
	// Given a node that defines a binomials heap, initializes a new heap
	// detaches the
	// pre: node != null
	BinomialHeap(HeapNode node)
	{
		this.size = 0;
		this.min = node;

		HeapNode curr = node;
		while (curr.next != null && curr.next != node)
		{
			curr.parent = null;
			this.size += (int)Math.pow(2, curr.rank); 
			if (curr.item.key < this.min.item.key)
				this.min = curr;
			curr = curr.next;
		}
		if (curr.item.key < this.min.item.key)
			this.min = curr;
		
		if (curr.next == null)
			curr.next = curr;
		this.last = curr;
	}

	BinomialHeap()
	{
		this.last = null;
		this.min = null;
		this.size = 0;
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 */
	public HeapItem insert(int key, String info) 
	{    
		HeapItem new_item = new HeapItem(key, info);
		this.meld(new BinomialHeap(new_item.node));
		return new_item;
	}

	private static HeapNode find_prev()

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin() 
	// TODO update size
	{
		// TODO case only tree - simple
		// TODO case many trees - cut from parent, close the top linked list cycle, meld.

		BinomialHeap children_heap = new BinomialHeap(this.min.child);
		this.meld(children_heap);
	}

	/**
	 * 
	 * Return the minimal HeapItem
	 *
	 */
	public HeapItem findMin() // OK
	{
		return this.min.item;
	} 

	// Assumes the heap is valid, makes this.min = minimun
	public void updateMin()
	{
		HeapNode curr = this.last.next;

		while (curr != this.last)
		{
			if (curr.item.key < this.min.item.key)
				this.min = curr;
		}
	}

	/**
	 * 
	 * pre: 0 < diff < item.key
	 * 
	 * Decrease the key of item by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapItem item, int diff) 
	{    
		item.key -= diff;

		HeapNode curr = item.node;
		while (curr.parent != null && curr.parent.item.key > curr.item.key)
		{
			swapNodeItems(curr, curr.parent);
			curr = curr.parent;
		}

		if (curr.parent == null) // maybe we need to update minimum in heap
		{
			this.updateMin();
		}
	}

	// swapping items
	// need to test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static void swapNodeItems(HeapNode n1, HeapNode n2)
	{
		HeapItem tmp = n1.item;
		n1.item = n2.item;
		n2.item = tmp;

		n1.item.node = n1;
		n2.item.node = n2;
	}

	/**
	 * 
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item) // OK
	{    
		this.decreaseKey(item, item.key + 1); // 'item.key = -1;' -  promises it will be min
		this.deleteMin();
	}


	private void link(); // TODO

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2) // TODO
	{
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return this.size; 
	}

	/**
	 * 
	 * The method returns true if and only if the heap
	 * is empty.
	 *   
	 */
	public boolean empty()
	{
		return this.size <= 0;
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees()
	{
		int cnt = 0;
		HeapNode curr = this.last;

		if (this.empty())
			return 0;

		do
		{
			curr = curr.next;
			cnt++;
		} while (curr != null);
		
		return cnt;
	}

	/**
	 * Class implementing a node in a Binomial Heap.
	 *  
	 */
	public class HeapNode{
		public HeapItem item;
		public HeapNode child;
		public HeapNode next;
		public HeapNode parent;
		public int rank;

		HeapNode(HeapItem item, HeapNode child, HeapNode next, HeapNode parent, int rank)
		{
			this.item = item;
			this.child = child;
			this.next = next;
			this.parent = parent;
			this.rank = rank;
		}

		HeapNode(HeapItem item)
		{
			this.item = item;
			this.child = null;
			this.next = this;
			this.parent = null;
			this.rank = 0;
		}
	}

	/**
	 * Class implementing an item in a Binomial Heap.
	 *  
	 */
	public class HeapItem{
		public HeapNode node;
		public int key;
		public String info;

		HeapItem(HeapNode node, int key, String info)
		{
			this.node = node;
			this.key = key;
			this.info = info;
		}

		HeapItem(int key, String info)
		{
			this.key = key;
			this.info = info;
			this.node = new HeapNode(this);
		}
	}

	// Delete later!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public static void main(String[] args)
	{
		System.out.println("Hello World");	



	}

}
