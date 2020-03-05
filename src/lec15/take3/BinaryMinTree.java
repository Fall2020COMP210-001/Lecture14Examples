package lec15.take3;

public class BinaryMinTree<V, P extends Comparable<P>> implements PriorityQueue<V, P> {

	private Prioritized<V,P> _root_value;
	private BinaryMinTree<V,P> _left;
	private BinaryMinTree<V,P> _right;
	private boolean _is_empty;
	private int _height;
	private int _size;
	
	public BinaryMinTree() {
		_is_empty = true;
		_root_value = null;
		_left = null;
		_right = null;
		_height = -1;
		_size = 0;
	}
	
	public BinaryMinTree(Prioritized<V,P> value) {
		_root_value = value;
		_left = new BinaryMinTree<V,P>();
		_right = new BinaryMinTree<V,P>();
		_is_empty = false;
		_height = 0;
		_size = 1;
	}
	
	@Override
	public void enqueue(V value, P priority) {
		Prioritized<V,P> pv = new PrioritizedImpl(value, priority);
		enqueuePrioritized(pv);		
	}
	
	@Override
	public V dequeue() {
		Prioritized<V,P> pv = dequeuePrioritized();
		return pv.getValue();
	}

	@Override
	public V getMin() {
		if (_is_empty) {
			throw new UnsupportedOperationException();
		}
		
		Prioritized<V,P> pv = getPrioritizedMin();
		return pv.getValue();
	}

	@Override
	public int size() {
		return _size;
	}
	
	private void enqueuePrioritized(Prioritized<V,P> value) {
		if (_is_empty) {
			_root_value = value;
			_left = new BinaryMinTree<V,P>();
			_right = new BinaryMinTree<V,P>();
			_is_empty = false;
			_height = 0;
		} else {
			if (value.getPriority().compareTo(_root_value.getPriority()) < 0) {
				Prioritized<V,P> tmp = value;
				value = _root_value;
				_root_value = tmp;
			}
			
			if (_left.getHeight() <= _right.getHeight()) {
				_left.enqueuePrioritized(value);
			} else {
				_right.enqueuePrioritized(value);
			}
			
			_height = Math.max(_left.getHeight(), _right.getHeight()) + 1;
		}
		_size++;
	}

	private Prioritized<V,P> dequeuePrioritized() {
	
		if (_is_empty) {
			throw new UnsupportedOperationException();
		}
		
		Prioritized<V,P> result = _root_value;
		
		if (size() == 1) {
			// Case 1. Become empty.
			_is_empty = true;
			_root_value = null;
			_left = null;
			_right = null;
			_height = -1;
			return result;
			
		} else if (_left.isEmpty()) {
			// Case 2, left empty. Take from right.
			_root_value = _right.dequeuePrioritized();
			
		} else if (_right.isEmpty()) {
			// Case 2, right empty. Take from left.
			_root_value = _left.dequeuePrioritized();
			
		} else {
			// Case 3, both children non-empty
			Prioritized<V,P> left_min = _left.getPrioritizedMin();
			Prioritized<V,P> right_min = _right.getPrioritizedMin();
			
			if (left_min.getPriority().compareTo(right_min.getPriority()) < 0) {
				_root_value = _left.dequeuePrioritized();
			} else {
				_root_value = _right.dequeuePrioritized();
			}
			
			if (_left.getHeight() != _right.getHeight()) {
				BinaryMinTree<V,P> taller = 
						(_left.getHeight() > _right.getHeight()) ? _left : _right;
				BinaryMinTree<V,P> shorter = 
						(_left.getHeight() < _right.getHeight()) ? _left : _right;
				
				while (taller.getHeight() - shorter.getHeight() > 1) {
					shorter.enqueuePrioritized(taller.dequeuePrioritized());
				}
			}								
		}  
		
		_height = Math.max(_left.getHeight(), _right.getHeight())+1;
		_size--;
		return result;
	}

	private Prioritized<V,P> getPrioritizedMin() {		
		if (_is_empty) {
			throw new UnsupportedOperationException();
		}

		return _root_value;
	}
	
	private int getHeight() {
		return _height;
	}
	
	private boolean isEmpty() {
		return _is_empty;
	}
}
