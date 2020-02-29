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
package gr.interamerican.bo2.utils.adapters.cmd;

import gr.interamerican.bo2.utils.attributes.SimpleCommand;

/**
 * {@link PeriodicCommand} turns the behavior of a {@link SimpleCommand}
 * to periodic. <br>
 * 
 * PeriodicCommand is a wrapper around a SimpleCommand. The SimpleCommand
 * will be executed once for every x executions of the PeriodicCommand, 
 * where x is specified by the <code>period</code> field of the PeriodicCommand.
 */
public class PeriodicCommand
implements SimpleCommand {
	
	/** The command. */
	SimpleCommand command;
	
	/**
	 * Period: Defines how many idle cycles must pass before
	 * executing the command.
	 * 
	 */
	long period;
	
	/**
	 * Counter. Counts the current cycle.
	 * The wrapped command is executed when the cycle is equal
	 * with the period. Then the cycle is reset.
	 */
	long cycle;
	
	

	
	/**
	 * Creates a new PeriodicCommand object. 
	 *
	 * @param command the command
	 * @param period the period
	 */
	public PeriodicCommand(SimpleCommand command, long period) {
		super();
		this.period = period;
		if(period ==0) {
			throw new RuntimeException("Cannot create a PeriodicCommand with 0 period!"); //$NON-NLS-1$
		}
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
