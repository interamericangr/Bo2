package gr.interamerican.wicket.components;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.Random;

import org.junit.Test;

/**
 * Unit test of {@link FixedDigitsDoubleConverter}.
 */
public class TestFixedDigitsDoubleConverter {

	/**
	 * Test method for
	 * {@link FixedDigitsDoubleConverter#newNumberFormat(Locale)}.
	 */
	@Test
	public void testNewNumberFormatLocale() {
		Integer random = new Random().nextInt(9) + 1;
		FixedDigitsDoubleConverter tested = new FixedDigitsDoubleConverter(random);
		assertEquals(random.intValue(), tested.getNumberFormat(Locale.getDefault()).getMaximumFractionDigits());
		assertEquals(random.intValue(), tested.getNumberFormat(Locale.getDefault()).getMinimumFractionDigits());
	}
}