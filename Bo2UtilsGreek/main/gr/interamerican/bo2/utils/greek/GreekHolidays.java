package gr.interamerican.bo2.utils.greek;

import static gr.interamerican.bo2.utils.DateUtils.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Greek holidays utility class.
 */
public class GreekHolidays {

	/**
	 * Return known holidays for the given year.
	 *
	 * @param year the year
	 * @return a set of dates that are known holidays.
	 */
	static Set<Date> getKnownGreekHolidays(int year) {
		Set<Date> dates = new HashSet<Date>();
		dates.add(getDate(year, Calendar.JANUARY, 1));
		dates.add(getDate(year, Calendar.JANUARY, 6));
		dates.add(getDate(year, Calendar.DECEMBER, 25));
		dates.add(getDate(year, Calendar.DECEMBER, 26));
		dates.add(getDate(year, Calendar.AUGUST, 15));
		dates.add(getDate(year, Calendar.MARCH, 25));
		dates.add(getDate(year, Calendar.OCTOBER, 28));
		dates.add(getDate(year, Calendar.MAY, 1));
		dates.addAll(getOrthodoxEasterHolidays(year));
		return dates;
	}

	/**
	 * Identifies whether the given date is a holiday or weekend day in Greece.
	 *
	 * @param d the d
	 * @return true if the given date is part of the standard Greek holidays.
	 */
	public static boolean isStandardGreekHoliday(Date d) {
		Date date = getDateAtMidnight(d);
		boolean isWeekend = isWeekend(date);
		if (isWeekend) {
			return true;
		}
		int year=getYear(date);
		Set<Date> dates = getKnownGreekHolidays(year);
		boolean isHoliday = dates.contains(date);
		return isHoliday;
	}
	
	/**
	 * Returns the date on the orthodox easter on the given year.
	 * 
	 * @param myear
	 *            the year to get the easter date.
	 * @return the date of the orthodox easter.
	 */
	static Date getOrthodoxEaster(int myear) {
		Calendar dof = Calendar.getInstance();
		int r1 = myear % 4;
		int r2 = myear % 7;
		int r3 = myear % 19;
		int r4 = ((19 * r3) + 15) % 30;
		int r5 = ((2 * r1) + (4 * r2) + (6 * r4) + 6) % 7;
		int mdays = r5 + r4 + 13;
		if (mdays > 39) {
			mdays = mdays - 39;
			dof.set(myear, 4, mdays);
		} else if (mdays > 9) {
			mdays = mdays - 9;
			dof.set(myear, 3, mdays);
		} else {
			mdays = mdays + 22;
			dof.set(myear, 2, mdays);
		}		
		return getDateAtMidnight(dof.getTime());
	}

	/**
	 * Returns all the holidays associated with the easter.
	 *
	 * @param year the year
	 * @return Returns a list of {@link Date} objects, each one
	 *         representing   date that is a public holiday due
	 *         to the Greek Orthodox Easter of the specified year.
	 */
	static Set<Date> getOrthodoxEasterHolidays(int year) {
		Set<Date> dates = new HashSet<Date>();
		Date easter = getOrthodoxEaster(year);
		dates.add(easter);
		dates.add(addDays(easter, 1));// Monday.
		dates.add(addDays(easter, -2));// Friday.
		dates.add(addDays(easter, -48));// ash Monday.
		dates.add(addDays(easter, 50));// holy spirit.
		return dates;
	}
	

}
