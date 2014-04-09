package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.Transformation;

import java.math.BigDecimal;

/**
 * {@link Transformation} that converts negative arguments to zero.
 * 
 * If a negative argument is passed to the <code>execute()</code>
 * method of this Transformation, then the method will return 
 * <code>0</code>. Otherwise it will return the argument. 
 */
public class NegativeToZeroBigDecimal 
implements Transformation<BigDecimal,BigDecimal> {

	@Override
	public BigDecimal execute(BigDecimal a) {
		if (a.signum()==-1) {
			return BigDecimal.ZERO.setScale(a.scale());
		}
		return a;
	}
		
}



