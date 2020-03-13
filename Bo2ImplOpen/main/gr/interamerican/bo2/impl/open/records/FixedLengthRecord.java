package gr.interamerican.bo2.impl.open.records;

import gr.interamerican.bo2.arch.records.ModifiableIndexedFieldsRecord;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.NumberUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * its a record that comes from a file that has fixed length fields (grammografisi).
 */
public class FixedLengthRecord extends AbstractBaseRecord implements
ModifiableIndexedFieldsRecord<Integer> {

	/** buffer. */
	private String[] fields;

	/**
	 * contains the lengths for each field.
	 */
	private int[] lengths;
	/**
	 * field indexes.
	 */
	private List<Integer> fieldIndexes;

	/**
	 * creates new fields and with the appropriate length.
	 */
	private void setAndClearFields() {
		fields = new String[lengths.length];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = StringConstants.EMPTY;
			for (int j = 0; j < lengths[i]; j++) {
				fields[i] += StringConstants.SPACE;
			}
		}
	}

	/**
	 * default constructor.
	 *
	 * @param lengths
	 *            the lengths for each record.
	 */
	public FixedLengthRecord(int[] lengths) {
		this.lengths = lengths;
		setAndClearFields();
		Integer[] indexes = new Integer[lengths.length];
		for (int i = 0; i < fields.length; i++) {
			indexes[i] = i;
		}
		List<Integer> list = Arrays.asList(indexes);
		fieldIndexes = Collections.unmodifiableList(list);
	}

	/**
	 * sets the given field.
	 *
	 * @param fields
	 *            the new fields
	 */
	private void setFields(String[] fields) {
		setAndClearFields();
		for (int i = 0; i < this.fields.length; i++) {
			if (fields.length <= i) {
				break;
			}
			String s = Utils.notNull(fields[i], StringConstants.EMPTY);
			for (int j = s.length(); j < lengths[i]; j++) {
				s += StringConstants.SPACE;
			}
			this.fields[i] = s.substring(0, lengths[i]);
		}
	}

	/**
	 * default constructor.
	 *
	 * @param lengths
	 *            the lengths for each record.
	 * @param fields
	 *            the fields
	 */
	public FixedLengthRecord(int[] lengths, String[] fields) {
		this(lengths);
		setFields(fields);
	}

	/**
	 * Checks if this field exists.
	 *
	 * @param field
	 *            the field
	 */
	private void checkField(Integer field) {
		if (field >= fields.length) {
			throw new FieldNotFoundException(field.toString());
		}
	}

	@Override
	public String getString(Integer field) {
		checkField(field);
		return fields[field];
	}

	@Override
	public void setString(Integer field, String value) {
		checkField(field);
		fields[field] = value;
	}

	@Override
	public byte[] getBytes(Integer field) {
		return getString(field).getBytes(charset());
	}

	@Override
	public void setBytes(Integer field, byte[] value) {
		setString(field, new String(value, charset()));
	}

	@Override
	public boolean getBoolean(Integer field) {
		String val = getString(field);
		return StringUtils.string2Bool(val);
	}

	@Override
	public void setBoolean(Integer field, boolean value) {
		String val = StringUtils.bool2String(value);
		setString(field, val);
	}

	@Override
	public int getInt(Integer field) {
		String val = getString(field);
		return NumberUtils.string2Int(val);
	}

	@Override
	public void setInt(Integer field, int value) {
		String val = Integer.toString(value);
		setString(field, val);
	}

	@Override
	public long getLong(Integer field) {
		String val = getString(field);
		return NumberUtils.string2Long(val);
	}

	@Override
	public void setLong(Integer field, long value) {
		String val = Long.toString(value);
		setString(field, val);
	}

	@Override
	public double getDouble(Integer field) {
		String val = getString(field);
		return NumberUtils.string2Double(val);
	}

	@Override
	public void setDouble(Integer field, double value) {
		String val = NumberUtils.format(value);
		setString(field, val);
	}

	@Override
	public Date getDate(Integer field) {
		String val = getString(field);
		try {
			return DateUtils.getDate(val);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setDate(Integer field, Date value) {
		String val = DateUtils.formatDate(value);
		setString(field, val);
	}

	@Override
	public Calendar getCalendar(Integer field) {
		Date dt = getDate(field);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal;
	}

	@Override
	public void setCalendar(Integer field, Calendar value) {
		String val = DateUtils.formatCalendar(value);
		setString(field, val);
	}

	@Override
	public BigDecimal getBigDecimal(Integer field) {
		String val = getString(field);
		return NumberUtils.string2BigDecimal(val);
	}

	@Override
	public void setBigDecimal(Integer field, BigDecimal value) {
		String val = NumberUtils.format(value);
		setString(field, val);
	}

	@Override
	public short getShort(Integer field) {
		return new Integer(getInt(field)).shortValue();
	}

	@Override
	public void setShort(Integer field, short value) {
		setInt(field, value);
	}

	@Override
	public float getFloat(Integer field) {
		return new Double(getDouble(field)).floatValue();
	}

	@Override
	public void setFloat(Integer field, float value) {
		setDouble(field, value);
	}

	@Override
	public byte getByte(Integer field) {
		byte[] bytes = getBytes(field);
		if ((bytes == null) || (bytes.length == 0)) {
			return 0;
		}
		return bytes[0];
	}

	@Override
	public void setByte(Integer field, byte value) {
		byte[] bytes = { value };
		setBytes(field, bytes);
	}

	@Override
	public Object getObject(Integer field) {
		return getString(field);
	}

	@Override
	public void setObject(Integer field, Object value) {
		String v = value == null ? "" : value.toString(); //$NON-NLS-1$
		setString(field, v);
	}

	@Override
	public byte[] getBytes() {
		return getBuffer().getBytes(charset());
	}

	@Override
	public void setBytes(byte[] arg) {
		setBuffer(new String(arg, charset()));
	}

	@Override
	public List<Integer> getFields() {
		return fieldIndexes;
	}

	/**
	 * Gets the record length.
	 *
	 * @return the expected record length
	 */
	public int getRecordLength() {
		int sum = 0;
		for (int l : lengths) {
			sum += l;
		}
		return sum;
	}

	@Override
	public void setBuffer(String arg) {
		if (arg == null) {
			setAndClearFields();
			return;
		}
		int sum = 0;
		String padded = arg;
		for (int i = arg.length(); i < getRecordLength(); i++) {
			padded += StringConstants.SPACE;
		}
		for (int i = 0; i < lengths.length; i++) {
			int l = lengths[i];
			int end = l + sum;
			String s = padded.substring(sum, end);
			fields[i] = s;
			sum += l;
		}
	}

	@Override
	public String getBuffer() {
		return StringUtils.concat(fields);
	}
}