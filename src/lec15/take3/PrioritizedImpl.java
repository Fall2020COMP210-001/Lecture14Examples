package lec15.take3;

public class PrioritizedImpl<V,P extends Comparable<P>> implements Prioritized<V,P> {

	private V _value;
	private P _priority;
	
	public PrioritizedImpl(V value, P priority) {
		_value = value;
		_priority = priority;
	}
	
	@Override
	public V getValue() {
		return _value;
	}

	@Override
	public P getPriority() {
		return _priority;
	}

}
