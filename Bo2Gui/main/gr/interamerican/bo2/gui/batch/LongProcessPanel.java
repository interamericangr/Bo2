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

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;

import java.awt.Dimension;
import java.awt.FlowLayout;

/**
 * Panel for displaying the status of a {@link BatchProcess}
 * 
 */
public class LongProcessPanel extends BPanel<LongProcess> {

	/**
	 * serial version id.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Status panel.
	 */
	LongProcessStatusPanel statusPanel;
	
	/**
	 * Commands panel.
	 */
	LongProcessControlPanel commandPanel;
	
	/**
	 * Creates a new BatchProcessInputPanel object. 
	 *
	 * @param model
	 */
	public LongProcessPanel(LongProcess model) {
		super(model);
	}
	
	@Override
	public void paint() {
		setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		statusPanel = new LongProcessStatusPanel(model);
		commandPanel = new LongProcessControlPanel(model);
		
		add(statusPanel);
		add(commandPanel);
		
		Dimension spd = statusPanel.getPreferredSize();
		Dimension cpd = commandPanel.getPreferredSize();
		Dimension d = new Dimension((int) spd.getWidth() + (int)  cpd.getWidth() + 10, (int) spd.getHeight() + 5);
		setPreferredSize(d);
		
		Layout.layAsRow(this, 0, 0);
	}
	
	@Override
	public void setModel(LongProcess model) {	
		super.setModel(model);
		statusPanel.setModel(model);
		commandPanel.setModel(model);		
	}
	
	
	@Override
	public void model2panel() {	
		super.model2panel();
		statusPanel.model2panel();
		commandPanel.model2panel();
	}
	
}
