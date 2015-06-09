/**
 *
 */
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames;
import gr.interamerican.bo2.utils.StringConstants;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;


/**
 * test suite for {@link PropertiesLauncher}
 */
public class TestPropertiesLauncher {

	/**
	 *
	 */
	String name = TestCmd.class.getCanonicalName();

	/**
	 * Test method for {@link PropertiesLauncher#launchPreprocess(java.util.Properties)}.
	 *
	 * @throws UnexpectedException
	 * @throws LogicException
	 * @throws DataException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testLaunchPreprocess() throws ClassNotFoundException, InstantiationException,
	IllegalAccessException, DataException, LogicException, UnexpectedException {
		Properties p = new Properties();
		p.setProperty(BatchProcessParmNames.PRE_PROCESSING_CLASS, name);
		PropertiesLauncher.launchPreprocess(p);
		Assert.assertTrue(TestCmd.getRun());
		Assert.assertFalse(TestCmd.getRun());
	}

	/**
	 * Test method for {@link PropertiesLauncher#launchPostprocess(java.util.Properties)}.
	 *
	 * @throws UnexpectedException
	 * @throws LogicException
	 * @throws DataException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testLaunchPostprocess() throws ClassNotFoundException, InstantiationException,
	IllegalAccessException, DataException, LogicException, UnexpectedException {
		Properties p = new Properties();
		p.setProperty(BatchProcessParmNames.POST_PROCESSING_CLASS, name);
		PropertiesLauncher.launchPostprocess(p);
		Assert.assertTrue(TestCmd.getRun());
		Assert.assertFalse(TestCmd.getRun());
	}

	/**
	 * Test method for {@link PropertiesLauncher#launchRuntimeCommand(java.lang.String)} .
	 *
	 * @throws UnexpectedException
	 * @throws LogicException
	 * @throws DataException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testLaunchRuntimeCommand() throws ClassNotFoundException, InstantiationException,
	IllegalAccessException, DataException, LogicException, UnexpectedException {
		PropertiesLauncher.launchRuntimeCommand(name);
		Assert.assertTrue(TestCmd.getRun());
		Assert.assertFalse(TestCmd.getRun());
	}

	/**
	 * Test method for
	 * {@link PropertiesLauncher#launchAbstractBo2RuntimeWithPropertiesCmd(java.lang.String, java.util.Properties)}
	 * .
	 *
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws UnexpectedException
	 * @throws LogicException
	 * @throws DataException
	 * @throws ClassNotFoundException
	 */
	@Test
	public void testLaunchAbstractBo2RuntimeWithPropertiesCmd() throws ClassNotFoundException,
	DataException, LogicException, UnexpectedException, InstantiationException,
	IllegalAccessException {
		Properties p = new Properties();
		PropertiesLauncher.launchAbstractBo2RuntimeWithPropertiesCmd(name, p);
		Assert.assertTrue(TestCmd.getRun());
		Assert.assertFalse(TestCmd.getRun());
		Assert.assertEquals(p, TestCmd.getStaticProperties());
	}

	/**
	 * Test method for {@link PropertiesLauncher#hidePrePostOperation(java.util.Properties)}.
	 */
	@Test
	public void testHidePrePostOperation() {
		Properties p=new Properties();
		p.setProperty(BatchProcessParmNames.PRE_PROCESSING_CLASS, StringConstants.ONE);
		p.setProperty(BatchProcessParmNames.POST_PROCESSING_CLASS, StringConstants.TWO);
		p.setProperty(StringConstants.MINUS, StringConstants.MINUS);
		Properties c = PropertiesLauncher.hidePrePostOperation(p);
		Assert.assertNull(c.getProperty(BatchProcessParmNames.PRE_PROCESSING_CLASS));
		Assert.assertNull(c.getProperty(BatchProcessParmNames.POST_PROCESSING_CLASS));
		Assert.assertEquals(StringConstants.MINUS, c.getProperty(StringConstants.MINUS));
	}
}
