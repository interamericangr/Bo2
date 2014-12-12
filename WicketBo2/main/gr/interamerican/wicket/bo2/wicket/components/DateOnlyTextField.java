package gr.interamerican.wicket.bo2.wicket.components;

import java.util.Date;
import java.util.Locale;

import org.apache.wicket.datetime.DateConverter;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.model.IModel;

/**
 *
 */
public class DateOnlyTextField extends DateTextField {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 * @param converter
	 */
	public DateOnlyTextField(String id, DateConverter converter) {
		super(id, new ParseLocalDateConverter(converter.getDatePattern(Locale.getDefault()),
				converter.getApplyTimeZoneDifference()));
	}

	/**
	 * @param id
	 * @param model
	 * @param converter
	 */
	public DateOnlyTextField(String id, IModel<Date> model, DateConverter converter) {
		super(id, model, new ParseLocalDateConverter(converter.getDatePattern(Locale.getDefault()),
				converter.getApplyTimeZoneDifference()));
	}

}
