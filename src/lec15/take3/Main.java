package lec15.take3;

public class Main {

	public static void main(String[] args) {
		PriorityQueue<String, Integer> pq = new BinaryMinTree<String,Integer>();
		
		pq.enqueue("Apple", 3);
		pq.enqueue("Banana", -4);
		pq.enqueue("Carrot", 100);
		pq.enqueue("Date", 32);
		
		System.out.println(pq.dequeue());
		System.out.println(pq.dequeue());
		System.out.println(pq.dequeue());
		System.out.println(pq.dequeue());
	}
}
