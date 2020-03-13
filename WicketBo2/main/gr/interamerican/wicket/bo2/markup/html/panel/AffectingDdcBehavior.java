package gr.interamerican.wicket.bo2.markup.html.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;

import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.utils.CacheRegistry;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryBoPropertyDescriptor;
import gr.interamerican.wicket.bo2.markup.html.form.DropDownChoiceForEntry;
import gr.interamerican.wicket.bo2.utils.SelfDrawnUtils;
import gr.interamerican.wicket.utils.MarkupConstants;

/**
 * Behavior that will affect the choices of a Drop Down Choice of this panel
 * based on the selection performed on another Drop Down Choice of this panel
 * that affects the first.
 * 
 * @param <E>
 *            Entry type. This is hacky, not both DDCs have to have the same
 *            type.
 */
public class AffectingDdcBehavior<E extends TranslatableEntry<Long, ?, Long>> extends AjaxFormComponentUpdatingBehavior {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** Affecting drop down choice. */
	private DropDownChoiceForEntry<Long, E> affectingDdc;

	/** Affected drop down choice. */
	private DropDownChoiceForEntry<Long, E> affectedDdc;

	/** Cache name of the affectedDescriptor. */
	private String cacheName;

	/** Type id of the affectedDescriptor. */
	private Long typeId;

	/**
	 * Creates a new SelfDrawnPanel.AffectingDdcBehavior object.
	 *
	 * @param affectingDdc
	 *            the affecting ddc
	 * @param affectedDdc
	 *            the affected ddc
	 * @param affectedDescriptor
	 *            the affected descriptor
	 */
	public AffectingDdcBehavior(DropDownChoiceForEntry<Long, E> affectingDdc,
			DropDownChoiceForEntry<Long, E> affectedDdc, CachedEntryBoPropertyDescriptor<E, ?> affectedDescriptor) {
		super(MarkupConstants.CHANGE);
		this.affectedDdc = affectedDdc;
		this.cacheName = affectedDescriptor.getCacheName();
		this.typeId = affectedDescriptor.getTypeId();
		this.affectingDdc = affectingDdc;
	}

	@Override
	protected void onUpdate(AjaxRequestTarget target) {
		target.add(affectedDdc);
		E affectingChoice = affectingDdc.getModelObject();
		Long affectedSubListCd = null;
		if (affectingChoice != null) {
			affectedSubListCd = affectingChoice.getCode();
		}
		Set<E> choices = CacheRegistry.<Long> getRegisteredCache(cacheName).getSubCache(typeId, affectedSubListCd);
		List<E> choicesList = new ArrayList<E>(choices);
		affectedDdc.setChoices(choicesList);
		SelfDrawnUtils.<Long, E> sortCachedEntries(affectedDdc);
	}
}