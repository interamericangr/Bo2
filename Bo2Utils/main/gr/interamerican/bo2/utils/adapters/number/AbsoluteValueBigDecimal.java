package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.Transformation;

import java.math.BigDecimal;

/**
 * {@link Transformation} that returns the absolute value of the argument.
 */
public class AbsoluteValueBigDecimal 
implements Transformation<BigDecimal,BigDecimal> {

	@Override
	public BigDecimal execute(BigDecimal a) {	
		return a.abs();
	}
	

	
}



