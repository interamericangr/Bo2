package gr.interamerican.wicket.factories;

import gr.interamerican.wicket.behavior.ValidationStyleBehavior;
import gr.interamerican.wicket.components.BigDecimalTextField;
import gr.interamerican.wicket.components.CustomDateField;
import gr.interamerican.wicket.components.CustomDateTextField;
import gr.interamerican.wicket.components.DoubleTextField;
import gr.interamerican.wicket.components.PercentageBigDecimalTextField;
import gr.interamerican.wicket.components.PercentageTextField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * CmpFactory class - (Component Factory).
 * 
 */
public class ComponentFactory {

	/**
	 * Επιστρέφει ένα DropDownChoice component,
	 * δίνοντας to wicketId του Component,
	 * το πεδίο του object που θέλουμε να δείχνουμε,
	 * και το collection των αντικειμένων τα οποία θα δείχνουμε.
	 * 
	 * @param <P>
	 * @param wicketId
	 * @param displayExpression
	 * @param pos
	 * @return DropDownChoice
	 * 
	 */
	public static <P extends Serializable> DropDownChoice<P> addDropDownWithChoiceRenderer(String wicketId,String displayExpression,List<P> pos) {
		DropDownChoice<P> dd = new DropDownChoice<P>(wicketId, new Model<P>(), pos);
		ChoiceRenderer<P> cr = new ChoiceRenderer<P>(displayExpression);
		dd.setChoiceRenderer(cr);
		return dd;
	}

	/**
	 * Δημιουργεί CheckBox με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα CheckBox.
	 */
	public static void addCheckBoxes(MarkupContainer cmp, String[] wicketIds) {
		for (String booleanField : wicketIds) {
			cmp.add(new CheckBox(booleanField));
		}
	}


	/**
	 * Δημιουργεί Label με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Label.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Label.
	 */
	public static void addLabels(MarkupContainer cmp, String[] wicketIds) {
		for (String field : wicketIds) {
			cmp.add(new Label(field)
					);
		}
	}

	/**
	 * Δημιουργεί TextField<String> με νέο Model<String> με τα wicketId
	 * που του δίνουμε και τα προσθέτει στον markupContainer χωρίς να τα
	 * κάνει bind στον Object του model του MarkupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param fields
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 * 
	 */
	public static void addTextFieldsWithoutBinding(MarkupContainer cmp, String[] fields) {
		for (String field : fields) {
			cmp.add(new TextField<String>(field,new Model<String>()));
		}
	}

	/**
	 * Δημιουργεί TextField<String> με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 */
	public static void addTextFields(MarkupContainer cmp, String[] wicketIds) {
		for (String field : wicketIds) {
			TextField<String> tf = new TextField<String>(field);
			tf.setConvertEmptyInputStringToNull(false);
			tf.setOutputMarkupPlaceholderTag(true);
			cmp.add(tf);
		}
	}

	/**
	 * Δημιουργεί TextArea με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα TextArea.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα TextArea.
	 */
	public static void addTextAreaFields(MarkupContainer cmp, String[] wicketIds) {
		for (String field : wicketIds) {
			TextArea<String> ta = new TextArea<String>(field);
			ta.setConvertEmptyInputStringToNull(false);
			cmp.add(ta);
		}
	}
	/**
	 * Δημιουργεί Disabled TextField<String> με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 */
	@SuppressWarnings("rawtypes")
	public static void addDisableTextFields(MarkupContainer cmp, String[] wicketIds) {
		for (String field : wicketIds) {
			cmp.add(new TextField(field).setEnabled(false));
		}
	}

	/**
	 * Δημιουργεί DateField με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα DateFields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα DateField.
	 */
	public static void addDateFields(MarkupContainer cmp, String[] wicketIds) {
		for (String f : wicketIds) {
			cmp.add(new CustomDateField(f));
		}
	}


	/**
	 * Δημιουργεί DateField με νέο Model<Date> με τα wicketId
	 * που του δίνουμε και τα προσθέτει στον markupContainer χωρίς να τα
	 * κάνει bind στον Object του model του MarkupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα DateFields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα DateField.
	 * 
	 */
	public static void addDateFieldsWithoutBinding(MarkupContainer cmp, String[] wicketIds) {
		for (String f : wicketIds) {
			cmp.add(new CustomDateField(f, new Model<Date>()));
		}
	}


