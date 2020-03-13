package gr.interamerican.bo2.utils.adapters.trans.number;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.utils.beans.Pair;

/**
 * Tests for {@link GtZero}.
 */
public class TestGtZero {
	
	/**
	 * Pairs.
	 */
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	Pair[] pairs = {
			new Pair(3.13, true),
			new Pair(-3.13, false),
			new Pair(0.0, false),
			new Pair(BigDecimal.ZERO, false),
			new Pair(0, false),
			new Pair(10, true),
			new Pair(new BigDecimal(3), true),			
	};
	
	
	/**
	 * Test.
	 */
	@Test
	public void testExecute() {
		GtZero trans = new GtZero();		
		
		for (int i = 0; i < pairs.length; i++) {
			@SuppressWarnings("unchecked")
			Pair<Number, Boolean> pair = pairs[i];
			Number n = pair.getLeft();
			Boolean expected = pair.getRight();
			Boolean actual = trans.execute(n);
			String msg = pair.toString();			
			Assert.assertEquals(msg, expected, actual);			
		}
		
	}

	

}
