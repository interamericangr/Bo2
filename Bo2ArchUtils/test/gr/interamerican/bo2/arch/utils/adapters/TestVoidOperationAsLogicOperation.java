package gr.interamerican.bo2.arch.utils.adapters;

import static org.mockito.Mockito.*;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.utils.adapters.VoidOperation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link VoidOperationAsLogicOperation}.
 */
@SuppressWarnings("unchecked")
public class TestVoidOperationAsLogicOperation {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testConstructor() {		
		VoidOperation<Object> vo = mock(VoidOperation.class);
		VoidOperationAsLogicOperation<Object> voalo = new VoidOperationAsLogicOperation<Object>(vo);
		Assert.assertEquals(vo, voalo.operation);
	}
	
	/**
	 * Test for execute().
	 *
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test
	public void testExecute() throws DataException, LogicException {
		VoidOperation<Object> vo = mock(VoidOperation.class);
		VoidOperationAsLogicOperation<Object> voalo = new VoidOperationAsLogicOperation<Object>(vo);
		Object in = new Object();
		voalo.setInput(in);
		voalo.execute();
		verify(vo, times(1)).execute(in);
	}
	
	/**
	 * Test for execute().
	 *
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test(expected=DataException.class)
	public void testExecute_withDataException() throws DataException, LogicException {
		VoidOperation<Object> vo = mock(VoidOperation.class);
		Object in = new Object();
		RuntimeException rte = new RuntimeException(new DataException());
		doThrow(rte).when(vo).execute(in);		
		VoidOperationAsLogicOperation<Object> voalo = new VoidOperationAsLogicOperation<Object>(vo);		
		voalo.setInput(in);
		voalo.execute();		
	}
	
	/**
	 * Test for execute().
	 *
	 * @throws DataException the data exception
	 * @throws LogicException the logic exception
	 */
	@Test(expected=LogicException.class)
	public void testExecute_withLogicException() throws DataException, LogicException {
		VoidOperation<Object> vo = mock(VoidOperation.class);
		Object in = new Object();
		RuntimeException rte = new RuntimeException(new LogicException());
		doThrow(rte).when(vo).execute(in);		
		VoidOperationAsLogicOperation<Object> voalo = new VoidOperationAsLogicOperation<Object>(vo);		
		voalo.setInput(in);
		voalo.execute();		
	}
	

}
