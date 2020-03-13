package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.impl.open.annotations.Parameter;
import gr.interamerican.bo2.impl.open.annotations.ParametersOrder;
import gr.interamerican.bo2.impl.open.workers.TestAbstractBaseWorker.BaseWorkerBasicImpl;
import gr.interamerican.bo2.impl.open.workers.TestAbstractBaseWorker.BaseWorkerEmptyImpl;
import gr.interamerican.bo2.impl.open.workers.TestAbstractBaseWorker.PropertyParamer;
import gr.interamerican.bo2.utils.annotations.Child;

/**
 * Derived class of BaseWorkerBasicImpl used in this test.
 */
@ParametersOrder("integer,string,string,property.integer,property.subProperty.string,property.nullSubProperty.string") 
public class SubtypeOfBaseWorkerBasicImpl extends BaseWorkerBasicImpl {
	/**
	 * parameter field.
	 */
	@Parameter
	private
	String string = "string"; //$NON-NLS-1$
	/**
	 *
	 */
	@Parameter
	public PropertyParamer property = new PropertyParamer();

	/** Child worker. */
	@Child
	private BaseWorkerEmptyImpl grandChild = new BaseWorkerEmptyImpl();

	/**
	 * Gets the grand child.
	 *
	 * @return the grandChild
	 */
	public BaseWorkerEmptyImpl getGrandChild() {
		return grandChild;
	}

	/**
	 * @return the string
	 */
	public String getString() {
		return string;
	}

	/**
	 * @param string the string to set
	 */
	public void setString(String string) {
		this.string = string;
	}
}