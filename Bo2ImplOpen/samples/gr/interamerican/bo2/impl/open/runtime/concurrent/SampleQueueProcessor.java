package gr.interamerican.bo2.impl.open.runtime.concurrent;

import java.util.concurrent.ConcurrentLinkedQueue;

import gr.interamerican.bo2.arch.Operation;
import gr.interamerican.bo2.samples.implopen.runtime.concurrent.PrintStringOperation;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;

/**
 * Sample Factory of {@link QueueProcessor}.
 */
public class SampleQueueProcessor {

	/**
	 * Hidden Constructor.
	 */
	private SampleQueueProcessor() {
		// empty
	}

	/**
	 * Sample for the tests.
	 * 
	 * @return Returns a sample.
	 */
	@SuppressWarnings({ "nls" })
	public static QueueProcessor<String> sample() {
		String name = "TQ";
		Operation op = new PrintStringOperation();
		String inputPropertyName = "string"; // invalid property name.
		Formatter<String> formatter = Utils.cast(ObjectFormatter.INSTANCE);
		return new QueueProcessor<>(new ConcurrentLinkedQueue<>(), name, name, op, inputPropertyName, formatter, null,
				true);
	}
}