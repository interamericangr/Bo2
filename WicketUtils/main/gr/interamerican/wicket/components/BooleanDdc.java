package gr.interamerican.wicket.components;

import java.util.Arrays;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

/**
 * A {@link DropDownChoice} that backs a Boolean property where null is a valid choice.
 * Checkboxes are not desirable in this case, because on form submission time null value
 * cannot be preserved.
 */
public class BooleanDdc extends DropDownChoice<Boolean> {
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new BooleanDdc object. 
	 *
	 * @param id the id
	 */
	public BooleanDdc(String id) {
		super(id, Arrays.asList(Boolean.TRUE, Boolean.FALSE));
		setChoiceRenderer(new BooleanChoiceRenderer());
		setNullValid(true);
	}
	
	/**
	 * Creates a new BooleanDdc object. 
	 *
	 * @param id the id
	 * @param model the model
	 */
	public BooleanDdc(String id, IModel<Boolean> model) {
		super(id, model, Arrays.asList(Boolean.TRUE, Boolean.FALSE));
		setChoiceRenderer(new BooleanChoiceRenderer());
		setNullValid(true);
	}
}