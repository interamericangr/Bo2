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

import gr.interamerican.bo2.arch.CompositePo;
import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.Key;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.arch.utils.ext.Bo2State;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.impl.open.state.CrudStates;
import gr.interamerican.bo2.utils.CollectionUtils;
import gr.interamerican.bo2.utils.Debug;
import gr.interamerican.bo2.utils.GenericsUtils;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.annotations.Child;
import gr.interamerican.bo2.utils.reflect.analyze.TypeAnalysis;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base for implementation of {@link PersistentObject}.
 * 
 * When an attribute of a PersistentObject is annotated as child 
 * PersistentObject then its <code>setKey(key)</code> method will 
 * be called by the setKey method of its parent.
 * <br/>
 * This class offers functionalities necessary mainly in cases where
 * the key of the persistent object is composite and consists of more
 * than one elements. In this case, the children of the persistent
 * object usually have keys consisting by the properties of the key
 * of the father plus some additional properties. In this case it is
 * difficult to ensure that always the keys of the children remain
 * children of the father's key. This class offers this functionality.
 * Implementations of {@link PersistentObject} can be abstract classes
 * with some fields annotated with the appropriate annotations. The
 * {@link Factory} will create the appropriate concrete class. <br/>
 * 
 * The following limitations apply: <br/>
 * <li> Child collections can only be of type {@link Set}. 
 *      All these collections can be declared as sets. These sets are 
 *      created by the constructor. </li> 
 * <li> Child element must always be {@link CompositePo}. Defining
 *      a child element that is not instance of {@link CompositePo}
 *      makes no sense. The reason to mark an element as child is to have
 *      its key modified by the father's key. If the child can't be modified
 *      by the father's key it is useless to mark it as child. </li> 
 * 
 * @param <K> Type of the persistent object key.
 */
