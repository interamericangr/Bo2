package gr.interamerican.bo2.arch.utils.adapters.number;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gr.interamerican.bo2.arch.Money;
import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;
import gr.interamerican.bo2.utils.adapters.Transformation;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link MoneyAwareNumberTransformation}.
 */
public class TestMoneyAwareNumberTransformation {
	
	
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		@SuppressWarnings("unchecked")
		Transformation<Number, Number> trans = mock(Transformation.class);
		MoneyAwareNumberTransformation mant = new MoneyAwareNumberTransformation(trans);
		Assert.assertEquals(trans, mant.transformation);
	}
	
	/**
	 * Tests execute().
	 */
	@Test
	public void testExecute_withNull() {
		@SuppressWarnings("unchecked")
		Transformation<Number, Number> trans = mock(Transformation.class);
		when(trans.execute(any(Number.class))).thenReturn(5);
		
		MoneyAwareNumberTransformation mant = new MoneyAwareNumberTransformation(trans);
		Number actual = mant.execute(null);
		Assert.assertNull(actual);		
	}
	
	/**
	 * Tests execute().
	 */
	@Test
	public void testExecute_withNumber() {
		@SuppressWarnings("unchecked")
		Transformation<Number, Number> trans = mock(Transformation.class);
		Integer expected = 5;
		when(trans.execute(any(Number.class))).thenReturn(expected);
		
		MoneyAwareNumberTransformation mant = new MoneyAwareNumberTransformation(trans);
		Number actual = mant.execute(3);
		Assert.assertEquals(expected, actual);		
	}
	
	/**
	 * Tests execute().
	 */
	@Test
	public void testExecute_withMoney() {
		@SuppressWarnings("unchecked")
		Transformation<Number, Number> trans = mock(Transformation.class);
		Integer expected = 5;
		Money money = new MoneyImpl(BigDecimal.TEN);
		
		when(trans.execute(any(Number.class))).thenReturn(expected);
		
		MoneyAwareNumberTransformation mant = new MoneyAwareNumberTransformation(trans);
		Number actual = mant.execute(money);
		Assert.assertEquals(expected, actual);		
	}
	
	/**
	 * Tests execute().
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testExecute_withIllegalArgument() {
		@SuppressWarnings("unchecked")
		Transformation<Number, Number> trans = mock(Transformation.class);
		MoneyAwareNumberTransformation mant = new MoneyAwareNumberTransformation(trans);
		mant.execute(new Object());				
	}

}
