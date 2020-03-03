package lec15;

public interface PriorityQueue<V,P> {
	V getMin();
	V dequeue();
	void enqueue(V value, P priority);
	int size();
}
