package gr.interamerican.wicket.components;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.BigDecimalConverter;

/**
 * A {@link BigDecimalConverter} that will enforce a number of digits on it's result.
 */
public class FixedDigitsBigDecimalConverter extends BigDecimalConverter {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 26341L;

	/**
	 * Number of Decimal Digits
	 */
	private Integer dec;

	/**
	 * Public Constructor.
	 *
	 * @param dec
	 *            Number of Decimal Digits
	 */
	public FixedDigitsBigDecimalConverter(Integer dec) {
		this.dec = dec;
	}

	@Override
	protected NumberFormat newNumberFormat(Locale locale) {
		NumberFormat returned = super.newNumberFormat(locale);
		returned.setMaximumFractionDigits(dec);
		returned.setMinimumFractionDigits(dec);
		return returned;
	}
}