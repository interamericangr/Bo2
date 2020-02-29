package gr.interamerican.bo2.impl.open.utils;

import gr.interamerican.bo2.impl.open.records.CsvRecord;

import org.junit.Assert;
import org.junit.Test;


/**
 * The Class TestCopyRecordUtils.
 */
public class TestCopyRecordUtils {

	/**
	 * Count of columns of typical CsvRecord used in tests.
	 */
	private static final int COLUMN_COUNT = 5;
	
	/** 1st field. */
	private static final int F1 = 0;

	/**
	 * Test copy csv record.
	 */
	@Test
	public void testCopyCsvRecord() {
		CsvRecord sample = new CsvRecord(COLUMN_COUNT);
		sample.setString(F1, "aaaa"); //$NON-NLS-1$
		CsvRecord copy = CopyRecordUtils.copyCsvRecord(sample);
		Assert.assertEquals("aaaa", copy.getString(F1)); //$NON-NLS-1$
		Assert.assertNotEquals(sample, copy);
		Assert.assertEquals(sample.getBuffer(), copy.getBuffer());
	}
}
