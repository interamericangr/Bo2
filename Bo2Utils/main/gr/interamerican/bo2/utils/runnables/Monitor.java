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


import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.adapters.VoidOperation;
import gr.interamerican.bo2.utils.adapters.cmd.PeriodicCommand;
import gr.interamerican.bo2.utils.adapters.cmd.SimpleCommandSequence;
import gr.interamerican.bo2.utils.adapters.cmd.SingleSubjectOperation;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;
import gr.interamerican.bo2.utils.conditions.Condition;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@link Monitor} monitors an object.
 *
 * The Monitor is a {@link Runnable}, that and performs actions relevant
 * to the monitored object. The monitor keeps running until its <code>mustStop</code>
 * condition becomes <code>true</code> checking the monitored object. <br>
 * The monitor executes {@link MonitoringOperation}s using the monitored <code>system</code>
 * as argument to the <code>execute(o)</code> method of the MonitoringOperations.
 * Each monitoring operation is executed periodically according to an interval
 * specified for it. All monitoring operations are executed in the same thread,
 * so if the execution of two monitoring operations coincide, then they will
 * be executed sequentially.
 *
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
	 * List of operations.
	 */
	List<SingleSubjectOperation<?>> operations = new ArrayList<SingleSubjectOperation<?>>();

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
	 * @param monitored
	 *        Object being monitored by the monitoring operation.

	 *
	 */
	public <L> void addOperation(MonitoringOperation<? extends L> mo, L monitored) {
		if (started) {
			String msg = "Monitor is already started. Can't add operation!"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}

		if (mo.isValid()) {
			SingleSubjectOperation<L> sso = new SingleSubjectOperation<L>(mo, monitored);
			operations.add(sso);
			interval = NumberUtils.gcd(interval, mo.getPeriodInterval());
		}
	}

	/**
	 * Adds a SimpleCommand that will execute a {@link VoidOperation}
	 * with <code>system</code> as argument.
	 *
	 * This method is null safe. If vo is null, then nothing happens.
	 *
	 * @param mo
	 *        MonitoringOperation that will be executed.
	 *
	 */
	public <L> void addOperation(MonitoringOperation<? extends T> mo) {
		addOperation(mo, system);
	}


	/**
	 * Initializes <code>sequence</code>.
	 */
	@SuppressWarnings({ "rawtypes" })
	void initializeSequence() {
		for (SingleSubjectOperation ssop : operations) {
			MonitoringOperation mo = (MonitoringOperation) ssop.getVoidOperation();
			long period = interval / mo.getPeriodInterval();
			PeriodicCommand pc = new PeriodicCommand(ssop, period);
			sequence.addCommand(pc);
		}
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
