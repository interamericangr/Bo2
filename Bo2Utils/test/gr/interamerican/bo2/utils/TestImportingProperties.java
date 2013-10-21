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
package gr.interamerican.bo2.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@link ImportingProperties}.
 */
public class TestImportingProperties {
	
	/**
	 * Tests load.
	 */
	@Test
	public void testLoad() {
		String path = "/gr/interamerican/bo2/utils/improps.properties"; //$NON-NLS-1$
		Properties ext = new ImportingProperties();
		try {
			InputStream is = this.getClass().getResourceAsStream(path);
			ext.load(is);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load imported Properties file: " + path); //$NON-NLS-1$
		}
		Assert.assertEquals(ext.size(), 4);
	}

}
