/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.utils.beans.ModificationRecordImpl;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.impl.open.annotations.Bo2AnnoUtils;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.GenericsUtils;
import gr.interamerican.bo2.utils.JavaBeanUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.adapters.Modification;
import gr.interamerican.bo2.utils.annotations.Child;
import gr.interamerican.bo2.utils.matching.SameTypeAllPropertiesEqualMatchingRule;
import gr.interamerican.bo2.utils.reflect.AccessorUtils;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;
import gr.interamerican.bo2.utils.reflect.beans.BeanPropertyDefinition;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities for {@link PersistentObject}s.
 */
public class PoUtils {
	
	/**
	 * Name of detachStrategy property.
	 */
	public static String DETACH_STRATEGY = "detachStrategy"; //$NON-NLS-1$

	/**
	 * Logger.
	 */
	private static Logger logger = LoggerFactory.getLogger(PoUtils.class);
	
	/**
	 * Cache of PersistentObject type to Key type associations.
	 */
	private static Map<Class<?>, Class<?>> poKeys = new HashMap<Class<?>, Class<?>>(); 
	
	
	/**
	 * Makes a deep copy of an object.
	 * 
	 * Persistent objects, arrays and collections with {@link Child} annotation
	 * are deep-copied. 
	 * 
	 * Persistent objects without {@link Child} annotation are shallow-copied.
	 * 
	 * Arrays and collections without {@link Child} annotation have their elements
	 * shallow-copied, but new arrays and collection instances are created to hold
	 * the copies.
	 * 
	 * ModificationRecords properties are not copied. 
	 * 
	 * @param one
	 *            object to copy.
	 * @param resetMdfRec
	 *        Indicates if the mdf of the copy must be reset. 
	 * 
	 * @param <P>
	 *            Type of persistent objects.
	 * 
	 * @return Returns the copy.
	 */
	@SuppressWarnings("nls")
	private static <P extends PersistentObject<?>> P deepCopy(P one, boolean resetMdfRec) {		
		if (one == null) {			
			return null;
		}
		Bo2Session.setState(PoOperation.COPY);
		@SuppressWarnings("unchecked")
		Class<P> poClass = (Class<P>) one.getClass();
		P two = ReflectionUtils.newInstance(poClass);		
		TypeAnalysis analysis = TypeAnalysis.analyze(poClass);
		Set<Field> fields = analysis.getNonStaticFields();
		for (Field field : fields) {
			if (logger.isTraceEnabled()) {
				String msg = StringUtils.concat(
						"<<< processing field: ", field.getName(),
						" : ", strip(field.getType().getName()));
				logger.trace(msg);
			} 			
			if (!Modifier.isTransient(field.getModifiers())) {
				ReflectionUtils.setAccessible(field);
				Object oneFieldValue = ReflectionUtils.get(field, one);
				boolean isChild = field.isAnnotationPresent(Child.class);
				Object twoFieldValue = copyObject(oneFieldValue, isChild, resetMdfRec);				
				ReflectionUtils.set(field, twoFieldValue, two);
			}
		}
		Bo2Session.setState(null);
		if (resetMdfRec) {
			resetModificationRecord(two);				
		}
		return two;
	}
	
	
	/**
	 * Makes a deep copy of an object.
	 * 
	 * Persistent objects, arrays and collections with {@link Child} annotation
	 * are deep-copied. 
	 * 
	 * Persistent objects without {@link Child} annotation are shallow-copied.
	 * 
	 * Arrays and collections without {@link Child} annotation have their elements
	 * shallow-copied, but new arrays and collection instances are created to hold
	 * the copies.
	 * 
	 * ModificationRecords properties are not copied. 
	 * 
	 * @param one
	 *            object to copy.
	 * 
	 * @param <P>
	 *            Type of persistent objects.
	 * 
	 * @return Returns the copy.
	 */	
	public static <P extends PersistentObject<?>> P deepCopy(P one) {		
		return deepCopy(one,true);
	}
	
