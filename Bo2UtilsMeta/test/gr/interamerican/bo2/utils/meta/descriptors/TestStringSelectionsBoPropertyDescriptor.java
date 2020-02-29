package gr.interamerican.bo2.utils.meta.descriptors;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.meta.descriptors.StringSelectionsBoPropertyDescriptor.StringListFormatter;
import gr.interamerican.bo2.utils.meta.descriptors.StringSelectionsBoPropertyDescriptor.StringSelectionsValidator;
import gr.interamerican.bo2.utils.meta.exceptions.ParseException;
import gr.interamerican.bo2.utils.meta.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for {@link StringSelectionsBoPropertyDescriptor}
 */
public class TestStringSelectionsBoPropertyDescriptor {

	/**
	 * A {@link List} of {@link String}
	 */
	private List<String> availableValues;

	/**
	 * 
	 */
	@Before
	public void before() {
		availableValues = new ArrayList<String>();
		availableValues.add("TEST"); //$NON-NLS-1$
	}
	/**
	 * {@link StringSelectionsBoPropertyDescriptor#getAvailableValues()}
	 */
	@Test
	public void testGetAvailableValues() {
		StringSelectionsBoPropertyDescriptor classUnderTest = new StringSelectionsBoPropertyDescriptor(availableValues);
		assertTrue(classUnderTest.getAvailableValues().contains("TEST")); //$NON-NLS-1$
	}
	
	/**
	 * {@link StringSelectionsBoPropertyDescriptor#parse(String)}
	 * Null string - Empty List
	 * @throws ParseException 
	 */
	@Test
	public void testParse() throws ParseException {
		StringSelectionsBoPropertyDescriptor classUnderTest = new StringSelectionsBoPropertyDescriptor(availableValues);
		List<String> actual = classUnderTest.parse(null);
		assertTrue(actual.size() == 0);
	}
	
	/**
	 * {@link StringSelectionsBoPropertyDescriptor#parse(String)}
	 * @throws ParseException
	 */
	@Test
	public void testParse2() throws ParseException {
		StringSelectionsBoPropertyDescriptor classUnderTest = new StringSelectionsBoPropertyDescriptor(availableValues);
		List<String> actual = classUnderTest.parse("SAMPLE" + StringConstants.PIPE + "SAMPLE2"); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(actual.contains("SAMPLE")); //$NON-NLS-1$
		assertTrue(actual.contains("SAMPLE2")); //$NON-NLS-1$
	}
	
	/**
	 * {@link StringSelectionsBoPropertyDescriptor#getFormatter()}
	 */
	@Test
	public void testGetFormatter() {
		StringSelectionsBoPropertyDescriptor classUnderTest = new StringSelectionsBoPropertyDescriptor(availableValues);
		assertTrue(classUnderTest.getFormatter() instanceof StringListFormatter);
	}
	
	/**
	 * Test for {@link StringSelectionsValidator }
	 */
	public static class TestStringSelectionsValidator {
		
		/**
		 *Test for {@link StringSelectionsValidator#validate(List)}
		 * @throws ValidationException 
		 */
		@Test
		public void testValidate() throws ValidationException {
			StringSelectionsValidator classUnderTest = new StringSelectionsValidator(Arrays.asList("SAMPLE")); //$NON-NLS-1$
			classUnderTest.validate(null);
		}
		
		/**
		 *Test for {@link StringSelectionsValidator#validate(List)}
		 * @throws ValidationException 
		 */
		@Test(expected = ValidationException.class)
		public void testValidate2() throws ValidationException {
			StringSelectionsValidator classUnderTest = new StringSelectionsValidator(Arrays.asList("SAMPLE")); //$NON-NLS-1$
			classUnderTest.validate(Arrays.asList("bla")); //$NON-NLS-1$
		}
		
		/**
		 *Test for {@link StringSelectionsValidator#validate(List)}
		 * @throws ValidationException 
		 */
		@Test
		public void testValidate3() throws ValidationException {
			StringSelectionsValidator classUnderTest = new StringSelectionsValidator(Arrays.asList("SAMPLE")); //$NON-NLS-1$
			classUnderTest.validate(Arrays.asList("SAMPLE")); //$NON-NLS-1$
		}
	}
	
	/**
	 * Tests for {@link StringListFormatter}
	 */
	public static class TestStringListFormatter {
		
		/**
		 * Test for {@link StringListFormatter#format(List)}
		 */
		@Test
		public void testFormat() {
			StringListFormatter classUnderTest = new StringListFormatter();
			String actual = classUnderTest.format(Arrays.asList("SAMPLE", "MUMBLE")); //$NON-NLS-1$ //$NON-NLS-2$
			String[] split = actual.split("\\" + StringConstants.PIPE); //$NON-NLS-1$
			assertEquals("SAMPLE", split[0]); //$NON-NLS-1$
			assertEquals("MUMBLE", split[1]); //$NON-NLS-1$
		}
		
		/**
		 * Test for {@link StringListFormatter#format(List)}
		 * null
		 */
		@Test
		public void testFormat2() {
			StringListFormatter classUnderTest = new StringListFormatter();
			String actual = classUnderTest.format(null);
			assertEquals(StringConstants.EMPTY, actual);
		}
	}

}
