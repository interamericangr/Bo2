package gr.interamerican.wicket.bo2.markup.html.panel;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.Component;
import org.junit.Test;

import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link ErrorPanel}.
 */
public class TestErrorPanel extends WicketTest {

	/**
	 * Tests Constructor.
	 */
	@Test
	public void testConstructor() {
		tester.startPage(getTestPage());
		commonAssertions_noError();
		assertTrue(getTestSubject() instanceof ErrorPanel);
	}

	@Override
	protected Component initializeComponent(String wicketId) {
		return new ErrorPanel(wicketId);
	}
}