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
package gr.interamerican.wicket.bo2.markup.html.form;

import gr.interamerican.bo2.arch.ext.Cache;
import gr.interamerican.bo2.arch.ext.TranslatableEntry;
import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.arch.utils.beans.CacheImpl;
import gr.interamerican.bo2.arch.utils.beans.TypedSelectableImpl;
import gr.interamerican.bo2.samples.utils.meta.Bean1;
import gr.interamerican.bo2.samples.utils.meta.Bean1descriptor;
import gr.interamerican.bo2.utils.DateUtils;
import gr.interamerican.bo2.utils.meta.BasicBusinessObjectDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BigDecimalBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.BooleanBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.DateBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.DoubleBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.FloatBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.IntegerBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.LongBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.NumberBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.descriptors.StringBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.ext.descriptors.CachedEntryBoPropertyDescriptor;
import gr.interamerican.bo2.utils.meta.formatters.Formatter;
import gr.interamerican.bo2.utils.meta.formatters.ObjectFormatter;
import gr.interamerican.bo2.utils.meta.parsers.LongParser;
import gr.interamerican.bo2.utils.meta.parsers.Parser;
import gr.interamerican.wicket.bo2.protocol.http.Bo2WicketSession;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

/**
 * Page for testing SelfDrawnComponents.
 */
public class SelfDrawnTestPage extends WebPage{
	 
	
	/**
	 * Descriptors.
	 */
	private static final Map<Class<? extends BoPropertyDescriptor<?> >,BoPropertyDescriptor<?>> map = 
		new HashMap<Class<? extends BoPropertyDescriptor<?> >,BoPropertyDescriptor<?> >();

	static {		
		initializeDescriptors();
	}

