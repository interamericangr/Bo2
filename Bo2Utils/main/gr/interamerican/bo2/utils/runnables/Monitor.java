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
package gr.interamerican.bo2.utils.runnables;


import java.util.ArrayList;
import java.util.List;

import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.adapters.cmd.PeriodicCommand;
import gr.interamerican.bo2.utils.adapters.cmd.SimpleCommandSequence;
import gr.interamerican.bo2.utils.adapters.cmd.SingleSubjectOperation;
import gr.interamerican.bo2.utils.attributes.SimpleCommand;
import gr.interamerican.bo2.utils.beans.Pair;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.conditions.Condition;

/**
 * The {@link Monitor} monitors an object.
 * 
 * The Monitor is {@link Runnable}, that runs on its own thread
 * and performs actions relevant to the monitored object.
 * The monitor keeps running until its <code>mustStop</code> condition 
 * becomes true. The monitor periodically checks the stop condition
 * and if it is true, then it executes the {@link SimpleCommand}s
 * that have been added to it. <br/>  
 * The <code>interval</code> field specifies the time in milliseconds 
 * between the execution of the last SimpleCommand and the next
 * check of the mustStop condition. <br/>
 * The monitored object provides the subject of the mustStop condition.    
 * 
 * @param <T> 
 *        Type of object being monitored.
 * 
 */
public class Monitor<T> 
implements Runnable {
	
	/**
	 * Object being monitored.
	 */
	T system;
	
	/**
	 * time interval.
	 */
	long interval = 0;
	
	/**
	 * Stop condition;
	 */
	Condition<T> mustStop;
	
	
	/**
	 * List with operations.
	 */
	List<Pair<MonitoringOperation<T>,Long>> operations = 
		new ArrayList<Pair<MonitoringOperation<T>,Long>>();
	
	/**
	 * Commands sequence that is executed by the monitor.
	 */
	SimpleCommandSequence sequence = new SimpleCommandSequence(true);
	
	
	
	/**
	 * Start indicator.
	 */
	boolean started;

	
	/**
	 * Creates a new Monitor object. 
	 *
	 * @param system
	 *        System to monitor
	 * @param interval
	 *        Interval between two sub-sequent monitor calls in milliseconds.     
	 * @param mustStop
	 *        Condition that checks when the monitoring process must stop.
	 */
	public Monitor(T system, final Condition<T> mustStop) {
		super();
		this.system = system;		
		this.mustStop = mustStop;	
		this.sequence = new SimpleCommandSequence(true);
		this.started = false;
	}
	
	
	
	
	/**
	 * Adds a SimpleCommand that will execute a {@link VoidOperation}
	 * with <code>system</code> as argument.
	 * 
	 * This method is null safe. If vo is null, then nothing happens.
	 * 
	 * @param mo
	 *        MonitoringOperation that will be executed.
	 * @param operationInterval
	 *        Interval between two subsequent executions of the monitoring
	 *        operations. 
 
	 * 
 	 */
	public void addOperation(MonitoringOperation<T> mo, long operationInterval) {
		if (started) {
			String msg = "Monitor is already started. Can't add operation!"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		
		if (mo.isValid()) {
			Pair<MonitoringOperation<T>,Long> pair = 
				new Pair<MonitoringOperation<T>, Long>(mo, operationInterval);
			operations.add(pair);
		}
		
		interval = NumberUtils.gcd(interval, operationInterval);
	}
	
	
	/**
	 * Initializes <code>sequence</code>.
	 */
	void initializeSequence() {		
		for (Pair<MonitoringOperation<T>, Long> pair : operations) {
			PeriodicCommand pc = getPeriodicCommand(pair);
			sequence.addCommand(pc);
		}		
		
	}
	
	/**
	 * Creates a {@link PeriodicCommand} for a {@link MonitoringOperation}.
	 * 
	 * @param pair
	 *        Pair that contains the MonitoringOperation and its interval. 
	 * 
	 * @return Returns the {@link PeriodicCommand}.
	 */
	PeriodicCommand getPeriodicCommand(Pair<MonitoringOperation<T>, Long> pair) {		
		SingleSubjectOperation<T> ssop = new SingleSubjectOperation<T>(pair.getLeft(), system);
		long period = interval / pair.getRight();
		return new PeriodicCommand(ssop, period);		
	}

	
	




	@Override
	public void run() {
		started = true;
		initializeSequence();
		do {
			ThreadUtils.sleepMillis(interval);
			sequence.execute();
		} while (!mustStop.check(system));
	}
	
	
	
	
	
	

}
