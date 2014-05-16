package gr.interamerican.bo2.creation;


import java.util.HashMap;
import java.util.Map;

import javassist.ClassClassPath;
import javassist.NotFoundException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test {@link Bo2Creation}
 */
public class TestBo2Creation {
	
	/**
	 * Test get
	 * @throws NotFoundException
	 */
	@Test
	public void testGet() throws NotFoundException {
		Assert.assertNotNull(Bo2Creation.get(String.class.getName()));
	}
	
	/**
	 * Test flushClassPool
	 */
	@Test
	public void testFlushClassPool() {
		Map<Class<?>, ClassClassPath> appended = new HashMap<Class<?>, ClassClassPath>(Bo2Creation.APPENDED_CLASSPATHS);
		Bo2Creation.flushClassPool();
		
		Assert.assertTrue(Bo2Creation.APPENDED_CLASSPATHS.isEmpty());
		Assert.assertNull(Bo2Creation.BO2_CLASSPOOL);
		
		for(Class<?> clazz : appended.keySet()) {
			Bo2Creation.appendClassPath(clazz);
		}
	}

}
