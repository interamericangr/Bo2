package gr.interamerican.bo2.impl.open.runtime.monitor;

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.utils.attributes.ModifiableByProperties;
import gr.interamerican.bo2.utils.runnables.AbstractMonitoringOperation;
import gr.interamerican.bo2.utils.runnables.MonitoringOperation;

import java.util.Properties;

/**
 * Calls tidy on a {@link LongProcess}.
 */
public class Tidy 
extends AbstractMonitoringOperation<LongProcess>
implements MonitoringOperation<LongProcess>, ModifiableByProperties {
	
	/**
	 * Property key for interval.
	 * This property defines the interval between two executions
	 * in milliseconds.
	 */
	public static final String TIDY_INTERVAL = "tidyInterval"; //$NON-NLS-1$
	
	@Override
	public void execute(LongProcess a) {
		a.tidy();		
	}
	
	@Override
	public void beModified(Properties properties) {
		setIntervalFromProperties(properties, TIDY_INTERVAL);
	}

}
