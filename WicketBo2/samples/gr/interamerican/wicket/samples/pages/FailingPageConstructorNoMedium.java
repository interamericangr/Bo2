package gr.interamerican.wicket.samples.pages;

import org.apache.wicket.markup.html.WebPage;

/**
 * Page with constructor that throws an exception and is not a WicketOutputMedium
 */
public class FailingPageConstructorNoMedium extends WebPage {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 5311L;

	/**
	 * Public Constructor
	 */
	public FailingPageConstructorNoMedium() {
		throw new RuntimeException("fail c no m"); //$NON-NLS-1$
	}
}