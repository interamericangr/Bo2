package gr.interamerican.bo2.impl.open.records;

import gr.interamerican.bo2.utils.Utils;

import java.util.Comparator;

/**
 * {@link Comparator} implementation for {@link CsvRecord}.
 * This class compares the String representation of the records.
 */
public class SimpleCsvRecordComparator implements Comparator<CsvRecord> {
	
	/**
	 * Record comparison order. 
	 */
	int[] order = null;

	/**
	 * Creates a new CsvRecordComparator object. 
	 *
	 * @param order
	 *        Record comparison order. Zero based.
	 */
	public SimpleCsvRecordComparator(int... order) {
		super();
		this.order = order;
	}
	
	@Override
	@SuppressWarnings("nls")
	public int compare(CsvRecord o1, CsvRecord o2) {
		if(o1==o2 ) {
			return 0;
		}
		
		if(o1==null && o2!=null) {
			return 1;
		}
		
		if(o1!=null && o2==null) {
			return -1;
		}
		
		int o1size = o1.getFields().size();
		int o2size = o2.getFields().size();
		
		if(o1size != o2size) {
			String msg = "Will not compare two CsvRecords with different number of records";
			throw new IllegalStateException(msg);
		}
		
		if(order.length==0) {
			return o1.getBuffer().compareTo(o2.getBuffer());
		}
		
		for(int index : order) {
			if(index > o1size-1) {
				String msg = "Cannot compare based on index " + index + ". Out of bounds.";
				throw new IllegalStateException(msg);
			}
		}
		
		for(int index : order) {
			int recordComparison = compareRecordsOnIndex(o1, o2,index);
			if(recordComparison==0) {
				continue;
			}
			return recordComparison;
		}
		
		return 0;
	}
	
	/**
	 * Default implementation for the comparison of two columns of two records.
	 * This implementation compares String values.
	 * 
	 * @param o1
	 * @param o2
	 * @param index
	 * @return The comparison of a specific column of two {@link CsvRecord}s.
	 */
	protected int compareRecordsOnIndex(CsvRecord o1, CsvRecord o2, int index) {
		return Utils.nullSafeCompare(o1.getString(index), o2.getString(index));
	}

}
