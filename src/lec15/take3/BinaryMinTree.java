package lec15.take3;

public class BinaryMinTree<V, P extends Comparable<P>> implements PriorityQueue<V, P> {

	private Prioritized<V,P> _root_value;
	private BinaryMinTree<V,P> _left;
	private BinaryMinTree<V,P> _right;
	private boolean _is_empty;
	private int _height;
	
	public BinaryMinTree() {
		_is_empty = true;
		_root_value = null;
		_left = null;
		_right = null;
		_height = -1;
	}
	
	public BinaryMinTree(Prioritized<V,P> value) {
		_root_value = value;
		_left = new BinaryMinTree<V,P>();
		_right = new BinaryMinTree<V,P>();
		_is_empty = false;
		_height = 0;
	}
	
	@Override
	public void enqueue(V value, P priority) {
		Prioritized<V,P> pv = new PrioritizedImpl(value, priority);
		enqueuePrioritized(pv);		
	}
	
	@Override
	public V dequeue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public V getMin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
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
	}

	private int getHeight() {
		return _height;
	}
}
