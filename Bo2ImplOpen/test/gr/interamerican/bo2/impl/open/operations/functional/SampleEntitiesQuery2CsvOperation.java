package gr.interamerican.bo2.impl.open.operations.functional;

import java.util.ArrayList;
import java.util.List;

import gr.interamerican.bo2.arch.EntitiesQuery;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.utils.meta.beans.ExportDataSetup;

/**
 * @param <R>
 * @param <Q>
 */
class SampleEntitiesQuery2CsvOperation<R, Q extends EntitiesQuery<R>> extends AbstractEntitiesQuery2CsvOperation<R, Q> {

	/**
	 * 
	 */
	List<String> lines = new ArrayList<>();

	/**
	 * Constructor.
	 *
	 * @param query
	 * @param setup
	 */
	SampleEntitiesQuery2CsvOperation(Q query, ExportDataSetup<R> setup) {
		super(query, setup);
	}

	@Override
	protected void writeLine(String line) throws DataException {
		lines.add(line);
	}
}