	/**
	 * Makes a deep copy of an object.
	 * 
	 * Persistent objects, arrays and collections with {@link Child} annotation
	 * are deep-copied. 
	 * 
	 * Persistent objects without {@link Child} annotation are shallow-copied.
	 * 
	 * Arrays and collections without {@link Child} annotation have their elements
	 * shallow-copied, but new arrays and collection instances are created to hold
	 * the copies.
	 * 
	 * ModificationRecords properties are not copied. 
	 * 
	 * @param one
	 *            object to copy.
	 * 
	 * @param <P>
	 *            Type of persistent objects.
	 * 
	 * @return Returns the copy.
	 */	
	public static <P extends PersistentObject<?>> P deepCopyPreservingModificationRecord(P one) {		
		return deepCopy(one,false);
	}
	
	
	/**
	 * Makes a deep copy of an object.
	 * 
	 * Persistent objects, arrays and collections with {@link Child} annotation
	 * are deep-copied. 
	 * 
	 * Persistent objects without {@link Child} annotation are shallow-copied.
	 * 
	 * Arrays and collections without {@link Child} annotation have their elements
	 * shallow-copied, but new arrays and collection instances are created to hold
	 * the copies.
	 * 
	 * ModificationRecords properties are not copied. The user should manually
	 * set the lastModifiedBy properties before storing. It is not required to
	 * do anything for the lastModified property. This is taken care by
	 * Hibernate.
	 * 
	 * @param one
	 *            object to copy.
	 * @param resetMdfRec 
	 *        Indicates if the modification record properties of the copied 
	 *        object will be reset.  	            
	 * 
	 * @param <P>
	 *            Type of persistent objects.
	 * 
	 * @return Returns the copy.
	 */
	@SuppressWarnings("nls")
	public static <P extends PersistentObject<?>> P deepCopyUsingAccessors(P one, boolean resetMdfRec) {		
		if (one == null) {			
			return null;
		}
		Bo2Session.setState(PoOperation.COPY);
		
		@SuppressWarnings("unchecked")
		Class<P> poClass = (Class<P>) one.getClass();
		P two = ReflectionUtils.newInstance(poClass);
		
		List<Field> fields = ReflectionUtils.allFields(poClass, PersistentObject.class);

		for (Field field : fields) {
			if (logger.isTraceEnabled()) {
				String msg = StringUtils.concat(
						"<<< processing field: ", field.getName(),
						" : ", strip(field.getType().getName()));
				logger.trace(msg);
			}
			if (Modifier.isTransient(field.getModifiers()) 
			|| Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			
			Object twoFieldValue;
			Object oneFieldValue;
			
			Method getter = AccessorUtils.getGetter(field, poClass);
			if (getter != null) {				
				Object[] args = null;
				oneFieldValue = ReflectionUtils.invoke(getter, one, args);				
			}  else {
				if(!field.isAccessible()) {
					field.setAccessible(true);
				}
				oneFieldValue = ReflectionUtils.get(field, one);				
			}
			
			boolean isChild = field.isAnnotationPresent(Child.class);
			twoFieldValue = copyObject(oneFieldValue, isChild, resetMdfRec);
			
			Method setter = AccessorUtils.getSetter(field, poClass);
			if (setter != null) {
				Object[] args = new Object[] { twoFieldValue };
				ReflectionUtils.invoke(setter, two, args);
			} else {
				ReflectionUtils.setAccessible(field);
				ReflectionUtils.set(field, twoFieldValue, two);
			}
		}		
		Bo2Session.setState(null);
		if (resetMdfRec) {
			resetModificationRecord(two);				
		}
		return two;
	}

	/**
	 * Gets a copy of an an object. If the child flag is marked true
	 * then the object is marked with a {@link Child} annotation. This annotation
	 * should only be used on fields of {@link PersistentObject}, and collections
	 * or arrays containing {@link PersistentObject}.
	 * 
	 * Collections / arrays that are not marked with the {@link Child} have their elements
	 * shallow-copied, but new Collection / array instances are created. This works well
	 * for Collections / arrays of immutable objects (Strings, primitive wrappers, Enumerations).
	 * 
	 * 
	 * @param obj
	 *        Object to copy.
	 * @param child
	 *        Indicator if this is a child field.
	 * @param resetMdfRec 
	 *        Indicates if the modification record properties of the copied 
	 *        object will be reset.          
	 * @param <T> 
	 *        Type of object being copied. 
	 * 
	 * @return Returns a copy of an object.
	 */
	@SuppressWarnings("unchecked")
	static <T> T copyObject(T obj, boolean child, boolean resetMdfRec) {
		
		if (obj == null) {			
			return null;
		}
		
		if (obj instanceof Set) {
			Set<?> one = (Set<?>) obj;
			Set<?> two = copySet(one, child, resetMdfRec);
			return (T) two;
		}
		
		if (obj instanceof List) {
			List<?> one = (List<?>) obj;
			List<?> two = copyList(one, child, resetMdfRec);
			return (T) two;
		}
		
		if (obj instanceof Object[]) {
			Object[] one = (Object[]) obj;
			Object[] two = copyArray(one, child, resetMdfRec);
			return (T) two;
		}
		
		if (obj instanceof PersistentObject) {
			PersistentObject<?> po = (PersistentObject<?>) obj;			
			return (T)copyPo(po, child, resetMdfRec);
		}
		
		return PoCopier.get().copy(obj);
	}
	
	
	
	/**
	 * Copies a PersistentObject.
	 * 
	 * @param po
	 *        PersistentObject.
	 * @param child
	 *        Indication if the specified PersistentObject is a child.
	 * @param resetMdfRec 
	 *        Indicates if the modification record properties of the copied 
	 *        object will be reset.  	         
	 * @param <P>
	 *        Type of PersistentObject.
	 *        
	 * @return Returns the copy of the specified PersistentObject.
	 */
	static <P extends PersistentObject<?>> P copyPo(P po, boolean child, boolean resetMdfRec) {		
		if (child) { //Deep copy for child po
			P copied = copyChildPo(po,resetMdfRec);
			if (resetMdfRec) {
				resetModificationRecord(copied);				
			}
			return copied;
		} else { //shallow copy for POs that are not children.
			return po; 
		}
	}
	
	/**
	 * Makes a deep copy of a Child PersistentObject.
	 * 
	 * @param po
	 *        PersistentObject to copy.
	 * @param resetMdfRec 
	 *        Indicates if the modification record properties of the copied 
	 *        object will be reset.  	         
	 * @param <P>
	 *        Type of PersistentObject.
	 *        
	 * @return Returns a deep copy of the specified PersistentObject. 
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	static <P extends PersistentObject<?>> P copyChildPo(P po, boolean resetMdfRec) {
		Modification<PersistentObject<?>> onCopyChild = PoCopier.get().getOnCopyChildPo();
		P forCopy = po;
		if (onCopyChild!=null) {
			forCopy = (P) onCopyChild.execute(po);
		}		
		logger.trace("about to copy persistent object: " + strip(po.getClass().getName()));
		P result = deepCopy(forCopy, resetMdfRec);		
		logger.trace("Copied persistent object: " + strip(po.getClass().getName()));
		return result;		
	}
	
	
	/**
	 * Executes the transformation that must take place on copy and
	 * returns the transformed persistent object.
	 * 
	 * Non child persistent objects are shallow copied, therefore the
	 * modification record is never reset.
	 * 
	 * @param po
	 *        PersistentObject to copy.
	 * @param <P>
	 *        Type of PersistentObject.
	 *        
	 * @return Returns the transformed object ready to copy. 
	 */
	@SuppressWarnings({ "unchecked"})
	static <P extends PersistentObject<?>> P copyNonChildPo(P po) {
		Modification<PersistentObject<?>> onCopy = PoCopier.get().getOnCopyPo();
		P forCopy = po;
		if (onCopy!=null) {
			forCopy = (P) onCopy.execute(po);
		}		
		return forCopy;
	}
	
	/**
	 * Copies an element of an Iterable.
	 * 
	 * @param object
	 *        Element to copy. 
	 * @param child
	 *        Indicator if the Iterable marked as child.
	 * @param resetMdfRec 
	 *        Indicates if the modification record properties of the copied 
	 *        object will be reset.  	         
	 * @param <T> 
	 *        Type of object being copied.        
	 *        
	 * @return Returns a copy of the element.
	 */
	@SuppressWarnings("unchecked")
	static <T> T copyElement(T object, boolean child, boolean resetMdfRec) {		
		if (object instanceof PersistentObject<?>) {
			PersistentObject<?> po = (PersistentObject<?>) object;
			return (T) copyPo(po, child, resetMdfRec);
		}
		return copyObject(object, false, resetMdfRec); //Only a PO can be child.
	}
	
	/**
	 * Copies an array.
	 * 
	 * @param one
	 *        Array to copy.
	 * @param child
	 *        Indicator if this is a Child array.
	 * @param resetMdfRec 
	 *        Indicates if the modification record properties of the copied 
	 *        object will be reset.  	         
	 *        
	 * @return Returns a copy of the array.
	 */	
	static Object[] copyArray(Object[] one, boolean child, boolean resetMdfRec) {		
		Object[] two = new Object[one.length];
		for (int i = 0; i < one.length; i++) {			
			two[i] = copyElement(one[i], child, resetMdfRec);
		}		
		return two;
	}
	
	/**
	 * Copies a List.
	 * 
	 * @param one
	 *        List to copy.
	 * @param child
	 *        Indicator if this is a Child collection.
	 * @param resetMdfRec 
	 *        Indicates if the modification record properties of the copied 
	 *        objects will be reset.  	         
	 * @param <T> 
	 *        Type of objects in the list.       
	 *        
	 * @return Returns a copy of the array.
	 */	
	static <T> List<T> copyList(List<T> one, boolean child, boolean resetMdfRec) {
		List<T> two = new ArrayList<T>();
		for (T t : one) {
			two.add(copyElement(t, child, resetMdfRec));
		}
		return two;
	}
	
	/**
	 * Copies a List.
	 * 
	 * @param one
	 *        List to copy.
	 * @param child
	 *        Indicator if this is a Child collection.
	 * @param resetMdfRec 
	 *        Indicates if the modification record properties of the copied 
	 *        objects will be reset.       
	 * @param <T> 
	 *        Type of objects in the list.       
	 * 
	 *        
	 * @return Returns a copy of the array.
	 */	
	static <T> Set<T> copySet(Set<T> one, boolean child, boolean resetMdfRec) {
		Set<T> two = new HashSet<T>();
		for (T t : one) {
			two.add(copyElement(t, child, resetMdfRec));
		}
		return two;
	}
	
	
	
	/**
	 * Checks deep equality of two PersistentObjects that belong to the same class.
	 * 
	 * @param one
	 *            First object.
	 * @param two
	 *            Second object.
	 * @param <P>
	 *            Type of persistent objects.
	 * 
	 * @return Returns true if the objects have all their fields equal.
	 */
	@SuppressWarnings("nls")
	public static <P extends PersistentObject<?>> boolean deepEquals(P one, P two) {
		
		if (one == null || two == null) {
			logger.trace("<<<");
			logger.trace(">>> finished check. at least one was null.");			
			return (one == null && two == null);
		}
		
		logger.trace("<<< checking for equality: " + one.toString() + " " + two.toString());
		
		if (one == two) {
			logger.trace(">>> ended check. one==two.");
			return true;
		}

		if (!one.getKey().equals(two.getKey())) {
			logger.trace(">>> ended check. no Key equality.");
			return false;
		}
		if (one.getClass() != two.getClass()) {
			logger.trace(">>> ended check. different classes.");
			return false;
		}
		List<Field> fields = ReflectionUtils.allFields(one.getClass(),
				PersistentObject.class);
		for (Field field : fields) {
			logger.trace("<<< checking field " + field.getName());
			if (!fieldsAreEqual(field, one, two)) {
				return false;
			}
		}
		return true;

	}

	/**
	 * Checks if a field has equal values in two objects, based on the 
	 * following rules:
	 * 
	 * Child POs and child collections/arrays are deep checked.
	 * detachStrategy and modificationRecord fields are ignored.
	 * 
	 * @param <P> type to check
	 * 
	 * @param field
	 * @param one
	 * @param two
	 * @return true if a field has equal values in two objects.
	 */
	@SuppressWarnings({ "unchecked", "nls" })
	private static <P extends PersistentObject<?>> boolean fieldsAreEqual(Field field, Object one, Object two) {
		field.setAccessible(true);
		Object oneField = ReflectionUtils.get(field, one);
		Object twoField = ReflectionUtils.get(field, two);
		
		boolean fieldIsPo = PersistentObject.class.isAssignableFrom(field.getType());
		boolean fieldIsChild = field.isAnnotationPresent(Child.class);
		boolean fieldIsMdf = ModificationRecord.class.isAssignableFrom(field.getType());
		boolean fieldIsIterable = Iterable.class.isAssignableFrom(field.getType());
		boolean fieldIsArray = Object[].class.isAssignableFrom(field.getType());
		boolean fieldIsDetachStrategy = DetachStrategy.class.equals(field.getType());
		
		String oneFieldStr = (oneField==null) ? "null" : oneField.toString();
		String twoFieldStr = (twoField==null) ? "null" : twoField.toString();
		logger.trace("<<< checking field for equality: " + oneFieldStr + " " + twoFieldStr);
		
		if(fieldIsDetachStrategy) {
			logger.trace(">>> field was detachStrategy, equal by default.");
			return true;
		}
		
		if (oneField == null || twoField == null) {
			logger.trace(">>> ended check. at least one was null.");
			return (oneField == null && twoField == null);
		}
		
		if (oneField == twoField) {
			logger.trace(">>> ended check. one==two.");
			return true;
		}
		
		if (fieldIsMdf) {
			if(oneField.getClass()==ModificationRecordImpl.class) {
				logger.trace(">>> field was ModificationRecordImpl (and not null), equal by default.");
				return true;
			}
		}
		
		if(fieldIsPo && fieldIsChild) {
			logger.trace(">>> field was child po, checking deeper.");
			P onePo = (P) oneField;
			P twoPo = (P) twoField;
			Modification<PersistentObject<?>> onCompareChildPo = PoCopier.get().getOnCopyChildPo();
			if (onCompareChildPo!=null) {
				onePo = (P) onCompareChildPo.execute(onePo);
				twoPo = (P) onCompareChildPo.execute(twoPo);				
			}
			return deepEquals(onePo, twoPo);
		}
		
		if(fieldIsIterable && fieldIsChild) {
			logger.trace(">>> field was child iterable, checking deeper.");
			Set<P> oneFieldSet = CollectionUtils.iterableToSet((Iterable<P>) oneField);
			Set<P> twoFieldSet = CollectionUtils.iterableToSet((Iterable<P>) twoField);
			return setsDeepEqual(oneFieldSet, twoFieldSet);
		}
		
		if(fieldIsArray && fieldIsChild) {
			logger.trace(">>> field was child array, checking deeper.");
			@SuppressWarnings("rawtypes")
			Set<P> oneFieldSet = new HashSet(Arrays.asList((Object[]) oneField));
			@SuppressWarnings("rawtypes")
			Set<P> twoFieldSet = new HashSet(Arrays.asList((Object[]) twoField));
			return setsDeepEqual(oneFieldSet, twoFieldSet);
		}
		
		logger.trace(">>> checking field equality with Utils.equals()");
		boolean isEqualFields = Utils.equals(oneField, twoField);
		if (logger.isDebugEnabled()) {
			if (!isEqualFields) {
				String msg = "Different fields " + field.getName();
				logger.debug(msg);
				logger.debug("one's field:"); //$NON-NLS-1$
				PoLogger.print(oneField, logger);
				logger.debug("two's field:");
				PoLogger.print(twoField, logger);
			}
		}
		return isEqualFields;
	}
	
	/**
	 * Checks two sets for deep equality of elements. Set deep equality herein
	 * is defined as follows "oneSet and twoSet are deep equal if (and only if)
	 * for every element E of oneSet there exists one element of twoSet that is
	 * deep equal with E".
	 * 
	 * @param <P>
	 * @param oneSet
	 * @param twoSet
	 * @return true, if for every element E of oneSet there exists one element of
	 *         twoSet that is deep equal with E.
	 */
	@SuppressWarnings("nls")
	private static <P extends PersistentObject<?>> boolean setsDeepEqual(Set<P> oneSet, Set<P> twoSet) {
		
		boolean elementsAreEqual = oneSet.containsAll(twoSet) && twoSet.containsAll(oneSet);
		if(!elementsAreEqual) {
			logger.trace(">>> set elements mismatch. not equal.");
			return false; 
		}
		
		for(P one : oneSet) {
			logger.trace(">>> checking deep equality on set elements.");
			
			for(P two : twoSet) {
				if(two.equals(one)) {
					if(!deepEquals(one, two)) {
						logger.trace(">>> found mismatch. No element of second set deep equal with " + one.toString());
						return false;
					}
					break;
				}
			}
		}
		return true;
	}
	
	/**
	 * strips a fully qualified class name from the package
	 * to make debug output more readable, e.g.:
	 * gr.foo.Bar --> Bar
	 * 
	 * @param fqcn the (qualified) class name
	 * @return the (unqualified) class name
	 */
	@SuppressWarnings("nls")
	private static String strip(String fqcn) {
		if(fqcn.indexOf(".") == -1)
			return fqcn;
		else
			return fqcn.substring(fqcn.lastIndexOf(".")+1, fqcn.length());
	}
	
	/**
	 * Gets the detachStrategy of an object.
	 * 
	 * If the object is still transient, and hasn't yet a detach strategy,
	 * then the default detach strategy of objects of this class will be
	 * returned.
	 * 
	 * @param object
	 *        Object for which the DetachStrategy is searched.
	 *        
	 * @return Returns the {@link DetachStrategy} of the specified
	 *         object.
	 */
	public static DetachStrategy getDetachStrategy(Object object) {
		DetachStrategy strategy = null;
		BeanPropertyDefinition<?> bpd = detachStrategyProperty(object);
		if (bpd==null) {
			return null;
		}
		if (bpd.getGetter()!=null) {
			strategy = (DetachStrategy)
				ReflectionUtils.invoke(bpd.getGetter(), object, new Object[0]);
		}
		if (strategy==null) {
			strategy = Factory.getDefaultDetachStrategy(object.getClass());
			setDetachStrategy(object, strategy);
		}
		return strategy;
	}
	
	/**
	 * Sets the detachStrategy on an object.
	 * 
	 * If the object does not have such a property, then it remains unaffected.
	 * 
	 * @param object
	 *        Object on which the detachStrategy is set.
	 * @param detachStrategy 
	 *        The detachStrategy to set.

	 */
	public static void setDetachStrategy (Object object, DetachStrategy detachStrategy) {				
		BeanPropertyDefinition<?> bpd = detachStrategyProperty(object);
		if (bpd!=null && bpd.getSetter()!=null) {
			ReflectionUtils.invoke(bpd.getSetter(), object, detachStrategy);
		}
	}
	
	/**
	 * Gets the detach strategy property of an object,
	 * if it has such a property.
	 * 
	 * @param object
	 * 
	 * @return Returns the BeanPropertyDefinition for the detach
	 *         strategy property.
	 *       
	 */
	private static BeanPropertyDefinition<?> detachStrategyProperty(Object object) {
		if (object==null) {
			return null;
		}
		TypeAnalysis analysis = TypeAnalysis.analyze(object.getClass());
		BeanPropertyDefinition<?> bpd = 
			analysis.getFirstPropertyByName(DETACH_STRATEGY);
		if (bpd==null) {
			return null;
		}
		if (!DetachStrategy.class.equals(bpd.getType())) {
			return null;
		}
		return bpd;
	}
	
	
	
	
	/**
	 * Gets the manager name of the specified object.
	 * 
	 * @param object
	 *        Object who's manager name is searched.
	 *        
	 * @return Returns the manager name of the specified object. If there is 
	 *         no manager name specified for the object, returns null.
	 */
	public static String getManagerName(Object object) {
		if (object==null) {
			return null;
		}
		return Bo2AnnoUtils.getManagerName(object.getClass());
	}
	
	/**
	 * Re-attaches the specified object to the specified provider.
	 * 
	 * @param object
	 *        The object to re-attach
	 * @param provider 
	 *        Provider to re-attach the object.
	 */
	public static final void reattach(Object object, Provider provider) {		
		DetachStrategy strategy = getDetachStrategy(object);
		if (strategy!=null) {
			strategy.reattach(object, provider);
		}
	}
	
	/**
	 * In the unit of work that this is called, it is mandatory to perform
	 * a database update.
	 * 
	 * @param object
	 *        The object to re-attach
	 * @param provider 
	 *        Provider to re-attach the object.
	 */
	public static final void reattachForUpdate(Object object, Provider provider) {		
		DetachStrategy strategy = getDetachStrategy(object);
		if (strategy!=null) {
			strategy.reattachForUpdate(object, provider);
		}
	}	
	
	
	/**
	 * Marks the object as excluded from accidental saves due to
	 * any automation of the underlying persistence technology.
	 * 
	 * This method delegates to the object's detach strategy.
	 * 
	 * @param object
	 *        Object to mark.
	 * @param provider
	 *        Provider. 
	 */
	public static final void markExplicitSave(Object object, Provider provider) {
		DetachStrategy strategy = getDetachStrategy(object);
		if (strategy!=null) {
			strategy.markExplicitSave(object, provider);
		}
	}	
	
	/**
	 * Checks two {@link PersistentObject} for equality, excluding
	 * the properties of the key, or the modification properties, in
	 * case they implement the {@link ModificationRecord} interface.
	 * 
	 * @param first
	 * @param second
	 * @param poClass
	 * @param keyClass
	 * @param <K>
	 * @param <P>
	 * 
	 * @return Returns true if the two elements match.
	 */
	public static <K extends Serializable & Comparable<? super K>, P extends PersistentObject<K>>
	boolean equalsExcludingKeyAndModification(P first, P second, Class<P> poClass, Class<K> keyClass) {
		List<String> propertiesToExclude = new ArrayList<String>();
		PropertyDescriptor[] modificationProperties = 
			JavaBeanUtils.getBeansProperties(ModificationRecord.class);
		for (PropertyDescriptor pd : modificationProperties) {
			propertiesToExclude.add(pd.getName());
		}
		PropertyDescriptor[] keyProperties = 
			JavaBeanUtils.getBeansProperties(keyClass);
		for (PropertyDescriptor pd : keyProperties) {
			propertiesToExclude.add(pd.getName());
		}
		String[] excluded = propertiesToExclude.toArray(new String[0]);
		SameTypeAllPropertiesEqualMatchingRule<P> match = 
			new SameTypeAllPropertiesEqualMatchingRule<P>(poClass, excluded);
		return match.isMatch(first, second);
	}
	
	/**
	 * Serializes a PersistentObject and stores the result to a given {@link File}.
	 * The method re-throws any checked exceptions caught as a RuntimeException.
	 * 
	 * @param <P>
	 *        PersistentObject type
	 *        
	 * @param po
	 *        PersistentObject to serialize.
	 * @param filePath
	 *        Resource path to store the serialized object to.
	 */
	public static <P extends PersistentObject<?>> void serialize(P po, String filePath) {
		ObjectOutputStream out = null;
		try {
			File file = new File(filePath);
			P copy = deepCopy(po);
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(copy);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	/**
	 * Deserializes a PersistentObject that is stored in a given {@link File}.
	 * The method re-throws any checked exceptions caught as a RuntimeException.
	 * 
	 * @param <P>
	 *        Type of PersistentObject
	 * @param filePath
	 *        Path of file to read the serialized object from.
	 *        
	 * @return Returns an object instance.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <P extends PersistentObject<?>> P deserialize(String filePath) {
		ObjectInputStream in = null;
		P p = null;
		try {
			File file = new File(filePath);
			in = new ObjectInputStream(new FileInputStream(file));
			p = (P) in.readObject();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return p;
	}
	
	/**
	 * Gets the key class of a persistent object class.
	 * Works only if the key class is a sub-type of {@link Key}.
	 * 
	 * @param poClass
	 *        Class of persistent object.	         
	 *        
	 * @return Returns the class of key. If it isn't possible to 
	 *         find the key class, returns null.
	 */
	public static Class<?> getKeyType(Class<? extends PersistentObject<?>> poClass) {
		Class<?> keyType = poKeys.get(poClass);
		if(keyType==null) {
			keyType = GenericsUtils.findTypeParameter(poClass, PersistentObject.class, Key.class);
		}
		if(keyType!=null) {
			poKeys.put(poClass, keyType);
		}
		return keyType;
	}
	
	/**
	 * Gets the key properties of the specified persistent object
	 * class.
	 * 
	 * @param poClass
	 * 
	 * @return Returns an array that contains the names of the read-write
	 *         properties of the key class of the specified persistent object
	 *         class.
	 */
	public static String[] getKeyProperties(Class<? extends PersistentObject<?>> poClass) {
		Class<?> keyType = getKeyType(poClass);
		if (keyType==null) {
			return null;
		}
		TypeAnalysis keyTypeAnalysis = TypeAnalysis.analyze(keyType);
		Set<String> properties = keyTypeAnalysis.getNamesOfReadWriteProperties();
		return new ArrayList<String>(properties).toArray(new String[0]);
	}
	
	
	/**
	 * Resets the modification record of an object if it is a
	 * {@link ModificationRecord}.
	 * 
	 * @param po
	 */
	private static void resetModificationRecord (Object po) {
		if (po instanceof ModificationRecord) {
			ModificationRecord rec = (ModificationRecord) po;
			rec.setLastModified(null);
			rec.setLastModifiedBy(null);			
		}		
	}
	
	/**
	 * Gets the first declared interface of the specified class
	 * that is a PersistentObject. 
	 * 
	 * @param clazz
	 * 
	 * @return Returns the first interface
	 */
	public static Class<?> getPoDeclarationType(Class<?> clazz) {
		Class<?>[] interfaces = clazz.getInterfaces();
		for (Class<?> iface : interfaces) {
			if (PersistentObject.class.isAssignableFrom(iface)) {
				return iface;
			}
		}
		return null;
	}
	

}
