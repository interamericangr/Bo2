package gr.interamerican.wicket.components;

import java.math.BigDecimal;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.RangeValidator;

import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.wicket.behavior.ValidationStyleBehavior;

/**
 * A TextField for BigDecimal percentage values. It is appropriate for
 * percentage values with larger than four fraction digits. <br>
 * It has a RangeValidator that guarantees that the percentage is not grater
 * than a hundred and is equal or greater than zero.
 * 
 * 
 * Note: Use this when you need High Precision.
 * 
 */
public class PercentageBigDecimalTextField extends TextField<BigDecimal> {

	/**
	 * serialize.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The converter in use
	 */
	private final PercentageBigDecimalConverter converter;

	/**
	 * Public Constructor
	 *
	 * @param id
	 *            the id
	 * @param decimals
	 *            the decimals
	 */
	public PercentageBigDecimalTextField(String id, Integer decimals) {
		super(id);
		this.converter = new PercentageBigDecimalConverter(decimals);
		add(new RangeValidator<BigDecimal>(BigDecimal.ZERO, NumberUtils.newBigDecimal(100, 20)));
		add(ValidationStyleBehavior.INSTANCE);
		setOutputMarkupId(true);
		add(new NumberFormatBehaviour());
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> IConverter<C> getConverter(final Class<C> type) {
		return (IConverter<C>) converter;
	}
}