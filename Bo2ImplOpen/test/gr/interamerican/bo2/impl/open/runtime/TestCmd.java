package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;

import java.util.Properties;


/**
 * used with {@link TestPropertiesLauncher}
 */
public class TestCmd extends AbstractBo2RuntimeWithPropertiesCmd {

	/**
	 *
	 */
	private static boolean run = false;

	/**
	 *
	 */
	private static Properties staticProperties;

	/**
	 * @return access
	 */
	public static boolean getRun() {
		boolean a = run;
		run = false;
		return a;
	}

	/**
	 * @return the staticProperties
	 */
	public static Properties getStaticProperties() {
		return staticProperties;
	}

	@Override
	public void work() throws LogicException, DataException, InitializationException,
	UnexpectedException {
		run = true;
		staticProperties = executionProperties;
	}

}
