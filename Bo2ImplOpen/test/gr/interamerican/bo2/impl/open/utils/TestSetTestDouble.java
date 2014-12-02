package gr.interamerican.bo2.impl.open.utils;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.Query;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.operations.AbstractQueryCrawlerOperation;
import gr.interamerican.bo2.impl.open.workers.ConditionValidator;
import gr.interamerican.bo2.impl.open.workers.FactorySupportedPoDeleter;
import gr.interamerican.bo2.impl.open.workers.FactorySupportedPoHandler;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.conditions.Condition;

/**
 * Tests for {@link SetTestDouble}.
 */
@SuppressWarnings({"nls", "unchecked"})
public class TestSetTestDouble {
	
	/**
	 * Test for set().
	 */	
	@Test
	public void testSet_AbstractQueryCrawler() {
		AbstractQueryCrawlerOperation<Query> operation = 
				new AbstractQueryCrawlerOperation<Query>(null) {
					@Override
					protected void handleRow() throws LogicException, DataException {/*empty*/}		
		};
		Query q = Mockito.mock(Query.class);
		SetTestDouble.set(operation, q);
		Object actual = ReflectionUtils.get("query", operation);
		Assert.assertEquals(q, actual);		
	}
	
	/**
	 * Test for set().
	 */
	@Test
	public void testSet_FactorySupportedPoHandler() {
		FactorySupportedPoHandler<UserKey, User> handler =
			new FactorySupportedPoHandler<UserKey, User>(User.class);
		PersistenceWorker<User> pw = Mockito.mock(PersistenceWorker.class);
		SetTestDouble.set(handler, pw);
		Object actual = ReflectionUtils.get("pw", handler);
		Assert.assertEquals(pw, actual);		
	}
	
	/**
	 * Test for set().
	 */
	@Test
	public void testSet_ConditionValidator() {
		ConditionValidator<Object> rule = new ConditionValidator<Object>(null, null);
		Condition<Object> condition = Mockito.mock(Condition.class);
		SetTestDouble.set(rule, condition);
		Object actual = ReflectionUtils.get("condition", rule);
		Assert.assertEquals(condition, actual);		
	}

}
