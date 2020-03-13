package gr.interamerican.bo2.utils.doc;

/**
 * Utility that helps {@link DocumentEngine}s perform document conversions.
 */
public interface DocumentEngineUtility {

	/**
	 * Gets the business document in XHTML format.
	 *
	 * @param odf
	 *            Business document to convert to XHTML.
	 * 
	 * @return Returns a String containing the specified business document in
	 *         XHTML format.
	 * 
	 * @throws DocumentEngineException
	 *             the document engine exception
	 * 
	 * @deprecated Reported as not being used - will get dropped then
	 */
	@Deprecated
	String toHtml(byte[] odf) throws DocumentEngineException;

	/**
	 * Converts the specified ODF input stream to PDF.
	 *
	 * @param odf
	 *            ODF input stream.
	 * 
	 * @return Returns an output stream, that contains the PDF file.
	 * @throws DocumentEngineException
	 *             the document engine exception
	 */
	public byte[] toPdf(byte[] odf) throws DocumentEngineException;

}
