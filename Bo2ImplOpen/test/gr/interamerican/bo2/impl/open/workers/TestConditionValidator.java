package gr.interamerican.bo2.impl.open.workers;

import static org.mockito.Mockito.*;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
import gr.interamerican.bo2.utils.beans.MessagesBean;
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
	public void testConstructor_1() {
		String message = "X"; //$NON-NLS-1$
		Condition<Object> condition= Mockito.mock(Condition.class);
		ConditionValidator<Object> validator = 
			new ConditionValidator<Object>(message, condition);
		Assert.assertEquals(message, validator.messageKey);
		Assert.assertNull(validator.messages);
		Assert.assertFalse(validator.failOn);
		Assert.assertEquals(condition, validator.condition);		
	}
	
	/**
	 * tests the constructor.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConstructor_2() {
		String message = "X"; //$NON-NLS-1$
		Condition<Object> condition= Mockito.mock(Condition.class);
		MessagesBean bean = Mockito.mock(MessagesBean.class);
		boolean failOn = true;
		ConditionValidator<Object> validator = 
			new ConditionValidator<Object>(condition, failOn, bean, message);
		Assert.assertEquals(message, validator.messageKey);
		Assert.assertEquals(bean, validator.messages);
		Assert.assertEquals(failOn, validator.failOn);		
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
	 * tests the constrsetValidatedObjectuctor.
	 */	
	@Test
	public void testSetInput() {		
		ConditionValidator<Object> validator = new ConditionValidator<Object>(null,null);
		Object o = new Object();
		validator.setInput(o);
		Assert.assertEquals(o, validator.validatedObject);		
	}
	
	/**
	 * Tests the getMessage().
	 * 
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetMessage() {
		String expected = "foo";
		Object o = new Object();
		String key = "key";		
		MessagesBean bean = mock(MessagesBean.class);
		when(bean.getString(key, o)).thenReturn(expected);		
		ConditionValidator<Object> validator = 
			new ConditionValidator<Object>(null, true, bean, key);
		validator.setInput(o);
		String actual = validator.getMessage();
		Assert.assertEquals(expected, actual);
	}	
	
	/**
	 * Tests the apply().
	 * 
	 * @throws DataException 
	 * @throws RuleException 
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=RuleException.class)
	public void testApply_failOnTrue_true() throws RuleException, DataException {
		MessagesBean bean = mock(MessagesBean.class);
		String key = "X"; //$NON-NLS-1$		
		Condition<Object> condition= Mockito.mock(Condition.class);
		Mockito.when(condition.check(Mockito.anyObject())).thenReturn(true);		
		ConditionValidator<Object> validator = 
			new ConditionValidator<Object>(condition, true, bean, key);
		validator.setValidatedObject(new Object());
		validator.apply();		
	}
	
	/**
	 * Tests the apply().
	 * 
	 * @throws DataException 
	 * @throws RuleException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testApply_failOnTrue_false() throws RuleException, DataException {
		MessagesBean bean = mock(MessagesBean.class);
		String key = "X"; //$NON-NLS-1$		
		Condition<Object> condition= Mockito.mock(Condition.class);
		Mockito.when(condition.check(Mockito.anyObject())).thenReturn(false);		
		ConditionValidator<Object> validator = 
			new ConditionValidator<Object>(condition, true, bean, key);
		validator.setValidatedObject(new Object());
		validator.apply();	
		/* nothing should happen */
	}
	
	/**
	 * Tests apply().
	 * 
	 * @throws DataException 
	 * @throws RuleException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testApply_failOnFalse_true() throws RuleException, DataException {
		MessagesBean bean = mock(MessagesBean.class);
		String key = "X"; //$NON-NLS-1$		
		Condition<Object> condition= Mockito.mock(Condition.class);
		Mockito.when(condition.check(Mockito.anyObject())).thenReturn(true);		
		ConditionValidator<Object> validator = 
			new ConditionValidator<Object>(condition, false, bean, key);
		validator.setValidatedObject(new Object());
		validator.apply();
		/* nothing should happen */
	}
	
	/**
	 * Tests apply().
	 * 
	 * @throws DataException 
	 * @throws RuleException 
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=RuleException.class)
	public void testApply_failOnFalse_false() throws RuleException, DataException {
		MessagesBean bean = mock(MessagesBean.class);
		String key = "X"; //$NON-NLS-1$		
		Condition<Object> condition= Mockito.mock(Condition.class);
		Mockito.when(condition.check(Mockito.anyObject())).thenReturn(false);		
		ConditionValidator<Object> validator = 
			new ConditionValidator<Object>(condition, false, bean, key);
		validator.setValidatedObject(new Object());
		validator.apply();		
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
