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
package gr.interamerican.bo2.gui;



import gr.interamerican.bo2.gui.batch.TestSuiteBo2GuiBatch;
import gr.interamerican.bo2.gui.components.TestSuiteBo2GuiComponents;
import gr.interamerican.bo2.gui.frames.TestSuiteBo2GuiFrames;
import gr.interamerican.bo2.gui.handlers.TestSuiteBo2GuiHandlers;
import gr.interamerican.bo2.gui.listeners.TestSuiteBo2GuiListeners;
import gr.interamerican.bo2.gui.sql.TestSuiteBo2GuiSql;
import gr.interamerican.bo2.gui.util.TestSuiteBo2GuiUtil;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 
 */
@RunWith(Suite.class)
@SuiteClasses(
	{
		TestSuiteBo2GuiListeners.class,
		TestSuiteBo2GuiHandlers.class,
		TestSuiteBo2GuiComponents.class,
		TestSuiteBo2GuiFrames.class,
		TestSuiteBo2GuiUtil.class,
		TestSuiteBo2GuiBatch.class,
		TestSuiteBo2GuiSql.class,
		
	}
)
public class TestSuiteBo2Gui {
	/* empty */
}
