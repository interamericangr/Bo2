package gr.interamerican.wicket.callback;

import static org.mockito.Mockito.*;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDefImpl;
import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.junit.Test;
/**
 * Unit Test of {@link RenderBeanPanel}.
 */
@Deprecated
public class TestRenderBeanPanel {

	/**
	 * Test method for {@link RenderBeanPanel#callBack(AjaxRequestTarget)}.
	 */
	@Test
	public void testCallBackAjaxRequestTarget() {
		// setup
		BeanPanelDef<String> def = new CrudPickerPanelDefImpl<String>();
		ServicePanel servicePanel = mock(ServicePanel.class);
		def.setServicePanel(servicePanel);
		// test
		RenderBeanPanel<String> toTest = new RenderBeanPanel<String>(def);
		AjaxRequestTarget target = mock(AjaxRequestTarget.class);
		toTest.callBack(target);
		// verification
		verify(target).add(servicePanel);
	}
}