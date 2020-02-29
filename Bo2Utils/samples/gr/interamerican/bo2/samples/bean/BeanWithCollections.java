package gr.interamerican.bo2.samples.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Bean that contains collections.
 *
 * @param <T> the generic type
 */
public class BeanWithCollections<T> {
	
	/**
	 * id.
	 */
	int id;
	
	/**
	 * name.
	 */
	String name;
	
	/**
	 * List.
	 */
	List<T> list = new ArrayList<T>();
	
	/**
	 * Set.
	 */
	Set<T> set = new HashSet<T>();
	
	
	
	
	/**
	 * Gets the id.
	 *
	 * @return Returns the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return Returns the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the list.
	 *
	 * @return Returns the list
	 */
	public List<T> getList() {
		return list;
	}

	/**
	 * Sets the list.
	 *
	 * @param list the list to set
	 */
	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * Gets the set.
	 *
	 * @return Returns the set
	 */
	public Set<T> getSet() {
		return set;
	}

	/**
	 * Sets the set.
	 *
	 * @param set the set to set
	 */
	public void setSet(Set<T> set) {
		this.set = set;
	}}
