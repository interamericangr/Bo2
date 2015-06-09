package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.impl.open.runtime.concurrent.BatchProcessParmNames;

/**
 * sets the names of parameters used.
 */
public class PropertiesLauncherParamsNames {

	/**
	 * name of the class to launch
	 */
	public static final String CLASSNAME = "className"; //$NON-NLS-1$
	/**
	 * Property key for the pre-processing operation class.
	 */
	public static final String PRE_PROCESSING_CLASS = BatchProcessParmNames.PRE_PROCESSING_CLASS;
	/**
	 * Property key for the post processing operation class.
	 */
	public static final String POST_PROCESSING_CLASS = BatchProcessParmNames.POST_PROCESSING_CLASS;
	/**
	 * if the launch will support GUI or not.
	 */
	public static final String WITHGUI = "withGui"; //$NON-NLS-1$
}
