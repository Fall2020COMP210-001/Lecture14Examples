package lec15.take3;

public interface Prioritized<V,P extends Comparable<P>> {
	V getValue();
	P getPriority();
}
