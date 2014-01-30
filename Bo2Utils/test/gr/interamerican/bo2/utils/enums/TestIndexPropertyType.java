package gr.interamerican.bo2.utils.enums;

import static gr.interamerican.bo2.utils.enums.IndexPropertyType.INTEGER;
import static gr.interamerican.bo2.utils.enums.IndexPropertyType.LONG;
import static gr.interamerican.bo2.utils.enums.IndexPropertyType.SHORT;
import static gr.interamerican.bo2.utils.enums.IndexPropertyType.getIndexPropertyTypeFor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link IndexPropertyType}.
 */
public class TestIndexPropertyType {
	
	
	/**
	 * Test for getIndexPropertyTypeFor(clazz)
	 */
	@Test
	public void testGetIndexPropertyTypeFor() {
		Assert.assertEquals(LONG, getIndexPropertyTypeFor(long.class));
		Assert.assertEquals(INTEGER, getIndexPropertyTypeFor(Integer.class));
		Assert.assertEquals(SHORT, getIndexPropertyTypeFor(Short.class));
		Assert.assertNull(getIndexPropertyTypeFor(String.class));
	}
	

}
