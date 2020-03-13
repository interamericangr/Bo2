package gr.interamerican.bo2.utils.meta.descriptors;

import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.BusinessObjectDescriptorOwner;

import java.util.Set;

/**
 * An object definition is an object that holds meta-data for 
 * a business object. Note that there is not a one-to-one relationship
 * between business objects and classes defined in the application.
 */
public interface DataBusinessObjectDefinition 
extends BusinessObjectDescriptorOwner {

	/**
	 * Gets the name of the business object.
	 * 
	 * @return ObjectNm
	 */
	public String getObjectNm();
	
	/**
	 * Sets the name of the business object.
	 * 
	 * @param objectNm
	 */
	public void setObjectNm(String objectNm);
	
	/**
	 * Όνομα package στο οποίο ανήκει η κλάση.
	 *
	 * @return Επιστρέφει το package
	 */
	public String getPackageName();

	/**
	 * Καθορίζει το όνομα του package στο οποίο ανήκει το πεδίο.
	 *
	 * @param packageName Νέο package
	 */
	public void setPackageName(String packageName);

	/**
	 * Όνομα κλάσης στην οποία ανήκει η ιδιότητα, σκέτο χωρίς το package.
	 *
	 * @return Επιστρέφει το όνομα κλάσης χωρίς το package.
	 */
	public String getClassName();

	/**
	 * Καθορίζει το όνομα κλάσης στην οποία ανήκει η ιδιότητα.
	 * 
	 * Το όνομα κλασης δεν πρέπει να περιέχει και το package.
	 *
	 * @param className Νέο όνομα κλάσης.
	 */
	public void setClassName(String className);
	
	/**
	 * Περιγραφές ιδιοτήτων της κλάσης
	 * 
	 * @return Επιστρέφει της περιγραφές ιδιοτήτων της κλάσης
	 */
	public Set<? extends PropertyDefinition> getPropertyDefinitions();
	
	/**
	 * Θέτει τον descriptor της κλάσης.
	 * 
	 * @param descriptor
	 */
	public void setBusinessObjectDescriptor(BusinessObjectDescriptor<?> descriptor);
	
	/**
	 * Ένδειξη αν το συγκεκριμένο πεδίο είναι δυναμικό.
	 * 
	 * @return Επιστρέφει true αν το πεδίο είναι δυναμικό.
	 */
	public boolean isDynamicObject();
	
	/**
	 * Καθορίζει αν αυτό το αντικείμενο είναι δυναμικό.
	 * 
	 * @param dynamicObject
	 *        Νέα ένδειξη.
	 */
	public void setDynamicObject(boolean dynamicObject);
	
	
}
