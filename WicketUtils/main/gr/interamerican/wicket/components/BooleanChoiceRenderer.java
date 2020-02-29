package gr.interamerican.wicket.components;

import java.util.List;

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import gr.interamerican.bo2.utils.beans.MessagesBean;
import gr.interamerican.wicket.utils.WicketUtils;

/**
 * Choice renderer for {@link Boolean}.
 */
public class BooleanChoiceRenderer implements IChoiceRenderer<Boolean> {

	/**
	 * Resource bundle access bean.
	 */
	static MessagesBean BEAN = MessagesBean.get("gr.interamerican.wicket.components.booleanDdc"); //$NON-NLS-1$

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("nls")
	@Override
	public Object getDisplayValue(Boolean object) {
		if (object) {
			return BEAN.getString("true");
		}
		return BEAN.getString("false");
	}

	@Override
	public String getIdValue(Boolean object, int index) {
		if (object == null) {
			return null;
		}
		return String.valueOf(object);
	}

	@Override
	public Boolean getObject(String id, IModel<? extends List<? extends Boolean>> choices) {
		return WicketUtils.getObject(this, id, choices);
	}
}