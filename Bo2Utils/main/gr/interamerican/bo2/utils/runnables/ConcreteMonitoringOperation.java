package gr.interamerican.bo2.utils.runnables;

import gr.interamerican.bo2.utils.adapters.VoidOperation;

/**
 * {@link MonitoringOperation} that wraps another {@link VoidOperation}.
 *
 * @param <T> the generic type
 */
public class ConcreteMonitoringOperation<T> 
extends AbstractMonitoringOperation<T> {
	
	/**
	 * {@link VoidOperation} being executed.
	 */
	VoidOperation<T> vo;
	

	@Override
	public void execute(T a) {
		vo.execute(a);
	}

	/**
	 * Creates a new ConcreteMonitoringOperation.
	 *
	 * @param vo the vo
	 */
	public ConcreteMonitoringOperation(VoidOperation<T> vo) {
		super();
		this.vo = vo;
	}

}
