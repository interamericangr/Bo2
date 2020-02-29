package gr.interamerican.wicket.components;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link BigDecimalTextField}.
 */
public class TestBigDecimalTextField extends WicketTest {

	@Override
	protected Component initializeComponent(String wicketId) {
		return new BigDecimalTextField(wicketId, new Model<>(BigDecimal.ZERO), 5);
	}

	/**
	 * Test method for {@link BigDecimalTextField#BigDecimalTextField(String, IModel, Integer)}.
	 */
	@Test
	public void testBigDecimalTextFieldStringIModelOfBigDecimalInteger() {
		startAndTestComponent(BigDecimalTextField.class, Markup.input);
		// TODO : more assertions
	}

	/**
	 * Test method for {@link BigDecimalTextField#getConverter(Class)}.
	 */
	@Test
	public void testGetConverterClassOfC() {
		BigDecimalTextField tested = startAndTestComponent(BigDecimalTextField.class, Markup.input);
		assertTrue(tested.getConverter(BigDecimal.class)
				.convertToString(BigDecimal.valueOf(842.1421), Locale.getDefault()).endsWith("14210")); //$NON-NLS-1$
	}
}