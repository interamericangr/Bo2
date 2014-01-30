package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.utils.GenericsUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.annotations.Child;
import gr.interamerican.bo2.utils.enums.IndexPropertyType;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.util.Collection;
import java.util.Set;

import com.sun.star.uno.RuntimeException;

/**
 * This utility makes it easier to add an element to a {@link Child} collection
 * of a {@link PersistentObject}.
 * 
 */
public class ChildAdder {
	/**
	 * Class of Persistent object.
	 */
	Class<? extends PersistentObject<?>> poClass;
	/**
	 * Class of Persistent object key.
	 */
	Class<? extends Key> keyClass;
	/**
	 * Class of Child Persistent object.
	 */	
	Class<? extends PersistentObject<?>> childPoClass;
	/**
	 * Class of Persistent object key.
	 */
	Class<? extends Key> childKeyClass;
	
	
	
	/**
	 * Index property type.
	 */
	IndexPropertyType indexPropertyType;
	
	/**
	 * Name of child collection property.
	 */
	String childCollectionName;
	
	/**
	 * Name of index property.
	 */
	String indexPropertyName;
	
	/**
	 * Creates a new AddChild object. 
	 * 
	 * @param poClass
	 * @param childCollectionName
	 */
	public ChildAdder(Class<? extends PersistentObject<?>> poClass, String childCollectionName) {
		this.poClass = Utils.cast(poClass);
		this.childCollectionName = childCollectionName;
		
		this.keyClass = Utils.cast(PoUtils.getKeyType(poClass));		
		Class<?> elementsClass = 
				GenericsUtils.getCollectionTypeOfProperty(poClass, childCollectionName);
		
		if (!PersistentObject.class.isAssignableFrom(elementsClass)) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
					"The collection ", childCollectionName, 
					" is not declared as a Collection of some PersistentObject type");
			throw new RuntimeException(msg);					 
		}
		
		this.childPoClass = Utils.cast(elementsClass);
		this.childKeyClass = Utils.cast(PoUtils.getKeyType(childPoClass));
		
		
		TypeAnalysis keyAnalysis = TypeAnalysis.analyze(keyClass);
		Set<String> keyPropertiesSet = keyAnalysis.getNamesOfReadWriteProperties();
		
		TypeAnalysis childKeyAnalysis = TypeAnalysis.analyze(childKeyClass);
		Set<String> childKeyProperties = childKeyAnalysis.getNamesOfReadWriteProperties();

		childKeyProperties.removeAll(keyPropertiesSet);
		
		if (childKeyProperties.size()!=1) {
			String msg = "Could not find index property of child key!"; //$NON-NLS-1$
			throw new RuntimeException(msg);
		}
		
		this.indexPropertyName = childKeyProperties.iterator().next();
		
		BeanPropertyDefinition<?> indexProperty =  
				childKeyAnalysis.getFirstPropertyByName(indexPropertyName);
		
		Class<?> indexPropertyClass = indexProperty.getType();
		
		indexPropertyType = IndexPropertyType.getIndexPropertyTypeFor(indexPropertyClass);
		
		if (indexPropertyType==null) {
			String msg = "Index property type not supported!"; //$NON-NLS-1$
			throw new RuntimeException(msg);	
		}
	}
	
	
	/**
	 * Adds the specified elements
	 *  
	 * @param po 
	 *        Persistent object to add thee elements.
	 * @param objects
	 *        Elements to add.
	 */
	public void add(PersistentObject<?> po, PersistentObject<?>... objects) {
		Object col = ReflectionUtils.getProperty(childCollectionName, po);
		Collection<Object> childCollection = Utils.cast(col);
		for (PersistentObject<?> child : objects) {			
			Object childKey =  child.getKey();
			Object key = po.getKey();
			ReflectionUtils.copyProperties(key, childKey);
			indexPropertyType.addNext(childCollection, child, indexPropertyName);
		}
		
	}
	
	

}
