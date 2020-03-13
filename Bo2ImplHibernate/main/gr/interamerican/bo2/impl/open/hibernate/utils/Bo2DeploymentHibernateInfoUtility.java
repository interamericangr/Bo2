package gr.interamerican.bo2.impl.open.hibernate.utils;

import gr.interamerican.bo2.creation.beans.ObjectFactoryImpl;
import gr.interamerican.bo2.creation.exception.ClassCreationException;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.hibernate.HibernateSessionProviderImpl;
import gr.interamerican.bo2.impl.open.utils.Bo2;
import gr.interamerican.bo2.impl.open.utils.Bo2Deployment;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.ExceptionUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * utility to get deployment information regarding hibernate.
 */
public class Bo2DeploymentHibernateInfoUtility {

	/**
	 * Logger.
	 */
	private static Logger LOGGER = LoggerFactory.getLogger(ObjectFactoryImpl.class);

	/**
	 * Gets the hbm contents.
	 *
	 * @param hbmPath the hbm path
	 * @return the contents of the hdm file, null if not found.
	 */
	private String getHbmContents(String hbmPath) {
		String hbm = null;
		try {
			hbm = StreamUtils.getStringFromResourceFile(hbmPath);
		} catch (RuntimeException e) {
			String msg = StringUtils.concat(
					"Non existant mappings file: ", //$NON-NLS-1$
					hbmPath,
					". This is acceptable for unit tests, but FATAL in every other case and should be investigated."); //$NON-NLS-1$
			LOGGER.warn(msg);
			return null;
		}
		return hbm;
	}

	/**
	 * Gets the package from hbm.
	 *
	 * @param hbmPath the hbm path
	 * @return the package from the given hbm
	 */
	private String getPackageFromHbm(String hbmPath) {
		String hbm = getHbmContents(hbmPath);
		if (hbm == null) {
			return null;
		}
		Document doc = Jsoup.parse(hbm);
		Element el = doc.select("hibernate-mapping").first(); //$NON-NLS-1$
		String pack = el.attr("package"); //$NON-NLS-1$
		return pack;
	}
	
	/**
	 * Gets the class from hbm.
	 *
	 * @param hbmPath the hbm path
	 * @return the class for the given hbm.
	 */
	String getClassFromHbm(String hbmPath) {
		String hbm = getHbmContents(hbmPath);
		if (hbm == null) {
			return null;
		}
		Document doc = Jsoup.parse(hbm);
		String pack = getPackageFromHbm(hbmPath);
		Element clazz = doc.select("class").first(); //$NON-NLS-1$
		String c = clazz.attr("name"); //$NON-NLS-1$
		if (StringUtils.isNullOrBlank(pack)) {
			return c;
		}
		return pack + StringConstants.DOT + c;
	}

	/**
	 * Gets the sub classes from hbm.
	 *
	 * @param hbmPath the hbm path
	 * @return the join-subclasses from the hbm
	 */
	Set<String> getSubClassesFromHbm(String hbmPath) {
		Set<String> clazzes = new HashSet<String>();
		String hbm = getHbmContents(hbmPath);
		if (hbm == null) {
			return clazzes;
		}
		Document doc = Jsoup.parse(hbm);
		Set<Element> elements = new HashSet<Element>();
		elements.addAll(doc.select("joined-subclass"));//$NON-NLS-1$
		elements.addAll(doc.select("subclass"));//$NON-NLS-1$
		for (Element el : elements) {
			String c = el.attr("name"); //$NON-NLS-1$
			if (c.contains(StringConstants.DOT)) {
				clazzes.add(c);
			} else {
				String pack = getPackageFromHbm(hbmPath);
				if (StringUtils.isNullOrBlank(pack)) {
					clazzes.add(c);
				} else {
					clazzes.add(pack + StringConstants.DOT + c);
				}
			}
		}
		return clazzes;
	}

	/**
	 * Generate class from string.
	 *
	 * @param className the class name
	 * @return the class that will be created by the given classname.
	 */
	Class<?> generateClassFromString(String className) {
		if (StringUtils.isNullOrBlank(className)) {
			return null;
		}
		Object obj = null;
		try {
			obj = Factory.create(className);
		} catch (AbstractMethodError err) {
			LOGGER.warn("Non-Creatable class " + className); //$NON-NLS-1$
			return null;
		} catch (RuntimeException cce) {
			if (ExceptionUtils.isCausedBy(cce, ClassCreationException.class)) {
				LOGGER.error("Non-Creatable class " + className); //$NON-NLS-1$
			} else {
				throw cce;
			}
		}
		return obj.getClass();
	}
	
