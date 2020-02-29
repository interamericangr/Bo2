package gr.interamerican.bo2.hbm.meta.utils;

import gr.interamerican.bo2.hbm.meta.beans.Clazz;
import gr.interamerican.bo2.hbm.meta.beans.HibernateMapping;
import gr.interamerican.bo2.hbm.meta.xml.resolver.IgnoreDoctypeResolver;
import gr.interamerican.bo2.utils.ConditionUtils;
import gr.interamerican.bo2.utils.StreamUtils;
import gr.interamerican.bo2.utils.conditions.Condition;
import gr.interamerican.bo2.utils.conditions.InstanceOf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Utility class with jaxb utils for hibernate marshall/unmarshall 
 */
public class HbmMetaUtils {
	
	/**
	 * Creates an xml file from the given {@link HibernateMapping} object
	 * 
	 * @param filePath
	 * @param object 
	 */
	public static void marshal(String filePath, HibernateMapping object) {
		File file = new File(filePath);
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(HibernateMapping.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(object, file);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns the {@link HibernateMapping} object 
	 * created from the input file
	 * 
	 * @param filePath
	 * @return the {@link HibernateMapping} object 
	 * created from the input file
	 */
	public static HibernateMapping unmarshal(String filePath) {
		try {
			JAXBContext jc = JAXBContext.newInstance(HibernateMapping.class);
			SAXParserFactory spf = SAXParserFactory.newInstance();
	        spf.setNamespaceAware(true); // Binding attributes
			XMLReader xmlReader = spf.newSAXParser().getXMLReader();
			xmlReader.setEntityResolver(new IgnoreDoctypeResolver());
			InputSource inputSource = new InputSource(StreamUtils.getFileStream(filePath));
			SAXSource source = new SAXSource(xmlReader, inputSource);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			HibernateMapping bean = (HibernateMapping) unmarshaller.unmarshal(source);
			return bean;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} catch (SAXException e) {
			throw new RuntimeException(e);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Returns a sublist of objects which are contained in the given list
	 * and are instance of 'classToRetrieve'   
	 * @param list 
	 * @param classToRetrieve 
	 * @return a sublist of objects which are contained in the given list
	 * and are instance of 'classToRetrieve'   
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getSubListOfClass(List<Object> list, Class<T> classToRetrieve) {
		Condition<Object> condition = new InstanceOf(classToRetrieve);
		List<Object> subset = ConditionUtils.getSubset(list, condition);
		List<T> result = new ArrayList<T>();
		for(Object o : subset){
			result.add((T) o);
		}
		return result;
	}
	
	/**
	 * Returns the {@link Clazz} from a {@link HibernateMapping}
	 * @param hbm
	 * @return the {@link Clazz} from a {@link HibernateMapping}
	 */
	public static  Clazz getClazz(HibernateMapping hbm){
		List<Clazz> clazz = getSubListOfClass(hbm.getMetaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject(), Clazz.class);
		if (clazz.size() ==1){
			return clazz.get(0);
		}
		throw new RuntimeException("None or more than one in the hbm file"); //$NON-NLS-1$
	}
	
}
