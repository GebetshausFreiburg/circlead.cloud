
package org.rogatio.circlead.control.synchronizer.atlassian.content;

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
 * The Class Metadata.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "labels",
    "_expandable"
})
public class Metadata {

    /** The labels. */
    @JsonProperty("labels")
    private Labels labels;
    
    /** The expandable. */
    @JsonProperty("_expandable")
    private Expandable__________ expandable;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the labels.
     *
     * @return the labels
     */
    @JsonProperty("labels")
    public Labels getLabels() {
        return labels;
    }

    /**
     * Sets the labels.
     *
     * @param labels the new labels
     */
    @JsonProperty("labels")
    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    /**
     * Gets the expandable.
     *
     * @return the expandable
     */
    @JsonProperty("_expandable")
    public Expandable__________ getExpandable() {
        return expandable;
    }

    /**
     * Sets the expandable.
     *
     * @param expandable the new expandable
     */
    @JsonProperty("_expandable")
    public void setExpandable(Expandable__________ expandable) {
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
