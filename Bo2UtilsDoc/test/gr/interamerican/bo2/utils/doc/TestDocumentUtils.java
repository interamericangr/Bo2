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
package gr.interamerican.bo2.utils.doc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link DocumentUtils}.
 */
public class TestDocumentUtils {
	
	/**
	 * Test for safeAppend().
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testSafeAppend_noNulls() throws DocumentEngineException {
		BusinessDocument doc1 = mock(BusinessDocument.class);
		BusinessDocument doc2 = mock(BusinessDocument.class);
		
		BusinessDocument result = 
			DocumentUtils.safeAppend(doc1, doc2, true);
		Assert.assertEquals(doc1, result);
	}
	
	/**
	 * Test for safeAppend().
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testSafeAppend_withNullNull() throws DocumentEngineException {		
		BusinessDocument result = 
			DocumentUtils.safeAppend(null, null, true);
		Assert.assertNull(result);
	}
	
	/**
	 * Test for safeAppend().
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testSafeAppend_firstNull() throws DocumentEngineException {
		BusinessDocument doc1 = null;
		BusinessDocument doc2 = mock(BusinessDocument.class);
		
		BusinessDocument result = 
			DocumentUtils.safeAppend(doc1, doc2, true);
		Assert.assertEquals(doc2, result);
	}
	
	/**
	 * Test for appendRowIfNeeded().
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testAppendRowIfNeeded_needed() throws DocumentEngineException {
		DocumentTable table = mock(DocumentTable.class);
		when(table.getRowCount()).thenReturn(2);		 
		DocumentUtils.appendRowIfNeeded(table, 2);
		verify(table, times(1)).appendRow(); //appendRow was invoked once.
	}
	
	/**
	 * Test for appendRowIfNeeded().
	 * 
	 * @throws DocumentEngineException
	 */
	@Test
	public void testAppendRowIfNeeded_notNeeded() throws DocumentEngineException {
		DocumentTable table = mock(DocumentTable.class);
		when(table.getRowCount()).thenReturn(2);		 
		DocumentUtils.appendRowIfNeeded(table, 1);
		verify(table, times(0)).appendRow(); //appendRow was not invoked.
	}
	
	/**
	 * Test for safeAppend().
	 * 
	 * @throws DocumentEngineException
	 */
	@Test(expected=DocumentEngineException.class)
	public void testValidateColumnCount() throws DocumentEngineException {
		DocumentTable table = mock(DocumentTable.class);
		when(table.getColumnCount()).thenReturn(5);		 
		DocumentUtils.validateColumnCount(table, "table", 12);		 //$NON-NLS-1$
	}

}
