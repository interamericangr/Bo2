//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.04.04 at 09:59:23 AM EEST 
//


package gr.interamerican.bo2.hbm.meta.beans;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject"
})
@XmlRootElement(name = "hibernate-mapping")
public class HibernateMapping {

    @XmlAttribute(name = "schema")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String schema;
    @XmlAttribute(name = "catalog")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String catalog;
    @XmlAttribute(name = "default-cascade")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String defaultCascade;
    @XmlAttribute(name = "default-access")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String defaultAccess;
    @XmlAttribute(name = "default-lazy")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String defaultLazy;
    @XmlAttribute(name = "auto-import")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String autoImport;
    @XmlAttribute(name = "package")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String _package;
    @XmlElements({
        @XmlElement(name = "meta", type = Meta.class),
        @XmlElement(name = "identifier-generator", type = IdentifierGenerator.class),
        @XmlElement(name = "typedef", type = Typedef.class),
        @XmlElement(name = "filter-def", type = FilterDef.class),
        @XmlElement(name = "import", type = Import.class),
        @XmlElement(name = "class", type = Clazz.class),
        @XmlElement(name = "subclass", type = Subclass.class),
        @XmlElement(name = "joined-subclass", type = JoinedSubclass.class),
        @XmlElement(name = "union-subclass", type = UnionSubclass.class),
        @XmlElement(name = "resultset", type = Resultset.class),
        @XmlElement(name = "query", type = Query.class),
        @XmlElement(name = "sql-query", type = SqlQuery.class),
        @XmlElement(name = "fetch-profile", type = FetchProfile.class),
        @XmlElement(name = "database-object", type = DatabaseObject.class)
    })
    protected List<Object> metaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject;

    /**
     * Gets the value of the schema property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSchema() {
        return schema;
    }

    /**
     * Sets the value of the schema property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSchema(String value) {
        this.schema = value;
    }

    /**
     * Gets the value of the catalog property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * Sets the value of the catalog property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCatalog(String value) {
        this.catalog = value;
    }

    /**
     * Gets the value of the defaultCascade property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultCascade() {
        if (defaultCascade == null) {
            return "none";
        } else {
            return defaultCascade;
        }
    }

    /**
     * Sets the value of the defaultCascade property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultCascade(String value) {
        this.defaultCascade = value;
    }

    /**
     * Gets the value of the defaultAccess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultAccess() {
        if (defaultAccess == null) {
            return "property";
        } else {
            return defaultAccess;
        }
    }

    /**
     * Sets the value of the defaultAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultAccess(String value) {
        this.defaultAccess = value;
    }

    /**
     * Gets the value of the defaultLazy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultLazy() {
        if (defaultLazy == null) {
            return "true";
        } else {
            return defaultLazy;
        }
    }

    /**
     * Sets the value of the defaultLazy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultLazy(String value) {
        this.defaultLazy = value;
    }

    /**
     * Gets the value of the autoImport property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAutoImport() {
        if (autoImport == null) {
            return "true";
        } else {
            return autoImport;
        }
    }

    /**
     * Sets the value of the autoImport property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAutoImport(String value) {
        this.autoImport = value;
    }

    /**
     * Gets the value of the package property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPackage() {
        return _package;
    }

    /**
     * Sets the value of the package property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPackage(String value) {
        this._package = value;
    }

    /**
     * Gets the value of the metaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Meta }
     * {@link IdentifierGenerator }
     * {@link Typedef }
     * {@link FilterDef }
     * {@link Import }
     * {@link Clazz }
     * {@link Subclass }
     * {@link JoinedSubclass }
     * {@link UnionSubclass }
     * {@link Resultset }
     * {@link Query }
     * {@link SqlQuery }
     * {@link FetchProfile }
     * {@link DatabaseObject }
     * 
     * 
     */
    public List<Object> getMetaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject() {
        if (metaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject == null) {
            metaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject = new ArrayList<Object>();
        }
        return this.metaOrIdentifierGeneratorOrTypedefOrFilterDefOrImportOrClazzOrSubclassOrJoinedSubclassOrUnionSubclassOrResultsetOrQueryOrSqlQueryOrFetchProfileOrDatabaseObject;
    }

}
