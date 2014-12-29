package gr.interamerican.bo2.impl.open.runtime.concurrent;

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;
import gr.interamerican.bo2.utils.runnables.Monitor;

import java.util.Properties;
/**
 * Interface for a factory that creates a {@link SimpleCommand}
 * that can be registered with a {@link Monitor}.
 */
public interface MonitorCommandFactory {
	
	/**
	 * Creates a {@link SimpleCommand}.
	 *  
	 * @param properties
	 * @param process 
	 * 
	 * @return Returns the {@link SimpleCommand}.
	 */
	public SimpleCommand create(Properties properties, LongProcess process); 

}
