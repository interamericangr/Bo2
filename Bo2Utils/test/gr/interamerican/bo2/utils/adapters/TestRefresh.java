package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.attributes.Refreshable;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test {@link Refresh}.
 */
public class TestRefresh {
	
	/**
	 * 
	 */
	@Test
	public void testExecute() {
		Refreshable refreshable = Mockito.mock(Refreshable.class);
		Refresh<Refreshable> refresh = new Refresh<Refreshable>();
		refresh.execute(refreshable);
		Mockito.verify(refreshable, Mockito.times(1)).refresh();
	}

}
