package gr.interamerican.bo2.utils.meta.formatters;

import static org.junit.Assert.*;
import gr.interamerican.bo2.samples.bean.BeanWith2Fields;

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
	public void testFormat_1() {
		BeanWith2Fields bean = new BeanWith2Fields("s;m", 1);
		BeanCsvFormatter<BeanWith2Fields> formatter = new BeanCsvFormatter<BeanWith2Fields>(new String[]{"field1", "field2"}, false);
		String expected = "s m;1";
		String actual = formatter.format(bean);
		assertEquals(expected, actual);
	}
	
	/**
	 * test format
	 */
	@SuppressWarnings("nls")
	@Test
	public void testFormat_2() {
		BeanWith2Fields bean = new BeanWith2Fields("s|", 1);
		BeanCsvFormatter<BeanWith2Fields> formatter = new BeanCsvFormatter<BeanWith2Fields>(new String[]{"field1", "field2"}, false);
		formatter.setDelimiter("|");
		formatter.setRemoveDelimiterFromColumns(false);
		String expected = "s||1";
		String actual = formatter.format(bean);
		assertEquals(expected, actual);
	}
	
	/**
	 * test format
	 */
	@SuppressWarnings("nls")
	@Test
	public void testRemoveDelimiterFromColumns() {
		String[] original = new String[] {"aaa;a","bbb", "c;;;c;c"};
		String[] expected = new String[] {"aaa a","bbb", "c   c c"};
		BeanCsvFormatter<BeanWith2Fields> formatter = new BeanCsvFormatter<BeanWith2Fields>(new String[]{"field1", "field2"}, false);
		String[] actual = formatter.removeDelimiterFromColumns(original);
		assertArrayEquals(expected, actual);
	}
}