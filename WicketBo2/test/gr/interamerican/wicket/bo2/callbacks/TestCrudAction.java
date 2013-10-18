/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.wicket.bo2.callbacks;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.ext.CrudPerformer;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketRequestCycle;
import gr.interamerican.wicket.bo2.test.MockApplicationForWicketBo2;
import gr.interamerican.wicket.markup.html.panel.service.BeanPanelDef;
import gr.interamerican.wicket.markup.html.panel.service.ServicePanel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test for {@link CrudAction}.
 */
public class TestCrudAction {
	
	/**
	 * the WicketTester
	 */
	public WicketTester wicketTester =
		new WicketTester(new MockApplicationForWicketBo2());
	
	/**
	 * Unit test for create(clazz).
	 * @throws DataException 
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	@Test
	public void testAction() throws DataException{
		Bo2WicketRequestCycle rc = new Bo2WicketRequestCycle (
			wicketTester.getApplication(), wicketTester.getWicketRequest(),
			wicketTester.getWicketResponse());
		Bo2WicketRequestCycle.beginRequest(rc);
		
		
		NextUserKeyQuestion q = Mockito.mock(NextUserKeyQuestion.class);
		Class<NextUserKeyQuestion> clazz = (Class<NextUserKeyQuestion>) q.getClass();
		
		BeanPanelDef<User> definition = Factory.create(BeanPanelDef.class);
		User user1 = Factory.create(User.class);
		User user2 = Factory.create(User.class);
		
		IModel<User> model = new Model<User>(user1);
		
		ServicePanel panel = Mockito.mock(ServicePanel.class);
		
		definition.setBeanModel(model);
		definition.setDisableUnauthorizedButtons(false);
		definition.setPanelId("TestCrudActionPanelId");
		definition.setWicketId("TestCrudActionId");
		definition.setServicePanel(panel);
		
		CrudPerformer performer = Mockito.mock(CrudPerformer.class);
		
		CrudAction<UserKey, User, NextUserKeyQuestion> action =
			new CrudAction<UserKey, User, TestCrudAction.NextUserKeyQuestion>
			(definition, User.class, clazz, performer);
		
		Mockito.when(
			performer.perform((User)Mockito.any(), (PersistenceWorker<User>)Mockito.any())).
			thenReturn(user2);
		
		AjaxRequestTarget target = Mockito.mock(AjaxRequestTarget.class);
		
		action.callBack(target);
		
		User actual = model.getObject();
		Assert.assertEquals(user2, actual);
		
		Bo2WicketRequestCycle.endRequest(rc);		
	}
	
	/**
	 * Next key question.
	 */
	interface NextUserKeyQuestion 
	extends Question<UserKey>, PoDependent<User> {/*empty*/}

}
