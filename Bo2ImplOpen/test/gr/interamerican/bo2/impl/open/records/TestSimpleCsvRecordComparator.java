package gr.interamerican.bo2.impl.open.records;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link SimpleCsvRecordComparator}
 */
@SuppressWarnings("nls")
public class TestSimpleCsvRecordComparator {
	
	/**
	 * Test compare
	 */
	@Test
	public void testCompare() {
		/*
		 * 0
		 */
		SimpleCsvRecordComparator subject = new SimpleCsvRecordComparator();
		String[] records = new String[]{"a", "b", "c"};
		CsvRecord o1 = new CsvRecord(records);
		CsvRecord o2 = new CsvRecord(records);
		Assert.assertEquals(0, subject.compare(o1, o2));
		
		/*
		 * 1
		 */
		subject = new SimpleCsvRecordComparator();
		records = new String[]{"a", "b", "c"};
		o1 = new CsvRecord(records);
		records = new String[]{"b", "b", "c"};
		o2 = new CsvRecord(records);
		Assert.assertEquals(-1, subject.compare(o1, o2));
		
		subject = new SimpleCsvRecordComparator(0,1,2);
		records = new String[]{"a", "b", "c"};
		o1 = new CsvRecord(records);
		records = new String[]{"a", "b", "d"};
		o2 = new CsvRecord(records);
		Assert.assertEquals(-1, subject.compare(o1, o2));
		
		/*
		 * -1
		 */
		subject = new SimpleCsvRecordComparator();
		records = new String[]{"b", "b", "c"};
		o1 = new CsvRecord(records);
		records = new String[]{"a", "b", "c"};
		o2 = new CsvRecord(records);
		Assert.assertEquals(1, subject.compare(o1, o2));
		
		subject = new SimpleCsvRecordComparator(0,1,2);
		records = new String[]{"a", "b", "d"};
		o1 = new CsvRecord(records);
		records = new String[]{"a", "b", "c"};
		o2 = new CsvRecord(records);
		Assert.assertEquals(1, subject.compare(o1, o2));
		
	}
	
	/**
	 * Test compare fails
	 */
	@Test(expected=IllegalStateException.class)
	public void testCompare_failsSizeMismatch() {
		String[] records = new String[]{"a", "b", "c"};
		CsvRecord o1 = new CsvRecord(records);
		records = new String[]{"a", "b", "c", "d"};
		CsvRecord o2 = new CsvRecord(records);
		
		new SimpleCsvRecordComparator().compare(o1, o2);
	}
	
	/**
	 * Test compare fails
	 */
	@Test(expected=IllegalStateException.class)
	public void testCompare_failsNonExistantIndex() {
		String[] records = new String[]{"a", "b", "c"};
		CsvRecord o1 = new CsvRecord(records);
		records = new String[]{"a", "b", "c", "d"};
		CsvRecord o2 = new CsvRecord(records);
		
		new SimpleCsvRecordComparator(3).compare(o1, o2);
	}

}
