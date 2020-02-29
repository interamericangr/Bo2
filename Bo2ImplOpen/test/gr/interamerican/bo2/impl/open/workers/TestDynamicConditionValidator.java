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
public class TestDynamicConditionValidator {
	
	
	
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
		Assert.assertEquals("vo", validator.validatedObjectProperty);		 //$NON-NLS-1$
				
	}
	
	/**
	 * tests the constrsetValidatedObjectuctor.
	 */	
	@Test
	public void testSetValidatedObject() {		
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, null,  null);
		Object o = new Object();
		validator.setValidatedObject(o);
		Assert.assertEquals(o, validator.vo);		
	}
	
	/**
	 * tests the getValidatedObject.
	 */	
	@Test
	public void testGetValidatedObject() {		
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, null,  null);
		validator.vo = new Object();		
		Assert.assertEquals(validator.vo, validator.getValidatedObject());		
	}
	
	/**
	 * tests the constrsetValidatedObjectuctor.
	 */	
	@Test
	public void testSetInput() {		
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, null,  null);
		Object o = new Object();
		validator.setInput(o);
		Assert.assertEquals(o, validator.vo);		
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
	 * @throws RuleException the rule exception
	 * @throws DataException the data exception
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=RuleException.class)
	public void testApply_failOnTrue_true() throws RuleException, DataException {
		MessagesBean bean = mock(MessagesBean.class);
		String key = "X"; //$NON-NLS-1$		
		Condition<Object> condition= Mockito.mock(Condition.class);
		Mockito.when(condition.check(Mockito.any())).thenReturn(true);		
		ConcreteConditionValidator validator = new ConcreteConditionValidator(true, bean, key);		validator.setValidatedObject(new Object());
		validator.condition = condition;
		validator.setValidatedObject(new Object());
		validator.apply();		
	}
	
	/**
	 * Tests the apply().
	 *
	 * @throws RuleException the rule exception
	 * @throws DataException the data exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testApply_failOnTrue_false() throws RuleException, DataException {
		MessagesBean bean = mock(MessagesBean.class);
		String key = "X"; //$NON-NLS-1$		
		Condition<Object> condition= Mockito.mock(Condition.class);
		Mockito.when(condition.check(Mockito.any())).thenReturn(false);		
		ConcreteConditionValidator validator = new ConcreteConditionValidator(true, bean,  key);		validator.setValidatedObject(new Object());
		validator.condition = condition;
		validator.setValidatedObject(new Object());
		validator.apply();	
		/* nothing should happen */
	}
	
	/**
	 * Tests apply().
	 *
	 * @throws RuleException the rule exception
	 * @throws DataException the data exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testApply_failOnFalse_true() throws RuleException, DataException {
		MessagesBean bean = mock(MessagesBean.class);
		String key = "X"; //$NON-NLS-1$		
		Condition<Object> condition= Mockito.mock(Condition.class);
		Mockito.when(condition.check(Mockito.any())).thenReturn(true);		
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, bean,  key);		validator.setValidatedObject(new Object());
		validator.condition = condition;
		validator.setValidatedObject(new Object());
		validator.apply();
		/* nothing should happen */
	}
	
	/**
	 * Tests apply().
	 *
	 * @throws RuleException the rule exception
	 * @throws DataException the data exception
	 */
	@SuppressWarnings("unchecked")
	@Test(expected=RuleException.class)
	public void testApply_failOnFalse_false() throws RuleException, DataException {
		MessagesBean bean = mock(MessagesBean.class);
		String key = "X"; //$NON-NLS-1$		
		Condition<Object> condition= Mockito.mock(Condition.class);
		Mockito.when(condition.check(Mockito.any())).thenReturn(false);		
		ConcreteConditionValidator validator = new ConcreteConditionValidator(false, bean,  key);		validator.setValidatedObject(new Object());
		validator.condition = condition;
		validator.setValidatedObject(new Object());
		validator.apply();		
	}
		
	
	
	
	/**
	 * Implementation of AbstractConditionValidator.
	 */
	class ConcreteConditionValidator extends DynamicConditionValidator<Object> {
		/**
		 * Condition.
		 */
		Condition<Object> condition;
		
		/**
		 * Validated object.
		 */
		Object vo;

		/**
		 * Instantiates a new concrete condition validator.
		 *
		 * @param failOn the fail on
		 * @param messages the messages
		 * @param messageKey the message key
		 */
		@SuppressWarnings("nls")
		public ConcreteConditionValidator(boolean failOn, MessagesBean messages, String messageKey) {
			super(failOn, messages, messageKey, "vo");
		}

		@Override
		protected Condition<Object> getCondition() {			
			return condition;
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
