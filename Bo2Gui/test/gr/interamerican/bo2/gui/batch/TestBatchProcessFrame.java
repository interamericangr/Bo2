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
package gr.interamerican.bo2.gui.batch;

import gr.interamerican.bo2.utils.CollectionUtils;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;


/**
 * test method for {@link BatchProcessFrame}
 */
public class TestBatchProcessFrame {
	/**
	 * test the constructor.
	 */
	@Test
	public void testConstructor() {
		Properties model = new Properties();
		BatchProcessFrame frame = new BatchProcessFrame(model);
		Assert.assertNotNull(frame.inputPanel);
	}
	
	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String path = "/gr/interamerican/rsrc/batchprocess/PrintStrings.properties"; //$NON-NLS-1$
		Properties p = CollectionUtils.readProperties(path);
		BatchProcessFrame frame = new BatchProcessFrame(p);
		frame.setVisible(true);
	}
}
