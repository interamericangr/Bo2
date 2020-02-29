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
import gr.interamerican.bo2.gui.batch.GuiLauncher;
import gr.interamerican.bo2.impl.open.runtime.AbstractBo2RuntimeWithPropertiesCmd;
import gr.interamerican.bo2.impl.open.runtime.PropertiesLauncherParamsNames;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.ConsoleLauncher;
import gr.interamerican.bo2.utils.NumberUtils;

/**
 * executes a {@link BatchProcess} with the given properties.
 */
public class BpPropertiesLauncherCmd extends AbstractBo2RuntimeWithPropertiesCmd {

	/**
	 * Support gui.
	 *
	 * @return if the process will support GUI
	 */
	private boolean supportGui() {
		if ((executionProperties.getProperty(PropertiesLauncherParamsNames.WITHGUI) != null)
				&& (NumberUtils.string2Int(executionProperties
						.getProperty(PropertiesLauncherParamsNames.WITHGUI)) > 0)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void work() throws LogicException, DataException, InitializationException,
	UnexpectedException {
		if (supportGui()) {
			GuiLauncher lp = new GuiLauncher();
			lp.launch(executionProperties);
		}else{
			ConsoleLauncher lp = new ConsoleLauncher();
			lp.launch(executionProperties);
			lp.waitForThread();
		}
	}
}
