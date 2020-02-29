package gr.interamerican.wicket.links;

import static org.junit.Assert.*;

import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit test for {@link RunnableLink}
 */
public class TestRunnableLink extends WicketTest{
	
	/**
	 * flag showing if the tested {@link RunnableLink} is clicked.
	 */
	Boolean isClicked = false;

	/**
	 * Test method for {@link gr.interamerican.wicket.links.RunnableLink#onClick()}.
	 */
	@Test
	public void testOnClick() {
		RunnableLink tested = new RunnableLink(StringConstants.EIGHT, () -> isClicked = true);
		assertNotNull(tested);
	}

	/**
	 * Test method for {@link gr.interamerican.wicket.links.RunnableLink#RunnableLink(java.lang.String, gr.interamerican.bo2.utils.functions.SerializableRunnable)}.
	 */
	@Test
	public void testRunnableLink() {
		RunnableLink tested = new RunnableLink(StringConstants.EIGHT, () -> isClicked = true);
		tested = tester.startComponentInPage(tested);
		tester.clickLink(tested);
		assertTrue(isClicked);
	}

}
