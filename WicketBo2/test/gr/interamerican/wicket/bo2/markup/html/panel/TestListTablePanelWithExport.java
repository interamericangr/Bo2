package gr.interamerican.wicket.bo2.markup.html.panel;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.junit.Test;

import gr.interamerican.bo2.samples.bean.BeanWith1Field;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Tests {@link ListTablePanelWithExport}.
 */
@Deprecated
public class TestListTablePanelWithExport extends WicketTest {

	/**
	 * Test method for
	 * {@link ListTablePanelWithExport#ListTablePanelWithExport(String, Class, List, String[], String, Component)}.
	 */
	@Test
	public void testListTablePanelWithExport() {
		startAndTestComponent(ListTablePanelWithExport.class);
	}

	@Override
	protected Component initializeComponent(String wicketId) {
		return new ListTablePanelWithExport<BeanWith1Field>(wicketId, BeanWith1Field.class, new ArrayList<>(),
				new String[] {}, null, null);
	}
}
