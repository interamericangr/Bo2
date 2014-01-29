package gr.interamerican.bo2.impl.open.po;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.sun.star.uno.RuntimeException;

import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.utils.GenericsUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

/**
 * 
 */
public class AddChild<P extends PersistentObject<?>> {
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
	 * Persistent object.
	 */
	P po;
	
	/**
	 * Creates a new AddChild object. 
	 *
	 * @param po
	 * @param childCollection
	 */
	public AddChild(P po, String childCollection) {
		String noIndexMsg = "Could not find index property of child key!"; //$NON-NLS-1$
		
		this.po = po;
		this.poClass = Utils.cast(po.getClass());
		this.keyClass = Utils.cast(PoUtils.getKeyType(poClass));
		Class<?> elementsClass = 
				GenericsUtils.getCollectionTypeOfProperty(poClass, childCollection);
		
		if (!PersistentObject.class.isAssignableFrom(elementsClass)) {
			@SuppressWarnings("nls")
			String msg = StringUtils.concat(
					"The collection ", childCollection, 
					" is not declared as a Collection of some PersistentObject type");
			throw new RuntimeException(msg);					 
		}
		
		this.childPoClass = Utils.cast(elementsClass);
		this.childKeyClass = Utils.cast(PoUtils.getKeyType(childPoClass));
		
		
		TypeAnalysis keyAnalysis = TypeAnalysis.analyze(keyClass);
		Set<String> keyProperties = keyAnalysis.getNamesOfReadWriteProperties();
		
		TypeAnalysis childKeyAnalysis = TypeAnalysis.analyze(childKeyClass);
		Set<String> childKeyProperties = childKeyAnalysis.getNamesOfReadWriteProperties();

		childKeyProperties.removeAll(keyProperties);
		
		if (childKeyProperties.size()!=1) {
			throw new RuntimeException(noIndexMsg);
		}
		
		String indexPropertyName = childKeyProperties.iterator().next();
		
		BeanPropertyDefinition<?> indexProperty =  
				childKeyAnalysis.getFirstPropertyByName(indexPropertyName);
		
		Class<?> indexPropertyType = indexProperty.getType();
		
		
		
	}
	
	
	
	

}
