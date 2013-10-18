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
package gr.interamerican.bo2.utils.adapters;

import gr.interamerican.bo2.utils.attributes.SimpleCommand;

/**
 * {@link PeriodicCommand} turns the behavior of a {@link SimpleCommand}
 * to periodic. <br/>
 * 
 * PeriodicCommand is a wrapper around a SimpleCommand. The SimpleCommand
 * will be executed once for every x executions of the PeriodicCommand, 
 * where x is specified by the <code>period</code> field of the PeriodicCommand.
 */
public class PeriodicCommand
implements SimpleCommand {
	
	/**
	 * 
	 */
	SimpleCommand command;
	
	/**
	 * Interval.
	 */
	long period;
	
	/**
	 * Counter.
	 */
	long cycle;
	
	

	
	/**
	 * Creates a new Action object. 
	 *
	 * @param command
	 * @param period
	 */
	public PeriodicCommand(SimpleCommand command, long period) {
		super();
		this.period = period;
		this.command = command;		
		this.cycle = 0;		
	}

	
	@Override
	public void execute() {
		cycle++;
		if (cycle==period) {
			command.execute();
			cycle = 0;	
		}
	}




}
