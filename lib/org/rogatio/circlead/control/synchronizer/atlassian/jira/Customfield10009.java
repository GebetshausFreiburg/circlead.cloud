
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
 * The Class Customfield10009.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "hasEpicLinkFieldDependency",
    "showField",
    "nonEditableReason"
})
public class Customfield10009 {

    /** The has epic link field dependency. */
    @JsonProperty("hasEpicLinkFieldDependency")
    private Boolean hasEpicLinkFieldDependency;
    
    /** The show field. */
    @JsonProperty("showField")
    private Boolean showField;
    
    /** The non editable reason. */
    @JsonProperty("nonEditableReason")
    private NonEditableReason nonEditableReason;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the checks for epic link field dependency.
     *
     * @return the checks for epic link field dependency
     */
    @JsonProperty("hasEpicLinkFieldDependency")
    public Boolean getHasEpicLinkFieldDependency() {
        return hasEpicLinkFieldDependency;
    }

    /**
     * Sets the checks for epic link field dependency.
     *
     * @param hasEpicLinkFieldDependency the new checks for epic link field dependency
     */
    @JsonProperty("hasEpicLinkFieldDependency")
    public void setHasEpicLinkFieldDependency(Boolean hasEpicLinkFieldDependency) {
        this.hasEpicLinkFieldDependency = hasEpicLinkFieldDependency;
    }

    /**
     * Gets the show field.
     *
     * @return the show field
     */
    @JsonProperty("showField")
    public Boolean getShowField() {
        return showField;
    }

    /**
     * Sets the show field.
     *
     * @param showField the new show field
     */
    @JsonProperty("showField")
    public void setShowField(Boolean showField) {
        this.showField = showField;
    }

    /**
     * Gets the non editable reason.
     *
     * @return the non editable reason
     */
    @JsonProperty("nonEditableReason")
    public NonEditableReason getNonEditableReason() {
        return nonEditableReason;
    }

    /**
     * Sets the non editable reason.
     *
     * @param nonEditableReason the new non editable reason
     */
    @JsonProperty("nonEditableReason")
    public void setNonEditableReason(NonEditableReason nonEditableReason) {
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
