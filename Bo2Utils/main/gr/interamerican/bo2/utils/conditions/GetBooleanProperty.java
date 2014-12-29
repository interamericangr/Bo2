package gr.interamerican.bo2.utils.conditions;

import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.trans.GetProperty;

/**
 * Condition that is based on the value of a boolean property of
 * the object being examined.
 *
 * @param <T>
 */
public class GetBooleanProperty<T> 
extends GetProperty<T, Boolean> 
implements Transformation<T, Boolean>, Condition<T> {

	/**
	 * Creates a new GetBooleanProperty object.
	 * 
	 * @param propertyName
	 * @param clazz
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
