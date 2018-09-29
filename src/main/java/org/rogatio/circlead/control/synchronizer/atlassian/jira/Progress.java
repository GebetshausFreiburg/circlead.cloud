
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
 * The Class Progress.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "progress",
    "total"
})
public class Progress {

    /** The progress. */
    @JsonProperty("progress")
    private Integer progress;
    
    /** The total. */
    @JsonProperty("total")
    private Integer total;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the progress.
     *
     * @return the progress
     */
    @JsonProperty("progress")
    public Integer getProgress() {
        return progress;
    }

    /**
     * Sets the progress.
     *
     * @param progress the new progress
     */
    @JsonProperty("progress")
    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    /**
     * Gets the total.
     *
     * @return the total
     */
    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    /**
     * Sets the total.
     *
     * @param total the new total
     */
    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
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
