
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
 * The Class Comment.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "comments",
    "maxResults",
    "total",
    "startAt"
})
public class Comment {

    /** The comments. */
    @JsonProperty("comments")
    private List<Object> comments = null;
    
    /** The max results. */
    @JsonProperty("maxResults")
    private Integer maxResults;
    
    /** The total. */
    @JsonProperty("total")
    private Integer total;
    
    /** The start at. */
    @JsonProperty("startAt")
    private Integer startAt;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the comments.
     *
     * @return the comments
     */
    @JsonProperty("comments")
    public List<Object> getComments() {
        return comments;
    }

    /**
     * Sets the comments.
     *
     * @param comments the new comments
     */
    @JsonProperty("comments")
    public void setComments(List<Object> comments) {
        this.comments = comments;
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
