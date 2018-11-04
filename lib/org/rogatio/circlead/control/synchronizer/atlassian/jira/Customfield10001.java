
package org.rogatio.circlead.control.synchronizer.atlassian.jira;

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
 * The Class Customfield10001.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "isEditable",
    "nonEditableReason"
})
public class Customfield10001 {

    /** The is editable. */
    @JsonProperty("isEditable")
    private Boolean isEditable;
    
    /** The non editable reason. */
    @JsonProperty("nonEditableReason")
    private NonEditableReason_ nonEditableReason;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the checks if is editable.
     *
     * @return the checks if is editable
     */
    @JsonProperty("isEditable")
    public Boolean getIsEditable() {
        return isEditable;
    }

    /**
     * Sets the checks if is editable.
     *
     * @param isEditable the new checks if is editable
     */
    @JsonProperty("isEditable")
    public void setIsEditable(Boolean isEditable) {
        this.isEditable = isEditable;
    }

    /**
     * Gets the non editable reason.
     *
     * @return the non editable reason
     */
    @JsonProperty("nonEditableReason")
    public NonEditableReason_ getNonEditableReason() {
        return nonEditableReason;
    }

    /**
     * Sets the non editable reason.
     *
     * @param nonEditableReason the new non editable reason
     */
    @JsonProperty("nonEditableReason")
    public void setNonEditableReason(NonEditableReason_ nonEditableReason) {
        this.nonEditableReason = nonEditableReason;
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
