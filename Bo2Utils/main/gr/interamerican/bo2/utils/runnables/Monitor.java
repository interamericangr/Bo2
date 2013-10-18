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


import gr.interamerican.bo2.utils.attributes.SimpleCommand;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.conditions.Condition;

import java.util.ArrayList;
import java.util.List;

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
	 * Actions to be executed by the monitor.
	 */
	List<SimpleCommand> commands;

	
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
	public Monitor(T system, long interval, final Condition<T> mustStop) {
		super();
		this.system = system;
		this.interval = interval;
		this.mustStop = mustStop;
		this.commands = new ArrayList<SimpleCommand>();
	}
	
	/**
	 * Adds a SimpleCommand.
	 * 
	 * This method is null safe. If the operand is null then
	 * nothing happens. 
	 * 
	 * @param cmd
	 *        Command to add. 
	 */
	public void addCommand(SimpleCommand cmd) {	
		if (cmd!=null) {
			commands.add(cmd);			
		}		
	}


	@Override
	public void run() {
		while(!mustStop.check(system)) {
			ThreadUtils.sleepMillis(interval);
			executeAll();			
		}
		executeAll();
	}
	
	/**
	 * Executes all commands.
	 */
	void executeAll() {
		for (SimpleCommand cmd : commands) {
			failSafeExecute(cmd);
		}
	}
	
	/**
	 * Fail safe execution of a command.
	 * 
	 * @param cmd
	 */
	void failSafeExecute(SimpleCommand cmd) {
		try {
			cmd.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
