package gr.interamerican.bo2.impl.open.runtime.monitor;

import static gr.interamerican.bo2.impl.open.runtime.monitor.MonitoringConstants.TIDY_INTERVAL;

import java.util.Properties;

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.utils.attributes.ModifiableByProperties;
import gr.interamerican.bo2.utils.runnables.AbstractMonitoringOperation;
/**
 * Calls tidy on a {@link LongProcess}.
 */
public class Tidy
extends AbstractMonitoringOperation<LongProcess>
implements ModifiableByProperties {


	@Override
	public void execute(LongProcess a) {
		LOGGER.debug("Performing tidy"); //$NON-NLS-1$
		if (a != null) {
		a.tidy();
		} else {
			LOGGER.warn("Long Process is NULL!!!!"); //$NON-NLS-1$
	}
	}

	@Override
	public void beModified(Properties properties) {
		setIntervalFromProperties(properties, TIDY_INTERVAL);
	}

}
