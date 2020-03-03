package lec15;

// Invariants:
// Element at root of tree is smaller than all elements in either
// left or right tree.

public class BinaryMinTree<V,P extends Comparable<P>> implements PriorityQueue<V,P> {

	private Prioritized<V,P> _root_val;
	private BinaryMinTree<V,P> _left;
	private BinaryMinTree<V,P> _right;
	private int _height;
	private int _size;
		
	// Makes an empty tree
	public BinaryMinTree() {
		_root_val = null;
		_left = null;
		_right = null;
		_height = -1;
		_size = 0;
	}

	// Makes a non-empty tree. Technically a leaf.
	public BinaryMinTree(V value, P priority) {
		_root_val = new Prioritized<V,P>(value, priority);
		
		// Empty trees as children make this a leaf.
		_left = new BinaryMinTree<V,P>();
		_right = new BinaryMinTree<V,P>();
		_height = 0;
		_size = 1;
	}

	// Implementation of PriorityQueue<V,P>

	@Override
	public int size() {
		return _size;
	}
	
	@Override
	public V getMin() {
		if (isEmpty()) {
			// Undefined on empty priority queue
			throw new UnsupportedOperationException();
		}
		
		return _root_val.getValue();		
	}

	@Override
	public V dequeue() {
		// Delegate to private implementation
		// of prioritized values and return
	    // original value without priority attached.
		
		return dequeuePrioritized().getValue();
	}
	
	@Override
	public void enqueue(V value, P priority) {
		// Delegate to private implementation
		// on prioritized value, glueing value and
		// priority together.
		
		enqueuePrioritized(new Prioritized<V,P>(value, priority));
	}

	// Private implementation of dequeue on prioritized values.
	
	private Prioritized<V,P> dequeuePrioritized() {
		if (isEmpty()) {
			// Shouldn't be dequeueing an empty priority queue
			throw new UnsupportedOperationException();
		}

		// Smallest is at the top.
		Prioritized<V,P> result = _root_val;
		
		// Adjust size to what it will be after we remove
		// the root_val so we know whether we are empty or not.
		_size--;
		
		if (isEmpty()) {
			// We're empty, so set up other fields to reflect that
			_root_val = null;
			_left = null;
			_right = null;
			_height = -1;
		} else {
			// We aren't empty, so promote the minimum element
			// from either left or right to live here.
			
			if (_left.isEmpty()) {
				// Right can't be empty also because if it were, we would
				// have been a leaf and would be empty now and we know we aren't
				_root_val = _right.dequeuePrioritized();
			} else if (_right.isEmpty()) {
				// Left can't be empty also for same reason as above.
				_root_val = _left.dequeuePrioritized();
			} else {
				// Both left and right are not empty. Dequeue the min from both,
				// store the smaller here and reinsert the other one in which
				// ever tree is shorter (or left if a tie).
				Prioritized<V,P> from_left = _left.dequeuePrioritized();
				Prioritized<V,P> from_right = _right.dequeuePrioritized();
				Prioritized<V,P> to_reinsert = null;
				if (from_left.compareTo(from_right) < 0) {
					_root_val = from_left;
					to_reinsert = from_right;
				} else {
					_root_val = from_right;
					to_reinsert = from_left;
				}
			
				if (_left.getHeight() <= _right.getHeight()) {
					_left.enqueuePrioritized(to_reinsert);
				} else {
					_right.enqueuePrioritized(to_reinsert);
				}
			}
			// Size was already adjusted. Recompute height here.
			_height = Math.max(_left.getHeight(), _right.getHeight()) + 1;
		}
		return result;
	}

	private void enqueuePrioritized(Prioritized<V,P> value) {
		// If we are empty, store here and set up as a leaf.
		if (isEmpty()) {
			_root_val = value;
			_left = new BinaryMinTree<V,P>();
			_right = new BinaryMinTree<V,P>();
			_height = 0;
			_size = 1;
		} else {
			// What we are enqueuing goes here if it is smaller
			// than the root value. If this is the case,
			// switch values so that we enqueue what used to be the
			// root value in either the left or right.
			if (value.compareTo(_root_val) < 0) {
				Prioritized<V,P> tmp = _root_val;
				_root_val = value;
				value = tmp;
			}

			// value is now smaller of either original value or original root.
			// Recursively enqueue in either left or right. Prefer choice that
			// is most balanced, left if a tie.
			
			if (_left.getHeight() <= _right.getHeight()) {
				_left.enqueuePrioritized(value);
			} else {
				_right.enqueuePrioritized(value);
			}			
		}
		// Update properties of tree
		_height = Math.max(_left.getHeight(), _right.getHeight()) + 1;
		_size++;
	}

	private boolean isEmpty() {
		return (_size == 0);
	}
		
	private int getHeight() {
		return _height;
	}

}
