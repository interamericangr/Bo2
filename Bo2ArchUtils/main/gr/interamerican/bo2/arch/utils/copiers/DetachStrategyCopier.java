package gr.interamerican.bo2.arch.utils.copiers;

import java.util.Arrays;

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.ext.Copier;
import gr.interamerican.bo2.utils.ReflectionUtils;

/**
 * Copier for {@link DetachStrategy}.
 */
public class DetachStrategyCopier implements Copier<DetachStrategy> {
	
	/**
	 * DetachStrategy implementations that are Singletons.
	 */
	static final String[] SINGLETON_DETACH_STRATEGY_NAMES = new String[]{
		"gr.interamerican.bo2.impl.open.hibernate.HibernateReadOnlyDetachStrategy", //$NON-NLS-1$
		"gr.interamerican.bo2.impl.open.jdbc.JdbcDetachStrategy"}; //$NON-NLS-1$

	public DetachStrategy copy(DetachStrategy objectToCopy) {
		if(objectToCopy==null) {
			return null;
		}
		String name = objectToCopy.getClass().getName();
		if(Arrays.asList(SINGLETON_DETACH_STRATEGY_NAMES).contains(name)) {
			return objectToCopy;
		}
		return ReflectionUtils.newInstance(objectToCopy.getClass());
	}

}
