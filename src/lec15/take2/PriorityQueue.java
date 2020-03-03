package lec15.take2;

public interface PriorityQueue<V> {
	void enqueue(V value, int priority);
	V dequeue();
	V getMin();
	int size();
}
