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
 * Tests for {@link TestPredefinedConditionValidator}.
 */
public class TestPredefinedConditionValidator {
	
	
	
	/**
	 * tests the constructor.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConstructor() {
		String message = "X"; //$NON-NLS-1$
		Condition<Object> condition= Mockito.mock(Condition.class);
		MessagesBean bean = Mockito.mock(MessagesBean.class);
		boolean failOn = true;
		PredefinedConditionValidator<Object> validator = 
			new ConcreteConditionValidator(condition, failOn, bean, message);
		Assert.assertEquals(message, validator.messageKey);
		Assert.assertEquals(bean, validator.messages);
		Assert.assertEquals(failOn, validator.failOn);		
		Assert.assertEquals(condition, validator.condition);		
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
		PredefinedConditionValidator<Object> validator = 
			new ConcreteConditionValidator(null, true, bean, key);
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
		PredefinedConditionValidator<Object> validator = 
			new ConcreteConditionValidator(condition, true, bean, key);
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
		PredefinedConditionValidator<Object> validator = 
			new ConcreteConditionValidator(condition, true, bean, key);
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
		PredefinedConditionValidator<Object> validator = 
			new ConcreteConditionValidator(condition, false, bean, key);
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
		PredefinedConditionValidator<Object> validator = 
			new ConcreteConditionValidator(condition, false, bean, key);
		validator.setValidatedObject(new Object());
		validator.apply();		
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * Implementation of AbstractConditionValidator.
	 */
	class ConcreteConditionValidator extends PredefinedConditionValidator<Object> {				
		
		/**
		 * Validated object.
		 */
		Object vo;

		@SuppressWarnings("nls")
		public ConcreteConditionValidator(Condition<Object> condition, boolean failOn, MessagesBean messages, String messageKey) {
			super(condition, failOn, messages, messageKey, "vo");
		}		

		/**
		 * Gets the vo.
		 *
		 * @return Returns the vo
		 */
		public Object getVo() {
			return vo;
		}

		/**
		 * Sets the vo.
		 *
		 * @param vo the vo to set
		 */
		public void setVo(Object vo) {
			this.vo = vo;
		}
		
	}	
	
	
	
	
	
	
	

}
