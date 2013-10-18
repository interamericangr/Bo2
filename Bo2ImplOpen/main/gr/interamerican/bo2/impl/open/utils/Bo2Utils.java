package gr.interamerican.bo2.impl.open.utils;

import gr.interamerican.bo2.arch.ext.CriteriaDependent;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.Defaults;
import gr.interamerican.bo2.utils.GenericsUtils;

/**
 * Utility class.
 */
public class Bo2Utils {
	
	/**
	 * Gets the criteria of a {@link CriteriaDependent} object,
	 * if it's not null, otherwise creates a new criteria object.
	 * 
	 * This method does not modify the CriteriaDependent argument. 
	 * 
	 * @param cd
	 *        CriteriaDependent to get it's criteria.
	 *        
	 * @return Returns the criteria of the CriteriaDependent argument.
	 *         If it is null, then creates a new valid criteria object
	 *         and returns it.
	 */
	@SuppressWarnings("unchecked")
	public static <C> C getCriteria(CriteriaDependent<C> cd) {
		C c = cd.getCriteria();
		if (c!=null) {
			return c;
		}
		Class<?> cdClass = cd.getClass();
		Class<?> cClass = GenericsUtils.findTypeParameter(cdClass, CriteriaDependent.class, Object.class);		
		c = (C) Defaults.getDefaultValue(cClass);
		if (c==null) {			
			c = (C)Factory.create(cClass);
		}
		return c;
	}

}
