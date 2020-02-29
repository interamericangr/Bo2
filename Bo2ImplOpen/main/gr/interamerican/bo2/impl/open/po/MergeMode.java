package gr.interamerican.bo2.impl.open.po;

import gr.interamerican.bo2.impl.open.po.utils.PoMerger;

/**
 * Types of merging supported by {@link PoMerger}.
 */
public enum MergeMode {

	/**
	 * Merge Favor Target.<br>
	 * The target entity will be kept as is, but any extra child entities that
	 * exist on the source entity will be added to it.
	 */
	FAVOR_TARGET,

	/**
	 * Merge Favor Source.<br>
	 * The target entity will be overwritten by the source entity, but any extra
	 * child elements that existed on the target entity will be retained.
	 */
	FAVOR_SOURCE,

	/**
	 * Overwrite.<br>
	 * The target entity will be overwritten with the values the source entity
	 * has. Child Entities of the target element that do not exist in the source
	 * will not be retained.
	 */
	OVERWRITE
}