	/**
	 * Δημιουργεί DateTextField με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα DateTextField.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα DateTextField.
	 * 
	 */
	public static void addDateTextFields(MarkupContainer cmp, String[] wicketIds) {
		for (String field : wicketIds) {
			cmp.add(new CustomDateTextField(field));
		}
	}


	/**
	 * Δημιουργεί DateTextField με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer κάνοντας τα bind με το
	 * Object που του περνάμε.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα DateTextField.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα DateTextField.
	 * @param modelObject
	 * 		  To object στο οποίο θα τα κάνουμε bind.
	 * 
	 */
	public static void addDateTextFields(MarkupContainer cmp, String[] wicketIds, Object modelObject) {
		for (String field : wicketIds) {
			PropertyModel<Date> dateModel =
					new PropertyModel<Date>(modelObject, field);

			CustomDateTextField customDateTextField =
					new CustomDateTextField(field,dateModel);

			cmp.add(customDateTextField);
		}
	}

	/**
	 * Δημιουργεί TextField<Double> τα οποία είναι υποχρεωτικά, με τα wicketId
	 * που του δίνουμε και τα προσθέτει στον markupContainer. Στα textField
	 * θέτουμε το μέγιστο αριθμο των επιτρεπόμενων δεκαδικών ψηφίων.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 * @param decimals
	 * 		  O μέγιστος αριθμός των επιτρεπόμενων δεκαδικών ψηφίων.
	 * 
	 */
	public static void addRequiredDoubleTextField(MarkupContainer cmp, String[] wicketIds, Integer decimals){
		addDoubleTextField(cmp, wicketIds, decimals);
		for(String field : wicketIds){
			@SuppressWarnings("unchecked")
			TextField<Double> tf =  (TextField<Double>) cmp.get(field);
			tf.setRequired(true);
		}
	}

	/**
	 * Δημιουργεί TextField<Double> με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.Στα textField
	 * θέτουμε το μέγιστο αριθμο των επιτρεπόμενων δεκαδικών ψηφίων.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 * @param decimals
	 * 		  O μέγιστος αριθμός των επιτρεπόμενων δεκαδικών ψηφίων.
	 */
	public static void addDoubleTextField(MarkupContainer cmp, String[] wicketIds, Integer decimals) {
		final Integer dec;
		if(decimals == null){
			dec=new Integer(0);
		}else{
			dec=decimals;
		}
		for (String field : wicketIds) {
			TextField<Double> tf = new DoubleTextField(field, dec);
			cmp.add(tf);
		}
	}

	/**
	 * Δημιουργεί TextField<Double> με το wicketId (field) που του δίνουμε
	 * και κάνει bind στο CompoundPropertyModel<T> model.
	 * Στα textField
	 * θέτουμε επίσης το μέγιστο αριθμο των επιτρεπόμενων δεκαδικών ψηφίων.
	 * @param <T>
	 * @param field
	 * 
	 * 		το wicketId του TextField.
	 * @param model το μοντέλο που θα περιεχει το field στο οποίο θα κάνουμε το binding.
	 * @param decimals Ο μέγιστος αριθμός των επιτρεπόμενων δεκαδικών ψηφίων
	 * @return TF
	 */

	public static <T extends Serializable>  TextField<Double>
	addDoubleTextFieldWithBinding(String field, CompoundPropertyModel<T> model,Integer decimals) {
		final Integer dec;
		if(decimals == null){
			dec=new Integer(0);
		}else{
			dec=decimals;
		}
		IModel<Double> doubleModel = model.bind(field);
		return new DoubleTextField(field,doubleModel,dec);
	}
	/**
	 * Δημιουργεί TextField<Double> με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.Στα textField
	 * θέτουμε το μέγιστο αριθμο των επιτρεπόμενων δεκαδικών ψηφίων.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 * @param modelObject
	 * 		Το object που κάνει bind to component.
	 * @param decimals
	 */
	public static void addDoubleTextFields(MarkupContainer cmp, String[] wicketIds, Object modelObject,Integer decimals){
		final Integer dec;
		if(decimals == null){
			dec=new Integer(0);
		}else{
			dec=decimals;
		}
		for (String field : wicketIds) {
			PropertyModel<Double> doubleModel =
					new PropertyModel<Double>(modelObject, field);
			cmp.add(new DoubleTextField(field, doubleModel, dec));
		}
	}



