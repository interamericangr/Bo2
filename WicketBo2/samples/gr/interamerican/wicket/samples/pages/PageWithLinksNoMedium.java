package gr.interamerican.wicket.samples.pages;

import java.util.Date;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

/**
 * Page containing the {@link FailingLinksPanel} that is not a
 * WicketOutputMedium.
 */
public class PageWithLinksNoMedium extends WebPage {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 5311L;

	/**
	 * Public Constructor
	 */
	public PageWithLinksNoMedium() {
		add(new Label("label", new Date().toString())); //$NON-NLS-1$
		add(new FailingLinksPanel("links")); //$NON-NLS-1$
	}
}