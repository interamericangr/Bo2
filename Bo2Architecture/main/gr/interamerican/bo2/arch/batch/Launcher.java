package gr.interamerican.bo2.arch.batch;

import java.util.Properties;

/**
 * The Interface Launcher.
 */
public interface Launcher {

	/**
	 * Launches a long process.
	 *
	 * @param properties
	 *            Specification properties for the long process.
	 */
	public void launch(Properties properties);
}
