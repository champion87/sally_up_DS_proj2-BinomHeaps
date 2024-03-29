/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap
{
	public int size; 
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
	// O(log n)
	public static BinomialHeap node_to_heap(HeapNode node)
	{
		BinomialHeap new_heap = new BinomialHeap();
		new_heap.size = 0;
		new_heap.min = node;

		HeapNode curr = node;

		if (node == null)
			return new_heap;

		do {
			curr.parent = null;
			new_heap.size += (int)Math.pow(2, curr.rank); 
			if (curr.item.key < new_heap.min.item.key) { new_heap.min = curr; }
			curr = curr.next;
		}
		while (curr.next != null && curr.next != node);

		if (curr.next != curr)
		{
			if (curr.item.key < new_heap.min.item.key)
				new_heap.min = curr;
			curr.parent = null;
			new_heap.size += (int)Math.pow(2, curr.rank);
		}

		new_heap.last = curr;
		new_heap.reverse();
		new_heap.updateMin();

		return new_heap;
	}

	// reverses the structure of the trees list
	// O(log n)
	private void reverse()
    {
		HeapNode head_ref = this.last.next;
        if (head_ref == null || this.last == head_ref)
            return;
 
        HeapNode prev = this.last;
        HeapNode current = head_ref;
        HeapNode next;
        do {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        } while (current != (head_ref));
 
        (head_ref).next = this.last;
		this.last = head_ref;
    }

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 * 
	 * O(log n)
	 */
	public HeapItem insert(int key, String info) 
	{    
		HeapItem new_item = new HeapItem(key, info);
		BinomialHeap new_heap = node_to_heap(new_item.node);
		this.meld(new_heap);
		return new_item;
	}

	// returns null if only one tree in heap
	// O(log n)
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
	 * O(log n)
	 */
	public void deleteMin() 
	{
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
		
		this.updateMin();
		this.meld(children_heap);
	}

	/**
	 * 
	 * Return the minimal HeapItem
	 *
	 */
	public HeapItem findMin()
	{
		return this.min.item;
	} 

	// Assumes the heap is valid, makes this.min = minimun
	// O(log n)
	public void updateMin()
	{
		HeapNode curr = this.last.next;
		this.min = curr;

		do
		{
			if (curr.item.key < min.item.key)
				this.min = curr;
			curr = curr.next;
		} while (curr != this.last.next);
	}

	/**
	 * 
	 * pre: 0 < diff < item.key
	 * 
	 * Decrease the key of item by diff and fix the heap. 
	 * O(log n)
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

		if (curr.parent == null)
		{
			this.updateMin();
		}
	}

	// swapping items
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
	 * O(log n)
	 */
	public void delete(HeapItem item)
	{    
		this.decreaseKey(item, item.key + 1); // 'item.key = -1;' -  promises it will be min
		this.deleteMin();
	}

	/*
	 * responsible for maintaining:
	 *  - rank
	 * assumes a,b are of the same rank
	 * 
	 * O(1) if we could avoid maintaining circular lists.
	 * we were'nt taught a better way to implement this (for example, doublly linked lists)
	 * so due to shortage in time we maintained the circular list using a for loop.
	 */
	private HeapNode link(HeapNode a, HeapNode b)
	{
		HeapNode bigger, smaller;
		if (a.item.key > b.item.key) { bigger = a; smaller = b; }
		else { bigger = b; smaller = a; }

		
		if (smaller.child == null)
		{
			bigger.next = bigger;
		}
		else
		{
			bigger.next = smaller.child;
		}
		
		smaller.child = bigger;
		bigger.parent = smaller;
		
		// maintaining circular list........ :(
		HeapNode last = smaller.child;
		for (int i = 0; i < smaller.rank; i++)
		{
			last = last.next;
		}
		last.next = bigger;
		
		smaller.rank++;
		
		return smaller;
	}
		
		/**
		 * 
	 * Meld the heap with heap2
	 * responsible for maintaining:
	 *  - size
	 *  
	 *	assumes the heaps are valid:
	 *  - minimum is valid
	 *  - size is valid
	 *  
	 * O(log n)
	 */
	public void meld(BinomialHeap heap2)
	{
		// edge cases
		if (heap2.empty()) { return; }
		else if (this.empty())
		{
			this.last = heap2.last;
			this.min = heap2.min;
			this.size = heap2.size;
			return;
		}

		// from now on, both lists have at least one element.

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

		int nodes_left1 = this.numTrees();
		int nodes_left2 = heap2.numTrees();

		boolean done1 = false;
		boolean done2 = false;

		// add until one of the lists is over
		while (!done1 && !done2)
		{
			if (is_carry)
			{
				if (curr_rank == curr1.rank && curr_rank == curr2.rank)
				{
					is_carry = true;
					nodes_left1--;
					nodes_left2--;

					if (is_first_node) { new_list_start = carry; head = carry; is_first_node = false; }
					else { head.next = carry; head = head.next; }

					tmp1 = curr1.next; tmp2 = curr2.next;
					carry = link(curr1, curr2);
					curr1 = tmp1; curr2 = tmp2;
				}
				else if (curr_rank == curr1.rank && curr_rank != curr2.rank)
				{
					is_carry = true;
					nodes_left1--;

					tmp1 = curr1.next;
					carry = link(curr1, carry);
					curr1 = tmp1;
				}
				else if (curr_rank != curr1.rank && curr_rank == curr2.rank)
				{
					is_carry = true;
					nodes_left2--;

					tmp2 = curr2.next;
					carry = link(curr2, carry);
					curr2 = tmp2;
				}
				else if (curr_rank != curr1.rank && curr_rank != curr2.rank)
				{
					is_carry = false;

					if (is_first_node) { new_list_start = carry; head = carry; is_first_node = false; }
					else { head.next = carry; head = head.next; }
				}
			}
			else
			{
				if (curr_rank == curr1.rank && curr_rank == curr2.rank)
				{
					is_carry = true;
					nodes_left1--;
					nodes_left2--;

					tmp1 = curr1.next;
					tmp2 = curr2.next;
					carry = link(curr2, curr1);
					curr1 = tmp1;
					curr2 = tmp2;
				}
				else if (curr_rank == curr1.rank && curr_rank != curr2.rank)
				{
					is_carry = false;
					nodes_left1--;

					if (is_first_node) { new_list_start = curr1; head = curr1; is_first_node = false; }
					else { head.next = curr1; head = head.next; }
					curr1 = curr1.next;
				}
				else if (curr_rank != curr1.rank && curr_rank == curr2.rank)
				{
					is_carry = false;
					nodes_left2--;

					if (is_first_node) { new_list_start = curr2; head = curr2; is_first_node = false; }
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

			done1 = (nodes_left1 == 0);
			done2 = (nodes_left2 == 0);
		}
		// exits when one of the lists is finished

		// one of the lists is over
		// the end list is maybe uninitialized
		// there might be carry

		HeapNode i = null;
		int to_go;
		HeapNode tmp = null;

		// if one of the lists isn't done
		if (!done1 || !done2) // continue on one list
		{
			// choosing what list to run on
			if (!done1) 		{ i = curr1; to_go = nodes_left1; }
			else 				{ i = curr2; to_go = nodes_left2; }


			while (to_go > 0 && is_carry)
			{
				if (i.rank == curr_rank) // carry goes on
				{
					is_carry = true;
					to_go--;

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

			while (to_go > 0)
			{
				to_go--;

				head.next = i;
				head = head.next; i = i.next;
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
	 * O(log n)
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
		} while (curr != this.last);
		
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

	public static void main(String[] args) {
		System.out.println("hello");
	}
}
