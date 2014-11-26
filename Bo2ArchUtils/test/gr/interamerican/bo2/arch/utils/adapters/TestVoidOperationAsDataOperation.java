package gr.interamerican.bo2.arch.utils.adapters;

import static org.mockito.Mockito.*;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.utils.adapters.VoidOperation;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link VoidOperationAsDataOperation}.
 */
@SuppressWarnings("unchecked")
public class TestVoidOperationAsDataOperation {
	
	/**
	 * Test for execute().
	 */
	@Test
	public void testConstructor() {		
		VoidOperation<Object> vo = mock(VoidOperation.class);
		VoidOperationAsDataOperation<Object> voado = new VoidOperationAsDataOperation<Object>(vo);
		Assert.assertEquals(vo, voado.operation);
	}
	
	/**
	 * Test for execute().
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void testExecute() throws DataException, LogicException {
		VoidOperation<Object> vo = mock(VoidOperation.class);
		VoidOperationAsDataOperation<Object> voado = new VoidOperationAsDataOperation<Object>(vo);
		Object in = new Object();
		voado.setInput(in);
		voado.execute();
		verify(vo, times(1)).execute(in);
	}
	
	/**
	 * Test for execute().
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test(expected=DataException.class)
	public void testExecute_withDataException() throws DataException, LogicException {
		VoidOperation<Object> vo = mock(VoidOperation.class);
		Object in = new Object();
		RuntimeException rte = new RuntimeException(new DataException());
		doThrow(rte).when(vo).execute(in);		
		VoidOperationAsDataOperation<Object> voado = new VoidOperationAsDataOperation<Object>(vo);		
		voado.setInput(in);
		voado.execute();		
	}
	

}
