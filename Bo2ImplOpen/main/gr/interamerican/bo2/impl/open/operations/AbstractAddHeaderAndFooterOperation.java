package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.impl.open.namedstreams.NamedBufferedReader;
import gr.interamerican.bo2.impl.open.namedstreams.NamedPrintStream;
import gr.interamerican.bo2.impl.open.namedstreams.NamedStreamsProvider;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.workers.AbstractOperation;
import gr.interamerican.bo2.utils.Utils;

/**
 * Writes the contents of a {@link NamedBufferedReader} to a {@link NamedPrintStream}
 * prepending user defined headers and appending user defined footers.
 * 
 * Sub-class this and override {@link #headers()}, {@link #footers()} or
 * both to achieve the desired result.
 * 
 * Uses own managerName (if defined) or the deployment's default streamManager.
 * 
 */
public abstract class AbstractAddHeaderAndFooterOperation extends AbstractOperation {

	/**
	 * Το όνομα του {@link NamedPrintStream}.
	 */
	String printStreamName;
	
	/**
	 * Το όνομα του {@link NamedBufferedReader}.
	 */
	String bufferedReaderStreamName;

	/**
	 * Stream to write the output file.
	 */
	NamedPrintStream outputStream;

	/**
	 * Stream to read the intermediate file.
	 */
	NamedBufferedReader inputStream;
	
	/**
	 * Δημιουργεί ένα νέο {@link AbstractAddHeaderAndFooterOperation}. 
	 * 
	 * @param printStreamName όνομα του {@link NamedPrintStream}
	 * @param bufferedReaderStreamName όνομα του {@link NamedBufferedReader}
	 */
	public AbstractAddHeaderAndFooterOperation(String printStreamName, String bufferedReaderStreamName) {
		super();
		this.printStreamName = printStreamName;
		this.bufferedReaderStreamName = bufferedReaderStreamName;
	}
	
	@Override
	public void init(Provider parent) throws InitializationException {
		super.init(parent);
		String streamsManager = Utils.notNull(getManagerName(), Bo2.getDefaultDeployment().getDeploymentBean().getStreamsManagerName());
		NamedStreamsProvider nsp = parent.getResource(streamsManager, NamedStreamsProvider.class);
		outputStream = (NamedPrintStream) nsp.getStream(printStreamName);
		inputStream = (NamedBufferedReader) nsp.getStream(bufferedReaderStreamName);
	}
	
	@Override
	public void execute() throws LogicException, DataException {
		String[] headers = headers();
		if(headers != null){
			for(String header : headers) {
				outputStream.writeString(header);
			}
			
		}
		
		String line = null;
		while( (line = inputStream.readString()) != null){
			outputStream.writeString(line);
		}
		
		String[] footers = footers();
		if(footers != null){
			for(String footer : footers) {
				outputStream.writeString(footer);
			}
		}
	}
	
	/**
	 * User defined headers
	 * @return headers
	 */
	protected String[] headers() {
		return null;
	}
	
	/**
	 * User defined footers
	 * @return footers
	 */
	protected String[] footers() {
		return null;
	}
	
}

