package gr.interamerican.wicket.components;

import static org.junit.Assert.*;

import java.util.Locale;
import java.util.Random;

import org.junit.Test;

/**
 * Unit test of {@link FixedDigitsBigDecimalConverter}.
 */
public class TestFixedDigitsBigDecimalConverter {

	/**
	 * Test method for
	 * {@link FixedDigitsBigDecimalConverter#newNumberFormat(Locale)}.
	 */
	@Test
	public void testNewNumberFormatLocale() {
		Integer random = new Random().nextInt(9) + 1;
		FixedDigitsBigDecimalConverter tested = new FixedDigitsBigDecimalConverter(random);
		assertEquals(random.intValue(), tested.getNumberFormat(Locale.getDefault()).getMaximumFractionDigits());
		assertEquals(random.intValue(), tested.getNumberFormat(Locale.getDefault()).getMinimumFractionDigits());
	}
}