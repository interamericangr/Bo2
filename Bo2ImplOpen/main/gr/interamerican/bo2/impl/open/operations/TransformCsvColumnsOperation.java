package gr.interamerican.bo2.impl.open.operations;

import gr.interamerican.bo2.impl.open.namedstreams.CsvRecordQuery;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Transforms a CSV file to another CSV file that has the same 
 * count of columns but some columns are changed. <br>  
 * 
 * The columns that will be transformed is defined by an array of 
 * String transformations. The array must have the same count of
 * elements as the columns of the CSV file. Each element of the
 * transformations array defines a transformation for the column with
 * the same index. Array elements that are <code>null</code> will
 * not change their column.
 */
public class TransformCsvColumnsOperation 
extends EntitiesQueryExporterOperation<CsvRecordQuery, CsvRecord> {
	
	/**
	 * Creates a new TransformCsvOperation.
	 *
	 * @param columnCount the column count
	 * @param separator the separator
	 * @param columnTransformations the column transformations
	 * @param inputName the input name
	 * @param outputName the output name
	 */
	public TransformCsvColumnsOperation
	(int columnCount, char separator, 
			Transformation<String, String>[] columnTransformations,
			String inputName, String outputName) {
		
		super(new CsvRecordQuery(columnCount, separator), new T(columnTransformations), outputName);
		query.setStreamName(inputName);
	}
	
	
	
	/**
	 * The Class T.
	 */
	static class T implements Transformation<CsvRecord, byte[]> {
		
		/** The column transformations. */
		Transformation<String, String>[] columnTransformations;
		
		/**
		 * Instantiates a new t.
		 *
		 * @param columnTransformations the column transformations
		 */
		public T(Transformation<String, String>[] columnTransformations) {
			super();
			this.columnTransformations = columnTransformations;
		}

		@Override
		public byte[] execute(CsvRecord a) {			
			for (int i = 0; i < columnTransformations.length; i++) {
				Transformation<String, String> trans = columnTransformations[i];
				if (trans!=null) {
					String col = a.getString(i);
					col = trans.execute(col);
					a.setString(i, col);					
				}					
			}
			String row = a.toString() + StringConstants.NEWLINE;
			return row.getBytes();
		}
		
	}
	

}
