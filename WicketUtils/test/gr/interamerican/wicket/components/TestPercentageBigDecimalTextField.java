package gr.interamerican.wicket.components;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.apache.wicket.Component;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.RangeValidator;
import org.junit.Test;

import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link PercentageBigDecimalTextField}.
 */
public class TestPercentageBigDecimalTextField extends WicketTest {

	@Override
	protected Component initializeComponent(String wicketId) {
		return new PercentageBigDecimalTextField(wicketId, 5);
	}

	/**
	 * Test method for
	 * {@link PercentageBigDecimalTextField#PercentageBigDecimalTextField(String, Integer)}.
	 */
	@Test
	public void testPercentageBigDecimalTextFieldStringIModelOfBigDecimalInteger() {
		PercentageBigDecimalTextField tf = startAndTestComponent(PercentageBigDecimalTextField.class, Markup.input);
		assertEquals(1, tf.getValidators().size());
		assertTrue(tf.getValidators().iterator().next() instanceof RangeValidator);
	}

	/**
	 * Test method for
	 * {@link PercentageBigDecimalTextField#getConverter(Class)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetConverter() {
		PercentageBigDecimalTextField tf = new PercentageBigDecimalTextField("tf", 10);
		IConverter<BigDecimal> converter = tf.getConverter(BigDecimal.class);
		assertEquals("65,12345679",
				converter.convertToString(NumberUtils.newBigDecimal(65.1234567900, 10), getLocale()));
		assertEquals("65,1200", converter.convertToString(NumberUtils.newBigDecimal(65.12, 2), getLocale()));
	}
}