	/**
	 * Feedback panel.
	 */
	private FeedbackPanel feedbackPanel = new FeedbackPanel("feedBackPanel"); //$NON-NLS-1$
	
	
	/**
	 * 
	 */
	private AjaxButton ajaxButton = new AjaxButton("submit"){ //$NON-NLS-1$

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
    	
		@Override
		protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			//empty
		}
		@Override
		protected void onError(AjaxRequestTarget target, Form<?> form) {
			target.addComponent(feedbackPanel);
		}
    };
	

	/**
	 * Creates a new WicketPage object. 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SelfDrawnTestPage(){
		add(feedbackPanel);
		
		//BigDecimal
		NumberBoPropertyDescriptor<BigDecimal> boPropDescr =(NumberBoPropertyDescriptor<BigDecimal>) 
		map.get(BigDecimalBoPropertyDescriptor.class);	
		SelfDrawnBigDecimalTextField bdTxtField = new 
			SelfDrawnBigDecimalTextField("selfDrawnBigDecimalTextField",boPropDescr); //$NON-NLS-1$
		bdTxtField.setDefaultModelObject(new BigDecimal(3.000));
		add(bdTxtField);
		
		//Boolean		
		BooleanBoPropertyDescriptor booleanPropDescr = 
			(BooleanBoPropertyDescriptor) map.get(BooleanBoPropertyDescriptor.class);
		SelfDrawnCheckBox selfDrawnCheckBox = new SelfDrawnCheckBox("selfDrawnBooleanCheckBox", booleanPropDescr); //$NON-NLS-1$
		selfDrawnCheckBox.setDefaultModelObject(new Boolean(true));
		add(selfDrawnCheckBox);

		//Date
		DateBoPropertyDescriptor dateDescr =(DateBoPropertyDescriptor) map.get(DateBoPropertyDescriptor.class);			
		SelfDrawnDateField selfDrawnDateField = new SelfDrawnDateField("selfDrawnDateField",dateDescr); //$NON-NLS-1$
		selfDrawnDateField.setDefaultModelObject(DateUtils.getDate(2011, Calendar.NOVEMBER, 25));
		add(selfDrawnDateField);

		//Double
		NumberBoPropertyDescriptor<Double> doubleDescr =(NumberBoPropertyDescriptor<Double>) 
		map.get(DoubleBoPropertyDescriptor.class);
		SelfDrawnDoubleTextField selfDrawnDoubleTextField= new SelfDrawnDoubleTextField("selfDrawnDoubleTextField",doubleDescr); //$NON-NLS-1$
		selfDrawnDoubleTextField.setDefaultModelObject(4.2);
		add(selfDrawnDoubleTextField);
		
		SelfDrawnDoubleTextField selfDrawnDoubleTextField1= new SelfDrawnDoubleTextField("selfDrawnDoubleTextField1",doubleDescr); //$NON-NLS-1$
        Form selfDrawnDoubleForm = new Form("selfDrawnDoubleForm");  //$NON-NLS-1$
        selfDrawnDoubleForm.add(selfDrawnDoubleTextField1);        
        selfDrawnDoubleForm.add(ajaxButton);     
        add(selfDrawnDoubleForm);
		
		
		//DropDownChoice
		CachedEntryBoPropertyDescriptor<?,?> ceBoPd = createDescriptor();
		List<TypedSelectable<?>> choices = new ArrayList<TypedSelectable<?>>(ceBoPd.getValues());		
		Bo2WicketSession bo2 = new Bo2WicketSession(getRequest());				
		SelfDrawnDropDownChoiceForEntry selfDrawnDropDownChoice = new 
			SelfDrawnDropDownChoiceForEntry("selfDrawnDrowDownChoice", ceBoPd, choices,bo2); //$NON-NLS-1$
		
		Model<TypedSelectable<?>> model = 
			new Model<TypedSelectable<?>>(ceBoPd.getValues().iterator().next());
		SelfDrawnDropDownChoiceForEntry selfDrawnDropDownChoice1 = 
			new SelfDrawnDropDownChoiceForEntry("selfDrawnDrowDownChoice1",model,ceBoPd,choices,bo2);				 //$NON-NLS-1$
		add(selfDrawnDropDownChoice);
		add(selfDrawnDropDownChoice1);
		
								     
		//Float
		NumberBoPropertyDescriptor<Float> floatDescr =(NumberBoPropertyDescriptor<Float>) 
		map.get(FloatBoPropertyDescriptor.class);		
		SelfDrawnFloatTextField selfDrawnFloatTextField = 
			new SelfDrawnFloatTextField("selfDrawnFloatTextField",floatDescr); //$NON-NLS-1$
		selfDrawnFloatTextField.setDefaultModelObject(new Float(4.2));
		add(selfDrawnFloatTextField);

//		SeflDrawnForm
		Bean1 bean1 = new Bean1();
		Bean1descriptor bean1Desc = new Bean1descriptor();
		BasicBusinessObjectDescriptor<Bean1> busDesc = new BasicBusinessObjectDescriptor<Bean1>();
		busDesc.setPropertyDescriptors(bean1Desc.getPropertyDescriptors());
		SelfDrawnForm<Bean1> selfDrawnForm = new SelfDrawnForm<Bean1>("selfDrawnForm", new CompoundPropertyModel<Bean1>(bean1), busDesc); //$NON-NLS-1$
		add(selfDrawnForm);  

		//Integer
		NumberBoPropertyDescriptor<Integer> integerDescr =(NumberBoPropertyDescriptor<Integer>) 
		map.get(IntegerBoPropertyDescriptor.class);		
		SelfDrawnIntegerTextField selfDrawnIntegerTextField = 
			new SelfDrawnIntegerTextField("selfDrawnIntegerTextField",integerDescr); //$NON-NLS-1$
		selfDrawnIntegerTextField.setDefaultModelObject(new Integer(4));
		add(selfDrawnIntegerTextField);

		//Label
		SelfDrawnLabel selfDrawnLabel = new SelfDrawnLabel("selfDrawnLabel", "This is a test"); //$NON-NLS-1$ //$NON-NLS-2$
		add(selfDrawnLabel);

		//Long
		NumberBoPropertyDescriptor<Long> longDescr =(NumberBoPropertyDescriptor<Long>) 
		map.get(LongBoPropertyDescriptor.class);		
		SelfDrawnLongTextField selfDrawnLongTextField = 
			new SelfDrawnLongTextField("selfDrawnLongTextField",longDescr); //$NON-NLS-1$
		selfDrawnLongTextField.setDefaultModelObject(4L);
		add(selfDrawnLongTextField);

		//String
		BoPropertyDescriptor<String> stringDescr = (BoPropertyDescriptor<String>)
		map.get(StringBoPropertyDescriptor.class);		
		SelfDrawnStringTextField selfDrawnStringTextField = new 
		SelfDrawnStringTextField("selfDrawnStringTextField",(StringBoPropertyDescriptor)stringDescr); //$NON-NLS-1$
		selfDrawnStringTextField.setDefaultModelObject("This is a test"); //$NON-NLS-1$
        add(selfDrawnStringTextField);
        
       //String TextArea
		BoPropertyDescriptor<String> textAreaDescr = (BoPropertyDescriptor<String>)
		map.get(StringBoPropertyDescriptor.class);		
		SelfDrawnTextArea selfDrawnStringTextArea = 
			new SelfDrawnTextArea("selfDrawnStringTextArea",(StringBoPropertyDescriptor)textAreaDescr); //$NON-NLS-1$
		selfDrawnStringTextArea.setDefaultModelObject("This is a test"); //$NON-NLS-1$
        add(selfDrawnStringTextArea);
        
	}


	/**
	 * 
	 */
	private static void initializeDescriptors(){
		Bean1descriptor descriptor =  new Bean1descriptor();
		List<BoPropertyDescriptor<?>> descriptors = 
			descriptor.getPropertyDescriptors();		 
		for(BoPropertyDescriptor<?> desc : descriptors){			 
			if(desc instanceof BigDecimalBoPropertyDescriptor){
				map.put(BigDecimalBoPropertyDescriptor.class, desc);
			}else if(desc instanceof BooleanBoPropertyDescriptor){
				map.put(BooleanBoPropertyDescriptor.class, desc);
			}else if(desc instanceof DoubleBoPropertyDescriptor){
				map.put(DoubleBoPropertyDescriptor.class,desc);
			}else if(desc instanceof DateBoPropertyDescriptor){
				map.put(DateBoPropertyDescriptor.class, desc);
			}else if(desc instanceof IntegerBoPropertyDescriptor){
				map.put(IntegerBoPropertyDescriptor.class, desc);
			}else if(desc instanceof FloatBoPropertyDescriptor){
				map.put(FloatBoPropertyDescriptor.class, desc);
			}else if(desc instanceof StringBoPropertyDescriptor){
				map.put(StringBoPropertyDescriptor.class, desc);
			}else if(desc instanceof LongBoPropertyDescriptor){
				map.put(LongBoPropertyDescriptor.class, desc);
			}
		}	
	} 

	/**
	 * Create CachedEntryBoPropertyDescriptor
	 * @return CachedEntryBoPropertyDescriptor
	 * 
	 */
	private CachedEntryBoPropertyDescriptor<?,?> createDescriptor(){
		Cache<Long> CACHE = new CacheImpl<Long>();
		Parser<Long> PARSER = new LongParser();
		Formatter<Long> FORMATTER = ObjectFormatter.<Long>getInstance();
		

		 Entry value = new Entry();
		 value.setCode(1L);
		 value.setTypeId(1000L);
		 value.setSubTypeId(1L);
		 CACHE.put(value);		 		    
         CachedEntryBoPropertyDescriptor<Entry, Long> cashedDesc = new CachedEntryBoPropertyDescriptor<Entry, Long>(
        		 1000L, 1L,CACHE, PARSER, FORMATTER);
         
         
		return cashedDesc;
	}
	
	
	/**
	 * Entry.
	 */
	private class Entry
	extends TypedSelectableImpl<Long>
	implements TranslatableEntry<Long, Long, Long> {
		
		/**
		 * UID.
		 */
		private static final long serialVersionUID = 1L;

		public String getTranslation(Long languageId) {			
			return getName();
		}
		
		public Long getTranslationResourceId() {			
			return getCode();
		}		
	}


}
