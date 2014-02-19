package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
import gr.interamerican.bo2.utils.conditions.Condition;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link ConditionValidator}.
 */
public class TestConditionValidator {
	
	/**
	 * tests the constructor.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConstructor() {
		String message = "X"; //$NON-NLS-1$
		Condition<Object> condition= Mockito.mock(Condition.class);
		ConditionValidator<Object> validator = 
			new ConditionValidator<Object>(message, condition);
		Assert.assertEquals(message, validator.message);
		Assert.assertEquals(condition, validator.condition);		
	}
	
	/**
	 * tests the constrsetValidatedObjectuctor.
	 */	
	@Test
	public void testSetValidatedObject() {		
		ConditionValidator<Object> validator = new ConditionValidator<Object>(null,null);
		Object o = new Object();
		validator.setValidatedObject(o);
		Assert.assertEquals(o, validator.validatedObject);		
	}
	
	/**
	 * tests the getValidatedObject.
	 */	
	@Test
	public void testGetValidatedObject() {		
		ConditionValidator<Object> validator = new ConditionValidator<Object>(null,null);
		validator.validatedObject = new Object();		
		Assert.assertEquals(validator.validatedObject, validator.getValidatedObject());		
	}
	
	/**
	 * Tests the apply.
	 * 
	 * @throws DataException 
	 * @throws RuleException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testApply_passing() throws RuleException, DataException {
		String message = "X"; //$NON-NLS-1$
		Condition<Object> condition= Mockito.mock(Condition.class);
		Mockito.when(condition.check(Mockito.anyObject())).thenReturn(true);
		ConditionValidator<Object> validator = 
			new ConditionValidator<Object>(message, condition);
		validator.setValidatedObject(new Object());
		validator.apply();
		/* no assertion, validationshould pass */		
	}
	
	/**
	 * Tests the apply.
	 * 
	 * @throws DataException 
	 * @throws RuleException 
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=RuleException.class)
	public void testApply_throwing() throws RuleException, DataException {
		String message = "X"; //$NON-NLS-1$
		Condition<Object> condition= Mockito.mock(Condition.class);
		Mockito.when(condition.check(Mockito.anyObject())).thenReturn(false);
		ConditionValidator<Object> validator = 
			new ConditionValidator<Object>(message, condition);
		validator.setValidatedObject(new Object());
		validator.apply();		
	}
	
	

}
