package gr.interamerican.bo2.impl.open.hibernate.utils.reflect.analyze;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.hibernate.utils.reflect.beans.HibernateVariableDefinition;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.reflect.analyze.AbstractObjectStructureAnalyzer;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.collection.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;

/**
 * 
 */
public class HibernateAwarePoAnalyzer extends AbstractObjectStructureAnalyzer {
	
	/**
	 * Field names that are excluded from examination.
	 */
	static Set<String> excludedFieldNames = new HashSet<String>();
	static {
		excludedFieldNames.add("childFields"); //$NON-NLS-1$
		excludedFieldNames.add("nonChildFields"); //$NON-NLS-1$
	}
	
	/**
	 * Creates a new HibernateFieldsAnalyzer object. 
	 *
	 */
	public HibernateAwarePoAnalyzer() {
		leafConditions.add(new DoNotExploreFurtherHibernateCondition());
	}

	@Override
	protected List<VariableDefinition<?>> whichFieldsToInclude(Object object) {
		List<VariableDefinition<?>> list = new ArrayList<VariableDefinition<?>>();
		if (object==null) {
			return list;
		}
		List<Field> fieldsToInclude = getFieldsToInclude(object.getClass());
		
		for (Field field : fieldsToInclude) {
			field.setAccessible(true);
			Object value = ReflectionUtils.get(field, object);
			
			VariableDefinition<?> vd = HibernateVariableDefinition.createHibernateVd(value, field.getName(), field.getType());
			list.add(vd);
			
		}
		
		return list;
	}
	
	@Override
	protected VariableDefinition<?> createVariableDefinition(Object object, String fieldName, Class<?> fieldType) {
		VariableDefinition<?> vd = HibernateVariableDefinition.createHibernateVd(object, fieldName, fieldType);
		if(vd==null) {
			return super.createVariableDefinition(object, fieldName, fieldType);
		}
		return vd;
	}
	
	/**
	 * Include all fields up-to ModificationRecord and all Collection/Array fields
	 * for further inspection. Ignore static fields.
	 * 
	 * @param clazz
	 * @return fields to include.
	 */
	private List<Field> getFieldsToInclude(Class<?> clazz) {
		List<Field> fieldsToInclude = new ArrayList<Field>();
		List<Field> allFields = ReflectionUtils.allFields(clazz, PersistentObject.class);
		for (Field field : allFields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			if(excludedFieldNames.contains(field.getName())) {
				continue;
			}
			boolean isCollectionOrArray = ReflectionUtils.isArray(field.getType()) 
									   || ReflectionUtils.isCollection(field.getType());
			if(PersistentObject.class.isAssignableFrom(field.getType()) || isCollectionOrArray) {
				fieldsToInclude.add(field);
			}
		}
		return fieldsToInclude;
	}
	
	/**
	 * Best effort implementation.
	 */
	static class DoNotExploreFurtherHibernateCondition implements Condition<Object> {

		@Override
		public boolean check(Object t) {
			if(t==null) {
				return true;
			}
			if(t instanceof HibernateProxy) {
				HibernateProxy proxy = (HibernateProxy) t;
				if(proxy.getHibernateLazyInitializer().isUninitialized()) {
					return true;
				}
			}
			if(t instanceof PersistentCollection) {
				PersistentCollection coll = (PersistentCollection) t;
				if(!coll.wasInitialized()) {
					return true;
				}
			}
			if(t instanceof PersistentObject) {
				return false;
			}
			if(t instanceof Collection) {
				Collection<?> c = (Collection<?>) t;
				if(c.isEmpty()) { //if the collection is empty, stop at the collection.
					return true;
				}
				Object o = c.iterator().next();
				return !(o instanceof PersistentObject); //explore the collection if it contains POs.
			}
			if(t instanceof Object[]) {
				return true; //no PO arrays 
			}
			return true;
		}
		
	}

}
