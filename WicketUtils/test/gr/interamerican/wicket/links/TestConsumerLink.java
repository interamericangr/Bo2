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
package gr.interamerican.wicket.links;

import static org.junit.Assert.assertEquals;

import org.apache.wicket.model.Model;
import org.junit.Test;

import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.functions.SerializableConsumer;
import gr.interamerican.wicket.test.WicketTest;

/**
 * Unit tests for {@link ConsumerLink}
 */
public class TestConsumerLink extends WicketTest {

	/**
	 * Link click result
	 */
	String clickResult = StringConstants.EMPTY;

	/**
	 * Sample {@link SerializableConsumer} used in tests.
	 */
	SerializableConsumer<String> sampleConsumer = s -> clickResult = s;
	
	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.links.ConsumerLink#onClick()}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testOnClick() {
		ConsumerLink<String> tested = new ConsumerLink<String>("test", Model.of("text"), sampleConsumer);
		assertEquals("test", tested.getId());
		assertEquals("text", tested.getModelObject());
	}

	/**
	 * Test method for
	 * {@link gr.interamerican.wicket.links.ConsumerLink#ConsumerLink(java.lang.String, org.apache.wicket.model.IModel, gr.interamerican.bo2.utils.functions.SerializableConsumer)}.
	 */
	@SuppressWarnings("nls")
	@Test
	public void testConsumerLink() {
		ConsumerLink<String> tested = new ConsumerLink<String>("test", Model.of("text"), sampleConsumer);
		tested.onClick();
		assertEquals("text", clickResult);
	}

}
