package gr.interamerican.bo2.utils.greek;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date utilities.
 */
public class DateUtilsGr {
	
	/**
	 * Date format YYYYMMDD.
	 */
	private static final DateFormat dfgr = new SimpleDateFormat("dd/MM/yyyy"); //$NON-NLS-1$

	

	/**
	 * Hidden constructor.
	 */
	private DateUtilsGr() {/*empty*/}
	
	/**
	 * Parses a String dd/MM/yyyy representation of a date
	 * and returns a {@link Date}.
	 * 
	 * @param date
	 *        date to parse.
	 * 
	 * @return a {@link Date} instance.
	 */
	public static Date getDateDDMMYYYY(String date) {
		try {
			return dfgr.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}


}
