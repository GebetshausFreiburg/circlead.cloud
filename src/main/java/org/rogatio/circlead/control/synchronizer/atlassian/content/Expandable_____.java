
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
 * The Class Expandable_____.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "operations",
    "details",
    "personalSpace"
})
public class Expandable_____ {

    /** The operations. */
    @JsonProperty("operations")
    private String operations;
    
    /** The details. */
    @JsonProperty("details")
    private String details;
    
    /** The personal space. */
    @JsonProperty("personalSpace")
    private String personalSpace;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the operations.
     *
     * @return the operations
     */
    @JsonProperty("operations")
    public String getOperations() {
        return operations;
    }

    /**
     * Sets the operations.
     *
     * @param operations the new operations
     */
    @JsonProperty("operations")
    public void setOperations(String operations) {
        this.operations = operations;
    }

    /**
     * Gets the details.
     *
     * @return the details
     */
    @JsonProperty("details")
    public String getDetails() {
        return details;
    }

    /**
     * Sets the details.
     *
     * @param details the new details
     */
    @JsonProperty("details")
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * Gets the personal space.
     *
     * @return the personal space
     */
    @JsonProperty("personalSpace")
    public String getPersonalSpace() {
        return personalSpace;
    }

    /**
     * Sets the personal space.
     *
     * @param personalSpace the new personal space
     */
    @JsonProperty("personalSpace")
    public void setPersonalSpace(String personalSpace) {
        this.personalSpace = personalSpace;
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
