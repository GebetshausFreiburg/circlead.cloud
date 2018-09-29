
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
 * The Class Labels.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "results",
    "start",
    "limit",
    "size",
    "_links"
})
public class Labels {

    /** The results. */
    @JsonProperty("results")
    private List<Result> results = null;
    
    /** The start. */
    @JsonProperty("start")
    private Integer start;
    
    /** The limit. */
    @JsonProperty("limit")
    private Integer limit;
    
    /** The size. */
    @JsonProperty("size")
    private Integer size;
    
    /** The links. */
    @JsonProperty("_links")
    private Links________ links;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the results.
     *
     * @return the results
     */
    @JsonProperty("results")
    public List<Result> getResults() {
        return results;
    }

    /**
     * Sets the results.
     *
     * @param results the new results
     */
    @JsonProperty("results")
    public void setResults(List<Result> results) {
        this.results = results;
    }

    /**
     * Gets the start.
     *
     * @return the start
     */
    @JsonProperty("start")
    public Integer getStart() {
        return start;
    }

    /**
     * Sets the start.
     *
     * @param start the new start
     */
    @JsonProperty("start")
    public void setStart(Integer start) {
        this.start = start;
    }

    /**
     * Gets the limit.
     *
     * @return the limit
     */
    @JsonProperty("limit")
    public Integer getLimit() {
        return limit;
    }

    /**
     * Sets the limit.
     *
     * @param limit the new limit
     */
    @JsonProperty("limit")
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * Gets the size.
     *
     * @return the size
     */
    @JsonProperty("size")
    public Integer getSize() {
        return size;
    }

    /**
     * Sets the size.
     *
     * @param size the new size
     */
    @JsonProperty("size")
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * Gets the links.
     *
     * @return the links
     */
    @JsonProperty("_links")
    public Links________ getLinks() {
        return links;
    }

    /**
     * Sets the links.
     *
     * @param links the new links
     */
    @JsonProperty("_links")
    public void setLinks(Links________ links) {
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
