package gr.interamerican.bo2.utils.inspect;

import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * General purpose utility that inspects an object graph at the field level.
 * For every inspected object all fields of the object super class hierarchy
 * are considered. The user may narrow the type of fields considered by implementing
 * {@link #shouldInspectField(Field)}. The user can also decide when a field's value 
 * is included in the report by implementing {@link #shouldReportFieldValue(Object)}.
 * Array and collection objects are iterated to inspect their elements.
 */
public abstract class FieldInspector {
	
	/**
	 * Hashcodes of inspected objects.
	 */
	private Set<Integer> alreadyInspected = new HashSet<Integer>();
	
	/**
	 * Inspect the specified instance.
	 * @param obj
	 */
	public void inspect(Object obj) {
		alreadyInspected.clear();
		inspect0(obj);
	}
	
	/**
	 * 
	 * @param obj
	 */
	void inspect0(Object obj) {
		if(obj==null){
			return;
		}
		
		if(!alreadyInspected.add(System.identityHashCode(obj))) {
			return;
		}
		
		List<Field> fields = ReflectionUtils.allFields(obj.getClass(), Object.class);
		
		for(Field field : fields) {
			if(!shouldInspectField(field)) {
				continue;
			}
			
			field.setAccessible(true);
			
			Object fieldValue = ReflectionUtils.get(field, obj);
			
			if(fieldValue instanceof Iterable) {
				Iterable<?> it = (Iterable<?>) fieldValue;
				for(Object element : it) {
					if(shouldReportFieldValue(element)) {
						doReport(obj, field, element, true);
					}
					inspect0(element);
				}
			}
			
			if(fieldValue instanceof Object[]) {
				Object[] ar = (Object[]) fieldValue;
				for(Object element : ar) {
					if(shouldReportFieldValue(element)) {
						doReport(obj, field, element, true);
					}
					inspect0(element);
				}
			}
			
			if(shouldReportFieldValue(fieldValue)) {
				doReport(obj, field, fieldValue, false);
			}
			
			inspect0(fieldValue);
			
		}
	}
	
	/**
	 * Default reporting action.
	 * 
	 * @param inspected
	 * @param f
	 * @param fieldValue
	 * @param owned
	 */
	protected void doReport(Object inspected, Field f, Object fieldValue, boolean owned) {
		System.out.println(reportMessage(inspected, f, fieldValue, owned));
	}
	
	/**
	 * Default reporting message.
	 * 
	 * @param inspected
	 * @param f
	 * @param fieldValue
	 * @param owned
	 * @return reporting message.
	 */
	@SuppressWarnings("nls")
	protected String reportMessage(Object inspected, Field f, Object fieldValue, boolean owned) {
		String msg = StringUtils.concat(
			Modifier.toString(f.getModifiers()),
			StringConstants.SPACE,
			inspected.getClass().getSimpleName(),
			StringConstants.DOT,
			f.getName(),
			StringConstants.SPACE + StringConstants.COLON + StringConstants.SPACE,
			fieldValue==null ? "null" : fieldValue.getClass().getSimpleName());
		if(owned) {
			msg += " (owned array or collection element)";
		}
		return msg;
	}
	
	/**
	 * @param field
	 * @return Whether this field should be inspected
	 */
	protected abstract boolean shouldInspectField(Field field);
	
	/**
	 * @param fieldValue
	 * @return Whether this field value should be included in the report
	 */
	protected abstract boolean shouldReportFieldValue(Object fieldValue);
	
}
