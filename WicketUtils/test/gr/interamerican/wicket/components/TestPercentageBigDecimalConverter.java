package gr.interamerican.wicket.components;

import static org.junit.Assert.assertEquals;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import org.junit.Test;

/**
 * Tests {@link PercentageBigDecimalConverter}.
 */
public class TestPercentageBigDecimalConverter {

	/**
	 * Test method for {@link PercentageBigDecimalConverter#PercentageBigDecimalConverter(Integer)}.
	 */
	@Test
	public void testPercentageBigDecimalConverter() {
		PercentageBigDecimalConverter tested = new PercentageBigDecimalConverter(2);
		assertEquals(2, tested.decimals.intValue());
		assertEquals("#,##0.00", tested.decimalFormat); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link PercentageBigDecimalConverter#newNumberFormat(Locale)}.
	 */
	@Test
	public void testNewNumberFormatLocale() {
		Integer random = new Random().nextInt(5) + 4;
		PercentageBigDecimalConverter tested = new PercentageBigDecimalConverter(random);
		NumberFormat nf = tested.getNumberFormat(Locale.getDefault());
		assertEquals(3, nf.getMaximumIntegerDigits());
		assertEquals(4, nf.getMinimumFractionDigits());
		assertEquals(random.intValue(), nf.getMaximumFractionDigits());
	}
}