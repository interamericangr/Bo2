package gr.interamerican.wicket.samples.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Page with constructor that throws an exception and is not a WicketOutputMedium
 */
public class FailingPageNoMedium extends WebPage {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 5311L;

	/**
	 * Public Constructor
	 */
	public FailingPageNoMedium() {
		add(new Label("fail")); //$NON-NLS-1$
	}
}