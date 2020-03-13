package gr.interamerican.bo2.utils;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.AbstractConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;

/**
 * Configures {@link BeanUtilsBean}.
 */
public class BeanUtilsConfiguration {

	/**
	 * configure.
	 */
	static void configureBeanUtils() {
		BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();

		if (beanUtilsBean.getClass() != BeanUtilsBean.class) {
			throw new IllegalStateException(
					"Someone has already switched the default org.apache.commons.beanutils.BeanUtilsBean instance"); //$NON-NLS-1$
		}

		if (beanUtilsBean.getConvertUtils().getClass() != ConvertUtilsBean.class) {
			throw new IllegalStateException(
					"Someone has already switched the default org.apache.commons.beanutils.ConvertUtilsBean instance"); //$NON-NLS-1$
		}
		
		BeanUtilsBean.setInstance(new BeanUtilsBean(new EnumAwareConvertUtilsBean()));

		DateTimeConverter dtConverter = new DateConverter();
		dtConverter.setPattern("yyyy-MM-dd");  //$NON-NLS-1$
		ConvertUtils.register(dtConverter, java.util.Date.class);
	}

	/**
	 * ConvertUtilsBean sub-type that supports enums.
	 */
	public static class EnumAwareConvertUtilsBean extends ConvertUtilsBean {

		/** singleton. */
		private static final EnumConverter ENUM_CONVERTER = new EnumConverter();

		@Override
		public Converter lookup(Class<?> pClazz) {
			Converter converter = super.lookup(pClazz);

			if (converter == null && pClazz.isEnum()) {
				return ENUM_CONVERTER;
			}

			return converter;
		}

	}

	/**
	 * General purpose enum converter.
	 */
	public static class EnumConverter extends AbstractConverter {

		@Override
		protected String convertToString(final Object pValue) throws Throwable {
			if (pValue == null) {
				return null;
			}
			return ((Enum<?>) pValue).name();
		}

		@SuppressWarnings("unchecked")
		@Override
		protected <T> T convertToType(final Class<T> pType, final Object pValue) throws Throwable {
			if(pValue == null) {
				return null;
			}
			
			@SuppressWarnings("rawtypes")
			Class<? extends Enum> type = (Class<? extends Enum>) pType;
			return (T) Enum.valueOf(type, pValue.toString());
		}

		@Override
		protected Class<?> getDefaultType() {
			return null;
		}
	}
}