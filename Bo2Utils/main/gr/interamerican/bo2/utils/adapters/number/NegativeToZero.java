package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.beans.TypeBasedSelection;

import java.math.BigDecimal;

/**
 * {@link Transformation} that converts negative arguments to zero.
 * 
 * If a negative argument is passed to the <code>execute()</code>
 * method of this Transformation, then the method will return 
 * <code>0</code>. Otherwise it will return the argument. 
 */
public class NegativeToZero 
implements Transformation<Number, Number>{
	
	/**
	 * Absolute value calculators.
	 */
	@SuppressWarnings("rawtypes")
	TypeBasedSelection<Transformation> calculators;

	/**
	 * Creates a new AbsoluteValue object. 
	 *
	 */
	@SuppressWarnings("rawtypes")
	public NegativeToZero() {
		super();
		calculators = new TypeBasedSelection<Transformation>();
		
		calculators.registerSelection(BigDecimal.class, new NegativeToZeroBigDecimal());
		calculators.registerSelection(Double.class, new NegativeToZeroDouble());
	    calculators.registerSelection(Float.class, new NegativeToZeroFloat());
		calculators.registerSelection(Integer.class, new NegativeToZeroInt());
		calculators.registerSelection(Long.class, new NegativeToZeroLong());
		calculators.registerSelection(Short.class, new NegativeToZeroShort());
	}	
	
	public Number execute(Number a) {
		@SuppressWarnings("rawtypes")
		Transformation calc = calculators.select(a);
		Number arg;
		if (calc==null) {
			arg = a.doubleValue();
			calc = calculators.select(a.doubleValue());			
		} else {
			arg = a;
		}
		@SuppressWarnings("unchecked")
		Number result = (Number) calc.execute(arg); 
		return result;
	}
	
	

}
