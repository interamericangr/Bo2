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
package gr.interamerican.wicket.bo2.protocol.http;

import static gr.interamerican.bo2.utils.StringConstants.NULL;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.PoNotFoundException;
import gr.interamerican.bo2.arch.exceptions.RuleException;
import gr.interamerican.bo2.arch.ext.Session;
import gr.interamerican.bo2.arch.ext.User;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.beans.SystemState;
import gr.interamerican.bo2.utils.beans.Timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Statistics kept for wicket request cycles.
 */
public class RequestCycleStats {
	
	/**
	 * Slow cycle threshold. Any cycle with duration above this value (ms)
	 * will be logged.
	 */
	static final long TOO_SLOW = 1000L;
	
	/**
	 * logger.
	 * 
	 * TODO: change class.
	 */
	private static Logger logger = LoggerFactory.getLogger(Bo2WicketRequestCycle.class);
	
	/**
	 * Statistics for tracked exception types.
	 */
	Map<Class<? extends Throwable>, Long> trackedExceptionsStats = 
		new HashMap<Class<? extends Throwable>, Long>();
	
	/**
	 * Tracked exceptions.
	 */
	List<Class<? extends Throwable>> trackedExceptions = 
		new ArrayList<Class<? extends Throwable>>();
	
	/**
	 * Number of initiated cycles.
	 */
	long cycles = 0L;
	
	/**
	 * Number of cycles where an untracked exception was caught.
	 */
	long cyclesUntrackedEx = 0L;
	
	/**
	 * Creates a new RequestCycleStats object. 
	 *
	 */
	public RequestCycleStats() {
		super();
		trackedExceptions.add(StaleObjectStateException.class);
		trackedExceptions.add(HibernateException.class);
		
		trackedExceptions.add(PoNotFoundException.class);
		trackedExceptions.add(DataException.class);
		
		trackedExceptions.add(RuleException.class);
		trackedExceptions.add(LogicException.class);
		
		for(Class<? extends Throwable> trackedException : trackedExceptions) {
			trackedExceptionsStats.put(trackedException, 0L);
		}
	}
	
	/**
	 * Updates the exception statistics based on the caught exception.
	 * @param t
	 *        Caught exception.
	 */
	void updateExceptionStats(Throwable t) {
		for(Class<? extends Throwable> ex : trackedExceptions) {
			if(ExceptionUtils.isCausedBy(t, ex)) {
				Long sofar = trackedExceptionsStats.get(ex);
				trackedExceptionsStats.put(ex, sofar+1);
				return;
			}
		}
		cyclesUntrackedEx++;
	}
	
	/**
	 * Writes to the log file useful things for debug purposes.
	 * 
	 * @param workerNames
	 *        names of worker classes opened by this cycle. 
	 * @param timer 
	 *        Timer.
	 */
	void logForDebugging(String workerNames, Timer timer) {
		if (logger.isDebugEnabled()) {
			doLogForDebugging(workerNames, timer);
		}
	}
	
	/**
	 * Writes to the log file useful things for debug purposes.
	 * 
	 * @param workerNames
	 *        names of worker classes opened by this cycle. 
	 * @param timer 
	 *        Timer.
	 */
	@SuppressWarnings("nls")
	void doLogForDebugging(String workerNames, Timer timer) {
		logger.debug("Cycles so far: " + cycles);		
		SystemState state = new SystemState();
		logger.debug("Used memory = " + Double.toString(state.getUsedMemory()));
		long cycleDuration = timer.get();
		if (cycleDuration>TOO_SLOW) {
			Session<?,?> bo2session = Bo2Session.getSession();
			String userid = null;
			if(bo2session!=null) {
				User<?> user = bo2session.getUser();
				if (user!=null) {
					userid = user.getUserId();
				}
			}
			if(userid==null) {
				userid = NULL;
			}
			
			String msg = StringUtils.concat(
				"Slow cycle: ", this.toString(),
				" duration: ", StringUtils.toString(cycleDuration),
				" user: ", userid, 
				" workers: ", workerNames);
			logger.debug(msg);
		}
		logExceptionStats();
	}
	
	/**
	 * Logs cumulative exception statistics.
	 */
	@SuppressWarnings("nls")
	void logExceptionStats() {
		String exceptionStats = "";
		for(Class<?> ex : trackedExceptions) {
			exceptionStats += ex.getName() + ": " + trackedExceptionsStats.get(ex) + " - ";
		}
		exceptionStats += "Other exception types: " + cyclesUntrackedEx;
		logger.debug(exceptionStats);
	}
	
	/**
	 * Increases the cycles.
	 */
	void newCycle() {
		cycles++;
	}
	

	
	
	

}
