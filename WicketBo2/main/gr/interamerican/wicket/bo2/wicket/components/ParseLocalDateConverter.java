package gr.interamerican.wicket.bo2.wicket.components;

import java.util.Date;
import java.util.Locale;

import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.string.Strings;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 */
public class ParseLocalDateConverter extends PatternDateConverter {

	/**
	 * constructor
	 *
	 * @param datePattern
	 *
	 * @param applyTimeZoneDifference
	 */
	public ParseLocalDateConverter(String datePattern, boolean applyTimeZoneDifference) {
		super(datePattern, applyTimeZoneDifference);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Date convertToObject(String value, Locale locale) {
		if (Strings.isEmpty(value)) {
			return null;
		}
		DateTimeFormatter format = getFormat(locale);
		if (format == null) {
			throw new IllegalStateException("format must be not null"); //$NON-NLS-1$
		}
		if (getApplyTimeZoneDifference()) {
			LocalDateTime dateTime = null;
			// set time zone for client
			format = format.withZone(getTimeZone());
			try {
				// parse date retaining the time of the submission
				dateTime = format.parseLocalDateTime(value);
			} catch (RuntimeException e) {
				throw new ConversionException(e);
			}
			return dateTime.toDate();
		}
		try {
			LocalDateTime date = format.parseLocalDateTime(value);
			return date.toDate();
		} catch (RuntimeException e) {
			throw new ConversionException(e);
		}
	}

}
