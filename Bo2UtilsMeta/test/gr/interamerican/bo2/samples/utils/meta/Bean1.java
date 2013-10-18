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
package gr.interamerican.bo2.samples.utils.meta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * sample bean for bean descriptor test.
 */
public class Bean1 implements Serializable {
	/**
	 * serial
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * id.
	 */
	private Long id;
	/**
	 * description.
	 */
	private String description;
	/**
	 * testTextArea.
	 */
	private String testTextArea;
	/**
	 * issueDate.
	 */
	private Date issueDate;
	/**
	 * renewalDate.
	 */
	private Date renewalDate;
	/**
	 * some percentage.
	 */
	private Double percentage;
	/**
	 * amount.
	 */
	private BigDecimal amount;
	/**
	 * childBean.
	 */
	private ChildBean childBean;
	/**
	 * integerField.
	 */
	private Integer integerField;
	/**
	 * floatField.
	 */
	private Float floatField;
	/**
	 * checkField.
	 */
	private Boolean checkField;
	/**
	 * multipleChoiceField.
	 */
	private List<Long> multipleChoiceField = new ArrayList<Long>();
	
	/**
	 * bigDecimalTextField.
	 */
	private BigDecimal bigDecimalTextField;
	/**
	 * doubleField.
	 */
	private Double doubleField;
	/**
	 * doubleField_2.
	 */
	private Double doubleField_2;
	
	/**
	 * set of values.
	 */
	 private Set<Long> choices;
	
	/**
	 * Creates a new Bean1 object. 
	 *
	 */
	public Bean1() {
		super();
		this.childBean = new ChildBean();
	}
	/**
	 * Gets the id.
	 *
	 * @return Returns the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * Assigns a new value to the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * Gets the description.
	 *
	 * @return Returns the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Assigns a new value to the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Gets the issueDate.
	 *
	 * @return Returns the issueDate
	 */
	public Date getIssueDate() {
		return issueDate;
	}
	/**
	 * Assigns a new value to the issueDate.
	 *
	 * @param issueDate the issueDate to set
	 */
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	/**
	 * Gets the percentage.
	 *
	 * @return Returns the percentage
	 */
	public Double getPercentage() {
		return percentage;
	}
	/**
	 * Assigns a new value to the percentage.
	 *
	 * @param percentage the percentage to set
	 */
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	/**
	 * Gets the amount.
	 *
	 * @return Returns the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * Assigns a new value to the amount.
	 *
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * Gets the renewalDate.
	 *
	 * @return Returns the renewalDate
	 */
	public Date getRenewalDate() {
		return renewalDate;
	}
	/**
	 * Assigns a new value to the renewalDate.
	 *
	 * @param renewalDate the renewalDate to set
	 */
	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}
	/**
	 * Gets the childBean.
	 *
	 * @return Returns the childBean
	 */
	public ChildBean getChildBean() {
		return childBean;
	}
	/**
	 * Assigns a new value to the childBean.
	 *
	 * @param childBean the childBean to set
	 */
	public void setChildBean(ChildBean childBean) {
		this.childBean = childBean;
	}
	/**
	 * Assigns a new value to the testTextArea.
	 *
	 * @param testTextArea the testTextArea to set
	 */
	public void setTestTextArea(String testTextArea) {
		this.testTextArea = testTextArea;
	}
	/**
	 * Gets the testTextArea.
	 *
	 * @return Returns the testTextArea
	 */
	public String getTestTextArea() {
		return testTextArea;
	}
	/**
	 * Gets the integerField.
	 *
	 * @return Returns the integerField
	 */
	public Integer getIntegerField() {
		return integerField;
	}
	/**
	 * Assigns a new value to the integerField.
	 *
	 * @param integerField the integerField to set
	 */
	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}
	/**
	 * Gets the floatField.
	 *
	 * @return Returns the floatField
	 */
	public Float getFloatField() {
		return floatField;
	}
	/**
	 * Assigns a new value to the floatField.
	 *
	 * @param floatField the floatField to set
	 */
	public void setFloatField(Float floatField) {
		this.floatField = floatField;
	}
	/**
	 * Gets the checkField.
	 *
	 * @return Returns the checkField
	 */
	public Boolean getCheckField() {
		return checkField;
	}
	/**
	 * Assigns a new value to the checkField.
	 *
	 * @param checkField the checkField to set
	 */
	public void setCheckField(Boolean checkField) {
		this.checkField = checkField;
	}
	/**
	 * Gets the multipleChoiceField.
	 *
	 * @return Returns the multipleChoiceField
	 */
	public List<Long> getMultipleChoiceField() {
		return multipleChoiceField;
	}
	/**
	 * Assigns a new value to the multipleChoiceField.
	 *
	 * @param multipleChoiceField the multipleChoiceField to set
	 */
	public void setMultipleChoiceField(List<Long> multipleChoiceField) {
		this.multipleChoiceField = multipleChoiceField;
	}
	/**
	 * Gets the bigDecimalTextField.
	 *
	 * @return Returns the bigDecimalTextField
	 */
	public BigDecimal getBigDecimalTextField() {
		return bigDecimalTextField;
	}
	/**
	 * Assigns a new value to the bigDecimalTextField.
	 *
	 * @param bigDecimalTextField the bigDecimalTextField to set
	 */
	public void setBigDecimalTextField(BigDecimal bigDecimalTextField) {
		this.bigDecimalTextField = bigDecimalTextField;
	}
	/**
	 * Gets the doubleField.
	 *
	 * @return Returns the doubleField
	 */
	public Double getDoubleField() {
		return doubleField;
	}
	/**
	 * Assigns a new value to the doubleField.
	 *
	 * @param doubleField the doubleField to set
	 */
	public void setDoubleField(Double doubleField) {
		this.doubleField = doubleField;
	}
	/**
	 * Gets the doubleField_2.
	 *
	 * @return Returns the doubleField_2
	 */
	public Double getDoubleField_2() {
		return doubleField_2;
	}
	/**
	 * Assigns a new value to the doubleField_2.
	 *
	 * @param doubleField_2 the doubleField_2 to set
	 */
	public void setDoubleField_2(Double doubleField_2) {
		this.doubleField_2 = doubleField_2;
	}
	/**
	 * Gets the choices.
	 *
	 * @return Returns the choices
	 */
	public Set<Long> getChoices() {
		return choices;
	}
	/**
	 * Assigns a new value to the choices.
	 *
	 * @param choices the choices to set
	 */
	public void setChoices(Set<Long> choices) {
		this.choices = choices;
	}

}
