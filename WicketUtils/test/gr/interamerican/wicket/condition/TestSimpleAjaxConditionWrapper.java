package gr.interamerican.wicket.condition;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessages;
import org.apache.wicket.markup.html.form.TextArea;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.wicket.samples.conditions.SampleAjaxCondition;

/**
 * Unit tests for {@link SimpleAjaxConditionWrapper}
 */
public class TestSimpleAjaxConditionWrapper {

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.condition.SimpleAjaxConditionWrapper#SimpleAjaxConditionWrapper(gr.interamerican.wicket.condition.SimpleAjaxCondition)}.
	 */
	@Test
	public void testSimpleAjaxConditionWrapper() {
		SampleAjaxCondition condition = new SampleAjaxCondition();
		SimpleAjaxConditionWrapper<String> tested = new SimpleAjaxConditionWrapper<>(condition);
		assertTrue(tested != null);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.condition.SimpleAjaxConditionWrapper#check(java.lang.Object, org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.Component)}.
	 */
	@Test
	public void testCheck() {
		@SuppressWarnings("unchecked")
		TextArea<Object> comp = mock(TextArea.class);
		SampleAjaxCondition condition = new SampleAjaxCondition();
		SimpleAjaxConditionWrapper<String> tested = new SimpleAjaxConditionWrapper<>(condition);

		tested.check(StringConstants.EMPTY, null, comp);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.condition.SimpleAjaxConditionWrapper#check(java.lang.Object, org.apache.wicket.ajax.AjaxRequestTarget, org.apache.wicket.Component)} on a faliing scenario.
	 * @throws Exception 
	 */
	@Test
	@SuppressWarnings({ "unchecked"})
	public void testCheck_failure() throws Exception{
		Exception toBeThrown = new Exception(StringConstants.SPACE);
		SampleAjaxCondition condition = mock(SampleAjaxCondition.class);
		doThrow(toBeThrown).when(condition).check(any(String.class));
		
		TextArea<String> caller = mock(TextArea.class);
		doNothing().when(caller).error(any());
		FeedbackMessages sampleFeedback = new FeedbackMessages();
		doReturn(sampleFeedback).when(caller).getFeedbackMessages();
		
		AjaxRequestTarget target = mock(AjaxRequestTarget.class);
		SimpleAjaxConditionWrapper<String> tested = new SimpleAjaxConditionWrapper<>(condition);
		Boolean result = tested.check(StringConstants.EMPTY, target, caller);
		
		verify(target).add(caller);
		assertFalse(result);
	}

}
