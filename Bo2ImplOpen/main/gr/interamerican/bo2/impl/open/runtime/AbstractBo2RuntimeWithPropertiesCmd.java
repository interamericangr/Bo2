package gr.interamerican.bo2.impl.open.runtime;

import java.util.Properties;


/**
 * The Class AbstractBo2RuntimeWithPropertiesCmd.
 */
public abstract class AbstractBo2RuntimeWithPropertiesCmd extends AbstractBo2RuntimeCmd {

	/** properties for the execution. */
	protected Properties executionProperties = null;

	/**
	 * Sets the execution properties.
	 *
	 * @param executionProperties the executionProperties to set
	 */
	public void setExecutionProperties(Properties executionProperties) {
		this.executionProperties = executionProperties;
	}
}
