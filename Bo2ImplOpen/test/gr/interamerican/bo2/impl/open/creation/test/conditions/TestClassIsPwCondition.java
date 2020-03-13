package gr.interamerican.bo2.impl.open.creation.test.conditions;

import static org.junit.Assert.*;
import gr.interamerican.bo2.test.def.samples.SamplePo;
import gr.interamerican.bo2.test.def.samples.SamplePoKey;

import org.junit.Test;

/**
 * Unit test of {@link ClassIsPwCondition}.
 */
public class TestClassIsPwCondition {

	/**
	 * Test method for {@link ClassIsPwCondition#check(String)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testCheck() {
		ClassIsPwCondition cond = new ClassIsPwCondition();
		assertTrue(cond.check(SamplePo.class.getName()));
		assertFalse(cond.check(SamplePoKey.class.getName()));
		assertFalse(cond.check("java.lang.String"));
	}
}