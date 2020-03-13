package gr.interamerican.wicket.components;

import static org.junit.Assert.assertEquals;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import org.junit.Test;

/**
 * Tests {@link PercentageDoubleConverter}.
 */
public class TestPercentageDoubleConverter {

	/**
	 * Test method for {@link PercentageDoubleConverter#PercentageDoubleConverter(Integer)}.
	 */
	@Test
	public void testPercentageDoubleConverter() {
		PercentageDoubleConverter tested = new PercentageDoubleConverter(5);
		assertEquals(5, tested.decimals.intValue());
		assertEquals("#,#####0.00000", tested.decimalFormat); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link PercentageDoubleConverter#newNumberFormat(Locale)}.
	 */
	@Test
	public void testNewNumberFormatLocale() {
		Integer random = new Random().nextInt(5) + 4;
		PercentageDoubleConverter tested = new PercentageDoubleConverter(random);
		NumberFormat nf = tested.getNumberFormat(Locale.getDefault());
		assertEquals(3, nf.getMaximumIntegerDigits());
		assertEquals(4, nf.getMinimumFractionDigits());
		assertEquals(random.intValue(), nf.getMaximumFractionDigits());
	}
}