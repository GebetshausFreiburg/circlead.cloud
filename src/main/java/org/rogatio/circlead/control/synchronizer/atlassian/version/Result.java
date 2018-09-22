
package org.rogatio.circlead.control.synchronizer.atlassian.version;

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
    "by",
    "when",
    "friendlyWhen",
    "message",
    "number",
    "minorEdit",
    "syncRev",
    "syncRevSource",
    "confRev",
    "_expandable",
    "_links"
})
public class Result {

    @JsonProperty("by")
    private By by;
    @JsonProperty("when")
    private String when;
    @JsonProperty("friendlyWhen")
    private String friendlyWhen;
    @JsonProperty("message")
    private String message;
    @JsonProperty("number")
    private Integer number;
    @JsonProperty("minorEdit")
    private Boolean minorEdit;
    @JsonProperty("syncRev")
    private String syncRev;
    @JsonProperty("syncRevSource")
    private String syncRevSource;
    @JsonProperty("confRev")
    private String confRev;
    @JsonProperty("_expandable")
    private Expandable_ expandable;
    @JsonProperty("_links")
    private Links_ links;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("by")
    public By getBy() {
        return by;
    }

    @JsonProperty("by")
    public void setBy(By by) {
        this.by = by;
    }

    @JsonProperty("when")
    public String getWhen() {
        return when;
    }

    @JsonProperty("when")
    public void setWhen(String when) {
        this.when = when;
    }

    @JsonProperty("friendlyWhen")
    public String getFriendlyWhen() {
        return friendlyWhen;
    }

    @JsonProperty("friendlyWhen")
    public void setFriendlyWhen(String friendlyWhen) {
        this.friendlyWhen = friendlyWhen;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    @JsonProperty("number")
    public void setNumber(Integer number) {
        this.number = number;
    }

    @JsonProperty("minorEdit")
    public Boolean getMinorEdit() {
        return minorEdit;
    }

    @JsonProperty("minorEdit")
    public void setMinorEdit(Boolean minorEdit) {
        this.minorEdit = minorEdit;
    }

    @JsonProperty("syncRev")
    public String getSyncRev() {
        return syncRev;
    }

    @JsonProperty("syncRev")
    public void setSyncRev(String syncRev) {
        this.syncRev = syncRev;
    }

    @JsonProperty("syncRevSource")
    public String getSyncRevSource() {
        return syncRevSource;
    }

    @JsonProperty("syncRevSource")
    public void setSyncRevSource(String syncRevSource) {
        this.syncRevSource = syncRevSource;
    }

    @JsonProperty("confRev")
    public String getConfRev() {
        return confRev;
    }

    @JsonProperty("confRev")
    public void setConfRev(String confRev) {
        this.confRev = confRev;
    }

    @JsonProperty("_expandable")
    public Expandable_ getExpandable() {
        return expandable;
    }

    @JsonProperty("_expandable")
    public void setExpandable(Expandable_ expandable) {
        this.expandable = expandable;
    }

    @JsonProperty("_links")
    public Links_ getLinks() {
        return links;
    }

    @JsonProperty("_links")
    public void setLinks(Links_ links) {
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
