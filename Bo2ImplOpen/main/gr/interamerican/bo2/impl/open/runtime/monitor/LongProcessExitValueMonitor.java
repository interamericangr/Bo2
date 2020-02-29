package gr.interamerican.bo2.impl.open.runtime.monitor;

import static gr.interamerican.bo2.impl.open.runtime.monitor.MonitoringConstants.EXIT_INTERVAL;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessConstants;
import gr.interamerican.bo2.utils.attributes.ModifiableByProperties;
import gr.interamerican.bo2.utils.runnables.AbstractMonitoringOperation;

import java.util.Properties;

/**
 * Monitor to set the exit value of jvm running a {@link BatchProcess}.<br>
 * This monitor will periodical check the batch process and when it is finished it will exit the jvm
 * based of the batch process
 */
public class LongProcessExitValueMonitor extends AbstractMonitoringOperation<LongProcess>
implements ModifiableByProperties {

	@Override
	public void execute(LongProcess a) {
		Integer exitValue = BatchProcessConstants.getExitValue(a);
		if (exitValue != null) {
			System.exit(exitValue.intValue());
		}
	}

	@Override
	public void beModified(Properties properties) {
		setIntervalFromProperties(properties, EXIT_INTERVAL);
	}
}
