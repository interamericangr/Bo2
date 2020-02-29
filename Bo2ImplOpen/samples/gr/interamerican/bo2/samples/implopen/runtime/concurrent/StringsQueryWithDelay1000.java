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
package gr.interamerican.bo2.samples.implopen.runtime.concurrent;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.concurrent.ThreadUtils;

/**
 * {@link StringsQuery} that fetches 1000 strings.
 */
public class StringsQueryWithDelay1000
extends StringsQuery {

	/**
	 *
	 */
	private long delay;

	/**
	 * Creates a new StringsQuery1000 object.
	 */
	public StringsQueryWithDelay1000() {
		super(1000);
	}

	/**
	 * Assigns a new value to the delay.
	 *
	 * @param delay
	 *            the delay to set
	 */
	public void setDelay(long delay) {
		this.delay = delay;
	}

	@Override
	public void execute() throws  DataException {
		ThreadUtils.sleepMillis(delay);
		super.execute();
	}
}
