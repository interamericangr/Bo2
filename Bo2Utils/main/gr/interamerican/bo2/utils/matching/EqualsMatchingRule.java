package gr.interamerican.bo2.utils.matching;

/**
 * {@link MatchingRule} that checks if two elements are equal.
 * 
 * If both arguments are null, the rule will match them.
 *
 * @param <T>
 *        Type of objects being matched by the rule
 */
public class EqualsMatchingRule<T> implements MatchingRule<T, T> {

	@Override
	public boolean isMatch(T first, T second) {
		if (first==null) {
			return (second==null);
		}
		return first.equals(second);
	}


}
