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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.BeforeClass;
import org.junit.Test;

import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.utils.MockUtils;
import gr.interamerican.bo2.creation.annotations.MockMethods;
import gr.interamerican.bo2.creation.annotations.MockProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.creation.test.Bo2BaseTest;
import gr.interamerican.bo2.impl.open.workers.AbstractResourceConsumer;
import gr.interamerican.bo2.samples.archutil.po.User;
import gr.interamerican.bo2.samples.archutil.po.UserKey;

/**
 * Unit tests for {@link SaveWithQuestionAction}
 */
public class TestSaveWithQuestionAction extends Bo2BaseTest{

	/**
	 * Init sample data
	 */
	@BeforeClass
	public static void buildUp() {
		Factory.registerPwFixture(User.class, MockUtils.mockPw(null, null, null));
		NextUserKeyQuestion mock = mock(NextUserKeyQuestion.class);
		doReturn(Factory.create(UserKey.class)).when(mock).getAnswer();
		Factory.registerFixture(NextUserKeyQuestion.class, mock);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.SaveWithQuestionAction#SaveWithQuestionAction(java.lang.Class, java.lang.Class)}.
	 */
	@Test
	public void testSaveWithQuestionAction() {
		SaveWithQuestionAction<UserKey, User, NextUserKeyQuestion> tested = new SaveWithQuestionAction<>(User.class,
				NextUserKeyQuestion.class);
		assertNotNull(tested);
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.bo2.callbacks.SaveWithQuestionAction#process(gr.interamerican.bo2.arch.PersistentObject)}.
	 * 
	 * @throws LogicException
	 * @throws DataException
	 * @throws InitializationException
	 */
	@Test
	public void testProcess() throws InitializationException, DataException, LogicException {
		SaveWithQuestionAction<UserKey, User, NextUserKeyQuestion> tested = new SaveWithQuestionAction<>(User.class, NextUserKeyQuestion.class);
		User actual = tested.process(Factory.create(User.class));
		assertNull(actual);
	}

	/**
	 * Next key question.
	 */
	interface NextUserKeyQuestion extends Question<UserKey>, PoDependent<User> {/* empty */}

	/**
	 * Sample implementation for {@link NextUserKeyQuestion}
	 */
	@MockMethods({ "ask", "getAnswer" })
	@MockProperties({ "po", "key" })
	abstract class NextUserKeyQuestionImpl extends AbstractResourceConsumer implements NextUserKeyQuestion {/* empty */}

}
