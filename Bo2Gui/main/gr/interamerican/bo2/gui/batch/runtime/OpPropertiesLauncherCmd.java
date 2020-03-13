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
package gr.interamerican.bo2.gui.batch.runtime;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.gui.batch.BatchOperationFrame;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeWithPropertiesCmd;
import gr.interamerican.bo2.impl.open.runtime.PropertiesLauncherParamsNames;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.ConsoleOperationLauncher;
import gr.interamerican.bo2.utils.NumberUtils;

/**
 * executes a {@link BatchProcess} with the given properties.
 */
public class OpPropertiesLauncherCmd extends AbstractBo2RuntimeWithPropertiesCmd {

	/**
	 * Support gui.
	 *
	 * @return if the process will support GUI
	 */
	private boolean supportGui() {
		return NumberUtils.string2Int(executionProperties.getProperty(PropertiesLauncherParamsNames.WITHGUI)) > 0;
		}

	
	@Override
	public void work() throws LogicException, DataException, InitializationException,
	UnexpectedException {
		if (supportGui()) {
			BatchOperationFrame frame = new BatchOperationFrame(executionProperties);
			frame.setVisible(true);
		} else {
			ConsoleOperationLauncher col = new ConsoleOperationLauncher();
			col.launch(executionProperties);
		}
	}
}