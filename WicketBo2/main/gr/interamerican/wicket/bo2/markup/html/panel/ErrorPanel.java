package gr.interamerican.wicket.bo2.markup.html.panel;

import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.TokenUtils;
import gr.interamerican.wicket.bo2.markup.html.form.SelfDrawnLabel;
import gr.interamerican.wicket.def.WicketOutputMedium;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

/**
 * Panel that shows error messages not explicitly handled.
 */
public class ErrorPanel extends Panel implements WicketOutputMedium {

	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Model of label.
	 */
	private Model<String> stackTraceMessageLabelModel = new Model<String>();

	/**
	 * Bo2 deployment parameters setting for showing stacktrace in error panel.
	 */
	protected static Boolean showStackTraceInWebPages = Bo2.getDefaultDeployment().getDeploymentBean()
			.getShowStackTraceInWebPages();

	/**
	 * Debug mode.
	 */
	public static boolean debug = showStackTraceInWebPages == null ? false : showStackTraceInWebPages;

	/**
	 * Public Constructor.
	 * 
	 * @param id
	 *            Wicket Id
	 */
	public ErrorPanel(String id) {
		super(id);
		setOutputMarkupPlaceholderTag(true);
		add(createRepeater(StringConstants.EMPTY));
		Label stackTraceLabel = new Label("stackTraceMessage", stackTraceMessageLabelModel); //$NON-NLS-1$
		stackTraceLabel.setOutputMarkupId(true);
		add(stackTraceLabel);
	}

	/**
	 * Create repeater with Error Messages.
	 * 
	 * @param messagesList
	 * @return RepeatingView
	 */
	RepeatingView createRepeater(String... messagesList) {
		RepeatingView repeater = new RepeatingView("repeater"); //$NON-NLS-1$
		repeater.setOutputMarkupPlaceholderTag(true);
		Integer i = new Integer(0);
		for (String labelMsg : messagesList) {
			String containerWicketId = i.toString();
			WebMarkupContainer newContainer = new WebMarkupContainer(containerWicketId);
			SelfDrawnLabel label = new SelfDrawnLabel("message", labelMsg); //$NON-NLS-1$
			newContainer.add(label);
			repeater.add(newContainer);
			i++;
		}
		return repeater;
	}

	@Override
	public void showError(Throwable t, AjaxRequestTarget target) {
		if (debug) {
			stackTraceMessageLabelModel.setObject(ExceptionUtils.getThrowableStackTrace(t));
		}
		String message = t.getMessage();
		if (StringUtils.isNullOrBlank(message)) {
			message = t.getClass().getName();
		}
		replace(createRepeater(TokenUtils.split(message, StringConstants.NEWLINE)));
		target.add(this);
	}

	@Override
	public void clearMessages(AjaxRequestTarget target) {
		replace(createRepeater(StringConstants.EMPTY));
		stackTraceMessageLabelModel.setObject(StringConstants.EMPTY);
		target.add(this);
	}
}