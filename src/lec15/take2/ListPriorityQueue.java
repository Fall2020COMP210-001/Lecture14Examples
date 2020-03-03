package lec15.take2;

import java.util.List;
import java.util.ArrayList;

public class ListPriorityQueue<V> implements PriorityQueue<V> {

	private List<V> _values;
	private List<Integer> _priorities;
	
	public ListPriorityQueue() {
		_values = new ArrayList<V>();
		_priorities = new ArrayList<Integer>();
	}
	
	@Override
	public void enqueue(V value, int priority) {
		_values.add(value);
		_priorities.add(priority);
	}

	@Override
	public V dequeue() {
		if (size() == 0) {
			throw new UnsupportedOperationException();
		}
		
		int idx = find_min_idx();
		
		V result = _values.remove(idx);
		_priorities.remove(idx);
		
		return result;
	}

	@Override
	public V getMin() {
		if (size() == 0) {
			throw new UnsupportedOperationException();
		}
		
		int idx = find_min_idx();
		return _values.get(idx);
	}

	@Override
	public int size() {
		return _values.size();
	}


	private int find_min_idx() {
		if (size() == 0) {
			throw new UnsupportedOperationException();
		}
		int min_idx = 0;
		int min = _priorities.get(min_idx);

		for (int i=1; i<_priorities.size(); i++) {
			if (_priorities.get(i) < min) {
				min_idx = i;
				min = _priorities.get(i);
			}
		}
		
		return min_idx;
	}
}
