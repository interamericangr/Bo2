package gr.interamerican.bo2.impl.open.namedstreams;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.impl.open.records.CsvRecord;
import gr.interamerican.bo2.impl.open.utils.CopyRecordUtils;
import gr.interamerican.bo2.utils.TokenUtils;

/**
 * {@link CsvRecordQuery} that has a header row with column names.
 */
public class CsvWithHeaderQuery extends CsvRecordQuery {

	/**
	 * Header.
	 */
	CsvRecord header;

	/**
	 * Creates a new CsvWithHeaderQuery.
	 *
	 * @param columnCount the column count
	 * @param separator the separator
	 * @param streamName the stream name
	 */
	public CsvWithHeaderQuery(int columnCount, char separator, String streamName) {
		super(columnCount, separator, streamName);
	}

	/**
	 * Creates a new CsvWithHeaderQuery.
	 *
	 * @param columnCount the column count
	 * @param separator the separator
	 */
	public CsvWithHeaderQuery(int columnCount, char separator) {
		super(columnCount, separator);
	}

	@Override
	public void execute() throws DataException {
		super.execute();
		if (columnCount == 0) {// we must determine the number of columns from the header
			byte[] rec = null;
			rec = stream.readRecord();
			String s = new String(rec, stream.getEncoding());
			String[] split = TokenUtils.split(s, separator);
			columnCount = split.length;
			initializeRecord();
			record.setBytes(rec);
			header = CopyRecordUtils.copyCsvRecord((CsvRecord) getRecord());
		} else if (next()) {
			header = getEntity();
		}
	}

	/**
	 * Gets the header.<br>
	 * The header is null before calling <code>execute()</code>.
	 *
	 * @return Returns the header
	 */
	public CsvRecord getHeader() {
		return header;
	}
}