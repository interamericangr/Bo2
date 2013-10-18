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

import gr.interamerican.bo2.gui.components.BPanelForMap;
import gr.interamerican.bo2.gui.frames.BFrame;
import gr.interamerican.bo2.gui.layout.Layout;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcess;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessInput;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParm;
import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmFactoryImpl;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JFrame;

/**
 * BatchProcessFrameForJob.
 */
public class BatchProcessFrameForJob 
extends BFrame {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Input panel.
	 */
	BPanelForMap<Properties> inputPanel;
	
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
	BatchProcess<?> batch;
	
	/**
	 * Creates a new BatchProcessFrameForJob object. 
	 * @param input 
	 * @param criteria 
	 * @param inputFileDefinitions 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public BatchProcessFrameForJob(BatchProcessInput input, Object criteria, Map<String, String> inputFileDefinitions) {
		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		BatchProcessParm<?> parameters = new BatchProcessParmFactoryImpl().createParameter(input, criteria, inputFileDefinitions);
		batch = new BatchProcess(parameters);
		
		Properties model = createModelWithInput(input, criteria);
		inputPanel = new BPanelForMap<Properties>(model);
		inputPanel.addButton("start", null, this); //$NON-NLS-1$
		Layout.layAsStackOfLabeledFields(inputPanel, 5, 5);
		setPanel(inputPanel);
	}
	
	/**
	 * Starts the batch process.
	 */
	public void start() {
		new Thread(batch).start();		
		long interval = batch.getInitialThreads() * 10;
		ThreadUtils.sleepMillis(interval);
		processPanel = new MultiThreadedLongProcessPanel(batch);
		setPanel(processPanel);		
	}
	
	
	
	/**
	 * Creates a Properties object with the job input. This is used as a preview
	 * for the operator that launches the job.
	 * 
	 * @param input
	 * @param criteria
	 * 
	 * @return Properties with the job input.
	 */
	Properties createModelWithInput(BatchProcessInput input, Object criteria) {
		Properties model = new Properties();
		TypeAnalysis typeAnalysis = TypeAnalysis.analyze(BatchProcessInput.class);
		List<String> properties = new ArrayList<String>(typeAnalysis.getNamesOfProperties());
		Map<String, Object> mapBpi = ReflectionUtils.getProperties(input, properties.toArray(new String[]{}));
		Map<String, Object> mapCriteria = ReflectionUtils.getProperties(criteria);
		copyMapToProperties(mapBpi, model);
		copyMapToProperties(mapCriteria, model);
		return model;
	}
	
	/**
	 * Copies a map to a properties object.
	 * @param map
	 * @param p
	 */
	void copyMapToProperties(Map<String,Object> map, Properties p) {
		for(Map.Entry<String, Object> entry : map.entrySet()) {
			p.setProperty(entry.getKey(), StringUtils.toString(entry.getValue()));
		}
	}

}
