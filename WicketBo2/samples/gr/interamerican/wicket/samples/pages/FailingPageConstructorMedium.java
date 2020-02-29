package gr.interamerican.wicket.samples.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;

import gr.interamerican.wicket.bo2.markup.html.panel.ErrorPanel;
import gr.interamerican.wicket.def.WicketOutputMedium;

/**
 * Page with constructor that throws an exception and is a WicketOutputMedium.
 */
public class FailingPageConstructorMedium extends WebPage implements WicketOutputMedium {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 5311L;

	/**
	 * Error Panel
	 */
	private ErrorPanel errorPanel = new ErrorPanel("error"); //$NON-NLS-1$

	/**
	 * Public Constructor
	 */
	public FailingPageConstructorMedium() {
		throw new RuntimeException("fail c with m"); //$NON-NLS-1$
	}

	@Override
	public void showError(Throwable t, AjaxRequestTarget target) {
		errorPanel.showError(t, target);
	}

	@Override
	public void clearMessages(AjaxRequestTarget target) {
		errorPanel.clearMessages(target);
	}
}