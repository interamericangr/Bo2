package gr.interamerican.wicket.bo2.wicket.components;

import gr.interamerican.bo2.utils.ReflectionUtils;

import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

/**
 * it's the same as {@link DateField} but hours is assumed to be 12 in the noon.
 */
public class DateOnlyField extends DateField {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * default constructor.
	 *
	 * @param id
	 */
	public DateOnlyField(String id) {
		super(id);
		DateOnlyTextField my = new DateOnlyTextField(DATE, new ParseLocalDateConverter("dd/MM/yy.yy", false)); //$NON-NLS-1$
		alterEmbendedDateTextField(my);
	}

	/**
	 * Construct.
	 *
	 * @param id
	 * @param model
	 */
	public DateOnlyField(String id, IModel<Date> model) {
		super(id, model);
		PropertyModel<Date> dateFieldModel = new PropertyModel<Date>(this, DATE);
		DateOnlyTextField my = new DateOnlyTextField(DATE, dateFieldModel, new ParseLocalDateConverter(
				"dd/MM/yyyy", false)); //$NON-NLS-1$
		alterEmbendedDateTextField(my);
	}

	/**
	 * alter the {@link DateField} with the {@link DateOnlyTextField}.
	 *
	 * @param my
	 */
	private void alterEmbendedDateTextField(DateOnlyTextField my) {
		ReflectionUtils.set("dateField", my, this); //$NON-NLS-1$
		get(DATE).replaceWith(my);
		my.add(newDatePicker());
	}

	@Override
	protected void convertInput() {
		try {
			// Get the converted input values
			Date dateFieldInput = ((DateOnlyTextField) get(DATE)).getConvertedInput();
			if (dateFieldInput == null) {
				return;
			}
			// Get year, month and day ignoring any timezone of the Date object
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFieldInput);
			// Use the input to create a date object with proper timezone
			// The date will be in the server's timezone
			setConvertedInput(newDateInstance(cal.getTimeInMillis()));
		} catch (RuntimeException e) {
			error(e.getMessage());
			invalid();
		}
	}
}
