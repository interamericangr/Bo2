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
package gr.interamerican.bo2.quartz.samples;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.annotations.ManagerName;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.jdbc.JdbcCommand;
import gr.interamerican.bo2.impl.open.jdbc.Sql;
import gr.interamerican.bo2.impl.open.job.JobDescription;
import gr.interamerican.bo2.impl.open.job.JobSchedulerProvider;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.utils.annotations.Child;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Sample Operation implementation.
 */
@SuppressWarnings("all")
@ManagerName("LOCALDB")
public class OperationWithScheduledJob extends AbstractOperation {
	
	/** The Constant FAILING_ID. */
	public static final String FAILING_ID = "1560";
	
	/** The jsp. */
	JobSchedulerProvider jsp;
	
	/** The id. */
	String id;
	
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		jsp = getResource(JobSchedulerProvider.class);
	}

	@Override
	public void execute() throws LogicException, DataException {
		JobDescription jobDescription = Factory.create(JobDescription.class);
		jobDescription.setOperationClass(ScheduledOperation.class);
		jsp.scheduleJob(jobDescription);
		if(FAILING_ID.equals(id)) {
			throw new DataException();
		}
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * The Class ScheduledOperation.
	 */
	public static class ScheduledOperation extends AbstractOperation {
		
		/** The Constant ctr. */
		public static final AtomicInteger ctr = new AtomicInteger(0);
		
		@Override
		public void execute() throws LogicException, DataException {
			ctr.incrementAndGet();
		}
	}

}
