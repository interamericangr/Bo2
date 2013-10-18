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
package gr.interamerican.bo2.utils;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;

/**
 * System utilities.
 */
public class SystemUtils {
	
	/**
	 * Bytes in a MegaByte.
	 */
	private static final int MB = 1024*1024;

	/**
	 * Used memory in MB.
	 * 
	 * @return Returns the amount of memory used in MB.
	 */
	public static double usedMemory() {		 
		 Runtime runtime = Runtime.getRuntime();
		 long used = runtime.totalMemory() - runtime.freeMemory();
		 return used / MB;
	}
	
	/**
	 * Maximum available memory in MB.
	 * 
	 * @return Returns the maximum available of memory in MB.
	 */
	public static double maxMemory() {		 
		 Runtime runtime = Runtime.getRuntime();
		 return runtime.maxMemory() / MB;
	}	
	
	/**
	 * Free memory in MB.
	 * 
	 * @return Returns the amount of free memory in MB.
	 */
	public static double freeMemory() {		 
		 Runtime runtime = Runtime.getRuntime();
		 return runtime.freeMemory() / MB;
	}	
	
	/**
	 * Total allocated memory in MB.
	 * 
	 * @return Returns the total allocated memory in MB.
	 */
	public static double totalMemory() {		 
		 Runtime runtime = Runtime.getRuntime();
		 return runtime.totalMemory() / MB;	 
	}
	
	/**
	 * @return Number of garbage collection events performed since VM start.
	 */
	public static long gcEvents() {
		long totalEvents=0;
		for(GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
			long count = gc.getCollectionCount();
			if(count >= 0) {
				totalEvents += count;
	        }
		}
		return totalEvents;
	}
	
	/**
	 * @return Total cpu time allocated for garbage collection.
	 */
	public static long gcTime() {
		long totalTime=0;
		for(GarbageCollectorMXBean gc : ManagementFactory.getGarbageCollectorMXBeans()) {
			long time = gc.getCollectionTime();
			if(time >= 0) {
				totalTime += time;
	        }
		}
		return totalTime;
	}

}
