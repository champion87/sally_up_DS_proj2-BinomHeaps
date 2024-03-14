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


	public int size; // maintained only in meld, deletemin
	public HeapNode last; // the tree of highst rank
	public HeapNode min;


	BinomialHeap()
	{
		this.last = null;
		this.min = null;
		this.size = 0;
	}


	// Make Heap with one node
	// Given a node that defines a binomials heap, initializes a new heap
	// detaches from parents if there are any
	// pre: node != null
	public static BinomialHeap node_to_heap(HeapNode node)
	{
		BinomialHeap new_heap = new BinomialHeap();
		new_heap.size = 0;
		new_heap.min = node;

		HeapNode curr = node;

		if (curr.next == curr) // only one tree
		{
			new_heap.size = 1;
		}

		while (curr.next != null && curr.next != node)
		{
			curr.parent = null;
			new_heap.size += (int)Math.pow(2, curr.rank); 
			if (curr.item.key < new_heap.min.item.key)
				new_heap.min = curr;
			curr = curr.next;
		}
		if (curr.item.key < new_heap.min.item.key)
			new_heap.min = curr;
		
		if (curr.next == null)
			curr.next = curr;
		new_heap.last = curr;
		return new_heap;
	}

	// reverses the structure of the trees list
	void reverse()
    {
		HeapNode head_ref = this.last.next;
        // if list is empty
        if (head_ref == null || this.last == head_ref)
            return;
 
        // reverse procedure same as reversing a
        // singly linked list
        HeapNode prev = this.last;
        HeapNode current = head_ref;
        HeapNode next;
        do {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        } while (current != (head_ref));
 
        // adjusting the links so as to make the
        // last node point to the first node
        (head_ref).next = this.last;
        //head_ref = prev;
        //this.last.next = this.last;
		this.last = head_ref;
    }

	void add(int k)
	{
		HeapNode n = new HeapNode(new HeapItem(k, null));
		n.next = this.last.next;
		this.last.next = n;
		this.last = n;
	}

	void p()
	{
		HeapNode c = this.last.next;
		do
		{
			System.out.println(c.item.key);
			c = c.next;
		} while (c != this.last.next);
	}


	public void print()
	{
		System.out.println(this.last == null);
		HeapNode curr = this.last.next;

		if (curr == curr.next)
		{
			System.out.println(curr.item.key);
			return;
		}

		while (curr != null && curr != this.last.next)
		{
			System.out.println(curr.item.key);
			curr = curr.next;
		}
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
		BinomialHeap new_heap = node_to_heap(new_item.node);
		this.meld(new_heap);
		return new_item;
	}

	// returns null if only one tree in heap
	private HeapNode find_prev()
	{
		HeapNode c = this.min.next;
		if (c == this.min)
			return null; 
		while (c.next != this.min)
			c = c.next;
		return c;
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin() 
	// TODO update size
	{
		// TODO case many trees - cut from parent, close the top linked list cycle, meld.

		BinomialHeap children_heap = node_to_heap(this.min.child);

		HeapNode prev = this.find_prev();
		if (prev == null) // only one tree
		{
			this.min = children_heap.min;
			this.last = children_heap.last;
			this.size = children_heap.size;
			return;
		}

		prev.next = this.min.next;
		this.size = this.size - 1 - children_heap.size;
		if (this.last == this.min)
		{
			this.last = prev;
		}
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

	/*
	 * responsible for maintaining:
	 *  - rank
	 *  - 
	 * assumes a,b are of the same rank
	 */
	private HeapNode link(HeapNode a, HeapNode b) // unchecked
	{
		HeapNode bigger, smaller;
		if (a.item.key > b.item.key) { bigger = a; smaller = b; }
		else { bigger = b; smaller = a; }

		smaller.rank++;
		bigger.next = smaller.child;
		smaller.child = bigger;
		bigger.parent = smaller;

		System.out.print("smaller: ");
		System.out.println(smaller);
		return smaller;
	}

	// private static HeapNode append(list_start, head) -> the new head

	// private static HeapNode 

	/**
	 * 
	 * Meld the heap with heap2
	 * responsible for maintaining:
	 *  - size
	 *  - 
	 *	assumes the heaps are valid:
	 *  - minimum is valid
	 *  - size is valid
	 *  - 
	 */
	public void meld(BinomialHeap heap2) // TODO
	{
		// edge cases - done
		if (heap2.empty()) { return; }
		else if (this.empty())
		{
			System.out.println("this.empty()");

			this.last = heap2.last;
			this.min = heap2.min;
			this.size = heap2.size;
			return;
		}

		this.size += heap2.size;
		if (this.min.item.key > heap2.min.item.key)
		{
			this.min = heap2.min;
		}

		HeapNode new_list_start = null;
		boolean is_first_node = true;
		HeapNode head = null;
		int curr_rank = 0;
		HeapNode tmp1 = null;
		HeapNode tmp2 = null;
		HeapNode curr1 = this.last.next;
		HeapNode curr2 = heap2.last.next;
		HeapNode carry = null;
		boolean is_carry = false;

		this.last.next = null;
		heap2.last.next = null;

		// add until one of the lists is over
		while (curr1 != null && curr2 != null)
		{
			if (is_carry)
			{
				if (curr_rank == curr1.rank && curr_rank == curr2.rank)
				{
					is_carry = true;

					if (is_first_node) { new_list_start = carry; head = carry; }
					else { head.next = carry; head = head.next; }

					tmp1 = curr1.next; tmp2 = curr2.next;
					carry = link(curr1, curr2);
					curr1 = tmp1; curr2 = tmp2;
				}
				else if (curr_rank == curr1.rank && curr_rank != curr2.rank)
				{
					is_carry = true;

					tmp1 = curr1.next;
					carry = link(curr1, carry);
					curr1 = tmp1;
				}
				else if (curr_rank != curr1.rank && curr_rank == curr2.rank)
				{
					is_carry = true;

					tmp2 = curr2.next;
					carry = link(curr2, carry);
					curr2 = tmp2;
				}
				else if (curr_rank != curr1.rank && curr_rank != curr2.rank)
				{
					is_carry = false;

					if (is_first_node) { new_list_start = carry; head = carry; }
					else { head.next = carry; head = head.next; }
				}
			}
			else
			{
				if (curr_rank == curr1.rank && curr_rank == curr2.rank)
				{
					is_carry = true;

					tmp1 = curr1.next;
					tmp2 = curr2.next;
					System.out.println("wow");
					carry = link(curr2, curr1);
					curr1 = tmp1;
					curr2 = tmp2;
				}
				else if (curr_rank == curr1.rank && curr_rank != curr2.rank)
				{
					is_carry = false;

					if (is_first_node) { new_list_start = curr1; head = curr1; }
					else { head.next = curr1; head = head.next; }
					curr1 = curr1.next;
				}
				else if (curr_rank != curr1.rank && curr_rank == curr2.rank)
				{
					is_carry = false;

					if (is_first_node) { new_list_start = curr2; head = curr2; }
					else { head.next = curr2; head = head.next; }
					curr2 = curr2.next;
				}
				else if (curr_rank != curr1.rank && curr_rank != curr2.rank)
				{
					is_carry = false;

					// nothing to do here!
				}
			}
			curr_rank++;
		}

		// one of the lists is over
		// the end list is maybe uninitialized
		// there might be carry
		//TODO carry and both null

		HeapNode i = null;
		HeapNode tmp = null;
		if (curr1 != null || curr2 != null) // continue on one list
		{
			// choosing what list to run on
			if (curr1 != null) 	{ i = curr1; }
			else 				{ i = curr2; }

			// continue adding
			while (i != null) 	// head should go all the way down.
			{
				if (is_carry)	
				{
					if (i.rank == curr_rank) // carry goes on
					{
						is_carry = true;

						tmp = i.next;
						carry = link(i, carry);
						i = tmp;

						curr_rank++;
					}
					else					// carry is pluged in, and we won't have another carry
					{
						is_carry = false;

						if (is_first_node) { new_list_start = carry; head = carry; }
						else { head.next = carry; head = head.next; }
					}
				}
				else // just concat 'i' to 'head'
				{
					is_carry = false;

					head.next = i;
					head = head.next; i = i.next;
				}
			}
		}
		
		// both lists are gone
		// there still might be carry
		if (is_carry)
		{
			if (is_first_node) { new_list_start = carry; head = carry; }
			else { head.next = carry; head = head.next; }
		}
		
		

		this.last = head;
		this.last.next = new_list_start;
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

		BinomialHeap b = new BinomialHeap();
		//b.insert(3, "3");

		System.out.println("done inserting first item");	
		//b.insert(10, "syhjk");

		System.out.println("done inserting 2 items");	


		BinomialHeap c = new BinomialHeap();
		c.insert(0, null);
		c.add(1);
		c.add(2);
		c.add(3);
		c.add(4);
		c.p();
		System.out.println();
		c.reverse();
		c.p();
		//b.print();


	}
}
