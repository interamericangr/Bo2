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
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.gui.components.ButtonPanel;
import gr.interamerican.bo2.gui.frames.BFrame;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.properties.ButtonProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessUtility;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.vo.Refresh;
import gr.interamerican.bo2.utils.attributes.Refreshable;
import gr.interamerican.bo2.utils.runnables.ConcreteMonitoringOperation;
import gr.interamerican.bo2.utils.runnables.Monitor;
import gr.interamerican.bo2.utils.runnables.MonitoringOperation;

import java.awt.Dimension;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 * BatchProcessFrame.
 */
public class BatchProcessFrame
extends BFrame {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Input panel.
	 */
	BatchProcessInputPanel inputPanel;

	/**
	 * Process panel.
	 */
	MultiThreadedLongProcessPanel processPanel;

	/**
	 * Batch process.
	 * This variable takes value when the batch process starts.
	 * Before that it is null, indicating that processing didn't
	 * start yet.
	 */
	BatchProcess<?> batch = null;

	/**
	 * Process name.
	 */
	String name;

	/**
	 * Monitor.
	 */
	Monitor<LongProcess> monitor;

	/**
	 * BatchProcessController.
	 */
	BatchProcessUtility controller;
	
	/** The listener. */
	BatchProcessFrameCloseConfirmationWindowListener listener;

	/**
	 * Creates a new BatchProcessFrame object.
	 *
	 * @param input
	 *        Input properties.
	 *
	 */
	public BatchProcessFrame(Properties input) {
		super();
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		listener = new BatchProcessFrameCloseConfirmationWindowListener();
		addWindowListener(listener);
		controller = new BatchProcessUtility(Bo2Session.getSession());
		inputPanel = new BatchProcessInputPanel(input, true);

		JPanel buttons = createButtons();
		inputPanel.add(buttons);
		Dimension d = Sizes.stack(inputPanel.getPreferredSize(), buttons.getPreferredSize());
		inputPanel.setPreferredSize(d);
		Layout.layAsStackOfLabeledFields(inputPanel, 5, 5);
		setPanel(inputPanel);
		name = Utils.notNull(input.getProperty(BatchProcessParmNames.BATCH_PROCESS_NAME),
				"Batch Process"); //$NON-NLS-1$
		String applicationName = Bo2.getDefaultDeployment().getDeploymentBean().getApplicationName();
		setTitle(applicationName + ": " + name); //$NON-NLS-1$
	}

	/**
	 * Creates a panel with the buttons.
	 *
	 * @return Returns the panel.
	 */
	JPanel createButtons() {
		ButtonProperties bp = buttonProperties();
		ButtonPanel buttons = new ButtonPanel();
		buttons.addButton("start", bp, this); //$NON-NLS-1$
		buttons.addButton("exit", bp, this); //$NON-NLS-1$
		buttons.setPreferredSize(Sizes.square(60, 1, true));
		Layout.layAsRow(buttons, 5, 5);
		return buttons;
	}

	/**
	 * Properties for the buttons.
	 *
	 * @return Returns the properties for the buttons.
	 */
	ButtonProperties buttonProperties() {
		ButtonProperties bp = Factory.create(ButtonProperties.class);
		bp.setPreferredSize(Sizes.square(15, 1, false));
		return bp;
	}

	/**
	 * Starts the batch process.
	 */
	public void start() {
		/*
		 * set the thread local session to the thread
		 * that actually starts the batch process.
		 */
		inputPanel.panel2model();
		Properties properties = inputPanel.getModel();
		batch = controller.createBatchProcess(properties);
		controller.startBatchProcess(batch);

		processPanel = new MultiThreadedLongProcessPanel(batch);
		setPanel(processPanel);
		setTitle(name);
		setPreferredSize(processPanel.getDefaultSize());
		setSize(processPanel.getDefaultSize());
		repaint();

		monitor = controller.createMonitor(batch, properties);
		Refresh<Refreshable> refresh = new Refresh<Refreshable>();
		MonitoringOperation<Refreshable> mo = new ConcreteMonitoringOperation<Refreshable>(refresh);
		mo.setPeriodInterval(1000L);
		monitor.addOperation(mo, processPanel);
		controller.startMonitor(monitor);
		if (listener != null) {
			listener.setBatchProcess(batch);
		}
	}

	/**
	 * Exits the program.
	 */
	public void exit() {
		System.exit(0);
	}
}
