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
 * {@link FailSafeCommand} is a wrapper for {@link SimpleCommand}s that
 * guarantees that the execute method will never throw an exception,
 * not even a {@link RuntimeException}. <br/>
 * 
 * PeriodicCommand is a wrapper around a SimpleCommand. The SimpleCommand
 * will be executed once for every x executions of the PeriodicCommand, 
 * where x is specified by the <code>period</code> field of the PeriodicCommand.
 */
public class FailSafeCommand
implements SimpleCommand {
	
	/**
	 * SimpleCommand wrapped by this FailSafeCommand.
	 */
	SimpleCommand command;
	
	/**
	 * Creates a new PeriodicCommand object. 
	 *
	 * @param command
	 *        SimpleCommand wrapped by this FailSafeCommand.
	 */
	public FailSafeCommand(SimpleCommand command) {
		super();
		this.command = command;
	}
	
	@Override
	public void execute() {
		try {
			command.execute();
		} catch (RuntimeException re) {
			re.printStackTrace();
		}	
	}

	/**
	 * Gets the wrapped  command.
	 *
	 * @return Returns the wrapped command
	 */
	public SimpleCommand getWrappedCommand() {
		return command;
	}




}
