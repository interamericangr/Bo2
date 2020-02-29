package gr.interamerican.bo2.utils.conditions;

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Condition that is based on the value of a boolean property of the object
 * being examined.
 *
 * @param <T>
 *            the generic type
 * @deprecated Just use the Functional Interface for either {@link Condition} or
 *             {@link Transformation}
 */
@Deprecated
public class GetBooleanProperty<T>
extends gr.interamerican.bo2.utils.adapters.trans.GetProperty<T, Boolean>
implements Transformation<T, Boolean>, Condition<T> {

	/**
	 * Creates a new GetBooleanProperty object.
	 *
	 * @param propertyName
	 *            the property name
	 * @param clazz
	 *            the clazz
	 */
	public GetBooleanProperty(String propertyName, Class<T> clazz) {
		super(propertyName, clazz);
	}

	@Override
	public boolean check(T t) {
		Boolean b = execute(t);
		return Utils.notNull(b, false);
	}
}