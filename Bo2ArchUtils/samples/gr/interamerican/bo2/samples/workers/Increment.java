package gr.interamerican.bo2.samples.workers;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.arch.Rule;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
import gr.interamerican.bo2.samples.archutil.BeanWithFirst;
import gr.interamerican.bo2.utils.Utils;

/**
 * {@link EmptyWorker} that implements the {@link Operation} and {@link Rule}
 * interfaces.
 * 
 * It operates on a {@link BeanWithFirst} object by incrementing its <code>first</code>
 * property.
 */
public class Increment 
extends EmptyWorker
implements Operation, Rule {
	/**
	 * input.
	 */
	BeanWithFirst bean;

	/**
	 * Gets the bean.
	 *
	 * @return Returns the bean
	 */
	public BeanWithFirst getBean() {
		return bean;
	}

	/**
	 * Assigns a new value to the bean.
	 *
	 * @param bean the bean to set
	 */
	public void setBean(BeanWithFirst bean) {
		this.bean = bean;
	}

	/**
	 * increments.
	 */
	void increment() {
		int first = Utils.notNull(bean.getFirst(), 0);
		first++;
		bean.setFirst(first);
	}

	@Override
	public void apply() throws RuleException, DataException {
		increment();		
	}

	@Override
	public void execute() throws LogicException, DataException {
		increment();		
	}

}
