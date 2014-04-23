package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.beans.TypeBasedSelection;

import java.math.BigDecimal;

/**
 * {@link Transformation} that returns the absolute value of the argument.
 * 
 * The transformation can be applied to any type of {@link Number}.
 */
public class AbsoluteValue 
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
	public AbsoluteValue() {
		super();
		calculators = new TypeBasedSelection<Transformation>();
		
		calculators.registerSelection(BigDecimal.class, new AbsoluteValueBigDecimal());
		calculators.registerSelection(Double.class, new AbsoluteValueDouble());
	    calculators.registerSelection(Float.class, new AbsoluteValueFloat());
		calculators.registerSelection(Integer.class, new AbsoluteValueInt());
		calculators.registerSelection(Long.class, new AbsoluteValueLong());
		calculators.registerSelection(Short.class, new AbsoluteValueShort());
	}	
	
	public Number execute(Number a) {
		@SuppressWarnings("rawtypes")
		Transformation calc = calculators.select(a);
		if (calc==null) {
			return Math.abs(a.doubleValue());
		}
		@SuppressWarnings("unchecked")
		Number result = (Number) calc.execute(a); 
		return result;
	}
	
	

}
