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
public class TestAbstractConditionValidator {
	
	
	
	/**
	 * tests the constructor.
	 */
	@Test
	public void testConstructor() {
		String message = "X"; //$NON-NLS-1$		
		MessagesBean bean = Mockito.mock(MessagesBean.class);
		boolean failOn = true;
		ConcreteConditionValidator validator = 
			new ConcreteConditionValidator(failOn, bean, message);
		Assert.assertEquals(message, validator.messageKey);
		Assert.assertEquals(bean, validator.messages);
		Assert.assertEquals(failOn, validator.failOn);		
				
	}
	
	/**
	 * tests the constrsetValidatedObjectuctor.
	 */	
	@Test
	public void testSetValidatedObject() {		
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, null,  null);
		Object o = new Object();
		validator.setValidatedObject(o);
		Assert.assertEquals(o, validator.validatedObject);		
	}
	
	/**
	 * tests the getValidatedObject.
	 */	
	@Test
	public void testGetValidatedObject() {		
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, null,  null);
		validator.validatedObject = new Object();		
		Assert.assertEquals(validator.validatedObject, validator.getValidatedObject());		
	}
	
	/**
	 * tests the constrsetValidatedObjectuctor.
	 */	
	@Test
	public void testSetInput() {		
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, null,  null);
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
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, bean,  key);		
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
		ConcreteConditionValidator validator = new ConcreteConditionValidator(true, bean, key);		validator.setValidatedObject(new Object());
		validator.condition = condition;
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
		ConcreteConditionValidator validator = new ConcreteConditionValidator(true, bean,  key);		validator.setValidatedObject(new Object());
		validator.condition = condition;
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
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, bean,  key);		validator.setValidatedObject(new Object());
		validator.condition = condition;
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
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, bean,  key);		validator.setValidatedObject(new Object());
		validator.condition = condition;
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
	
	
	
	/**
	 * Implementation of AbstractConditionValidator.
	 */
	class ConcreteConditionValidator extends AbstractConditionValidator<Object> {
		/**
		 * Condition.
		 */
		Condition<Object> condition;

		public ConcreteConditionValidator(boolean failOn, MessagesBean messages, String messageKey) {
			super(failOn, messages, messageKey);
		}

		@Override
		protected Condition<Object> getCondition() {			
			return condition;
		}
		
	}
	
	

}
