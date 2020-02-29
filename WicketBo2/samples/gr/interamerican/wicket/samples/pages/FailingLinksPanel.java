package gr.interamerican.wicket.samples.pages;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Panel that contains 2 links that will throw a {@link RuntimeException} when
 * clicked.<br>
 * One is Ajax based, and the other is not.
 */
public class FailingLinksPanel extends Panel {

	/**
	 * Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new FailingLinksPanel object.
	 *
	 * @param id
	 */
	public FailingLinksPanel(String id) {
		super(id);
		add(new Link<Void>("failingLinkNoAjax") { //$NON-NLS-1$

			/**
			 * Version UID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				throw new RuntimeException("not ajax"); //$NON-NLS-1$
			}
		});
		add(new AjaxLink<Void>("failingLinkAjax") { //$NON-NLS-1$

			/**
			 * Version UID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				throw new RuntimeException("ajax"); //$NON-NLS-1$

			}
		});
	}
}