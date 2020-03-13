package gr.interamerican.bo2.utils.meta.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Descriptor bean for fields
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataFieldDescriptor", propOrder = {
	    "name",
	    "description",
	    "propertyName",
	    "columnName",
	    "fieldMeta"
	})
@XmlRootElement
public class DataFieldDescriptor {
	
	/**
	 * Field's name
	 */
	@XmlElement
	String name;
	
	/**
	 * Field's description
	 */
	@XmlElement
	String description;
	
	/**
	 * Property's name
	 */
	@XmlElement
	String propertyName;
	
	/**
	 * Column's name
	 */
	@XmlElement
	String columnName;
	
	/**
	 * Constraints
	 */
	@XmlElement
	List<DataFieldMetaDescriptor> fieldMeta = new ArrayList<DataFieldMetaDescriptor>();

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
	 * Returns the description
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns property's name
	 * @return property's name
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * Sets property's name
	 * @param propertyName
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * Returns column's name
	 * @return column's name
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * Sets column's name
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * Returns field's meta
	 * @return Returns field's meta 
	 */
	public List<DataFieldMetaDescriptor> getFieldMeta() {
		return fieldMeta;
	}

	/**
	 * Sets field's meta
	 * @param fieldMeta
	 */
	public void setFieldMeta(List<DataFieldMetaDescriptor> fieldMeta) {
		this.fieldMeta = fieldMeta;
	}
	
}
