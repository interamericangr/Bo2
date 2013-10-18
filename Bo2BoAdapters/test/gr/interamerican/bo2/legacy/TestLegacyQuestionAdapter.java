package gr.interamerican.bo2.legacy;

import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeCmd;
import gr.interamerican.bo2.samples.MockFailingLegacyQuestion;
import gr.interamerican.bo2.samples.MockLegacyQuestion;

import org.junit.Test;

/**
 * Unit test for {@link TestLegacyQuestionAdapter}.
 */
public class TestLegacyQuestionAdapter {
	
	/**
	 * Unit test .
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test
	public void testAsk() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				MockLegacyQuestion mock = new MockLegacyQuestion();
				LegacyQuestionAdapter<String> adapter = new LegacyQuestionAdapter<String>(mock);
				adapter.setManagerName("LOCALDB"); //$NON-NLS-1$
				adapter.init(getProvider());				
				adapter.open();
				adapter.ask();	
				assertEquals(adapter.getAnswer(), mock.getAnswer());
			}
		}.execute();
	}
	
	/**
	 * Unit test .
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=LogicException.class)
	public void testAsk_throwingLogic() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				MockFailingLegacyQuestion mock = new MockFailingLegacyQuestion(true);
				LegacyQuestionAdapter<String> adapter = new LegacyQuestionAdapter<String>(mock);
				adapter.setManagerName("LOCALDB"); //$NON-NLS-1$
				adapter.init(getProvider());				
				adapter.open();
				adapter.ask();
			}
		}.execute();
	}
	
	/**
	 * Unit test .
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 * @throws UnexpectedException 
	 */
	@Test(expected=DataException.class)
	public void testAsk_throwingData() 
	throws UnexpectedException, DataException, LogicException {
		new AbstractBo2RuntimeCmd() {			
			@Override public void work() throws LogicException, 
			DataException, InitializationException, UnexpectedException {
				MockFailingLegacyQuestion mock = new MockFailingLegacyQuestion(false);
				LegacyQuestionAdapter<String> adapter = new LegacyQuestionAdapter<String>(mock);
				adapter.setManagerName("LOCALDB"); //$NON-NLS-1$
				adapter.init(getProvider());				
				adapter.open();
				adapter.ask();
			}
		}.execute();
	}
	

}
