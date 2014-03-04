package gr.interamerican.wicket.components;

import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.wicket.behavior.ValidationStyleBehavior;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.BigDecimalConverter;
import org.apache.wicket.validation.validator.RangeValidator;


/**
 * A TextField for BigDecimal percentage values. It is appropriate for percentage values with larger than four fraction digits. <br/>
 * It has a RangeValidator that guarantees that the percentage is not grater than a hundred and is equal or greater than zero.
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
	 *
	 * 
	 */
	private Integer decimals;

	/**
	 * Creates a new PercentageTextField object. r
	 *
	 * @param id
	 * @param decimals
	 */
	public PercentageBigDecimalTextField(String id, Integer decimals) {
		super(id);
		this.decimals = decimals;
		add(new RangeValidator<BigDecimal>(BigDecimal.ZERO, NumberUtils.newBigDecimal(100, 20)));
		add(ValidationStyleBehavior.INSTANCE);
		setOutputMarkupId(true);
		add(new NumberFormatBehaviour(decimals));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <C> IConverter<C> getConverter(final Class<C> type) {
		int counter = decimals;
		String sharp_str = "#,"; //$NON-NLS-1$
		String zero_str = "0."; //$NON-NLS-1$
		while(counter>0){
			sharp_str = sharp_str.concat("#"); //$NON-NLS-1$
			zero_str  = zero_str.concat("0"); //$NON-NLS-1$
			counter--;
		}
		String decimalFormat = sharp_str.concat(zero_str);
		NumberFormat nf = new DecimalFormat(decimalFormat);
		nf.setMaximumIntegerDigits(3);
		nf.setMaximumFractionDigits(decimals);
		nf.setMinimumFractionDigits(4);
		BigDecimalConverter dc = new BigDecimalConverter();
		dc.setNumberFormat(getLocale(), nf);
		return (IConverter<C>) dc;
	}
}