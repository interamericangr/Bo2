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
package gr.interamerican.bo2.impl.open.parse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import gr.interamerican.bo2.samples.sql.MockParser;
import gr.interamerican.bo2.utils.sql.parsers.SqlParser;

import java.util.Properties;

import org.junit.Test;
import org.mockito.Mockito;

/**
 * 
 */
public class TestParserProviderImpl {
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testGetParser() {		
		ParserProviderImpl impl = new ParserProviderImpl(Mockito.mock(SqlParser.class));
		assertEquals(impl.parser, impl.getParser());
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_withParser() {
		SqlParser p = Mockito.mock(SqlParser.class);
		ParserProviderImpl impl = new ParserProviderImpl(p);
		assertNotNull(impl);
		assertEquals(impl.parser, p);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_withClassName() {
		String className = MockParser.class.getName();
		ParserProviderImpl impl = new ParserProviderImpl(className);
		assertNotNull(impl);
		assertNotNull(impl.parser);
		assertTrue(impl.parser instanceof MockParser);
	}
	
	/**
	 * Tests the constructor.
	 */
	@Test
	public void testConstructor_withProperties() {
		Properties properties = new Properties();
		properties.setProperty("parserClass", MockParser.class.getName()); //$NON-NLS-1$
		ParserProviderImpl impl = new ParserProviderImpl(properties);
		assertNotNull(impl);
		assertNotNull(impl.parser);
		assertTrue(impl.parser instanceof MockParser);
	}

}
