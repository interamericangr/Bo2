package gr.interamerican.wicket.bo2.wicket.components;

import java.util.Date;
import java.util.Locale;

import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.model.IModel;

/**
 * The Class DateOnlyTextField.
 */
public class DateOnlyTextField extends DateTextField {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new date only text field.
	 *
	 * @param id the id
	 * @param converter the converter
	 */
	public DateOnlyTextField(String id, DateConverter converter) {
		super(id, new ParseLocalDateConverter(converter.getDatePattern(Locale.getDefault()),
				converter.getApplyTimeZoneDifference()));
	}

	/**
	 * Instantiates a new date only text field.
	 *
	 * @param id the id
	 * @param model the model
	 * @param converter the converter
	 */
	public DateOnlyTextField(String id, IModel<Date> model, DateConverter converter) {
		super(id, model, new ParseLocalDateConverter(converter.getDatePattern(Locale.getDefault()),
				converter.getApplyTimeZoneDifference()));
	}

}
