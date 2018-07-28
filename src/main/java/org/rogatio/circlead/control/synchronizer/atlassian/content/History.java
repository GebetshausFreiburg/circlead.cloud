
package org.rogatio.circlead.control.synchronizer.atlassian.content;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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

    @JsonProperty("lastUpdated")
    private LastUpdated lastUpdated;
    @JsonProperty("latest")
    private Boolean latest;
    @JsonProperty("createdBy")
    private CreatedBy createdBy;
    @JsonProperty("createdDate")
    private String createdDate;
    @JsonProperty("_expandable")
    private Expandable____ expandable;
    @JsonProperty("_links")
    private Links____ links;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("lastUpdated")
    public LastUpdated getLastUpdated() {
        return lastUpdated;
    }

    @JsonProperty("lastUpdated")
    public void setLastUpdated(LastUpdated lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @JsonProperty("latest")
    public Boolean getLatest() {
        return latest;
    }

    @JsonProperty("latest")
    public void setLatest(Boolean latest) {
        this.latest = latest;
    }

    @JsonProperty("createdBy")
    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    @JsonProperty("createdBy")
    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("createdDate")
    public String getCreatedDate() {
        return createdDate;
    }

    @JsonProperty("createdDate")
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @JsonProperty("_expandable")
    public Expandable____ getExpandable() {
        return expandable;
    }

    @JsonProperty("_expandable")
    public void setExpandable(Expandable____ expandable) {
        this.expandable = expandable;
    }

    @JsonProperty("_links")
    public Links____ getLinks() {
        return links;
    }

    @JsonProperty("_links")
    public void setLinks(Links____ links) {
        this.links = links;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
