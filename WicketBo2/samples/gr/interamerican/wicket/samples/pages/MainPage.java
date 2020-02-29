package gr.interamerican.wicket.samples.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

import gr.interamerican.wicket.bo2.markup.html.panel.ErrorPanel;
import gr.interamerican.wicket.def.WicketOutputMedium;

/**
 * Page Containing Various Test Cases.
 */
public class MainPage extends WebPage implements WicketOutputMedium {

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
	public MainPage() {
		add(new BookmarkablePageLink<Void>("failingPageConstructorMedium", FailingPageConstructorMedium.class)); //$NON-NLS-1$
		add(new BookmarkablePageLink<Void>("failingPageConstructorNoMedium", FailingPageConstructorNoMedium.class)); //$NON-NLS-1$
		add(new BookmarkablePageLink<Void>("failingPageMedium", FailingPageMedium.class)); //$NON-NLS-1$
		add(new BookmarkablePageLink<Void>("failingPageNoMedium", FailingPageNoMedium.class)); //$NON-NLS-1$
		add(errorPanel);
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