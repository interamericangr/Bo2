package gr.interamerican.bo2.utils.meta.formatters;

import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

import org.junit.Assert;
import org.junit.Test;

/**
 * test {@link BeanCsvFormatter}
 */
public class TestBeanCsvFormatter {
	
	/**
	 * test format
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFormat() {
		BeanWith2Fields bean = new BeanWith2Fields("s", 1);
		BeanCsvFormatter<BeanWith2Fields> formatter = new BeanCsvFormatter<BeanWith2Fields>(new String[]{"field1", "field2"}, false);
		String expected = "s;1";
		String actual = formatter.format(bean);
		Assert.assertEquals(expected, actual);
	}

}
