package lec15.take2;

import java.util.List;
import java.util.ArrayList;

public class ListPriorityQueue<V> implements PriorityQueue<V> {

	private List<Prioritized<V>> _values;
	
	public ListPriorityQueue() {
		_values = new ArrayList<Prioritized<V>>();
	}
	
	@Override
	public void enqueue(V value, int priority) {
		_values.add(new PrioritizedImpl<V>(value, priority));
	}

	@Override
	public V dequeue() {
		if (size() == 0) {
			throw new UnsupportedOperationException();
		}
		
		int idx = find_min_idx();
		
		return _values.remove(idx).getValue();
	}

	@Override
	public V getMin() {
		if (size() == 0) {
			throw new UnsupportedOperationException();
		}
		
		int idx = find_min_idx();
		return _values.get(idx).getValue();
	}

	@Override
	public int size() {
		return _values.size();
	}


	private int find_min_idx() {
		if (size() == 0) {
			throw new UnsupportedOperationException();
		}
		Prioritized<V> min = _values.get(0);
		int min_idx = 0;
		
		for (int i=1; i<_values.size(); i++) {
			if (_values.get(i).getPriority() < min.getPriority()) {
				min = _values.get(i);
				min_idx = i;
			}
		}
		
		return min_idx;
	}
}
