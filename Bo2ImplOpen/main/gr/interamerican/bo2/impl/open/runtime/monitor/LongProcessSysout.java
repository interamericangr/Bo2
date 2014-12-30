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
package gr.interamerican.bo2.impl.open.runtime.monitor;

import gr.interamerican.bo2.arch.batch.LongProcess;
import gr.interamerican.bo2.utils.attributes.ModifiableByProperties;
import gr.interamerican.bo2.utils.runnables.AbstractMonitoringOperation;
import gr.interamerican.bo2.utils.runnables.MonitoringOperation;

import java.util.Properties;

/**
 * Sends an eMail with the status of a long process.
 */
public class LongProcessSysout
extends AbstractMonitoringOperation<LongProcess>
implements MonitoringOperation<LongProcess>, ModifiableByProperties {
	
	/**
	 * Property key for interval.
	 * This property defines the interval between two executions
	 * in milliseconds.
	 */
	public static final String SYSOUT_INTERVAL = "sysoutInterval"; //$NON-NLS-1$
	
	/**
	 * Creates the message.
	 */
	LongProcessToString msgCreator = new LongProcessToString();

	@Override
	public void execute(LongProcess a) {		
		String msg = msgCreator.execute(a);
		System.out.println(msg);
	}
	
	@Override
	public void beModified(Properties properties) {
		setIntervalFromProperties(properties, SYSOUT_INTERVAL);
	}

	
}
