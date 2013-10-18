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

/**
 * {@link Properties} extension that may load properties from 
 * multiple files that import eachother.
 * <br/>
 * To trigger the loading of properties file B, when loading 
 * properties file A, declare the fully qualified resource path 
 * of B as the value of the reserved property key '_import_' in A.
 * <br/>
 * e.g. _import_=/gr/interamerican/bo2/toBeImported.properties
 * <br/>
 * The _import_ property is removed after loading is complete.
 */
public class ImportingProperties extends Properties {
	
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Import properties key.
	 */
	static final String IMPORT = "_import_"; //$NON-NLS-1$
	
	@Override
	public synchronized void load(InputStream inStream) throws IOException {
		super.load(inStream);
		doImport();
		remove(IMPORT);
	}
	
	/**
	 * Imports external Properties file.
	 */
	void doImport() {
		String extPath = (String) get(IMPORT);
		if(StringUtils.isNullOrBlank(extPath)) {
			return;
		}
		Properties ext = new ImportingProperties();
		try {
			InputStream is = this.getClass().getResourceAsStream(extPath);
			ext.load(is);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load imported Properties file: " + extPath); //$NON-NLS-1$
		}
		putAll(ext);
	}

}
