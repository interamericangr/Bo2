package gr.interamerican.wicket.bo2.callbacks;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.utils.attributes.Input;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.callback.Consume;

/**
 * A {@link Consume} that is based on a bo2 {@link Operation} with {@link Input}
 * of the type that is gonna be consumed.
 * 
 * @param <B>
 *            Type of picked item
 * @param <O>
 *            The Type of the Operation
 */
public class InputOperationAction<B, O extends Operation & Input<B>>
implements Consume<B> {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class of the invoked Operation
	 */
	final Class<O> clz;

	/**
	 * Public Constructor.
	 * 
	 * @param clz
	 *            Class of the invoked Operation
	 */
	public InputOperationAction(Class<O> clz) {
		this.clz = clz;
	}

	@Override
	public void consume(B bean) throws Exception {
		O op = Bo2WicketRequestCycle.open(clz);
		op.setInput(bean);
		op.execute();
	}
}