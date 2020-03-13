package gr.interamerican.bo2.utils.matching;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link EqualsMatchingRule}.
 */
public class TestEqualsMatchingRule {


	/**
	 * Tests isMatch with null arguments.
	 */
	@Test
	public void testIsMatch_null_match() {
		EqualsMatchingRule<Object> rule = new EqualsMatchingRule<Object>();
		boolean b = rule.isMatch(null, null);
		Assert.assertTrue(b);
	}
	
	/**
	 * Tests isMatch with null and a not matching argument.
	 */
	@Test
	public void testIsMatch_null_nomatch() {
		EqualsMatchingRule<Object> rule = new EqualsMatchingRule<Object>();
		boolean b = rule.isMatch(null, new Object());
		Assert.assertFalse(b);
	}

	/**
	 * Tests isMatch with matching arguments.
	 */
	@Test
	public void testIsMatch_match() {
		EqualsMatchingRule<Object> rule = new EqualsMatchingRule<Object>();
		Object o = new Object();
		boolean b = rule.isMatch(o, o);
		Assert.assertTrue(b);
	}
	
	/**
	 * Tests isMatch with no matching arguments.
	 */
	@Test
	public void testIsMatch_nomatch() {
		EqualsMatchingRule<Object> rule = new EqualsMatchingRule<Object>();		
		boolean b = rule.isMatch(new Object(), new Object());
		Assert.assertFalse(b);
		
	}

	

}
