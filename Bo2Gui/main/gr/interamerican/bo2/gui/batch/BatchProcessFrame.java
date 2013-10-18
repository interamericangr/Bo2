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
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.gui.components.ButtonPanel;
import gr.interamerican.bo2.gui.frames.BFrame;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.properties.ButtonProperties;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParm;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmFactoryImpl;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmsFactory;
import gr.interamerican.bo2.impl.open.runtime.concurrent.IsFinished;
import gr.interamerican.bo2.impl.open.runtime.concurrent.LongProcessMail;
import gr.interamerican.bo2.impl.open.runtime.concurrent.Tidy;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.adapters.PeriodicCommand;
import gr.interamerican.bo2.utils.adapters.Refresh;
import gr.interamerican.bo2.utils.adapters.SingleSubjectOperation;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.runnables.Monitor;

import java.awt.Dimension;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * BatchProcessFrame.
 */
public class BatchProcessFrame 
extends BFrame {
	
	/**
	 * 
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
	 * Session.
	 */
	Session<?, ?> session;
	
	/**
	 * Process name.
	 */
	String name;
	
	/**
	 * Monitor.
	 */
	Monitor<MultiThreadedLongProcess> monitor;
	
	
	/**
	 * Creates a new BatchProcessFrame object. 
	 * 
	 * @param input
	 *        Input properties. 
	 *
	 */
	public BatchProcessFrame(Properties input) {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		session = Bo2Session.getSession();
		inputPanel = new BatchProcessInputPanel(input, true);
		
		JPanel buttons = createButtons();
		inputPanel.add(buttons);
		Dimension d = Sizes.stack(inputPanel.getPreferredSize(), buttons.getPreferredSize());
		inputPanel.setPreferredSize(d);
		Layout.layAsStackOfLabeledFields(inputPanel, 5, 5);
		setPanel(inputPanel);		
		name = input.getProperty(BatchProcessParmNames.BATCH_PROCESS_NAME);
		setTitle(name);
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
		Bo2Session.setSession(session);
		inputPanel.panel2model();
		startBatchProcess();
		processPanel = new MultiThreadedLongProcessPanel(batch);
		setPanel(processPanel);
		setTitle(name);
		setPreferredSize(processPanel.getDefaultSize());
		setSize(processPanel.getDefaultSize());
		repaint();
		startMonitor();
	}
	
	
	/**
	 * Exits the program.
	 */
	public void exit() {
		System.exit(0);
	}
	
	/**
	 * Creates the {@link BatchProcessParmsFactory}.
	 * 
	 * @param p
	 * 
	 * @return Returns the {@link BatchProcessParmsFactory}.
	 */
	BatchProcessParmsFactory getFactory(Properties p) {
		String className = p.getProperty(BatchProcessParmNames.PARAMETERS_FACTORY_CLASS);
		if (StringUtils.isNullOrBlank(className)) {
			return new BatchProcessParmFactoryImpl();
		}
		Object factory = ReflectionUtils.newInstance(className);
		return (BatchProcessParmsFactory) factory;		
	}
	
	/**
	 * Starts the batch process.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	void startBatchProcess() {	
		Properties properties = inputPanel.getModel();
		BatchProcessParmsFactory factory = getFactory(properties);		 
		BatchProcessParm bpi = factory.createParameter(properties);				
		batch = new BatchProcess(bpi);
		new Thread(batch).start();		
		long interval = batch.getInitialThreads() * 10;
		ThreadUtils.sleepMillis(interval);
	}
	
	/**
	 * Starts the monitoring thread.
	 *
	 */
	void startMonitor() {
		Condition<MultiThreadedLongProcess> stop = 
			new IsFinished<MultiThreadedLongProcess>();
		monitor = new Monitor<MultiThreadedLongProcess>(batch, 1000, stop);
		monitor.addCommand(uiRefresh());
		monitor.addCommand(mail());
		monitor.addCommand(tidy());
		new Thread(monitor).start();
	}
	
	
	
	/**
	 * Creates a SimpleCommand that refreshes the UI.
	 * 
	 * @return Returns the command.
	 */
	SimpleCommand uiRefresh() {
		VoidOperation<MultiThreadedLongProcessPanel> refresh = 
			new Refresh<MultiThreadedLongProcessPanel>();
		return new SingleSubjectOperation <MultiThreadedLongProcessPanel> 
			(refresh, processPanel);
				
	}
	
	/**
	 * Creates a SimpleCommand that refreshes the UI.
	 * 
	 * @return Returns the command.
	 */
	SimpleCommand mail() {
		BatchProcessParm<?> bpi = batch.getParameters();
		String recipients = bpi.getMonitoringMailRecipients();		
		if (StringUtils.isNullOrBlank(recipients)) {
			return null;
		}
		LongProcessMail mail = new LongProcessMail();
		mail.setStatusMessageRecipients(recipients);
		SingleSubjectOperation<MultiThreadedLongProcess> op =
			new SingleSubjectOperation<MultiThreadedLongProcess>(mail, batch);
		int minutes = bpi.getMonitoringMailInterval();
		long period = minutes * 60;
		return new PeriodicCommand(op, period);
	}
	
	
	/**
	 * Creates a SimpleCommand that refreshes the UI.
	 * 
	 * @return Returns the command.
	 */
	SimpleCommand tidy() {
		BatchProcessParm<?> bpi = batch.getParameters();
		int minutes = bpi.getTidyInterval();
		if (minutes==0) {
			return null;
		}
		Tidy tidy = new Tidy();
		SingleSubjectOperation<LongProcess> op =
			new SingleSubjectOperation<LongProcess>(tidy, batch);		
		long period = minutes * 60;
		return new PeriodicCommand(op, period);
	}

}
