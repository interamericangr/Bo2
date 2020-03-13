package gr.interamerican.bo2.utils.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.utils.StringUtils;

import org.junit.Test;

/**
 * Unit test for ({@link Triplet}.
 */
public class TestTriplet {

	/**
	 * Tests the three arguments constructor.
	 */
	@Test
	public void testConstructor_WithThreeArguments(){
		Triplet<Long,Integer,String> triplet = new Triplet<Long, Integer, String>(2L, 2, "2"); //$NON-NLS-1$
		assertEquals(new Long(2L), triplet.getLeft());
		assertEquals(new Integer(2),triplet.getMiddle());
		assertEquals("2", triplet.getRight()); //$NON-NLS-1$
	}
	
	/**
	 * Test equals().
	 */
	@Test
	public void testEquals(){
		Integer l = 2;
		Integer m = 3;
		Integer r = 4;
		Triplet<Integer,Integer,Integer> t1 = new Triplet<Integer, Integer, Integer>(l, m, r);
		Triplet<Integer,Integer,Integer> t2 = new Triplet<Integer, Integer, Integer>(l, m, r);
		assertTrue(t1.equals(t2));
		assertTrue(t1.hashCode()==t2.hashCode());
		
		Triplet<Integer,Integer,Integer> t3 = new Triplet<Integer, Integer, Integer>(8, 3, 4);
		assertFalse(t1.equals(t3));
		
		assertFalse(t1.equals(null));
		assertFalse(t1.equals(new Object()));
	}
	
	/**
	 * test equals_WithNulls().
	 */
	@Test
	public void testEquals_WithNulls(){
		Triplet<Integer, Integer, Integer> t1 = new Triplet<Integer, Integer, Integer>();
		Triplet<Integer, Integer, Integer> t2 = new Triplet<Integer, Integer, Integer>();
		assertTrue(t1.equals(t2));
		assertTrue(t1.hashCode()==t2.hashCode());
	}
	
	/**
	 * Test hashcode().
	 */
	@Test
	public void testHashcode(){
		Integer l = 2;
		Integer m = 3;
		Integer r = 4;
		
		Triplet<Integer, Integer, Integer> t1 = new Triplet<Integer, Integer, Integer>(l, m, r);
		Triplet<Integer, Integer, Integer> t2 = new Triplet<Integer, Integer, Integer>(l, m, r);
		assertEquals(t1.hashCode(), t2.hashCode());
		
		Triplet<Integer, Integer, Integer> t3 = new Triplet<Integer, Integer, Integer>(8, 4, 2);
		assertTrue(t1.hashCode()!=t3.hashCode());
	}
	
	/**
	 * test hashcode_WithNulls().
	 */
	@Test
	public void testHashcode_WithNulls(){
		Integer l = 2;
		Triplet<Integer, Integer, Integer> t1 = new Triplet<Integer, Integer, Integer>();
		Triplet<Integer, Integer, Integer> t2 = new Triplet<Integer, Integer, Integer>();
		t1.setLeft(l);
		t2.setLeft(l);
		assertEquals(t1.hashCode(),t2.hashCode());
	}
	
	/**
	 * Unit test for toString().
	 */
	@Test
	public void testToString(){
		testToString(new Triplet<Object, Object, Object>());
		testToString(new Triplet<Object, Object, Object>(new Object(),new Object(), "ss")); //$NON-NLS-1$
	}
	
	/**
	 * Unit tests for toString().
	 *
	 * @param triplet the triplet
	 */
	private void testToString(Triplet<?,?,?> triplet){
		String string = triplet.toString();
		assertTrue(string.startsWith("[")); //$NON-NLS-1$
		assertTrue(string.endsWith("]")); //$NON-NLS-1$
		assertTrue(string.indexOf(',')!=0);
		String left = StringUtils.toString(triplet.getLeft());
		assertTrue(string.indexOf(left)==1);
		String middle = StringUtils.toString(triplet.getMiddle());
		assertTrue(string.indexOf(middle)!=0);
		String right = StringUtils.toString(triplet.getRight());
		assertTrue(string.indexOf(right)!=0);
	}
	
	/**
	 * Unit test for toString().
	 */
	@Test
	public void testTriplet(){
		Object[] array = {new Object(),new Object(), new Object()};
		Triplet<Object, Object, Object> actual = Triplet.triplet(array);
		assertNotNull(actual);
		Triplet<Object, Object, Object> expected = new Triplet<Object, Object, Object>(array[0], array[1], array[2]);
		assertEquals(expected, actual);
	}
	
}
