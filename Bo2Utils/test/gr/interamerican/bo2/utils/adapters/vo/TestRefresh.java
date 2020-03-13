package gr.interamerican.bo2.utils.adapters.vo;

import gr.interamerican.bo2.utils.adapters.vo.Refresh;
import gr.interamerican.bo2.utils.attributes.Refreshable;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test {@link Refresh}.
 */
public class TestRefresh {
	
	/**
	 * Test execute.
	 */
	@Test
	public void testExecute() {
		Refreshable refreshable = Mockito.mock(Refreshable.class);
		Refresh<Refreshable> refresh = new Refresh<Refreshable>();
		refresh.execute(refreshable);
		Mockito.verify(refreshable, Mockito.times(1)).refresh();
	}

}
