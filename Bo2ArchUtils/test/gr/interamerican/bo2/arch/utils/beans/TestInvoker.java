package gr.interamerican.bo2.arch.utils.beans;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;



import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.samples.bean.BeanWithCollections;
import gr.interamerican.bo2.utils.adapters.VoidOperation;

/**
 * Tests for {@link Invoker}. 
 */
public class TestInvoker {
	
	/**
	 * Mock operation.
	 */
	@SuppressWarnings("unchecked")
	VoidOperation<Object> vo = mock(VoidOperation.class);
	
	/**
	 * Executes <code>vo</code> on an object.
	 * 
	 * @param o
	 */
	void doSomething(Object o) {
		vo.execute(o);
	}
	
	
	
	
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		Invoker invoker = new Invoker(this);
		Assert.assertEquals(this, invoker.owner);
	}
	
	/**
	 * Tests the invokeOnCollection(string, collection).
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void testinvokeOnCollection_withCollection() throws DataException, LogicException {
		Invoker invoker = new Invoker(this);
		List<Object> list = new ArrayList<Object>();
		list.add(new Object());
		list.add(new Object());
		invoker.invokeOnCollection("doSomething", list); //$NON-NLS-1$
		for (Object object : list) {
			verify(vo, times(1)).execute(object);
		}
	}
	
	/**
	 * Tests the invokeOnCollection(string, collection).
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	@Test
	public void testinvokeOnCollection_withNestedProperty() throws DataException, LogicException {
		Invoker invoker = new Invoker(this);
		BeanWithCollections<Object> bean = new BeanWithCollections<Object>();		
		List<Object> list = new ArrayList<Object>();
		list.add(new Object());
		list.add(new Object());
		bean.setList(list);
		invoker.invokeOnCollection("doSomething", bean, "list"); //$NON-NLS-1$ //$NON-NLS-2$
		for (Object object : list) {
			verify(vo, times(1)).execute(object);
		}
	}

}
