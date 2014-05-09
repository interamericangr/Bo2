package gr.interamerican.bo2.quartz.servlet;

import gr.interamerican.bo2.quartz.QuartzSchedulerRegistry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.impl.ServletContextCleaner;
import org.quartz.SchedulerException;

/**
 * Register this ServletContextListener to your application 
 * to shutdown quartz properly.
 */
public class Bo2QuartzServletContextListener 
implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		/* empty */
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("org.apache.commons.logging.impl.ServletContextCleaner context destruction"); //$NON-NLS-1$
		new ServletContextCleaner().contextDestroyed(sce);
		
		System.out.println("Quartz scheduler shutdown"); //$NON-NLS-1$
		try {
			QuartzSchedulerRegistry.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
