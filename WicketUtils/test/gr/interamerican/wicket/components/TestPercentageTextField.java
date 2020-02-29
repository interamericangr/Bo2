package gr.interamerican.wicket.components;

import static org.junit.Assert.*;

import org.apache.wicket.Component;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.RangeValidator;
import org.junit.Test;

import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link PercentageTextField}.
 */
public class TestPercentageTextField extends WicketTest {

	@Override
	protected Component initializeComponent(String wicketId) {
		return new PercentageTextField(wicketId, 5);
	}

	/**
	 * Test method for {@link PercentageTextField#PercentageTextField(String, Integer)}.
	 */
	@Test
	public void testPercentageTextFieldStringIModelOfBigDecimalInteger() {
		PercentageTextField tf = startAndTestComponent(PercentageTextField.class, Markup.input);
		assertEquals(1, tf.getValidators().size());
		assertTrue(tf.getValidators().iterator().next() instanceof RangeValidator);
	}

	/**
	 * Test method for {@link PercentageTextField#getConverter(Class)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetConverter() {
		PercentageTextField tf = new PercentageTextField("tf", 10);
		IConverter<Double> converter = tf.getConverter(Double.class);
		assertEquals("65,12345679", converter.convertToString(65.1234567900, getLocale()));
		assertEquals("65,1200", converter.convertToString(65.12, getLocale()));
	}
}