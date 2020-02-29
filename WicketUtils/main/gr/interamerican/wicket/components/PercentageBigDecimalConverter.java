package gr.interamerican.wicket.components;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.BigDecimalConverter;

/**
 * A {@link BigDecimalConverter} that is customized to display results as
 * percentages.
 */
public class PercentageBigDecimalConverter extends BigDecimalConverter {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 636L;
	/**
	 * Actual Pattern used in {@link DecimalFormat}.
	 */
	String decimalFormat;
	/**
	 * Decimal digits to display
	 */
	Integer decimals;

	/**
	 * Public Constructor.
	 *
	 * @param decimals
	 *            Decimal digits to display
	 */
	public PercentageBigDecimalConverter(Integer decimals) {
		this.decimals = decimals;
		int counter = decimals;
		String sharp_str = "#,"; //$NON-NLS-1$
		String zero_str = "0."; //$NON-NLS-1$
		while (counter > 0) {
			sharp_str = sharp_str.concat("#"); //$NON-NLS-1$
			zero_str = zero_str.concat("0"); //$NON-NLS-1$
			counter--;
		}
		decimalFormat = sharp_str.concat(zero_str);
	}

	@Override
	protected NumberFormat newNumberFormat(Locale locale) {
		NumberFormat nf = new DecimalFormat(decimalFormat);
		nf.setMaximumIntegerDigits(3);
		nf.setMaximumFractionDigits(decimals);
		nf.setMinimumFractionDigits(4);
		return nf;
	}
}