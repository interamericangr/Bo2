/**
 * 
 */
package gr.interamerican.bo2.impl.open.hibernate.utils;

import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;


/**
 * The Class TestBo2DeploymentHibernateInfoUtility.
 */
public class TestBo2DeploymentHibernateInfoUtility {

	/** The subject. */
	Bo2DeploymentHibernateInfoUtility subject = Bo2DeploymentHibernateInfoUtility.get();

	/**
	 * test method for {@link Bo2DeploymentHibernateInfoUtility#getHibernateClassesFromFlatFile(String)}.
	 */
	@Test
	public void testGetHibernateClassesFromFlatFile() {
		Set<Class<?>> clazzes = subject.getHibernateClassesFromFlatFile("/gr/interamerican/rsrc/hbmsutility.txt"); //$NON-NLS-1$
		Assert.assertTrue(clazzes.size() > 0);
	}

	/**
	 * test method for {@link Bo2DeploymentHibernateInfoUtility#getHibernateClassesFromFlatFile(String)}.
	 */
	@Test
	public void testGetHibernateClassesFromCfg() {
		Set<Class<?>> clazzes = subject.getHibernateClassesFromCfg("/gr/interamerican/rsrc/hibernateutility.cfg.xml"); //$NON-NLS-1$
		Assert.assertTrue(clazzes.size() > 0);
	}

	/**
	 * Test method for {@link Bo2DeploymentHibernateInfoUtility#getHibernateManagers()}.
	 */
	@Test
	public void testGetHibernateManagers() {
		List<Properties> properties = subject.getHibernateManagers();
		Assert.assertTrue(properties.size() > 0);
	}

	/**
	 * test method for {@link Bo2DeploymentHibernateInfoUtility#getAllHibernateClasses()}.
	 */
	@Test
	public void testGetAllHibernateClasses() {
		Set<Class<?>> clazzes = subject.getAllHibernateClasses();
		Assert.assertTrue(clazzes.size() > 0);
	}
}
