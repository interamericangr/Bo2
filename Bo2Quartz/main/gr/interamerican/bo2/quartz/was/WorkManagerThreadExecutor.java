package gr.interamerican.bo2.quartz.was;

import commonj.work.Work;
import commonj.work.WorkManager;
import org.quartz.spi.ThreadExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Modified version of {@link org.quartz.commonj.WorkManagerThreadExecutor}
 * for WebSphere 8.5
 * Quartz configuration below:
 * 
 * <pre>
 * org.quartz.threadExecutor.class=gr.interamerican.bo2.quartz.was.WorkManagerThreadExecutor
 * org.quartz.threadExecutor.workManagerName=wm/default
 * </pre>
 * 
 * In order to use this feature, it is suggested to maintain a 2nd quartz.properties file
 * in a specific (non-default) package and use the JVM argument -Dorg.quartz.properties
 * in the WebSphere JVM definition in order to reference it. 
 */
public class WorkManagerThreadExecutor implements ThreadExecutor {
	
    /**
     * LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(WorkManagerThreadExecutor.class);

    /**
     * Work manager jndi name.
     */
    private String workManagerName;
    
    /**
     * Looked up instance.
     */
    private WorkManager workManager;

    @Override
	public void execute(Thread thread) {
        Work work = new DelegatingWork(thread);
        try {
            this.workManager.schedule(work);
        } catch (Exception e) {
        	LOG.error("Error attempting to schedule QuartzSchedulerThread: " + e.getMessage(), e); //$NON-NLS-1$
        }
    }

    @Override
	public void initialize() {
        try {
            this.workManager = (WorkManager) new InitialContext().lookup(workManagerName);
        } catch (NamingException e) {
            throw new IllegalStateException("Could not locate WorkManager: " + e.getMessage(), e); //$NON-NLS-1$
        }
    }

    /**
     * Sets the JNDI name of the work manager to use.
     *
     * @param workManagerName the JNDI name to use to lookup the work manager
     */
    public void setWorkManagerName(String workManagerName) {
        this.workManagerName = workManagerName;
    }

}

/**
 * {@link Work} impl
 */
class DelegatingWork implements Work {

    /**
     * Actual thread to delegate to.
     */
    private final Runnable delegate;

    /**
     * Create a new DelegatingWork.
     *
     * @param delegate the Runnable implementation to delegate to
     */
    public DelegatingWork(Runnable delegate) {
        this.delegate = delegate;
    }

    /**
     * @return the wrapped Runnable implementation.
     */
    public final Runnable getDelegate() {
        return this.delegate;
    }

    /**
     * Delegates execution to the underlying Runnable.
     */
    @Override
	public void run() {
        this.delegate.run();
    }

    @Override
	public boolean isDaemon() {
        return true;
    }

    /**
     * This implementation is empty, since we expect the Runnable to terminate
     * based on some specific shutdown signal.
     */
    @Override
	public void release() {
    	// empty
    }
}