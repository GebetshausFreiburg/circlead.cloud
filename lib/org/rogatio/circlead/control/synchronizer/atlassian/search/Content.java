
package org.rogatio.circlead.control.synchronizer.atlassian.search;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class Content.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "status",
    "title",
    "childTypes",
    "macroRenderedOutput",
    "restrictions",
    "_expandable",
    "_links"
})
public class Content {

    /** The id. */
    @JsonProperty("id")
    private String id;
    
    /** The type. */
    @JsonProperty("type")
    private String type;
    
    /** The status. */
    @JsonProperty("status")
    private String status;
    
    /** The title. */
    @JsonProperty("title")
    private String title;
    
    /** The child types. */
    @JsonProperty("childTypes")
    private ChildTypes childTypes;
    
    /** The macro rendered output. */
    @JsonProperty("macroRenderedOutput")
    private MacroRenderedOutput macroRenderedOutput;
    
    /** The restrictions. */
    @JsonProperty("restrictions")
    private Restrictions restrictions;
    
    /** The expandable. */
    @JsonProperty("_expandable")
    private Expandable expandable;
    
    /** The links. */
    @JsonProperty("_links")
    private Links links;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the id.
     *
     * @return the id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the child types.
     *
     * @return the child types
     */
    @JsonProperty("childTypes")
    public ChildTypes getChildTypes() {
        return childTypes;
    }

    /**
     * Sets the child types.
     *
     * @param childTypes the new child types
     */
    @JsonProperty("childTypes")
    public void setChildTypes(ChildTypes childTypes) {
        this.childTypes = childTypes;
    }

    /**
     * Gets the macro rendered output.
     *
     * @return the macro rendered output
     */
    @JsonProperty("macroRenderedOutput")
    public MacroRenderedOutput getMacroRenderedOutput() {
        return macroRenderedOutput;
    }

    /**
     * Sets the macro rendered output.
     *
     * @param macroRenderedOutput the new macro rendered output
     */
    @JsonProperty("macroRenderedOutput")
    public void setMacroRenderedOutput(MacroRenderedOutput macroRenderedOutput) {
        this.macroRenderedOutput = macroRenderedOutput;
    }

    /**
     * Gets the restrictions.
     *
     * @return the restrictions
     */
    @JsonProperty("restrictions")
    public Restrictions getRestrictions() {
        return restrictions;
    }

    /**
     * Sets the restrictions.
     *
     * @param restrictions the new restrictions
     */
    @JsonProperty("restrictions")
    public void setRestrictions(Restrictions restrictions) {
        this.restrictions = restrictions;
    }

    /**
     * Gets the expandable.
     *
     * @return the expandable
     */
    @JsonProperty("_expandable")
    public Expandable getExpandable() {
        return expandable;
    }

    /**
     * Sets the expandable.
     *
     * @param expandable the new expandable
     */
    @JsonProperty("_expandable")
    public void setExpandable(Expandable expandable) {
        this.expandable = expandable;
    }

    /**
     * Gets the links.
     *
     * @return the links
     */
    @JsonProperty("_links")
    public Links getLinks() {
        return links;
    }

    /**
     * Sets the links.
     *
     * @param links the new links
     */
    @JsonProperty("_links")
    public void setLinks(Links links) {
        this.links = links;
    }

    /**
     * Gets the additional properties.
     *
     * @return the additional properties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * Sets the additional property.
     *
     * @param name the name
     * @param value the value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
