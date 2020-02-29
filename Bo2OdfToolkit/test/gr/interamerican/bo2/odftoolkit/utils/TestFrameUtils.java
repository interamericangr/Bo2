package gr.interamerican.bo2.odftoolkit.utils;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.odftoolkit.odfdom.dom.element.draw.DrawFrameElement;
import org.odftoolkit.simple.TextDocument;

/**
 * Unit test for {@link FrameUtils}.
 */
public class TestFrameUtils {

	/**
	 * Unit test for getDrawFrameElements().
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testGetDrawFrameElements() throws Exception {
		String path = ResourceUtils.inputPath("DocumentWithFrames.odt"); //$NON-NLS-1$
		TextDocument doc = TextDocument.loadDocument(path);
		List<DrawFrameElement> list = FrameUtils.getDrawFrameElements(doc.getContentDom());
		assertEquals(2, list.size());
	}

	/**
	 * Unit test for getDrawFrameElements().
	 *
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("nls")
	@Test
	public void testGetByName() throws Exception {
		String path = ResourceUtils.inputPath("DocumentWithFrames.odt");
		TextDocument doc = TextDocument.loadDocument(path);
		DrawFrameElement frame1 = FrameUtils.getByName(doc.getContentDom(), "Frame1");
		DrawFrameElement frame2 = FrameUtils.getByName(doc.getContentDom(), "Frame2");
		DrawFrameElement frame3 = FrameUtils.getByName(doc.getContentDom(), "Frame3");
		assertNotNull(frame1);
		assertNotNull(frame2);
		assertNull(frame3);
	}
}