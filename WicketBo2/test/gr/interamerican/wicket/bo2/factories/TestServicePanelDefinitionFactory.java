package gr.interamerican.wicket.bo2.factories;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.junit.Test;

import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.test.def.samples.SamplePo;
import gr.interamerican.bo2.test.def.samples.SamplePoKey;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.wicket.bo2.callbacks.DeleteAction;
import gr.interamerican.wicket.bo2.callbacks.SaveAction;
import gr.interamerican.wicket.bo2.callbacks.SaveWithQuestionAction;
import gr.interamerican.wicket.bo2.callbacks.UpdateAction;
import gr.interamerican.wicket.bo2.test.Bo2WicketTest;
import gr.interamerican.wicket.callback.ProcessAction;
import gr.interamerican.wicket.markup.html.panel.crud.picker.CrudPickerPanelDef;

/**
 * Unit Tests of {@link ServicePanelDefinitionFactory}.
 */
public class TestServicePanelDefinitionFactory extends Bo2WicketTest {

	/**
	 * Test method for {@link ServicePanelDefinitionFactory#crudPickerPanelDef(Class)}.
	 */
	@Test
	public void testCrudPickerPanelDefClassOfPO() {
		CrudPickerPanelDef<SamplePo> def = ServicePanelDefinitionFactory.crudPickerPanelDef(SamplePo.class);
		IModel<SamplePo> imodel = def.getBeanModel();
		assertNotNull(imodel);
	}

	/**
	 * Test method for {@link ServicePanelDefinitionFactory#crudPickerPanelDef(Class, gr.interamerican.wicket.bo2.callbacks.CrudCallbackActionsOwner)}.
	 *
	 * @throws Exception 
	 */
	@Deprecated
	@Test
	public void testCrudPickerPanelDefClassOfPOCrudCallbackActionsOwner() throws Exception {
		// setup
		SamplePoPanel panel = spy(new SamplePoPanel());
		CrudPickerPanelDef<SamplePo> def = ServicePanelDefinitionFactory.crudPickerPanelDef(SamplePo.class, panel);
		assertNotNull(def.getBeanModel());
		// test with verification of invocation
		def.getSaveAction().process(null);
		verify(panel).saveAction(null);
		def.getDeleteAction().consume(null);
		verify(panel).deleteAction(isNull());
		def.getUpdateAction().process(null);
		verify(panel).updateAction(null);
	}

	/**
	 * Test method for {@link ServicePanelDefinitionFactory#crudPickerPanelDef(Class, Class)}.
	 */
	@Test
	public void testCrudPickerPanelDefClassOfPOClassOfKQ() {
		CrudPickerPanelDef<SamplePo> def = ServicePanelDefinitionFactory.crudPickerPanelDef(SamplePo.class,
				SamplePoNextIdQuestion.class);
		assertTrue(def.getUpdateAction() instanceof UpdateAction);
		assertTrue(def.getSaveAction() instanceof SaveWithQuestionAction);
		assertTrue(def.getDeleteAction() instanceof DeleteAction);
		assertTrue(ReflectionUtils.get("questionClass", def.getSaveAction()) == SamplePoNextIdQuestion.class); //$NON-NLS-1$
	}

	/**
	 * Test method for {@link ServicePanelDefinitionFactory#crudPickerPanelDefWithActions(Class)}.
	 */
	@Test
	public void testCrudPickerPanelDefWithActions() {
		CrudPickerPanelDef<SamplePo> def = ServicePanelDefinitionFactory.crudPickerPanelDefWithActions(SamplePo.class);
		assertNotNull(def.getBeanModel());
		ProcessAction<SamplePo> saveAction = def.getSaveAction();
		assertTrue(saveAction instanceof SaveAction);
	}
}

/**
 * A CrudCallbackActionsOwner for {@link SamplePo}.
 */
@Deprecated
class SamplePoPanel implements gr.interamerican.wicket.bo2.callbacks.CrudCallbackActionsOwner {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveAction(AjaxRequestTarget target) throws LogicException, DataException {
		// code to save the SamplePo is supposed to be here
	}

	@Override
	public void updateAction(AjaxRequestTarget target) throws LogicException, DataException {
		// code to update the SamplePo is supposed to be here
	}

	@Override
	public void deleteAction(AjaxRequestTarget target) throws LogicException, DataException {
		// code to delete the SamplePo is supposed to be here
	}
}
/**
 * Dummy Interface to extract the next id of {@link SamplePo}.
 */
interface SamplePoNextIdQuestion extends Question<SamplePoKey> , PoDependent<SamplePo> {
	// empty
}