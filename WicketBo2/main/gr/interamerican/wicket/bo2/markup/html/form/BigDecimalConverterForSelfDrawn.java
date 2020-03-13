package gr.interamerican.wicket.bo2.markup.html.form;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.BigDecimalConverter;

import gr.interamerican.bo2.utils.meta.descriptors.NumberBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;

/**
 * The {@link BigDecimalConverter} used in {@link SelfDrawnBigDecimalTextField}.
 */
public class BigDecimalConverterForSelfDrawn extends BigDecimalConverter {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 624L;

	/**
	 * Number Format in use
	 */
	private final NumberFormat nf;

	/**
	 * Public Constructor.
	 *
	 * @param descriptor
	 *            Descriptor in use
	 */
	public BigDecimalConverterForSelfDrawn(NumberBoPropertyDescriptor<BigDecimal> descriptor) {
		nf = SelfDrawnUtils.getNumberFormatForSelfDrawnWithDecimals(descriptor);
	}

	@Override
	protected NumberFormat newNumberFormat(Locale locale) {
		return nf;
	}
}