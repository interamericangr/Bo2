package gr.interamerican.bo2.utils.beans;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link SegmentedArea}.
 */
public class TestSegmentedArea {
	
	
	/**
	 * Fixes an area separating it to 3 rows and 2 columns, 
	 * associating each cell with a string that contains its
	 * dimensions.
	 * 
	 * @param area
	 */
	@SuppressWarnings("nls")
	void fixArea(SegmentedArea<Integer, Long, String> area) {
		area.setValue(0, 5, 0L, 5L, "1-1");
		area.setValue(6, 10, 0L, 5L, "2-1");
		area.setValue(11, 15, 0L, 5L, "3-1");
		area.setValue(0, 5, 6L, 20L, "1-2");
		area.setValue(6, 10, 6L, 20L, "2-2");
		area.setValue(11, 15, 6L, 20L, "3-2");	
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor() {
		SegmentedArea<Integer, Long, String> area = 
			new SegmentedArea<Integer, Long, String>();
		Assert.assertNotNull(area.xSegments);
	}
	
	/**
	 * tests setValue().
	 */
	@Test
	public void testSetValue() {
		SegmentedArea<Integer, Long, String> area = 
				new SegmentedArea<Integer, Long, String>();
		fixArea(area);	
		Assert.assertEquals(area.xSegments.ranges.size(), 3);
	}
	
	/**
	 * tests setValue() with x values overlapping.
	 */
	@Test(expected=RuntimeException.class)
	public void testSetValue_withOverlapping_X() {
		SegmentedArea<Integer, Long, String> area = 
				new SegmentedArea<Integer, Long, String>();
		fixArea(area);	
		area.setValue(6, 12, 21L, 50L, "X"); //$NON-NLS-1$
	}
	
	/**
	 * tests setValue() with y values overlapping.
	 */
	@Test(expected=RuntimeException.class)
	public void testSetValue_withOverlapping_Y() {
		SegmentedArea<Integer, Long, String> area = 
				new SegmentedArea<Integer, Long, String>();
		fixArea(area);	
		area.setValue(0, 5, 11L, 50L, "X"); //$NON-NLS-1$
	}
	
	/**
	 * tests getValue(x,y).
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetValue() {
		SegmentedArea<Integer, Long, String> area = 
				new SegmentedArea<Integer, Long, String>();
		fixArea(area);
		String actual;
		actual = area.getValue(2, 2L);
		Assert.assertEquals("1-1", actual);
		actual = area.getValue(4, 2L);
		Assert.assertEquals("1-1", actual);
		actual = area.getValue(14, 2L);
		Assert.assertEquals("3-1", actual);
		actual = area.getValue(14, 12L);
		Assert.assertEquals("3-2", actual);
		actual = area.getValue(-6, 12L);
		Assert.assertNull(actual);
		actual = area.getValue(52, 12L);
		Assert.assertNull(actual);
		actual = area.getValue(2, 52L);
		Assert.assertNull(actual);
	}
	
	/**
	 * tests getContains(x,y).
	 */	
	@Test
	public void testContains() {
		SegmentedArea<Integer, Long, String> area = 
				new SegmentedArea<Integer, Long, String>();
		fixArea(area);
		Assert.assertTrue(area.contains(2, 2L));
		Assert.assertTrue(area.contains(4, 2L));
		Assert.assertFalse(area.contains(-6, 12L));
		Assert.assertFalse(area.contains(2, 52L));	
	}
	
	/**
	 * tests setValue().
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetValues() {
		SegmentedArea<Integer, Long, String> area = 
				new SegmentedArea<Integer, Long, String>();
		area.setValue(0, 5, 0L, 5L, "A");
		area.setValue(6, 10, 0L, 5L, "B");
		area.setValue(11, 15, 0L, 5L, "A");
		area.setValue(0, 5, 6L, 20L, null);
		area.setValue(6, 10, 6L, 20L, null);
		area.setValue(11, 15, 6L, 20L, "C");
		Set<String> values = area.getValues();
		Assert.assertEquals(4, values.size());
		Assert.assertTrue(values.contains("A"));
		Assert.assertTrue(values.contains("B"));
		Assert.assertTrue(values.contains("C"));
		Assert.assertTrue(values.contains(null));
	}
	
	/**
	 * tests getSegmentsX().
	 */	
	@Test
	public void testGetSegmentsX() {
		SegmentedArea<Integer, Long, String> area = 
				new SegmentedArea<Integer, Long, String>();
		fixArea(area);
		Set<Range<Integer>> xseg = area.getSegmentsX();
		Assert.assertEquals(3, xseg.size());
		Assert.assertTrue(xseg.contains(new Range<Integer>(0,5)));
		Assert.assertTrue(xseg.contains(new Range<Integer>(6,10)));
		Assert.assertTrue(xseg.contains(new Range<Integer>(11,15)));
	}
	
	/**
	 * tests getSegmentsY().
	 */	
	@Test
	public void testGetSegmentsY() {
		SegmentedArea<Integer, Long, String> area = 
				new SegmentedArea<Integer, Long, String>();
		fixArea(area);
		Set<Range<Long>> yseg = area.getSegmentsY();
		Assert.assertEquals(2, yseg.size());
		Assert.assertTrue(yseg.contains(new Range<Long>(0L,5L)));
		Assert.assertTrue(yseg.contains(new Range<Long>(6L,20L)));
	}

}
