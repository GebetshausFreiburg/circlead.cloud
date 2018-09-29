
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
 * The Class Fields_.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "summary",
    "status",
    "priority",
    "issuetype"
})
public class Fields_ {

    /** The summary. */
    @JsonProperty("summary")
    private String summary;
    
    /** The status. */
    @JsonProperty("status")
    private Status status;
    
    /** The priority. */
    @JsonProperty("priority")
    private Priority_ priority;
    
    /** The issuetype. */
    @JsonProperty("issuetype")
    private Issuetype_ issuetype;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the summary.
     *
     * @return the summary
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the summary.
     *
     * @param summary the new summary
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    @JsonProperty("status")
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    @JsonProperty("status")
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the priority.
     *
     * @return the priority
     */
    @JsonProperty("priority")
    public Priority_ getPriority() {
        return priority;
    }

    /**
     * Sets the priority.
     *
     * @param priority the new priority
     */
    @JsonProperty("priority")
    public void setPriority(Priority_ priority) {
        this.priority = priority;
    }

    /**
     * Gets the issuetype.
     *
     * @return the issuetype
     */
    @JsonProperty("issuetype")
    public Issuetype_ getIssuetype() {
        return issuetype;
    }

    /**
     * Sets the issuetype.
     *
     * @param issuetype the new issuetype
     */
    @JsonProperty("issuetype")
    public void setIssuetype(Issuetype_ issuetype) {
        this.issuetype = issuetype;
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
