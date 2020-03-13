package gr.interamerican.bo2.impl.open.creation.test.conditions;

import static org.junit.Assert.*;
import gr.interamerican.bo2.test.def.samples.SamplePo;
import gr.interamerican.bo2.test.impl.samples.SamplePoImpl;

import org.junit.Test;

/**
 * Unit test of {@link ClassIsInterface}.
 */
public class TestClassIsInterface {

	/**
	 * Test method for {@link ClassIsInterface#check(String)}.
	 */
	@Test
	public void testCheck() {
		ClassIsInterface cond = new ClassIsInterface();
		assertTrue(cond.check(SamplePo.class.getName()));
		assertFalse(cond.check(SamplePoImpl.class.getName()));
	}
}