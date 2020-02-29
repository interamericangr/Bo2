/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import gr.interamerican.bo2.utils.beans.Range;

/**
 * Utilities for dates.
 */
public class DateUtils {

	/**
	 * Milliseconds of a day.
	 */
	private static final long HOUR_IN_MILLIS = 60 * 60 * 1000;

	/**
	 * Milliseconds of a day.
	 */
	private static final long DAY_IN_MILLIS = 24 * HOUR_IN_MILLIS;

	/**
	 * Thread Local containing a Date format for YYYYMMDD.
	 */
	private static ThreadLocal<DateFormat> dfYYYYMMDD = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd"); //$NON-NLS-1$
		}
	};

	/**
	 * ISO date format (ISO format).
	 */
	private static final String ISO_DATE_FORMAT_PATTERN = "yyyy-MM-dd"; //$NON-NLS-1$

	/**
	 * Thread Local containing an ISO date format (ISO format).
	 */
	private static ThreadLocal<DateFormat> dfIso = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat(ISO_DATE_FORMAT_PATTERN);
		}
	};

	/**
	 * Calendar constant 1/1/0001
	 */
	private static final Calendar ZERO_CAL = zero();

	/**
	 * Calendar of epoch. On Gregorian it's January 1, 1970
	 */
	private static final Calendar EPOCH_CAL = epoch();

	/** date constant 1/1/0001. */
	private static final Date ZERO_DT = ZERO_CAL.getTime();

	/**
	 * VM argument to override the value of today().
	 */
	static final String valueOfTodayArg = "Bo2.valueOfToday"; //$NON-NLS-1$

	/**
	 * Hidden constructor.
	 * 
	 * This is a utility class having only static methods. There is no need to
	 * create any instance of this class.
	 */
	private DateUtils() {
		/* empty */
	}

	/**
	 * default empty date with value 1/1/0001.
	 * 
	 * @return date 1/1/0001
	 */
	private static Calendar zero() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, 1);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		removeTime(cal);
		return cal;
	}

	/**
	 * Gets the first date after Christ, 1 January of Year 1 (0001-01-01).
	 * 
	 * @return Returns a date with value 0001-01-01.
	 */
	public static Date getDay1AD() {
		return new Date(ZERO_DT.getTime());
	}

	/**
	 * Gets the first day of the specified month.
	 * 
	 * @param year
	 *            year
	 * @param month
	 *            month. Use {@link Calendar} constants for months.
	 * 
	 * @return Returns the first day of month.
	 */
	public static Date getMonth1st(int year, int month) {
		return getDate(year, month, 1);

	}

	/**
	 * date of epoch of Gregorian calendar.
	 *
	 * @return date 01/01/1970
	 */
	private static Calendar epoch() {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(0);
		removeTime(cal);
		return cal;
	}

	/**
	 * Gets a date at midnight (time is zero).
	 *
	 * @param year
	 *            year
	 * @param month
	 *            month. Use {@link Calendar} constants for months.
	 * @param day
	 *            day
	 * @return Returns the first day of month.
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		removeTime(cal);
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * removes time from a calendar.
	 * 
	 * The calendar remains with the same date and time set to 00:00:00.000
	 * 
	 * @param cal
	 *            calendar object who's time is removed.
	 */
	public static void removeTime(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * removes time from a date.
	 * 
	 * The date remains with the same date and time set to 00:00:00.000
	 * 
	 * @param date
	 *            Date who's time is removed.
	 */
	public static void removeTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		removeTime(cal);
		date.setTime(cal.getTime().getTime());
	}

	/**
	 * Shows if a date equals 1/1/0001.
	 * 
	 * @param dt
	 *            date.
	 * @return returns true is date equals 1/1/0001.
	 */
	public static boolean isZero(Date dt) {
		return ZERO_DT.equals(dt);
	}

	/**
	 * Shows if a calendar equals 1/1/0001.
	 * 
	 * @param cal
	 *            calendar.
	 * @return returns true is date equals 1/1/0001.
	 */
	public static boolean isZero(Calendar cal) {
		return ZERO_CAL.equals(cal);
	}

	/**
	 * Gets the time passed between two dates.
	 * 
	 * The time is counted in days, months or years, according to the parameter
	 * <code>type</code> specified. The first parameter <code>fromDate</code>
	 * must be less or equal than the second parameter <code>toDate</code>.
	 * Otherwise the method will return 0.
	 *
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @param type
	 *            Rime integral of the result period of time. Valid values are
	 *            <code>Calendar.DATE, Calendar.YEAR, Calendar.MONTH</code>
	 * @return Returns the time passed from <code>fromDate</code> until
	 *         <code>toDate</code>
	 */
	static int dateDif(Calendar fromDate, Calendar toDate, int type) {
		if (Calendar.DAY_OF_MONTH == type) {
			return daysDifPositive(fromDate, toDate);
		}

		Calendar from = new GregorianCalendar();
		from.setTime(fromDate.getTime());
		Calendar to = new GregorianCalendar();
		to.setTime(toDate.getTime());

		int count = 0;
		from.add(type, 1);
		while (from.before(to) || from.equals(to)) {
			count++;
			from.add(type, 1);
		}
		return count;
	}

	/**
	 * Gets the days between two dates.
	 * 
	 * The first parameter <code>fromDate</code> must be less or equal than the
	 * second parameter <code>toDate</code>. Otherwise the method will return 0.
	 * 
	 * @param fromDate
	 *            From Date
	 * @param toDate
	 *            To Date
	 * 
	 * @return Returns the time passed from <code>fromDate</code> until
	 *         <code>toDate</code>
	 */
	static int daysDifPositive(Calendar fromDate, Calendar toDate) {
		int days = DateUtils.daysDif(fromDate, toDate);
		if (days < 0) {
			return 0;
		}
		return days;
	}

	/**
	 * Gets the time between two dates in days.
	 *
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return Returns the days passed from <code>fromDate</code> until
	 *         <code>toDate</code>
	 */
	static int daysDif(Calendar fromDate, Calendar toDate) {
		Range<Calendar> period = new Range<Calendar>(fromDate, toDate);
		boolean isNegative = period.getLeft().equals(toDate);

		long from = period.getLeft().getTimeInMillis();
		long to = period.getRight().getTimeInMillis();
		long dif = to - from;
		long days = dif / DAY_IN_MILLIS;
		/*
		 * Fix for BOTWO-14: Wrong result if from is in winter time zone and to
		 * in summer time zone.
		 */
		long remainder = dif % DAY_IN_MILLIS;
		long hours = remainder / HOUR_IN_MILLIS;
		if (hours > 22) {
			days++;
		}
		if (isNegative) {
			days = -days;
		}
		return Long.valueOf(days).intValue();
	}

	/**
	 * Gets the time passed between two dates in days.
	 * 
	 * The first parameter <code>fromDate</code> must be less or equal than the
	 * second parameter <code>toDate</code>. Otherwise the method will return 0.
	 *
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return Returns the time passed from <code>fromDate</code> until
	 *         <code>toDate</code>
	 */
	public static int difDays(Calendar fromDate, Calendar toDate) {
		return dateDif(fromDate, toDate, Calendar.DATE);
	}

	/**
	 * Gets the time passed between two dates in years.
	 * 
	 * The first parameter <code>fromDate</code> must be less or equal than the
	 * second parameter <code>toDate</code>. Otherwise the method will return 0.
	 *
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return Returns the time passed from <code>fromDate</code> until
	 *         <code>toDate</code>
	 */
	public static int difYears(Calendar fromDate, Calendar toDate) {
		return dateDif(fromDate, toDate, Calendar.YEAR);
	}

	/**
	 * Gets the time passed between two dates in months.
	 * 
	 * The time is counted in days, months or years, according to the parameter
	 * <code>type</code> specified. The first parameter <code>fromDate</code>
	 * must be less or equal than the second parameter <code>toDate</code>.
	 * Otherwise the method will return 0.
	 *
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return Returns the time passed from <code>fromDate</code> until
	 *         <code>toDate</code>
	 */
	public static int difMonths(Calendar fromDate, Calendar toDate) {
		return dateDif(fromDate, toDate, Calendar.MONTH);
	}

	/**
	 * Gets the time passed between two dates in days.
	 * 
	 * The time is counted in days, months or years, according to the parameter
	 * <code>type</code> specified. The first parameter <code>fromDate</code>
	 * must be less or equal than the second parameter <code>toDate</code>.
	 * Otherwise the method will return 0.
	 *
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return Returns the time passed from <code>fromDate</code> until
	 *         <code>toDate</code>
	 */
	public static int difDays(Date fromDate, Date toDate) {
		GregorianCalendar from = new GregorianCalendar();
		from.setTime(fromDate);
		GregorianCalendar to = new GregorianCalendar();
		to.setTime(toDate);

		return dateDif(from, to, Calendar.DATE);
	}

	/**
	 * Gets the time passed between two dates in years.
	 * 
	 * The time is counted in days, months or years, according to the parameter
	 * <code>type</code> specified. The first parameter <code>fromDate</code>
	 * must be less or equal than the second parameter <code>toDate</code>.
	 * Otherwise the method will return 0.
	 *
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return Returns the time passed from <code>fromDate</code> until
	 *         <code>toDate</code>
	 */
	public static int difYears(Date fromDate, Date toDate) {
		GregorianCalendar from = new GregorianCalendar();
		from.setTime(fromDate);
		GregorianCalendar to = new GregorianCalendar();
		to.setTime(toDate);

		return dateDif(from, to, Calendar.YEAR);
	}

	/**
	 * Gets the time passed between two dates in months.
	 * 
	 * The time is counted in days, months or years, according to the parameter
	 * <code>type</code> specified. The first parameter <code>fromDate</code>
	 * must be less or equal than the second parameter <code>toDate</code>.
	 * Otherwise the method will return 0.
	 *
	 * @param fromDate
	 *            the from date
	 * @param toDate
	 *            the to date
	 * @return Returns the time passed from <code>fromDate</code> until
	 *         <code>toDate</code>
	 */
	public static int difMonths(Date fromDate, Date toDate) {
		GregorianCalendar from = new GregorianCalendar();
		from.setTime(fromDate);
		GregorianCalendar to = new GregorianCalendar();
		to.setTime(toDate);

		return dateDif(from, to, Calendar.MONTH);
	}

	/**
	 * Adds days months or years to a date.
	 * 
	 * @param datein
	 *            input date
	 * @param type
	 *            (Calendar.DATE,Calendar.MONTH,Calendar.YEAR)
	 * @param amount
	 *            days, months or years that will be added to datein. Can be
	 *            negative.
	 * @return returns the result
	 */
	private static Date add(Date datein, int type, int amount) {
		GregorianCalendar result = new GregorianCalendar();
		result.setTime(datein);
		result.add(type, amount);
		return new Date(result.getTimeInMillis());
	}

	/**
	 * Adds days to a date.
	 * 
	 * The parameter <code>days</code> can be negative. In this case the result
	 * will be a date before the input parameter <code>datein</code>.
	 *
	 * @param datein
	 *            the datein
	 * @param days
	 *            the days
	 * @return result date
	 */
	public static Date addDays(Date datein, int days) {
		return add(datein, Calendar.DATE, days);
	}

	/**
	 * Adds months to a date.
	 * 
	 * The parameter <code>months</code> can be negative. In this case the
	 * result will be a date before the input parameter <code>datein</code>.
	 *
	 * @param datein
	 *            the datein
	 * @param months
	 *            the months
	 * @return result date
	 */
	public static Date addMonths(Date datein, int months) {
		return add(datein, Calendar.MONTH, months);
	}

	/**
	 * Adds years to a date.
	 * 
	 * The parameter <code>years</code> can be negative. In this case the result
	 * will be a date before the input parameter <code>datein</code>.
	 *
	 * @param datein
	 *            the datein
	 * @param years
	 *            the years
	 * @return result date as string
	 */
	public static Date addYears(Date datein, int years) {
		return add(datein, Calendar.YEAR, years);
	}

	/**
	 * Parses a date using a sequence of patterns.
	 * 
	 * If parsing is successful, the result is returned. Otherwise, the
	 * ParseException that was thrown by the last parse attempt is thrown.
	 *
	 * @param date
	 *            the date
	 * @param patterns
	 *            the patterns
	 * @return Returns the date.
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date parse(String date, String... patterns) throws ParseException {
		ParseException last = null;
		for (int i = 0; i < patterns.length; i++) {
			String pattern = patterns[i];
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			try {
				return df.parse(date);
			} catch (ParseException pe) {
				last = pe;
			}
		}
		throw last;
	}

	/**
	 * Gets a date from the parameter dt.
	 * 
	 * If dt is a date type (Calendar, Timestamp etc) it will be converted to a
	 * Date object. Otherwise the result of the objects <code>toString()</code>
	 * will be converted to date according to the system locale.
	 *
	 * @param dt
	 *            object representing a date.
	 * @return Returns a date for this object.
	 * @throws ParseException
	 *             the parse exception
	 */
	public static Date getDate(Object dt) throws ParseException {
		if (dt == null) {
			return null;
		}
		if (dt instanceof Date) {
			return (Date) dt;
		}
		if (dt instanceof Calendar) {
			Calendar cal = (Calendar) dt;
			return new Date(cal.getTimeInMillis());
		}
		String str = dt.toString().trim();
		String[] patterns = { Bo2UtilsEnvironment.get().getShortDateFormatPattern(),
				Bo2UtilsEnvironment.get().getLongDateFormatPattern(), ISO_DATE_FORMAT_PATTERN };
		return parse(str, patterns);
	}

	/**
	 * Creates a formatted string from a Date.
	 * 
	 * The formatted string is based on the short date format specified by
	 * {@link Bo2UtilsEnvironment#getShortDateFormatPattern()}.
	 * 
	 * @param dt
	 *            Date to format.
	 * 
	 * @return Returns a formatted string for this date.
	 */
	public static String formatDate(Date dt) {
		SimpleDateFormat df = new SimpleDateFormat(Bo2UtilsEnvironment.get().getShortDateFormatPattern());
		return df.format(dt);
	}

	/**
	 * Creates a formatted string from a Date.
	 * 
	 * The formatted string is based on the ISO date format (
	 * {@link #ISO_DATE_FORMAT_PATTERN} ).
	 * 
	 * @param dt
	 *            Date to format.
	 * 
	 * @return Returns a formatted string for this date.
	 */
	public static String formatDateIso(Date dt) {
		return dfIso.get().format(dt);
	}

	/**
	 * Creates a formatted string from a {@link Calendar}.<br>
	 * The formatted string is based on the long date format specified by
	 * {@link Bo2UtilsEnvironment#getLongDateFormatPattern()}.
	 * 
	 * @param cal
	 *            Date to format.
	 * 
	 * @return Returns a formatted string for this date.
	 */
	public static String formatCalendar(Calendar cal) {
		Date dt = cal.getTime();
		return formatCalendar(dt);
	}

	/**
	 * Creates a formatted string from a Date.
	 * 
	 * The formatted string is based on the long date format specified by
	 * {@link Bo2UtilsEnvironment#getLongDateFormatPattern()}.
	 * 
	 * @param dt
	 *            Date to format.
	 * 
	 * @return Returns a formatted string for this date.
	 */
	public static String formatCalendar(Date dt) {
		SimpleDateFormat df = new SimpleDateFormat(Bo2UtilsEnvironment.get().getLongDateFormatPattern());
		return df.format(dt);
	}

	/**
	 * Returns the current date, without hours, minutes and seconds.
	 * 
	 * @return Returns the current date.
	 */
	public static Date today() {
		if (valueOfToday() != null) {
			return valueOfToday();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		removeTime(cal);
		return cal.getTime();
	}

	/**
	 * @return the value of today as set by the user in the argument
	 *         {@link DateUtils#valueOfTodayArg}. Date is set on
	 *         {@link DateUtils#ISO_DATE_FORMAT_PATTERN}
	 */
	static Date valueOfToday() {
		String today = System.getProperty(valueOfTodayArg);
		if (today == null) {
			return null;
		}
		Date bo2Today;
		try {
			bo2Today = dfIso.get().parse(today);
		} catch (ParseException e) {
			return null;
		}
		return bo2Today;
	}

	/**
	 * Converts a Date to a Calendar object.
	 * 
	 * @param dt
	 *            Date to convert.
	 * 
	 * @return Returns a calendar with the same value as the specified date.
	 */
	public static Calendar getCalendar(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal;
	}

	/**
	 * Gets the year of the specified date.
	 * 
	 * @param date
	 *            Date.
	 * 
	 * @return Returns the year of the specified date.
	 */
	public static final int year(Date date) {
		Calendar cal = DateUtils.getCalendar(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * Gets the month of the specified date.<br>
	 * <b>The month returned is for display format and not the same as the int
	 * values defined on {@link Calendar} (i.e. for January this method will
	 * return 1 and not 0)</b>
	 * 
	 * @param date
	 *            Date.
	 * 
	 * @return Returns the month of the specified date.
	 */
	public static final int month(Date date) {
		Calendar cal = DateUtils.getCalendar(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * Gets the day of the month of the specified date.
	 * 
	 * The first day of month returns 1.
	 * 
	 * @param date
	 *            Date.
	 * 
	 * @return Returns the day of month of the specified date.
	 */
	public static final int days(Date date) {
		Calendar cal = DateUtils.getCalendar(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Gets the time passed between a date and <u>epoch</u> in days.<br>
	 * Epoch is defined in {@link GregorianCalendar} as January 1, 1970. If the
	 * date passed as parameter is before <u>epoch</u> then a negative result
	 * will be returned.
	 * 
	 * @param date
	 *            Date.
	 * 
	 * @return Difference from Date to 'epoch' in days. Negative if Date is
	 *         before epoch
	 */
	public static int daysDifFromEpoch(Date date) {
		Calendar cal = DateUtils.getCalendar(date);
		if (cal.after(EPOCH_CAL)) {
			return difDays(EPOCH_CAL, cal);
		}
		return difDays(cal, EPOCH_CAL) * -1;
	}

	/**
	 * Formats a Date using a format yyyyMMdd.
	 * 
	 * @param dt
	 *            Date to convert.
	 * 
	 * @return Returns a calendar with the same value as the specified date.
	 */
	public static String formatYYYYMMDD(Date dt) {
		return dfYYYYMMDD.get().format(dt);
	}

	/**
	 * returns the date with the time part cleared.
	 *
	 * @param d
	 *            the d
	 * @return date.
	 */
	public static Date getDateAtMidnight(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		removeTime(cal);
		return cal.getTime();
	}

	/**
	 * returns if the given date is Saturday or Sunday.
	 *
	 * @param d
	 *            the d
	 * @return true if the given date corresponds to weekend date
	 */
	public static boolean isWeekend(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		if ((c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
			return true;
		}
		return false;
	}

	/**
	 * Gets the year.
	 *
	 * @param d
	 *            the d
	 * @return this (current) year.
	 */
	public static int getYear(Date d) {
		Calendar c = Calendar.getInstance();
		c.setTime(d);
		return c.get(Calendar.YEAR);
	}

	/**
	 * compare two dates ignoring the time.
	 *
	 * @param d1
	 *            the d 1
	 * @param d2
	 *            the d 2
	 * @return If the dates are equal (ignoring the time part of the dates)
	 */
	public static boolean equalDatesIgnoringTime(Date d1, Date d2) {
		return getDateAtMidnight(d1).equals(getDateAtMidnight(d2));
	}
}