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

import static javax.swing.SpringLayout.EAST;
import static javax.swing.SpringLayout.WEST;
import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.arch.batch.MultiThreadedLongProcess;
import gr.interamerican.bo2.gui.components.BButton;
import gr.interamerican.bo2.gui.components.BPanel;
import gr.interamerican.bo2.gui.layout.Sizes;
import gr.interamerican.bo2.gui.util.SystemMonitorPanel;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 * Panel for {@link BatchProcess}.
 */
public class MultiThreadedLongProcessPanel 
extends BPanel<MultiThreadedLongProcess> {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * multiplication factor for the height of aggregatePanel so that is visible in the panel.
	 */
	private static final double erma = 1.1;
	/**
	 * sets the number of initially visible {@link LongProcessPanel}
	 */
	private static final int initialVisiblePanels = 6;
	/**
	 * Labels.
	 */
	LongProcessStatusLabelsPanel statusLabelsPanel;
	
	/**
	 * Panel for the model LongProcess.
	 */
	LongProcessPanel aggregatePanel;
	
	/**
	 * System monitor.
	 */
	SystemMonitorPanel sysInfo;
	
	/**
	 * Refresh button.
	 */
	BButton refresh;
	
	/**
	 * newThread button.
	 */
	BButton newThread;
	
	/**
	 * Statistics of the long process.
	 */
	MultiThreadedLongProcessStatsPanel statsPanel;

	/**
	 * Panels for the sub-processes.
	 */
	List<LongProcessPanel> subProcessesPanels;
	
	/**
	 * Creates a new MultiThreadedLongProcessPanel object. 
	 *
	 * @param model
	 */
	public MultiThreadedLongProcessPanel(MultiThreadedLongProcess model) {
		super(model);
	}


	@Override
	public void paint() {
		removeAll();
		
		drawBasicComponents();
		Component reference = defineReferenceComponent();		
		defineLayout(reference);		
		Component previous = aggregatePanel;				
		subProcessesPanels=new ArrayList<LongProcessPanel>();
		for (LongProcess subProcess : model.getSubProcesses()) {			
			JPanel panel = drawSubProcessPanel(subProcess, previous);
			previous = panel;
		}		

		revalidate();
		repaint();
	}
	
	/**
	 * @return the default size used for the frame.
	 */
	public Dimension getDefaultSize() {
		Component reference = defineReferenceComponent();
		double preferredWidth = aggregatePanel.getPreferredSize().getWidth() + 50/* scrollbar width */;
		double preferredHeight = reference.getPreferredSize().getHeight()
				+ (aggregatePanel.getPreferredSize().getHeight() * initialVisiblePanels);
		Dimension size = Sizes.dimension(preferredWidth, preferredHeight);
		return size;
	}

	/**
	 * Calculates and sets the size.
	 * 
	 * @param reference
	 * @return
	 */
	Dimension defineSize(Component reference) {
		double preferredWidth = aggregatePanel.getPreferredSize().getWidth();
		double preferredHeight = reference.getPreferredSize().getHeight()
				+ (aggregatePanel.getPreferredSize().getHeight() * (erma * subProcessesPanels.size() + 3));
		Dimension size = Sizes.dimension(preferredWidth, preferredHeight);
		return size;
	}
	
	/**
	 * Defines the reference component.
	 * 
	 * @return Returns the reference component.
	 */
	Component defineReferenceComponent() {
		if (sysInfo.getPreferredSize().getHeight()>statsPanel.getPreferredSize().getHeight()) {
			return sysInfo;
		} else {
			return statsPanel;
		}
	}
	
	/**
	 * Calculates and sets the size.
	 * 
	 * @param reference
	 */
	void defineLayout(Component reference) {
		SpringLayout layout = new SpringLayout();
		setLayout(layout);
		
		/*
		 * sysInfo and statsPanel on top
		 */
		layout.putConstraint(SpringLayout.NORTH, sysInfo, 5, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, statsPanel, 5, SpringLayout.NORTH, this);		
		
		/*
		 * sysInfo and statsPanel in a row
		 */
		layout.putConstraint(WEST, statsPanel, 5, WEST, this);	
		layout.putConstraint(WEST, sysInfo, 20, EAST, statsPanel);
		/*
		 * buttons to the right of the sysInfopanel
		 */
		layout.putConstraint(SpringLayout.WEST, refresh, 20, SpringLayout.EAST, sysInfo);
		layout.putConstraint(SpringLayout.WEST, newThread, 20, SpringLayout.EAST, sysInfo);
		/*
		 * ...and stacked
		 */
		layout.putConstraint(SpringLayout.NORTH, refresh, 15, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, newThread, 5, SpringLayout.SOUTH, refresh);
		/*
		 * Status labels and the rest one under the reference panel 
		 * (reference is the highest of sysInfo and statsPanel).
		 */		
		layout.putConstraint(SpringLayout.NORTH, statusLabelsPanel, 20, SpringLayout.SOUTH, reference);
		layout.putConstraint(SpringLayout.NORTH, aggregatePanel, 5, SpringLayout.SOUTH, statusLabelsPanel);
	}
	
	/**
	 * Draws the panel for the specified LongProcess.
	 * 
	 * @param subProcess
	 *        LongProcess to draw its panel.
	 * @param previous 
	 *        Previous component. Required for the alignment of the panel.
	 *        
	 * @return Returns the panel. 
	 */
	JPanel drawSubProcessPanel(LongProcess subProcess, Component previous) {
		LongProcessPanel panel = new LongProcessPanel(subProcess);
		subProcessesPanels.add(panel);
		add(panel);
		SpringLayout layout = (SpringLayout) getLayout();
		layout.putConstraint(SpringLayout.NORTH, panel, 2, SpringLayout.SOUTH, previous);
		return panel;
	}
	
	/**
	 * Draws the basic components.
	 */
	void drawBasicComponents() {
		refresh = addSimpleButton("refresh", null); //$NON-NLS-1$		
		newThread = addSimpleButton("newThread", null); //$NON-NLS-1$
		newThread.setEnabled(model.isCanIncreaseSubProcesses());
		
		sysInfo = new SystemMonitorPanel();
		add(sysInfo);
		
		statsPanel = new MultiThreadedLongProcessStatsPanel(model);
		add(statsPanel);
		
		statusLabelsPanel = new LongProcessStatusLabelsPanel();
		add(statusLabelsPanel);
		
		aggregatePanel = new LongProcessPanel(model);
		add(aggregatePanel);
		aggregatePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	/**
	 * Draws any new sub-process panels.
	 */
	void drawNewSubProcessPanels() {
		
		int lastPanelNo = subProcessesPanels.size();
		int lastProcessNo = model.getSubProcesses().size();
		if (lastPanelNo<lastProcessNo) {
			Component previous;
			if (lastPanelNo==0) {
				previous = aggregatePanel;
			} else {
				previous = subProcessesPanels.get(lastPanelNo-1);
			}
			for (int i = lastPanelNo; i < lastProcessNo; i++) {
				LongProcess subProcess = model.getSubProcesses().get(i);
				JPanel panel = drawSubProcessPanel(subProcess, previous);
				previous = panel;
			}
			setSize(defineSize(defineReferenceComponent()));
			setPreferredSize(getSize());
			revalidate();
			repaint();
		}
	}
	
	
	
	/**
	 * action when refresh button is pressed
	 */
	@Override
	public void refresh() {
		drawNewSubProcessPanels();
		model2panel();				
	}
	
	
	@Override
	public void model2panel() {
		super.model2panel();
		sysInfo.model2panel();		
		statsPanel.model2panel();		
		aggregatePanel.model2panel();		
		for (LongProcessPanel subPanel : subProcessesPanels) {
			subPanel.model2panel();	
		}
	}

	/**
	 * Add new thread to batch process.
	 */
	public void newThread() {
		BatchProcess<?> batch = (BatchProcess<?>)model;
		batch.addQueueProcessors(1);
	}
	
}
