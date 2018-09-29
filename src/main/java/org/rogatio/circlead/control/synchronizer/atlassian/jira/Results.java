
package org.rogatio.circlead.control.synchronizer.atlassian.jira;

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
 * The Class Results.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "expand",
    "startAt",
    "maxResults",
    "total",
    "issues"
})
public class Results {

    /** The expand. */
    @JsonProperty("expand")
    private String expand;
    
    /** The start at. */
    @JsonProperty("startAt")
    private Integer startAt;
    
    /** The max results. */
    @JsonProperty("maxResults")
    private Integer maxResults;
    
    /** The total. */
    @JsonProperty("total")
    private Integer total;
    
    /** The issues. */
    @JsonProperty("issues")
    private List<Issue> issues = null;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the expand.
     *
     * @return the expand
     */
    @JsonProperty("expand")
    public String getExpand() {
        return expand;
    }

    /**
     * Sets the expand.
     *
     * @param expand the new expand
     */
    @JsonProperty("expand")
    public void setExpand(String expand) {
        this.expand = expand;
    }

    /**
     * Gets the start at.
     *
     * @return the start at
     */
    @JsonProperty("startAt")
    public Integer getStartAt() {
        return startAt;
    }

    /**
     * Sets the start at.
     *
     * @param startAt the new start at
     */
    @JsonProperty("startAt")
    public void setStartAt(Integer startAt) {
        this.startAt = startAt;
    }

    /**
     * Gets the max results.
     *
     * @return the max results
     */
    @JsonProperty("maxResults")
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * Sets the max results.
     *
     * @param maxResults the new max results
     */
    @JsonProperty("maxResults")
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
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
     * Gets the issues.
     *
     * @return the issues
     */
    @JsonProperty("issues")
    public List<Issue> getIssues() {
        return issues;
    }

    /**
     * Sets the issues.
     *
     * @param issues the new issues
     */
    @JsonProperty("issues")
    public void setIssues(List<Issue> issues) {
        this.issues = issues;
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
