package gr.interamerican.wicket.bo2.markup.html.form;

import java.text.NumberFormat;
import java.util.Locale;

import org.apache.wicket.util.convert.converter.DoubleConverter;

import gr.interamerican.bo2.utils.meta.descriptors.NumberBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;

/**
 * The {@link DoubleConverter} used in {@link SelfDrawnDoubleTextField}.
 */
public class DoubleConverterForSelfDrawn extends DoubleConverter {

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
	public DoubleConverterForSelfDrawn(NumberBoPropertyDescriptor<Double> descriptor) {
		nf = SelfDrawnUtils.getNumberFormatForSelfDrawnWithDecimals(descriptor);
	}

	@Override
	protected NumberFormat newNumberFormat(Locale locale) {
		return nf;
	}
}