/**
 *
 */
package gr.interamerican.bo2.impl.open.records;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import org.junit.Assert;
import org.junit.Test;


/**
 * The Class TestFixedLengthRecord.
 */
public class TestFixedLengthRecord {

	/** lengths. */
	private static int[] LENGTHS = new int[] { 1, 2, 3, 4 };

	/**
	 * Test method for {@link gr.interamerican.bo2.impl.open.records.FixedLengthRecord#FixedLengthRecord(int[])}.
	 */
	@Test
	public void testFixedLengthRecordIntArray() {
		FixedLengthRecord fix = new FixedLengthRecord(LENGTHS);
		String s=StringConstants.EMPTY;
		Assert.assertEquals(10, fix.getRecordLength());
		for (int i = 0; i < fix.getRecordLength(); i++) {
			s += StringConstants.SPACE;
		}
		Assert.assertEquals(s, fix.getBuffer());
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.impl.open.records.FixedLengthRecord#FixedLengthRecord(int[], java.lang.String[])}.
	 */
	@Test
	public void testFixedLengthRecordIntArrayStringArray() {
		String[] input = new String[] { "a", "ab", "abc", "abcd" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		FixedLengthRecord fix = new FixedLengthRecord(LENGTHS, input);
		Assert.assertEquals(StringUtils.concat(input), fix.getBuffer());
		String[] input2 = new String[] { "abcd", "abcd", "abcd", "abcd" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		fix = new FixedLengthRecord(LENGTHS, input2);
		Assert.assertEquals(StringUtils.concat(input), fix.getBuffer());
		String[] input3 = new String[] { "a", "a", "a", "a" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		fix = new FixedLengthRecord(LENGTHS, input3);
		Assert.assertEquals("aa a  a   ", fix.getBuffer()); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link gr.interamerican.bo2.impl.open.records.FixedLengthRecord#setBuffer(java.lang.String)}.
	 */
	@Test
	public void testSetBuffer() {
		FixedLengthRecord fix = new FixedLengthRecord(LENGTHS);
		fix.setBuffer(null);
		String s = StringConstants.EMPTY;
		for (int i = 0; i < fix.getRecordLength(); i++) {
			s += StringConstants.SPACE;
		}
		Assert.assertEquals(s, fix.getBuffer());
		fix.setBuffer(s);
		Assert.assertEquals(s, fix.getBuffer());
		fix.setBuffer(StringConstants.SPACE);
		Assert.assertEquals(s, fix.getBuffer());
	}
}
