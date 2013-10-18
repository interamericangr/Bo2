package gr.interamerican.wicket.components;

import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.wicket.test.WicketTest;

import java.math.BigDecimal;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.RangeValidator;
import org.junit.Assert;
import org.junit.Test;


/**
 * 
 * Junit test for the PercentageBigDecimalTextField.
 *
 */
public class TestPercentageBigDecimalTextField extends WicketTest{

	@Test
	public void test() {
		PercentageBigDecimalTextField tf = new PercentageBigDecimalTextField("tf", 10);

		Assert.assertEquals(1, tf.getValidators().size());
		Assert.assertTrue(tf.getValidators().iterator().next() instanceof RangeValidator);

		tf.setDefaultModel(new CompoundPropertyModel<BigDecimal>(NumberUtils.newBigDecimal(65.12345678999, 10)));
		Assert.assertEquals("65.1234567900", tf.getModelObject().toString());
	}

}