	/**
	 * Gets the hibernate classes from flat file.
	 *
	 * @param propertyValue the property value
	 * @return a set of hibernate classes from the given flat file.
	 */
	Set<Class<?>> getHibernateClassesFromFlatFile(String propertyValue) {
		Set<Class<?>> clazzes = new HashSet<Class<?>>();
		try {
			String[] hbmNames = StreamUtils.readResourceFile(propertyValue);
			for (String hbmName : hbmNames) {
				if ((hbmName.length() == 0) || (hbmName.charAt(0) == StringConstants.SHARP.charAt(0))) {
					continue;
				}
				String className = getClassFromHbm(hbmName);
				Class<?> c=generateClassFromString(className);
				if (c!=null){
					clazzes.add(c);
				}
				Set<String> classNames = getSubClassesFromHbm(hbmName);
				for (String s : classNames) {
					c = generateClassFromString(s);
					if (c != null) {
						clazzes.add(c);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return clazzes;
	}

	/**
	 * Gets the hibernate classes from cfg.
	 *
	 * @param propertyValue the property value
	 * @return the hibernate classes from cfg
	 */
	Set<Class<?>> getHibernateClassesFromCfg(String propertyValue) {
		Set<Class<?>> clazzes = new HashSet<Class<?>>();
		String cfg = StreamUtils.getStringFromResourceFile(propertyValue);
		Document doc = Jsoup.parse(cfg);
		for (Element el : doc.select("mapping")) { //$NON-NLS-1$
			String hbmName = el.attr("resource"); //$NON-NLS-1$
			String className = getClassFromHbm(hbmName);
			Class<?> clazz = generateClassFromString(className);
			if (clazz != null) {
				clazzes.add(clazz);
			}
		}
		return clazzes;
	}
	
	/**
	 * Gets the hibernate classes.
	 *
	 * @param property the property
	 * @return a set of classes that exist in the hibernate configurations/mapping of the given manager.
	 */
	public Set<Class<?>> getHibernateClasses(Properties property) {
		Set<Class<?>> clazzes = new HashSet<Class<?>>();
		if (!StringUtils.isNullOrBlank(property.getProperty(HibernateSessionProviderImpl.HIBERNATE_CFG_XML))) {
			clazzes.addAll(getHibernateClassesFromCfg(property
					.getProperty(HibernateSessionProviderImpl.HIBERNATE_CFG_XML)));
		}
		if (!StringUtils.isNullOrBlank(property.getProperty(HibernateSessionProviderImpl.HIBERNATE_MAPPINGS))) {
			clazzes.addAll(getHibernateClassesFromFlatFile(property
					.getProperty(HibernateSessionProviderImpl.HIBERNATE_MAPPINGS)));
		}
		return clazzes;
	}

	/**
	 * Finds and returns the manager names of all managers that use hibernate resources.
	 *
	 * @param deplPath
	 *            Path of the Bo2 deployment properties
	 *
	 * @return The manager names of all managers that use jdbc resources.
	 */
	public List<Properties> getHibernateManagers(String deplPath) {
		try {
			Bo2Deployment bo2Depl = Bo2.getDeployment(deplPath);
			List<Properties> managers = new ArrayList<Properties>();
			String path = bo2Depl.getDeploymentBean().getPathToManagersList();
			String[] managerPaths = StreamUtils.readResourceFile(path);
			for (String managerPath : managerPaths) {
				Properties p = CollectionUtils.readEnhancedProperties(managerPath);
				if (!StringUtils.isNullOrBlank(p.getProperty(HibernateSessionProviderImpl.HIBERNATE_CFG_XML))) {
					managers.add(p);
				} else if (!StringUtils.isNullOrBlank(p.getProperty(HibernateSessionProviderImpl.HIBERNATE_MAPPINGS))) {
					managers.add(p);
				}
			}
			return managers;
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	/**
	 * Finds and returns the manager names of all managers that use hibernate resources for the default Bo2 deployment.
	 *
	 * @return The manager names of all managers that use jdbc resources.
	 */
	public List<Properties> getHibernateManagers() {
		return getHibernateManagers(Bo2.DEFAULT_DEPLOYMENT_PROPERTIES_PATH);
	}

	/**
	 * Gets the all hibernate classes.
	 *
	 * @return all hibernate classes.
	 */
	public Set<Class<?>> getAllHibernateClasses() {
		Set<Class<?>> clazzes = new HashSet<Class<?>>();
		for (Properties p : getHibernateManagers()) {
			clazzes.addAll(getHibernateClasses(p));
		}
		return clazzes;
	}
	/**
	 * Factory method.
	 *
	 * @return Bo2DeploymentInfoUtility instance.
	 */
	public static Bo2DeploymentHibernateInfoUtility get() {
		return new Bo2DeploymentHibernateInfoUtility();
	}
}
