package gr.interamerican.bo2.run;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.operations.TransformCsvColumnsOperation;
import gr.interamerican.bo2.impl.open.runtime.RuntimeCommand;
import gr.interamerican.bo2.utils.adapters.Transformation;
import gr.interamerican.bo2.utils.adapters.trans.InvokeMethod;
import gr.interamerican.bo2.utils.greek.GreekUtils;

/**
 * Changes a column from greek to latin in a CSV file.
 */
public class ZCsvCol2GreekOperation 
extends TransformCsvColumnsOperation {
	
	/**
	 *  Column transformations.
	 */
	@SuppressWarnings("rawtypes")
	static Transformation[] transformations = {
		null, 
		new InvokeMethod<String, String>(GreekUtils.class, "toLatin", String.class),  //$NON-NLS-1$
		null, 
		null, 
		null
	};

	/**
	 * Creates a new CsvCol2GreekOperation.
	 * 
	 */
	@SuppressWarnings({ "nls", "unchecked" })
	public ZCsvCol2GreekOperation() {
		super(5, ';', transformations, "CSV", "OUTPUT"); 
	}
	
	
	
		
	
	
	
	/**
	 * Main method.
	 * 
	 * @param args
	 * @throws DataException
	 * @throws LogicException
	 * @throws UnexpectedException
	 */
	public static void main(String[] args) 
	throws DataException, LogicException, UnexpectedException {
		
		ZCsvCol2GreekOperation op = new ZCsvCol2GreekOperation();
		op.setManagerName("LOCALFS"); //$NON-NLS-1$
		RuntimeCommand cmd = new RuntimeCommand(op);
		cmd.execute();
	}

	

}