public abstract class AbstractBasePo<K extends Key> 
implements PersistentObject<K> {	
			

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * States to ignore fixing of children.
	 */
	private static Set<Bo2State> states = new HashSet<Bo2State>();
	
	/**
	 * logger.
	 */
	protected static Logger logger = LoggerFactory.getLogger(AbstractBasePo.class);	
	
	static {
		states = new HashSet<Bo2State>();
		states.add(PoOperation.COPY);
		states.add(CrudStates.DELETE);
		states.add(CrudStates.READ);
		states.add(CrudStates.STORE);
		states.add(CrudStates.UPDATE);
	}
	
	/**
	 * Non children fields per class cache.
	 */
	private static Map<Class<?>, List<Field>> nonChildFieldsCache = new HashMap<Class<?>, List<Field>>();

	/**
	 * List with the fields of the class of this object that
	 * have been annotated as child fields.
	 */
	private transient List<Field> childFields;
	
	/**
	 * List with the fields of the class of this object that
	 * are not marked with the {@link Child} annotation and
	 * are either:
	 * <li>PersistentObject</li>
	 * <li>Array of PersistentObject</li>
	 * <li>Collection of PersistentObject</li>
	 */
	private transient List<Field> nonChildFields;

	/**
	 * DetachStrategy.
	 */
	private DetachStrategy detachStrategy;
			
	/**
	 * Key of this persistent object.
	 * 
	 *  This key must be created inside the constructor of
	 *  subclasses.
	 */
	protected K key;
	
	/**
	 * Gets the value of the persistence flag.
	 * 
	 * @return Returns the value of the persistence flag.
	 */
	static boolean isCanIgnorePoChildren() {		
		Bo2State state = Bo2Session.getState();
		
		if (states.contains(state)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a new AbstractBasePo object. 
	 *
	 */
	public AbstractBasePo() {
		super();
		resolveChildFields();
	}
	
	public K getKey() {		
		return key;
	}
	
	public void setKey(K key) {
		this.key = key;
		fixKeysOfChildren();
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass()!=this.getClass()) {
			return false;
		}
		
		@SuppressWarnings("unchecked")
		AbstractBasePo<K> that = (AbstractBasePo<K>) obj;
		return this.getKey().equals(that.getKey());
	}

	@Override
	public int hashCode() {
		if (key==null) {
			return 0;
		}
		return key.hashCode();
	}
	
	public void tidy() {
		doTidy();
		if (isCanIgnorePoChildren()) {
			return;
		}		
		fixChildren(null,false);
	}
	
	/**
	 * Extension point for tidy. 
	 */
	protected void doTidy() {
		/*
		 * empty 
		 */				
	}

	/**
	 * Assigns a new value to the detachStrategy.
	 *
	 * @param detachStrategy the detachStrategy to set
	 */
	public void setDetachStrategy(DetachStrategy detachStrategy) {
		this.detachStrategy = detachStrategy;
	}

	/**
	 * Gets the detachStrategy.
	 *
	 * @return Returns the detachStrategy
	 */
	public DetachStrategy getDetachStrategy() {
		return detachStrategy;
	}
		
	/**
	 * Fixes the keys to all child objects it has.
	 */
	protected void fixKeysOfChildren() {
		if (isCanIgnorePoChildren()) {
			return;
		}
		fixChildren(null);
	}		
	
	/**
	 * Fixes the key of a child.
	 * 
	 * @param child
	 */
	protected void fixChild(Object child) {	
		if (child!=null && !isCanIgnorePoChildren()) {
			fixChildField(child,null,true);
		}
	}
	
	/**
	 * Fixes the keys of this PO's children.
	 * 
	 * @param properties 
	 *
	 */
	protected void fixChildren(String[] properties) {
		fixChildren(properties, true);
	}
	
	/**
	 * Fixes and tidies the children of this PO.
	 * 
	 * @param properties 
	 * @param onlyKeys 
	 *        If true then only keys will be fixed, otherwise the children
	 *        will be also tidied by their <code>doTidy()</code> method.
	 */
	private void fixChildren(String[] properties, boolean onlyKeys) {
		List<Object> children = getChildren();
		for (Object object : children) {
			if (object!=null) {
				fixChildField(object,properties,onlyKeys);				
			}			
		}
	}
	
	/**
	 * Gets values of this object's fields that have been marked with
	 * the @Child annotation.
	 * 
	 * This list might include nulls in the case of fields with null
	 * value.
	 *  
	 * @return Returns a list with the child objects.
	 */
	List<Object> getChildren() {
		if(childFields==null) {
			resolveChildFields();
		}		
		return ReflectionUtils.getValuesOfFields
			(this, childFields, Object.class, true);
	}
	
	
	
	/**
	 * Resolves the fields of this class that are annotated with {@link Child}.
	 * Instances of this class that have been created after deserialization may
	 * have the transient field childFields null. For this reason, a null check
	 * with a subsequent call to this method should be performed whenever the
	 * childFields field is used. 
	 */
	private void resolveChildFields() {
		childFields = new ArrayList<Field>();
		TypeAnalysis analysis = TypeAnalysis.analyze(this.getClass());
		Set<Field> children = analysis.getAnnotated(Child.class);		
		if (children!=null) {
			for (Field field : children) {
				childFields.add(field);
				ReflectionUtils.setAccessible(field);
			}
		}
	}
	
	/**
	 * Resolves the non-child fields of this class.
	 * @see #nonChildFields
	 */
	private void resolveNonChildFields() {
		nonChildFields = nonChildFieldsCache.get(this.getClass());
		if(nonChildFields!=null) {
			return;
		}
		nonChildFields = new ArrayList<Field>();
		
		TypeAnalysis analysis = TypeAnalysis.analyze(this.getClass());
		Set<Field> nonChildren = analysis.getAllFields();
		nonChildren.removeAll(getChildFields());
		for(Field field : nonChildren) {
			if(Modifier.isTransient(field.getModifiers()) || Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			ReflectionUtils.setAccessible(field);
			Class<?> clazz = field.getType();
			if(PersistentObject.class.isAssignableFrom(clazz)) {
				nonChildFields.add(field);
			}
			if(clazz.isArray()) {
				if(PersistentObject.class.isAssignableFrom(clazz.getComponentType())) {
					nonChildFields.add(field);
				}
			}
			if(Collection.class.isAssignableFrom(clazz)) {
				Class<?> collectionType = GenericsUtils.getCollectionTypeOfField(field);
				if(PersistentObject.class.isAssignableFrom(collectionType)) {
					nonChildFields.add(field);
				}
			}
		}
		nonChildFieldsCache.put(this.getClass(), nonChildFields);
	}
	
	/**
	 * Fixes a {@link Child} of type {@link PersistentObject}.
	 * 
	 * If the specified child is an {@link AbstractBasePo}, then its fixChildren
	 * and optionally doTidy will be invoked. If the specified child is any other
	 * type of PersistentObject, its tidy method will be invoked.
	 * 
	 * @param child
	 *        Child Persistent Object to fix.
	 * @param properties 
	 *        Key properties to fix.
	 * @param onlyKeys 
	 *        If set to true, then only the key properties of the child will be fixed,
	 *        otherwise it's <code>doTidy()</code> method will be invoked.
	 */
	private void fixChildPo(PersistentObject<?> child, String[] properties, boolean onlyKeys) {
		Debug.increaseCounter("fixChildPo"); //$NON-NLS-1$
		Object childKey = child.getKey();
		ReflectionUtils.copyProperties(this.key, childKey, properties);
		if (child instanceof AbstractBasePo) {
			AbstractBasePo<?> abpo = (AbstractBasePo<?>) child;
			if (!onlyKeys) {
				abpo.doTidy();
			}
			abpo.fixChildren(properties,onlyKeys);			
		} else {
			child.tidy();			
		}
	}
	
	/**
	 * Fixes an array of child {@link PersistentObject}s.
	 * 
	 * @param children
	 *        Child Persistent Object to fix.
	 * @param properties 
	 *        Key properties to fix.
	 * @param onlyKeys 
	 *        If set to true, then only the key properties of each child will be fixed,
	 *        otherwise their <code>doTidy()</code> method will be invoked.
	 */
	private void fixChildArray(Object[] children, String[] properties, boolean onlyKeys) {
		for (int i = 0; i < children.length; i++) {
			if (children[i]!=null) {
				PersistentObject<?> po = (PersistentObject<?>) children[i]; 
				fixChildPo(po, properties, onlyKeys);
			}			
		}
	}
	
	/**
	 * Fixes a {@link Child} collection of {@link PersistentObject}s.
	 * 
	 * @param children
	 *        Child Persistent Object to fix.
	 * @param properties 
	 *        Key properties to fix.
	 * @param onlyKeys 
	 *        If set to true, then only the key properties of each child will be fixed,
	 *        otherwise their <code>doTidy()</code> method will be invoked.
	 */
	private void fixChildCollection(Collection<?> children,String[] properties, boolean onlyKeys) {
		for (Object child : children) {
			if (child!=null) {
				PersistentObject<?> po = (PersistentObject<?>) child; 
				fixChildPo(po,properties,onlyKeys);				
			}
		}
		/*
		 * TODO: Other types of Collections searching
		 * with hashcode before equals?
		 */
		if(children instanceof Set) {
			CollectionUtils.reArrange((Set<?>) children);
		}
	}
	
	/**
	 * Fixes a child object.
	 * 
	 * @param object 
	 * @param properties 
	 * @param onlyKeys 
	 */
	private void fixChildField (Object object, String[] properties, boolean onlyKeys) {		
		if (PoConditionChecker.isChildNotNeedFix(object)) {
			return;
		}
		if (object instanceof PersistentObject<?>) {			
			PersistentObject<?> po = (PersistentObject<?>) object;
			fixChildPo(po,properties,onlyKeys);
		} else if (object instanceof Object[]) {			
			Object[] array = (Object[]) object;
			fixChildArray(array,properties,onlyKeys);
		} else if (object instanceof Collection) {
			Collection<?> collection = (Collection<?>) object;
			fixChildCollection(collection,properties,onlyKeys);
		}
	}
	
	/**
	 * Gets the childFields.
	 *
	 * @return Returns the childFields
	 */
	List<Field> getChildFields() {
		if(childFields==null) {
			resolveChildFields();
		}
		return childFields;
	}
	
	/**
	 * Gets the nonChildFields.
	 *
	 * @return Returns the nonChildFields
	 */
	List<Field> getNonChildFields() {
		if(nonChildFields==null) {
			resolveNonChildFields();
		}
		return nonChildFields;
	}
	
	@Override
	public String toString() {
		return StringUtils.concat(
				//this.getClass().getSimpleName(),
				//StringConstants.COLON,
				StringUtils.toString(key));
	}

}