	/**
	 * Δημιουργεί TextField<Double> με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer χωρίς να τα
	 * κάνει bind στον Object του model.Στα textField
	 * θέτουμε το μέγιστο αριθμο των επιτρεπόμενων δεκαδικών ψηφίων.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 * @param decimals
	 * 
	 */
	public static void addDoubleTextFieldWithoutBinding(MarkupContainer cmp, String[] wicketIds, Integer decimals) {
		final Integer dec;
		if(decimals == null){
			dec=new Integer(0);
		}else{
			dec=decimals;
		}
		for (String field : wicketIds) {
			cmp.add(new DoubleTextField(field, new Model<Double>(), dec));
		}
	}

	/**
	 * Δημιουργεί TextField<BigDecimal> με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer κάνοντας τα bind με το
	 * Object που του περνάμε.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 * @param modelObject
	 */
	public static void addBigDecimalTextFields(MarkupContainer cmp, String[] wicketIds, Object modelObject){
		for(String field : wicketIds){
			cmp.add(new TextField<BigDecimal>(field, new PropertyModel<BigDecimal>(modelObject, field),BigDecimal.class));
		}
	}

	/**
	 * Δημιουργεί TextField<BigDecimal> με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.Στα textField
	 * θέτουμε το μέγιστο αριθμο των επιτρεπόμενων δεκαδικών ψηφίων.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 * @param decimals
	 * 		  O μέγιστος αριθμός των επιτρεπόμενων δεκαδικών ψηφίων.
	 */
	public static void addBigDecimalTextFields(MarkupContainer cmp, String[] wicketIds, Integer decimals){
		final Integer dec;
		if(decimals == null){
			dec=new Integer(0);
		}else{
			dec=decimals;
		}
		for (String field : wicketIds) {
			TextField<BigDecimal> tf = new BigDecimalTextField(field, dec);
			cmp.add(tf);
		}
	}

	/**
	 * Δημιουργεί TextField<BigDecimal> με νέο Model<BigDecimal> με τα wicketId
	 * που του δίνουμε και τα προσθέτει στον markupContainer χωρίς να τα
	 * κάνει bind στον Object του model του MarkupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param fields
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 *
	 */
	public static void addBigDecimalTextFieldsWithoutBinding(MarkupContainer cmp, String[] fields) {
		for (String field : fields) {
			cmp.add(new TextField<BigDecimal>(field, new Model<BigDecimal>(),BigDecimal.class));
		}
	}

	/**
	 * Δημιουργεί TextField<BigDecimal> με το wicketId (field) που του δίνουμε
	 * και κάνει bind στο CompoundPropertyModel<T> model.
	 * Στα textField
	 * θέτουμε επίσης το μέγιστο αριθμο των επιτρεπόμενων δεκαδικών ψηφίων.
	 * @param <T>
	 * @param field
	 * 
	 * 		το wicketId του TextField.
	 * @param model το μοντέλο που θα περιεχει το field στο οποίο θα κάνουμε το binding.
	 * @param decimals Ο μέγιστος αριθμός των επιτρεπόμενων δεκαδικών ψηφίων
	 * @return TF
	 */

	public static <T extends Serializable>  TextField<BigDecimal>
	addBigDecimalTextFieldWithBinding(String field, CompoundPropertyModel<T> model,Integer decimals) {
		final Integer dec;
		if(decimals == null){
			dec=new Integer(0);
		}else{
			dec=decimals;
		}
		IModel<BigDecimal> bigDecimalModel = model.bind(field);
		return new BigDecimalTextField(field, bigDecimalModel , dec);
	}

