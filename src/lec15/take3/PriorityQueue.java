package lec15.take3;

public interface PriorityQueue<V,P extends Comparable<P>> {
	void enqueue(V value, P priority);
	V dequeue();
	V getMin();
	int size();
}
