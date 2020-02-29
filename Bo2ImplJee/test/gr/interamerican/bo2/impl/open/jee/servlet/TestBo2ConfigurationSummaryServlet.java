package gr.interamerican.bo2.impl.open.jee.servlet;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class TestBo2ConfigurationSummaryServlet.
 */
public class TestBo2ConfigurationSummaryServlet {
	
	/**
	 * Test get html.
	 */
	@Test
	public void testGetHtml() {
		String html = new Bo2ConfigurationSummaryServlet().getHtml();
		System.out.println(html);
		Assert.assertNotNull(html);
		Assert.assertTrue(html.length() > 0);
	}

}
