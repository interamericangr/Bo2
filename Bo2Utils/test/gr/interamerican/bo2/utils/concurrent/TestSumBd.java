package gr.interamerican.bo2.utils.concurrent;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link SumBd}.
 */
public class TestSumBd {
	
	/**
	 * Tests add().
	 */
	@Test
	public void testAdd() {
		SumBd sum = new SumBd();
		BigDecimal bd1 = BigDecimal.valueOf(3.14);
		BigDecimal bd2 = BigDecimal.valueOf(3.156);
		BigDecimal bd3 = bd1.add(bd2);
		
		BigDecimal sum1 = sum.add(bd1);
		Assert.assertEquals(bd1, sum1);
		
		BigDecimal sum2 = sum.add(bd2);
		Assert.assertEquals(bd3, sum2);		
	}

}
