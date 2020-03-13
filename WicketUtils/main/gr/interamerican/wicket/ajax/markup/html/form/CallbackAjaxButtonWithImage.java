package gr.interamerican.wicket.ajax.markup.html.form;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.image.resource.LocalizedImageResource;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.resource.ResourceReference;

import gr.interamerican.wicket.callback.ICallbackAction;
import gr.interamerican.wicket.callback.LegacyCallbackAction;
import gr.interamerican.wicket.utils.images.EmbeddedImage;

/**
 * A {@link CallbackAjaxButton} that contains an Image.
 * 
 * @see EmbeddedImage For Images embedded in the framework already
 */
@SuppressWarnings("deprecation")
public class CallbackAjaxButtonWithImage
extends CallbackAjaxButton
implements IResourceListener {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/** The image resource this image component references */
	final LocalizedImageResource localizedImageResource = new LocalizedImageResource(this);

	/**
	 * Public Constructor with an {@link ICallbackAction}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param reference
	 *            {@link ResourceReference} for the Image
	 * @param action
	 *            ICallbackAction to be executed on the button press.
	 * @deprecated To be removed on bo2 v4 due to wicket changes
	 */
	@Deprecated
	public CallbackAjaxButtonWithImage(String id, ResourceReference reference, ICallbackAction action) {
		super(id, action);
		localizedImageResource.setResourceReference(reference);
	}

	/**
	 * Public Constructor with an {@link ICallbackAction} and a
	 * {@link FeedbackPanel}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param reference
	 *            {@link ResourceReference} for the Image
	 * @param action
	 *            ICallbackAction to be executed on the button press.
	 * @param feedbackPanel
	 *            Feedback panel for error messages.
	 * @deprecated To be removed on bo2 v4 due to wicket changes
	 */
	@Deprecated
	public CallbackAjaxButtonWithImage(String id, ResourceReference reference, ICallbackAction action,
			FeedbackPanel feedbackPanel) {
		super(id, action, feedbackPanel);
		localizedImageResource.setResourceReference(reference);
	}

	/**
	 * Public Constructor with an {@link LegacyCallbackAction} and a
	 * {@link FeedbackPanel}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param reference
	 *            {@link ResourceReference} for the Image
	 * @param action
	 *            LegacyCallbackAction to be executed on the button press.
	 * @param feedbackPanel
	 *            Feedback panel for error messages.
	 */
	public CallbackAjaxButtonWithImage(String id, ResourceReference reference, LegacyCallbackAction action,
			FeedbackPanel feedbackPanel) {
		super(id, action, feedbackPanel);
		localizedImageResource.setResourceReference(reference);
	}

	/**
	 * Public Constructor with an {@link LegacyCallbackAction}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param reference
	 *            {@link ResourceReference} for the Image
	 * @param action
	 *            LegacyCallbackAction to be executed on the button press.
	 */
	public CallbackAjaxButtonWithImage(String id, ResourceReference reference, LegacyCallbackAction action) {
		super(id, action);
		localizedImageResource.setResourceReference(reference);
	}

	/**
	 * Public Constructor with an {@link LegacyCallbackAction}.
	 *
	 * @param id
	 *            Id of the button.
	 * @param reference
	 *            {@link ResourceReference} for the Image
	 * @param action
	 *            LegacyCallbackAction to be executed on the button press
	 * @param confirmationText 
	 *            Text to be displayed on the confirmation dialog (optional)
	 */
	public CallbackAjaxButtonWithImage(String id, ResourceReference reference, LegacyCallbackAction action, String confirmationText) {
		super(id, null, action, null, confirmationText);
		localizedImageResource.setResourceReference(reference);
	}

	@Override
	public void onResourceRequested() {
		localizedImageResource.onResourceRequested(null);
	}

	@Override
	public CallbackAjaxButtonWithImage setDefaultModel(IModel<?> model) {
		// Null out the image resource, so we reload it (otherwise we'll be
		// stuck with the old model.
		localizedImageResource.setResourceReference(null);
		localizedImageResource.setResource(null);
		super.setDefaultModel(model);
		return this;
	}

	@SuppressWarnings("nls")
	@Override
	protected final void onComponentTag(final ComponentTag tag) {
		checkComponentTag(tag, "input");
		checkComponentTagAttribute(tag, "type", "image");
		localizedImageResource.setSrcAttribute(tag);
		super.onComponentTag(tag);
	}

	@Override
	protected boolean getStatelessHint() {
		return localizedImageResource.getResource() == null && localizedImageResource.isStateless();
	}
}