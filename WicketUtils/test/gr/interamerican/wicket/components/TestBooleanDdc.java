package gr.interamerican.wicket.components;

import static org.junit.Assert.*;

import org.apache.wicket.Component;
import org.junit.Test;

import gr.interamerican.wicket.markup.html.Markup;
import gr.interamerican.wicket.test.WicketTest;

/**
 * The Class TestBooleanDdc.
 */
public class TestBooleanDdc extends WicketTest {

	/**
	 * Test creation.
	 */
	@Test
	public void testCreation() {
		BooleanDdc ddc =  startAndTestComponent(BooleanDdc.class, Markup.select);
		assertTrue(ddc.isNullValid());
		assertEquals(2, ddc.getChoices().size());
	}

	@Override
	protected Component initializeComponent(String wicketId) {
		return new BooleanDdc(wicketId);
	}
}