package gr.interamerican.bo2.utils.meta.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Descriptor bean for metadata of {@link DataFieldDescriptor}
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataFieldMetaDescriptor", propOrder = {
	    "source",
	    "name",
	    "value"
	})
@XmlRootElement
public class DataFieldMetaDescriptor {
	
	/**
	 * Source
	 */
	@XmlElement
	String source;
	
	/**
	 * Name
	 */
	@XmlElement
	String name;
	
	/**
	 * Value
	 */
	@XmlElement
	String value;

	/**
	 * Returns the source
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Sets the source
	 * @param source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Returns the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the value
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
