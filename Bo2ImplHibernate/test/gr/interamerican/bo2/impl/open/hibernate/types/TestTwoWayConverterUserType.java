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
package gr.interamerican.bo2.impl.open.hibernate.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.samples.sql.MockResultSet;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

/**
 * 
 */
public class TestTwoWayConverterUserType {
	
	/**
	 * Test subject.
	 */
	private TwoWayConverterUserType<String, TypedSelectable<Long>> subject = 
		new TwoWayConverterUserType<String, TypedSelectable<Long>>();
	
	/**
	 * Configuration properties for user type.
	 */
	private Properties props = CollectionUtils.readProperties(
		"/gr/interamerican/rsrc/hibernate/types/TwoWayConverterUserType.properties"); //$NON-NLS-1$
	
	/**
	 * Test method for setParameterValues.
	 */
	@Test
	@SuppressWarnings({ "nls", "rawtypes" })
	public void testSetParameterValues() {
		ReflectionUtils.invokeMethodByUniqueName(subject, "setParameterValues", props);
		assertEquals(2, (((Map) ReflectionUtils.get("knownClasses", subject)).size()));
		assertEquals(1, (((Map) ReflectionUtils.get("converters", subject)).size()));
	}

	/**
	 * Test method for nullSafeGet.
	 * @throws SQLException 
	 */
	@Test
	@SuppressWarnings({ "cast", "nls" })
	public void testNullSafeGet() throws SQLException {
		ReflectionUtils.invokeMethodByUniqueName(subject, "setParameterValues", props);
		ResultSet rs = new MockResultSet();
		Object obj = ReflectionUtils.invokeMethodByUniqueName(subject, "nullSafeGet", rs, new String[]{StringConstants.EMPTY}, null);
		assertTrue(obj instanceof String);
		assertEquals(rs.getString(StringConstants.EMPTY), (String) obj);
	}

	/**
	 * Test method for nullSafeSet.
	 * @throws SQLException 
	 */
	@Test
	@SuppressWarnings("nls")
	public void testNullSafeSet() throws SQLException {
		ReflectionUtils.invokeMethodByUniqueName(subject, "setParameterValues", props);
		PreparedStatement ps = mock(PreparedStatement.class);
		int index=1;
		String val=StringConstants.EMPTY;
		ReflectionUtils.invokeMethodByUniqueName(subject, "nullSafeSet", ps, val, index);
		verify(ps,atLeastOnce()).setObject(index, val, subject.sqlType);
	}

}
