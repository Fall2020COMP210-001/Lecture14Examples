package lec15;

public class Prioritized<V,P extends Comparable<P>> implements Comparable<Prioritized<?,P>> {

	private P _priority;
	private V _value;
	
	public Prioritized(V value, P priority) {
		_value = value;
		_priority = priority;
	}

	public P getPriority() {
		return _priority;
	}
	
	public V getValue() {
		return _value;
	}
	
	@Override
	public int compareTo(Prioritized<?, P> other) {
		return _priority.compareTo(other.getPriority());
	}
}
