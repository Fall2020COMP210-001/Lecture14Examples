package lec15;

import java.util.ArrayList;
import java.util.List;

public class BinaryHeap<V,P extends Comparable<P>> implements PriorityQueue<V,P> {

	private List<Prioritized<V,P>> _elements;
	
	public BinaryHeap() {
		_elements = new ArrayList<Prioritized<V,P>>();
	}

	@Override
	public V dequeue() {
		// Can't take the minimum item from an empty heap.
		if (size() == 0) {
			throw new UnsupportedOperationException();
		}

		V result = _elements.get(0).getValue();

		if (_elements.size() == 1) {
			// Just took the last element from the list.
			// Remove it from list and return. Heap is now
			// empty.
			_elements.remove(0);
		} else {
			
			// Move the last element to the top of the heap
			Prioritized<V,P> last = _elements.remove(_elements.size()-1);
			_elements.set(0, last);
			
			// Bubble down until it falls into place.
		
			int cur_idx = 0;
			int left_idx = cur_idx*2+1;

			// As long as we have at least a valid left element,
			// keep bubbling down. We can stop once left is invalid
			// because that means we are now a leaf.
			while (left_idx < _elements.size()) {
				Prioritized<V,P> cur_element = _elements.get(cur_idx);
				Prioritized<V,P> left_element = _elements.get(left_idx);

				int right_idx = cur_idx*2+2;
			
				if (right_idx < _elements.size()) {
					// We also have a right element
					
					Prioritized<V,P> right_element = _elements.get(right_idx);

					// See if we are smaller than both left and right.
					if (cur_element.compareTo(left_element) < 0 &&
						cur_element.compareTo(right_element) < 0) {
						// We have found our proper spot in the heap.
						break;
					}
					
					// At least either left or right is smaller
					// than the current element. Figure out which is smallest,
					// swap elements, and keep bubbling down.
				
					if (left_element.compareTo(right_element) < 0) {
						_elements.set(left_idx, cur_element);
						_elements.set(cur_idx, left_element);
						cur_idx = left_idx;
					} else {
						_elements.set(right_idx, cur_element);
						_elements.set(cur_idx, right_element);
						cur_idx = right_idx;					
					}
				} else {
					// We only have a left element. See if we are at
					// the right spot and break if so. Otherwise,
					// swap and bubble down.
				
					if (cur_element.compareTo(left_element) < 0) {
						break;
					}
					
					_elements.set(left_idx, cur_element);
					_elements.set(cur_idx, left_element);
					cur_idx = left_idx;
				}
				
				// Recompute left index from updated current index
				left_idx = cur_idx*2+1;
			}
		}
		return result;
	}

	@Override
	public void enqueue(V value, P priority) {
		// Add to end of list and bubble up.
		_elements.add(new Prioritized<V,P>(value, priority));
		int cur_idx = _elements.size()-1;

		// Bubble until we are at the top of the heap.
		while (cur_idx > 0) {
			Prioritized<V,P> cur_element = _elements.get(cur_idx);
			int parent_idx = (cur_idx - 1) / 2;
			Prioritized<V,P> parent_element = _elements.get(parent_idx);
			if (parent_element.compareTo(cur_element) <= 0) {
				// Found our spot. We're good.
				break;
			}
			// Parent is larger than we are, swap and continue
			// from parent's index.
			
			_elements.set(cur_idx, parent_element);
			_elements.set(parent_idx, cur_element);
			cur_idx = parent_idx;
		}
	}

	@Override
	public int size() {
		return _elements.size();
	}

	@Override
	public V getMin() {
		if (size() == 0) {
			// Undefined for empty priority queue
			throw new UnsupportedOperationException();
		}
		return _elements.get(0).getValue();
	}
}
