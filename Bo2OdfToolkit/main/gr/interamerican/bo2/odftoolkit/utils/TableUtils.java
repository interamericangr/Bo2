package gr.interamerican.bo2.odftoolkit.utils;

import java.util.ArrayList;
import java.util.List;

import org.odftoolkit.simple.TextDocument;
import org.odftoolkit.simple.table.Table;
import org.odftoolkit.simple.table.TableContainer;

/**
 * Utilities for relevant with {@link Table} objects.
 */
public class TableUtils {

	/**
	 * Gets the table with the specified name from an array of
	 * TableContainer objects.
	 *
	 * @param containers the containers
	 * @param name the name
	 * @return Returns the table with the specified name if one exists,
	 *         otherwise returns null.
	 */
	public static Table getTable(TableContainer[] containers, String name) {
		for (TableContainer container : containers) {
			Table table = container.getTableByName(name);
			if (table!=null) {
				return table;
			}
		}
		return null;
	}
	
	/**
	 * Gets all table containers of a document.
	 *
	 * @param document the document
	 * @return Returns an array of table container objects.
	 */
	public static TableContainer[] getTableContainers(TextDocument document) {
		 TableContainer[] containers = {
				 document,
				 document.getHeader(false),
				 document.getHeader(true),
				 document.getFooter(false),
				 document.getFooter(true)
		 };
		 return containers;		
	}
	
	/**
	 * Gets the table with the specified name from a TextDocument.
	 *
	 * @param document the document
	 * @param name the name
	 * @return Returns the table with the specified name if one exists,
	 *         otherwise returns null.
	 */
	public static Table getTable(TextDocument document, String name) {
		TableContainer[] containers = getTableContainers(document);
		return getTable(containers, name);
	}
	
	
	/**
	 * Gets a list with all tables of the specified document.
	 *
	 * @param doc the doc
	 * @return Returns the list of tables.
	 */
	public static List<Table> getTables(TextDocument doc) {
		List<Table> all = new ArrayList<Table>();
		TableContainer[] containers = getTableContainers(doc);
		for (int i = 0; i < containers.length; i++) {
			List<Table> tables = containers[i].getTableList();
			all.addAll(tables);
		}
		return all;
	}
	

}
