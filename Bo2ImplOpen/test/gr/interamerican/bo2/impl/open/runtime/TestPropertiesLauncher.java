/**
 *
 */
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.utils.StringConstants;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;


/**
 * test suite for {@link PropertiesLauncher}.
 */
public class TestPropertiesLauncher {

	/** The name. */
	String name = TestWithPropertiesCmd.class.getCanonicalName();

	/**
	 * Test method for {@link PropertiesLauncher#launchPreprocess(java.util.Properties)}.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 * @throws UnexpectedException the unexpected exception
	 */
	@Test
	public void testLaunchPreprocess() throws ClassNotFoundException, InstantiationException,
	IllegalAccessException, DataException, LogicException, UnexpectedException {
		Properties p = new Properties();
		p.setProperty(PropertiesLauncherParamsNames.PRE_PROCESSING_CLASS, name);
		PropertiesLauncher.launchPreprocess(p);
		Assert.assertTrue(TestWithPropertiesCmd.getRun());
		Assert.assertFalse(TestWithPropertiesCmd.getRun());
	}

	/**
	 * Test method for {@link PropertiesLauncher#launchPostprocess(java.util.Properties)}.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 * @throws UnexpectedException the unexpected exception
	 */
	@Test
	public void testLaunchPostprocess() throws ClassNotFoundException, InstantiationException,
	IllegalAccessException, DataException, LogicException, UnexpectedException {
		Properties p = new Properties();
		p.setProperty(PropertiesLauncherParamsNames.POST_PROCESSING_CLASS, name);
		PropertiesLauncher.launchPostprocess(p);
		Assert.assertTrue(TestWithPropertiesCmd.getRun());
		Assert.assertFalse(TestWithPropertiesCmd.getRun());
	}

	/**
	 * Test method for
	 * {@link PropertiesLauncher#launchRuntimeCommand(java.lang.String,java.util.Properties)} .
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 * @throws UnexpectedException the unexpected exception
	 */
	@Test
	public void testLaunchRuntimeCommand() throws ClassNotFoundException, InstantiationException,
	IllegalAccessException, DataException, LogicException, UnexpectedException {
		PropertiesLauncher.launchRuntimeCommand(name, new Properties());
		Assert.assertTrue(TestWithPropertiesCmd.getRun());
		Assert.assertFalse(TestWithPropertiesCmd.getRun());

		PropertiesLauncher.launchRuntimeCommand(TestOp.class.getCanonicalName(), new Properties());
		Assert.assertTrue(TestOp.getRun());
		Assert.assertFalse(TestOp.getRun());
	}

	/**
	 * Test method for
	 * {@link PropertiesLauncher#launchAbstractBo2RuntimeWithPropertiesCmd(java.lang.String, java.util.Properties)}
	 * .
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 * @throws UnexpectedException the unexpected exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	@Test
	public void testLaunchAbstractBo2RuntimeWithPropertiesCmd() throws ClassNotFoundException,
	DataException, LogicException, UnexpectedException, InstantiationException,
	IllegalAccessException {
		Properties p = new Properties();
		PropertiesLauncher.launchAbstractBo2RuntimeWithPropertiesCmd(name, p);
		Assert.assertTrue(TestWithPropertiesCmd.getRun());
		Assert.assertFalse(TestWithPropertiesCmd.getRun());
		Assert.assertEquals(p, TestWithPropertiesCmd.getStaticProperties());
		PropertiesLauncher.launchAbstractBo2RuntimeWithPropertiesCmd(
				TestCmd.class.getCanonicalName(), p);
		Assert.assertTrue(TestCmd.getRun());
		Assert.assertFalse(TestCmd.getRun());
	}

	/**
	 * Test method for {@link PropertiesLauncher#hidePrePostOperation(java.util.Properties)}.
	 */
	@Test
	public void testHidePrePostOperation() {
		Properties p=new Properties();
		p.setProperty(PropertiesLauncherParamsNames.PRE_PROCESSING_CLASS, StringConstants.ONE);
		p.setProperty(PropertiesLauncherParamsNames.POST_PROCESSING_CLASS, StringConstants.TWO);
		p.setProperty(StringConstants.MINUS, StringConstants.MINUS);
		Properties c = PropertiesLauncher.hidePrePostOperation(p);
		Assert.assertNull(c.getProperty(PropertiesLauncherParamsNames.PRE_PROCESSING_CLASS));
		Assert.assertNull(c.getProperty(PropertiesLauncherParamsNames.POST_PROCESSING_CLASS));
		Assert.assertEquals(StringConstants.MINUS, c.getProperty(StringConstants.MINUS));
	}
}
