
package org.rogatio.circlead.control.synchronizer.atlassian.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class Storage.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "value",
    "representation",
    "embeddedContent",
    "_expandable"
})
public class Storage {

    /** The value. */
    @JsonProperty("value")
    private String value;
    
    /** The representation. */
    @JsonProperty("representation")
    private String representation;
    
    /** The embedded content. */
    @JsonProperty("embeddedContent")
    private List<Object> embeddedContent = null;
    
    /** The expandable. */
    @JsonProperty("_expandable")
    private Expandable________ expandable;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the value.
     *
     * @return the value
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the representation.
     *
     * @return the representation
     */
    @JsonProperty("representation")
    public String getRepresentation() {
        return representation;
    }

    /**
     * Sets the representation.
     *
     * @param representation the new representation
     */
    @JsonProperty("representation")
    public void setRepresentation(String representation) {
        this.representation = representation;
    }

    /**
     * Gets the embedded content.
     *
     * @return the embedded content
     */
    @JsonProperty("embeddedContent")
    public List<Object> getEmbeddedContent() {
        return embeddedContent;
    }

    /**
     * Sets the embedded content.
     *
     * @param embeddedContent the new embedded content
     */
    @JsonProperty("embeddedContent")
    public void setEmbeddedContent(List<Object> embeddedContent) {
        this.embeddedContent = embeddedContent;
    }

    /**
     * Gets the expandable.
     *
     * @return the expandable
     */
    @JsonProperty("_expandable")
    public Expandable________ getExpandable() {
        return expandable;
    }

    /**
     * Sets the expandable.
     *
     * @param expandable the new expandable
     */
    @JsonProperty("_expandable")
    public void setExpandable(Expandable________ expandable) {
        this.expandable = expandable;
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
