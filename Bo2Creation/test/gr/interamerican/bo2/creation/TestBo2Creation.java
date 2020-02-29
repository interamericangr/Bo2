package gr.interamerican.bo2.creation;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.NotFoundException;

/**
 * Test {@link Bo2Creation}.
 */
public class TestBo2Creation {
	
	/**
	 * Test get.
	 *
	 * @throws NotFoundException the not found exception
	 */
	@Test
	public void testGet() throws NotFoundException {
		assertNotNull(Bo2Creation.get(String.class.getName()));
	}
	
	/**
	 * Test flushClassPool.
	 */
	@Test
	public void testFlushClassPool() {
		assertNotNull(Bo2Creation.bo2ClassPool());
		
		Map<Class<?>, ClassClassPath> appended = new HashMap<Class<?>, ClassClassPath>(Bo2Creation.APPENDED_CLASSPATHS);
		ClassPool oldPool = Bo2Creation.BO2_CLASSPOOL;
		
		assertNotNull(oldPool);
		Bo2Creation.flushClassPool();
		
		assertTrue(Bo2Creation.APPENDED_CLASSPATHS.isEmpty());
		assertNull(Bo2Creation.BO2_CLASSPOOL);
		
		Bo2Creation.BO2_CLASSPOOL = oldPool;
		for(Class<?> clazz : appended.keySet()) {
			Bo2Creation.appendClassPath(clazz);
		}
	}

}
