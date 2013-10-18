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
package gr.interamerican.bo2.utils.doc;


/**
 * Utilities.
 */
public class DocumentUtils {

	/**
	 * This is a utility class having only static methods.
	 */
	private DocumentUtils() {/*empty*/}
	
	/**
	 * This method adds a row to the end of the documentTable if needed.
	 * That means that if and only if the rows are shown at the template are used we add a new one.
	 * 
	 * @param documentTable
	 * @param rowPointer
	 * @throws DocumentEngineException
	 */
	public static void appendRowIfNeeded(DocumentTable documentTable, int rowPointer) throws DocumentEngineException{
		if(documentTable.getRowCount()==rowPointer){
			documentTable.appendRow();
		}
	}
	
	

	
	/**
	 * Μέθοδος που ελέγχει αν το columnIndex αναφέρεται σε στήλη του πίνακα table που υπάρχει, πρέπει 
	 * το columnIndex να είναι μεγαλύτερο table.maxColumn 
	 * Αν ο πίνακας έχει λιγότερες στήλες απο το columnIndex τότε πετάμε DocumentEngineException().
	 * @param table o πίνακας που δημιουργούμε.
	 * @param tableName το όνομα του πίνακα, για error message.
	 * @param columnIndex ο μέγιστος αριθμός στηλών που θέλουμε να έχει ο πίνακας.
	 * @throws DocumentEngineException
	 */
	public static void validateColumnCount(DocumentTable table, String tableName,int columnIndex) throws DocumentEngineException{
		if(columnIndex >= table.getColumnCount()){
			String message = "Column index exceeds the number of columns of table "  + tableName; //$NON-NLS-1$ 
			throw new DocumentEngineException(message);
		}
	}
	
	/**
	 * Κάνει append με null-safe τροπο δυο BusinessDocuments και επιστρέφει το αποτέλεσμα.
	 * <br>Αν και τα δύο documents είναι null τότε δεν κάνει τιποτα και επιστρεφει null
	 * <br>Αν είναι και τα δύο documents not-null τότε κανει append το extraDoc στο τέλος 
	 * του baseDoc και επιστρέφει το extraDoc.
	 * <br>Αν είναι το baseDoc ειναι null αλλα το extraDoc οχι τοτε επιστρέφει το extraDoc.
	 * @param baseDoc το βασικό document στο οποίο θα κανουμε append.
	 * @param extraDoc το document το οποίο θα μπεί στο τέλος του βασικού.
	 * @param addPageBreak Αν είναι true βάζει ένα pageBreak μεταξύ των δύο σελίδων.
	 * @return resultDoc το αποτελεσμα του append
	 * @throws DocumentEngineException
	 */
	public static BusinessDocument safeAppend
	(BusinessDocument baseDoc, BusinessDocument extraDoc, boolean addPageBreak) 
	throws DocumentEngineException{
		if(baseDoc!= null && addPageBreak && extraDoc != null){
			baseDoc.pageBreak();
		}		
		BusinessDocument resultDoc = baseDoc;
		if(baseDoc!= null && extraDoc!=null ){
			baseDoc.append(extraDoc);
			resultDoc = baseDoc;
		}
		else if(baseDoc == null && extraDoc!=null ){
			resultDoc = extraDoc;
		}		
		return resultDoc;
	}
	
	
}