	/**
	 * Δημιουργεί TextField<Long> με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 */
	public static void addLongTextField(MarkupContainer cmp, String[] wicketIds) {
		for (String field : wicketIds) {
			TextField<Long> tf = new TextField<Long>(field);
			tf.add(ValidationStyleBehavior.INSTANCE);
			tf.setOutputMarkupId(true);
			cmp.add(tf);
		}
	}

	/**
	 * Δημιουργεί TextField<Integer> με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 */
	public static void addIntegerTextField(MarkupContainer cmp, String[] wicketIds) {
		for (String field : wicketIds) {
			TextField<Integer> tf = new TextField<Integer>(field);
			tf.add(ValidationStyleBehavior.INSTANCE);
			tf.setOutputMarkupId(true);
			cmp.add(tf);
		}
	}


	/**
	 * Δημιουργεί Percentage TextField με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 * @param decimals ο αριθμός των δεκαδικών
	 */
	public static void addPercentageTextField(MarkupContainer cmp, String[] wicketIds, int decimals) {
		for (String field : wicketIds) {
			PercentageTextField tf = new PercentageTextField(field, decimals);
			cmp.add(tf);
		}
	}

	/**
	 * Δημιουργεί BigDecimal Percentage TextField με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 * @param decimals ο αριθμός των δεκαδικών
	 */
	public static void addPercentageBigDecimalTextField(MarkupContainer cmp, String[] wicketIds, int decimals) {
		for (String field : wicketIds) {
			PercentageBigDecimalTextField tf = new PercentageBigDecimalTextField(field, decimals);
			cmp.add(tf);
		}
	}

	/**
	 * Επιστρέφει ένα Label με το message και το wicketId που του δίνουμε.
	 * 
	 * @param id
	 * 		  Το wicketId του Label.
	 * @param message
	 * 		  To μήνυμα που θα εμφανίζει το Label.
	 * 
	 * @return Label
	 * 
	 */
	public static Label returnViewLabels(String id, String message) {
		Label label = new Label(id, new Model<String>(message));
		return label;
	}

	/**
	 * Επιστρέφει ένα Label με το με την τιμή του Enum και το
	 * wicketId που του δίνουμε.
	 * 
	 * @param id
	 * 		  Το wicketId του Label.
	 * @param enumeration
	 * 		  Η τιμή που θα εμφανίζει το Label.
	 * 
	 * @return Label
	 * 
	 */
	public static Label returnViewLabels(String id, Enum<?> enumeration) {
		Label label = new Label(id, new Model<Enum<?>>(enumeration));
		return label;
	}

	/**
	 * Δημιουργεί AjaxCheckBox με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer.
	 * 
	 * @param markupContainer
	 * 		O markupContainer,στον οποίο θα προστεθούν τα AjaxCheckBox.
	 * @param checkBoxes
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα AjaxCheckBox.
	 * 
	 */
	public static void addAjaxCheckBox(MarkupContainer markupContainer, String[] checkBoxes){
		for(String s : checkBoxes){
			AjaxCheckBox checkBox = new AjaxCheckBox(s){
				/**
				 * serialize.
				 */
				private static final long serialVersionUID = 1L;
				@Override
				protected void onUpdate(AjaxRequestTarget target){
					//TODO empty on update
				}
			};
			markupContainer.add(checkBox);
		}
	}

	/**
	 * 
	 * 
	 * Δημιουργεί TextField<Integer> με τα wicketId που του δίνουμε
	 * και τα προσθέτει στον markupContainer κάνοντας τα bind με το
	 * Object που του περνάμε. .
	 * 
	 * @param cmp
	 * 		O markupContainer,στον οποίο θα προστεθούν τα Textfields.
	 * @param wicketIds
	 * 		Τα wicketIds με τα οποία θα δημιουργηθούν τα Textfields.
	 * @param modelObject
	 * 		  The Object that contains the property.
	 */
	public static void addIntegerTextField(MarkupContainer cmp, String[] wicketIds, Object modelObject) {
		for (String field : wicketIds) {
			TextField<Integer> tf = new TextField<Integer>(field,new PropertyModel<Integer>(modelObject,field));
			tf.add(ValidationStyleBehavior.INSTANCE);
			tf.setOutputMarkupId(true);
			cmp.add(tf);
		}
	}
}