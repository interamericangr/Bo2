package gr.interamerican.bo2.utils.runnables;

import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.attributes.Periodic;
import gr.interamerican.bo2.utils.attributes.Validatable;

/**
 * Operation used by a Monitor.
 *
 * @param <T>
 *        Type od system being monitored.
 */
public interface MonitoringOperation<T> 
extends VoidOperation<T>, Periodic, Validatable {
	/* empty */
}
