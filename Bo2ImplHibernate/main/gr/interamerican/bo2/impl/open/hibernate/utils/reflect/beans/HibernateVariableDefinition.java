package gr.interamerican.bo2.impl.open.hibernate.utils.reflect.beans;

import gr.interamerican.bo2.arch.ModificationRecord;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.utils.StringUtils;
import gr.interamerican.bo2.utils.Utils;
import gr.interamerican.bo2.utils.reflect.beans.VariableDefinition;

import java.util.Date;

import org.hibernate.collection.AbstractPersistentCollection;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.proxy.HibernateProxy;


/**
 * {@link VariableDefinition} for {@link PersistentObject}s in a Hibernate
 * managed environment.
 */
@SuppressWarnings("all")
public class HibernateVariableDefinition extends VariableDefinition {
	
	String attached;
	
	Date lastModified;
	
	String initialized;
	
	String status;
	
	Class<?> actualType;
	
	String id;
	
	String role;
	
	String hashCode;
	
	/**
	 * Creates a new HibernateVariableDefinition object. 
	 *
	 * @param name
	 * @param type
	 */
	private HibernateVariableDefinition(String name, Class type) {
		super(name, type);
	}
	
	public static VariableDefinition createHibernateVd(Object object, String fieldName, Class<?> fieldType) {
		if(object instanceof HibernateProxy) {
			return createForProxy((HibernateProxy) object, fieldName, fieldType);
		} else if (object instanceof PersistentCollection) {
			return createForPersistentCollection((PersistentCollection) object, fieldName, fieldType);
		} else if(object instanceof PersistentObject) {
			return createForPersistentObject((PersistentObject) object, fieldName, fieldType);
		}
		VariableDefinition vd = new VariableDefinition(fieldName, fieldType);
		vd.setValue(object);
		return vd;
	}
	
	static HibernateVariableDefinition createForProxy(HibernateProxy proxy, String name, Class<?> fieldType) {
		HibernateVariableDefinition result = new HibernateVariableDefinition(name, fieldType);
		result.status = "HibernateProxy";
		result.hashCode = String.valueOf(System.identityHashCode(proxy));
		
		boolean initialized = !proxy.getHibernateLazyInitializer().isUninitialized();
		
		if(initialized) {
			return createForInitializedProxy(proxy, name, fieldType);
		}
		
		result.initialized = StringUtils.toString(!proxy.getHibernateLazyInitializer().isUninitialized());
		
		
		
		SessionImplementor session = proxy.getHibernateLazyInitializer().getSession();
		result.attached = StringUtils.toString(session!=null && session.isOpen() && session.getPersistenceContext().containsProxy(proxy));
		
		result.setValue(proxy);
		
		result.id = StringUtils.toString(proxy.getHibernateLazyInitializer().getIdentifier());
		
		result.actualType = proxy.getClass();
		
		if(!proxy.getHibernateLazyInitializer().isUninitialized()) {
			if(proxy instanceof ModificationRecord) {
				result.lastModified = ((ModificationRecord)proxy).getLastModified();
			}
		}
		return result;
	}
	
	static HibernateVariableDefinition createForInitializedProxy(HibernateProxy proxy, String name, Class<?> fieldType) {
		HibernateVariableDefinition result = new HibernateVariableDefinition(name, fieldType);
		result.status = "Initialized HibernateProxy";
		result.hashCode = String.valueOf(System.identityHashCode(proxy));
		result.initialized = StringUtils.toString(true);
		
		Object implementation = proxy.getHibernateLazyInitializer().getImplementation();
		boolean isPo = implementation instanceof PersistentObject;
		
		if(!isPo) {
			throw new RuntimeException("Cannot assess.");
		}
		
		PersistentObject<?> poImpl = (PersistentObject<?>) implementation;
		
		SessionImplementor session = proxy.getHibernateLazyInitializer().getSession();
		result.attached = StringUtils.toString(session!=null && session.isOpen() && session.getPersistenceContext().containsProxy(proxy));
		
		result.setValue(implementation);
		result.id = StringUtils.toString(poImpl.getKey());
		
		result.actualType = poImpl.getClass();
		
		if(poImpl instanceof ModificationRecord) {
			result.lastModified = ((ModificationRecord)poImpl).getLastModified();
		}
		
		return result;
	}
	
	static HibernateVariableDefinition createForPersistentCollection(PersistentCollection collection, String name, Class<?> fieldType) {
		boolean isAbstractPersistentCollection = collection instanceof AbstractPersistentCollection;
		if(!isAbstractPersistentCollection) {
			throw new RuntimeException("Cannot assess.");
		}
		AbstractPersistentCollection apc = (AbstractPersistentCollection) collection;
		
		HibernateVariableDefinition result = new HibernateVariableDefinition(name, fieldType);
		result.status = "PersistentCollection";
		result.hashCode = String.valueOf(System.identityHashCode(collection));
		result.initialized = StringUtils.toString(apc.wasInitialized());
		
		SessionImplementor session = apc.getSession();
		result.attached = StringUtils.toString(session!=null && session.isOpen() && session.getPersistenceContext().containsCollection(collection));
		
		result.setValue(collection);
		result.id = StringUtils.toString(apc.getKey());
		
		result.actualType = collection.getClass();
		result.role = collection.getRole();
		
		return result;
	} 
	
	static HibernateVariableDefinition createForPersistentObject(PersistentObject<?> po, String name, Class<?> fieldType) {
		HibernateVariableDefinition result = new HibernateVariableDefinition(name, fieldType);
		result.status = "PersistentObject";
		result.hashCode = String.valueOf(System.identityHashCode(po));
		result.initialized = "N/A";
		result.attached = "N/A";
		result.setValue(po);
		result.id = StringUtils.toString(po.getKey());
		result.actualType = po.getClass();
		if(po instanceof ModificationRecord) {
			result.lastModified = ((ModificationRecord)po).getLastModified();
		}
		return result;
	}
	
	@Override
	public String toString() {
		String name = getName();
		String declaredType = StringUtils.stripPkgFromFqcn(this.getType().getName());
		String actualType = StringUtils.stripPkgFromFqcn(this.actualType.getName());
		String lastModified = StringUtils.toString(this.lastModified);
		String role = Utils.notNull(this.role, "N/A");
		
		String fields = StringUtils.concat(
//				StringUtils.squareBrackets("fieldName: " + name),
				StringUtils.squareBrackets("fieldType: " + declaredType),
				StringUtils.squareBrackets("actualType: " + actualType),
				StringUtils.squareBrackets("status: " + this.status),
				StringUtils.squareBrackets("initialized: " + this.initialized),
				StringUtils.squareBrackets("attached: " + this.attached),
				StringUtils.squareBrackets("lastModified: " + lastModified),
				StringUtils.squareBrackets("id: " + this.id),
				StringUtils.squareBrackets("role: " + this.role),
				StringUtils.squareBrackets("hashCode: " + this.hashCode));
			return StringUtils.squareBrackets(fields);
	}

}
