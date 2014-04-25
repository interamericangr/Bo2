package gr.interamerican.wicket.bo2.factories;

import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBack;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDef;
import gr.interamerican.wicket.markup.html.panel.back.ServicePanelWithBackDefImpl;
import gr.interamerican.wicket.samples.factories.ServicePanelIdProvider;
import gr.interamerican.wicket.test.WicketTest;

import org.apache.wicket.markup.html.panel.Panel;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test of {@link ServicePanelFactoryImpl}
 */
public class TestServicePanelFactoryImpl extends WicketTest {
	
	/**
	 * subject
	 */
	ServicePanelFactoryImpl impl = new ServicePanelFactoryImpl();
	
	/**
	 * Unit test for createPanel()
	 */
	@Test
	public void testCreatePanel() {
		ServicePanelWithBackDef def = new ServicePanelWithBackDefImpl();
		def.setWicketId("xx"); //$NON-NLS-1$
		def.setPanelId(ServicePanelIdProvider.TEST_PANEL_ID);
		Panel panel = PanelFactory.create(def);
		Assert.assertTrue(panel instanceof ServicePanelWithBack);
	}
	
	/**
	 * Unit test for getFixtureResolver()
	 */
	@Test
	public void testGetFixtureResolver() {
		Assert.assertNotNull(impl.getFixtureResolver());
	}

}
