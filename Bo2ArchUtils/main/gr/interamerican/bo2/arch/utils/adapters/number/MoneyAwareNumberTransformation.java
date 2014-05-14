package gr.interamerican.bo2.arch.utils.adapters.number;

import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Money aware {@link Transformation} that transforms a numbner to another
 * number.
 */
public class MoneyAwareNumberTransformation 
implements Transformation<Object, Number>{
	
	/**
	 * Transformation to execute.
	 */
	Transformation<Number, Number> transformation;

	/**
	 * Creates a new MoneyAwareNumberTransformation object. 
	 *
	 * @param transformation
	 */
	public MoneyAwareNumberTransformation(Transformation<Number, Number> transformation) {
		super();
		this.transformation = transformation;
	}

	@Override
	public Number execute(Object a) {
		if (a==null) {
			return null;
		}
		Number n;
		if (a instanceof Number) {
			n = (Number) a;
		} else if (a instanceof Money) {
			Money money = (Money) a;
			n = money.getAmount();
		} else {
			String msg = "Invalid argument type " + a.getClass().getName();  //$NON-NLS-1$
			throw new IllegalArgumentException(msg);
		}
		return transformation.execute(n);
	}
	
	

}
