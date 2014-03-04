package gr.interamerican.bo2.creation.creators;

import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.samples.interfaces.SmartCalc;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link FunctionalMockImplementorForInterfaces}.
 */
public class TestFunctionalMockImplementorForInterfaces  
extends BaseTestForInterfaceImplementors {

	/**
	 * Creates a new TestImplementorForInterfaces object. 
	 */
	public TestFunctionalMockImplementorForInterfaces() {
		super(new FunctionalMockImplementorForInterfaces());
	}
	
	@Test
	@Override
	public void testCreate_withMethods() throws ClassCreationException {
		Class<?> clazz =creator.create(SmartCalc.class);		 
		try {
			SmartCalc calc = (SmartCalc) clazz.newInstance();
			calc.setBeanId(5);
			Assert.assertEquals(Integer.valueOf(5), calc.getBeanId());
			calc.add(BigDecimal.TEN);
			calc.addNumbers();			
		} catch (InstantiationException e) {
			Assert.fail(e.toString());
		} catch (IllegalAccessException e) {
			Assert.fail(e.toString());
		}
	}


}
