package gr.interamerican.wicket.bo2.factories;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test {@link DefaultServicePanelFixtureResolver}
 */
public class TestDefaultServicePanelFixtureResolver {
	
	/**
	 * subject
	 */
	DefaultServicePanelFixtureResolver impl = new DefaultServicePanelFixtureResolver();
	
	/**
	 * test
	 */
	@Test
	public void testLifeCycle() {
		Assert.assertNull(impl.tlCache.get());
		
		ServicePanel fixture = Mockito.mock(ServicePanel.class);
		impl.registerFixture(StringConstants.COLON, fixture);
		
		Assert.assertNotNull(impl.tlCache.get());
		Assert.assertTrue(impl.tlCache.get().size()==1);
		
		Assert.assertTrue(impl.resolveFixture(StringConstants.COLON) == fixture);
		
		impl.clearFixturesCache();
		
		Assert.assertNull(impl.tlCache.get());
	}

}
