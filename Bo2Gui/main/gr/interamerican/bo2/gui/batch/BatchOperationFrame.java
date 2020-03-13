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

import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.gui.components.ButtonPanel;
import gr.interamerican.bo2.gui.frames.BFrame;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.properties.ButtonProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchOperation;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchOperationParmNames;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchOperationUtility;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.utils.Utils;

import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * BatchOperationFrame.
 */
public class BatchOperationFrame
extends BFrame {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Input panel.
	 */
	BatchOperationInputPanel inputPanel;

	/**
	 * Public Constructor.
	 *
	 * @param input
	 *        Input properties
	 */
	public BatchOperationFrame(Properties input) {
		inputPanel = new BatchOperationInputPanel(input);
		JPanel buttons = createButtons();
		inputPanel.add(buttons);
		inputPanel.setPreferredSize(Sizes.stack(inputPanel.getPreferredSize(), buttons.getPreferredSize()));
		Layout.layAsStackOfLabeledFields(inputPanel, 5, 5);
		setPanel(inputPanel);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		String name = Utils.notNull(input.getProperty(BatchOperationParmNames.BATCH_PROCESS_NAME),
				"Batch Operation"); //$NON-NLS-1$
		setTitle(Bo2.getDefaultDeployment().getDeploymentBean().getApplicationName() + ": " + name); //$NON-NLS-1$
	}

	/**
	 * Creates a panel with the buttons.
	 *
	 * @return Returns the panel.
	 */
	JPanel createButtons() {
		ButtonProperties bp = Factory.create(ButtonProperties.class);
		bp.setPreferredSize(Sizes.square(15, 1, false));
		ButtonPanel buttons = new ButtonPanel();
		buttons.addButton("start", bp, this); //$NON-NLS-1$
		buttons.setPreferredSize(Sizes.square(60, 1, true));
		Layout.layAsRow(buttons, 5, 5);
		return buttons;
	}

	/**
	 * Starts the batch operation
	 */
	public void start() {
		inputPanel.panel2model();
		BatchOperationUtility util = new BatchOperationUtility(Bo2Session.getSession());
		BatchOperation<?> batchOp = util.createBatchOperation(inputPanel.getModel());
		util.startOperation(batchOp);
		System.out.println("Operation is running - pls wait !"); //$NON-NLS-1$
		dispose();
	}
}