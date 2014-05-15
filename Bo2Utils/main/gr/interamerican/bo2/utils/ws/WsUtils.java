package gr.interamerican.bo2.utils.ws;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for WsPcOne  module.
 */
public class WsUtils {

	/**
	 * Logger.
	 */
	public static final Logger logger = LoggerFactory.getLogger(WsUtils.class);

	/**
	 * DatatypeFactory.
	 */
	static DatatypeFactory DATATYPE_FACTORY;

	static {
		try {
			DATATYPE_FACTORY = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * {@link XMLGregorianCalendar} to {@link Date}
	 * 
	 * @param xmlCalendar
	 * @return Date
	 */
	public static Date xmlGregorianCalendar2Date(XMLGregorianCalendar xmlCalendar) {
		if (xmlCalendar == null) {
			return null;
		}
		return xmlCalendar.toGregorianCalendar().getTime();
	}

	/**
	 * {@link Date} to {@link XMLGregorianCalendar}
	 * 
	 * @param date
	 * @return XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar date2XmlGregorianCalendar(Date date) {
		if (date == null) {
			return null;
		}
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		return DATATYPE_FACTORY.newXMLGregorianCalendar(c);
	}

	/**
	 * Hidden, empty.
	 */
	private WsUtils() { /* empty */ }

}
