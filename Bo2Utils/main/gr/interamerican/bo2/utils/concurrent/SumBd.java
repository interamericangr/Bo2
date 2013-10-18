package gr.interamerican.bo2.utils.concurrent;

import java.math.BigDecimal;

/**
 * Thread safe addition for BigDecimals
 */
public class SumBd {
	
	/**
	 * Sum.
	 */
	BigDecimal sum = BigDecimal.ZERO;
	
	/**
	 * Adds the specified number.
	 * 
	 * @param bd
	 *        Number to add.
	 *        
	 * @return The sum result.
	 */
	public synchronized BigDecimal add(BigDecimal bd) {
		sum = sum.add(bd);
		return sum;
	}

}
