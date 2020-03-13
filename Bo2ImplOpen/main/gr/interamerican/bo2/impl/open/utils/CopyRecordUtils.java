package gr.interamerican.bo2.impl.open.utils;

import gr.interamerican.bo2.impl.open.records.CsvRecord;

/**
 * utility to copy record.
 */
public class CopyRecordUtils {

	/**
	 * Copy csv record.
	 *
	 * @param input the input
	 * @return a copied csv record.
	 */
	public static CsvRecord copyCsvRecord(CsvRecord input) {
		CsvRecord newrecord = new CsvRecord(input.getFields().size(), input.getSeparator());
		newrecord.setBuffer(input.getBuffer());
		return newrecord;
	}
}
