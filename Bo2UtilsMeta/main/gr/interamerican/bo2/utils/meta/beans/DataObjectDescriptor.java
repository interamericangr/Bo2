package gr.interamerican.bo2.utils.meta.beans;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Descriptor bean for Objects  
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dataObjectDescriptor", propOrder = {
	    "name",
	    "description",
	    "className",
	    "tableName",
	    "schemaName",
	    "databaseName",
	    "fieldDescriptors"
	})
@XmlRootElement
public class DataObjectDescriptor {
	
	/**
	 * Object's name
	 */
	@XmlElement
	String name;

	/**
	 * Obkect's description
	 */
	@XmlElement
	String description;
	
	/**
	 * Object's class name;
	 */
	@XmlElement
	String className;
	
	/**
	 * Object's corresponding table name
	 */
	@XmlElement
	String tableName;
	
	/**
	 * Schema name of table
	 */
	@XmlElement
	String schemaName;
	
	/**
	 * Database name
	 */
	@XmlElement
	String databaseName;
	
	/**
	 * {@link DataFieldDescriptor}
	 */
	@XmlElement
	List<DataFieldDescriptor> fieldDescriptors = new ArrayList<DataFieldDescriptor>();

	/**
	 * Returns object's name
	 * @return object's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the object's name
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns object's description
	 * @return object's description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets object's description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns object's class name
	 * @return object's class name
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * Sets object's class name
	 * @param className
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * Returns object's table name
	 * @return object's table name
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Sets object's table name
	 * @param tableName
	 */	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * Returns object's schema name
	 * @return object's schema name
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * Sets object's schema name
	 * @param schemaName
	 */	
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	
	
	/**
	 * Returns database name
	 * @return database name
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * Sets database name
	 * @param databaseName
	 */	
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * Returns object's {@link DataFieldDescriptor}s
	 * @return object's {@link DataFieldDescriptor}s
	 */
	public List<DataFieldDescriptor> getFieldDescriptors() {
		return fieldDescriptors;
	}

	/**
	 * Sets object's {@link DataFieldDescriptor}s
	 * @param fieldDescriptors
	 */
	public void setFieldDescriptors(List<DataFieldDescriptor> fieldDescriptors) {
		this.fieldDescriptors = fieldDescriptors;
	}

}
