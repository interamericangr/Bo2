//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.04 at 09:59:23 AM EEST 
//


package gr.interamerican.bo2.hbm.meta.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "meta")
public class Meta {

    @XmlAttribute(name = "attribute", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String attribute;
    @XmlAttribute(name = "inherit")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String inherit;
    @XmlValue
    protected String value;

    /**
     * Gets the value of the attribute property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Sets the value of the attribute property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttribute(String value) {
        this.attribute = value;
    }

    /**
     * Gets the value of the inherit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInherit() {
        if (inherit == null) {
            return "true";
        } else {
            return inherit;
        }
    }

    /**
     * Sets the value of the inherit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInherit(String value) {
        this.inherit = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getvalue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setvalue(String value) {
        this.value = value;
    }

}
