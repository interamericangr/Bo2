package gr.interamerican.bo2.utils.adapters.number;

import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * {@link Transformation} that converts negative arguments to zero.
 * 
 * If a negative argument is passed to the <code>execute()</code>
 * method of this Transformation, then the method will return 
 * <code>0</code>. Otherwise it will return the argument. 
 */
public class NegativeToZeroLong 
implements Transformation<Long,Long> {
	
	@Override
	public Long execute(Long a) {
		if (a<0) {
			return 0L;
		}	
		return a;
	}
}



