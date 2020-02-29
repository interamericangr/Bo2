package gr.interamerican.bo2.utils.conditions;

import org.junit.Assert;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWithBoolean;

/**
 * Test for {@link GetBooleanProperty}.
 */
@Deprecated
public class TestGetBooleanProperty {
	
	/**
	 * Tests execute.
	 */
	@Test
	public void testExecute() {
		BeanWithBoolean bFalse = new BeanWithBoolean();
		bFalse.setBool(false);
		BeanWithBoolean bTrue = new BeanWithBoolean();
		bTrue.setBool(true);
		
		GetBooleanProperty<BeanWithBoolean> condition =
			new GetBooleanProperty<BeanWithBoolean>("bool", BeanWithBoolean.class); //$NON-NLS-1$
		Assert.assertTrue(condition.check(bTrue));
		Assert.assertFalse(condition.check(bFalse));
		
	}
}