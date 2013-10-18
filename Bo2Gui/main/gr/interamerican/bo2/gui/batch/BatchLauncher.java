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

/**
 * Launcher for BatchProcesses.
 */
public class BatchLauncher {
	
	/**
	 * Opens a window for the execution of a BatchProcess.
	 * 
	 * @param args
	 *        Only one argument is expected. The one and only argument must be 
	 *        the path to the properties file that contains the parameters for
	 *        the batch process.
	 */
	public static void main(String[] args) {
		String path = args[0];
		Properties p = CollectionUtils.readProperties(path, true);
		BatchProcessFrame frame = new BatchProcessFrame(p);
		frame.setVisible(true);
	}

}
