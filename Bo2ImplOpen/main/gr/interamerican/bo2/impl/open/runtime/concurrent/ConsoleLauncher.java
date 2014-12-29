package gr.interamerican.bo2.impl.open.runtime.concurrent;

import gr.interamerican.bo2.arch.batch.LongProcessLauncher;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;

import java.util.Properties;

/**
 * 
 */
public class ConsoleLauncher implements LongProcessLauncher {

	@Override
	public void launch(Properties properties) {
		BatchProcessUtility controller = new BatchProcessUtility(Bo2Session.getSession());
		BatchProcess<?> batch = controller.start(properties);
		/*
		SimpleCommand tidy = controller.tidyCommand(batch);
		SimpleCommand mail = controller.mailCommand(batch);
		SimpleCommand sysout = controller.sysoutCommand(batch);
		*/
				
		controller.startMonitor(batch, null);
	}

}
