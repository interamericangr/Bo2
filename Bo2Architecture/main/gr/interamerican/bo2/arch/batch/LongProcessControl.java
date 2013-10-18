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
package gr.interamerican.bo2.arch.batch;

/**
 * Control of a long running process.
 */
public interface LongProcessControl {

	/**
	 * Forces stopping of processing.
	 */
	void forceQuit();

	/**
	 * Pauses processing on this queue.
	 */
	void pause();

	/**
	 * Resumes processing.
	 * 
	 * This message will resume a paused queue processor.
	 */
	void resume();
	
	/**
	 * Tidy this long process.
	 * 
	 * A LongProcess shouldn't need any tidying throughout it lifecycle.
	 * It should run for ages without any intervention. In real life things 
	 * are not always perfect. This method is provided as a facility to the 
	 * poor developer who can't have a <i>perfect</i> LongProcess. Put here
	 * whatever is necessary in order to keep the LongProcess running for
	 * long.
	 */
	void tidy();

}
