package gr.interamerican.bo2.impl.open.runtime;

import java.util.Properties;


/**
 *
 */
public abstract class AbstractBo2RuntimeWithPropertiesCmd extends AbstractBo2RuntimeCmd {

	/**
	 * properties for the execution
	 */
	protected Properties executionProperties = null;

	/**
	 * @param executionProperties the executionProperties to set
	 */
	public void setExecutionProperties(Properties executionProperties) {
		this.executionProperties = executionProperties;
	}
}
