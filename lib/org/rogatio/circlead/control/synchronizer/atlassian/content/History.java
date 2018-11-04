
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
 * The Class History.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "lastUpdated",
    "latest",
    "createdBy",
    "createdDate",
    "_expandable",
    "_links"
})
public class History {

    /** The last updated. */
    @JsonProperty("lastUpdated")
    private LastUpdated lastUpdated;
    
    /** The latest. */
    @JsonProperty("latest")
    private Boolean latest;
    
    /** The created by. */
    @JsonProperty("createdBy")
    private CreatedBy createdBy;
    
    /** The created date. */
    @JsonProperty("createdDate")
    private String createdDate;
    
    /** The expandable. */
    @JsonProperty("_expandable")
    private Expandable____ expandable;
    
    /** The links. */
    @JsonProperty("_links")
    private Links____ links;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the last updated.
     *
     * @return the last updated
     */
    @JsonProperty("lastUpdated")
    public LastUpdated getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Sets the last updated.
     *
     * @param lastUpdated the new last updated
     */
    @JsonProperty("lastUpdated")
    public void setLastUpdated(LastUpdated lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Gets the latest.
     *
     * @return the latest
     */
    @JsonProperty("latest")
    public Boolean getLatest() {
        return latest;
    }

    /**
     * Sets the latest.
     *
     * @param latest the new latest
     */
    @JsonProperty("latest")
    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    @JsonProperty("createdBy")
    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy the new created by
     */
    @JsonProperty("createdBy")
    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the created date.
     *
     * @return the created date
     */
    @JsonProperty("createdDate")
    public String getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the created date.
     *
     * @param createdDate the new created date
     */
    @JsonProperty("createdDate")
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * Gets the expandable.
     *
     * @return the expandable
     */
    @JsonProperty("_expandable")
    public Expandable____ getExpandable() {
        return expandable;
    }

    /**
     * Sets the expandable.
     *
     * @param expandable the new expandable
     */
    @JsonProperty("_expandable")
    public void setExpandable(Expandable____ expandable) {
        this.expandable = expandable;
    }

    /**
     * Gets the links.
     *
     * @return the links
     */
    @JsonProperty("_links")
    public Links____ getLinks() {
        return links;
    }

    /**
     * Sets the links.
     *
     * @param links the new links
     */
    @JsonProperty("_links")
    public void setLinks(Links____ links) {
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
