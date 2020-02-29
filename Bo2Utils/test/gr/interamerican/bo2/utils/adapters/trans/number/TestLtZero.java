package gr.interamerican.bo2.utils.adapters.trans.number;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.utils.beans.Pair;

/**
 * Tests for {@link LtZero}.
 */
public class TestLtZero {
	
	
	/**
	 * Test.
	 */
	@Test
	public void testExecute() {
		List<Pair<Number, Boolean>> samples = new ArrayList<>();
		samples.add(new Pair<Number, Boolean>(3.13, false));
		samples.add(new Pair<Number, Boolean>(-3.13, true));
		samples.add(new Pair<Number, Boolean>(0.0, false));
		samples.add(new Pair<Number, Boolean>(BigDecimal.ZERO, false));
		samples.add(new Pair<Number, Boolean>(0, false));
		samples.add(new Pair<Number, Boolean>(10, false));
		samples.add(new Pair<Number, Boolean>(new BigDecimal(3), false));
		LtZero trans = new LtZero();		
		for (Pair<Number, Boolean> pair :samples) {
			Number n = pair.getLeft();
			Boolean expected = pair.getRight();
			Boolean actual = trans.execute(n);
			String msg = pair.toString();			
			Assert.assertEquals(msg, expected, actual);			
		}
	}
}