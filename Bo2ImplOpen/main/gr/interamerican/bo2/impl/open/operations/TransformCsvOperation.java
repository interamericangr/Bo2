package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.impl.open.namedstreams.CsvRecordQuery;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Transforms a CSV file. <br>
 * 
 * Each CSV record of the file is transformed to a byte array.
 * The byte array is then written to an OutputStream. 
 * 
 * 
 */
public class TransformCsvOperation 
extends EntitiesQueryExporterOperation<CsvRecordQuery, CsvRecord> {
	
	/**
	 * Creates a new TransformCsvOperation.
	 * 
	 * @param columnCount
	 *        Count of columns of the CSV file.
	 * @param separator
	 *        Column separator character.
	 * @param rowTransformation
	 *        Transformation that transforms the CSV record to a byte array. 
	 * @param inputName 
	 *        Named stream name of the input CSV file.
	 * @param outputName
	 *        Named stream name of the output file. 
	 */
	public TransformCsvOperation(int columnCount, char separator,
			Transformation<CsvRecord, byte[]> rowTransformation,
			String inputName, String outputName) {
		super(new CsvRecordQuery(columnCount, separator), rowTransformation, outputName);
		query.setStreamName(inputName);
	}
	

}
