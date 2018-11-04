
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
 * The Class LastUpdated.
 */
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
public class LastUpdated {

    /** The by. */
    @JsonProperty("by")
    private By by;
    
    /** The when. */
    @JsonProperty("when")
    private String when;
    
    /** The friendly when. */
    @JsonProperty("friendlyWhen")
    private String friendlyWhen;
    
    /** The message. */
    @JsonProperty("message")
    private String message;
    
    /** The number. */
    @JsonProperty("number")
    private Integer number;
    
    /** The minor edit. */
    @JsonProperty("minorEdit")
    private Boolean minorEdit;
    
    /** The sync rev. */
    @JsonProperty("syncRev")
    private String syncRev;
    
    /** The sync rev source. */
    @JsonProperty("syncRevSource")
    private String syncRevSource;
    
    /** The conf rev. */
    @JsonProperty("confRev")
    private String confRev;
    
    /** The expandable. */
    @JsonProperty("_expandable")
    private Expandable__ expandable;
    
    /** The links. */
    @JsonProperty("_links")
    private Links__ links;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the by.
     *
     * @return the by
     */
    @JsonProperty("by")
    public By getBy() {
        return by;
    }

    /**
     * Sets the by.
     *
     * @param by the new by
     */
    @JsonProperty("by")
    public void setBy(By by) {
        this.by = by;
    }

    /**
     * Gets the when.
     *
     * @return the when
     */
    @JsonProperty("when")
    public String getWhen() {
        return when;
    }

    /**
     * Sets the when.
     *
     * @param when the new when
     */
    @JsonProperty("when")
    public void setWhen(String when) {
        this.when = when;
    }

    /**
     * Gets the friendly when.
     *
     * @return the friendly when
     */
    @JsonProperty("friendlyWhen")
    public String getFriendlyWhen() {
        return friendlyWhen;
    }

    /**
     * Sets the friendly when.
     *
     * @param friendlyWhen the new friendly when
     */
    @JsonProperty("friendlyWhen")
    public void setFriendlyWhen(String friendlyWhen) {
        this.friendlyWhen = friendlyWhen;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     *
     * @param message the new message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the number.
     *
     * @return the number
     */
    @JsonProperty("number")
    public Integer getNumber() {
        return number;
    }

    /**
     * Sets the number.
     *
     * @param number the new number
     */
    @JsonProperty("number")
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * Gets the minor edit.
     *
     * @return the minor edit
     */
    @JsonProperty("minorEdit")
    public Boolean getMinorEdit() {
        return minorEdit;
    }

    /**
     * Sets the minor edit.
     *
     * @param minorEdit the new minor edit
     */
    @JsonProperty("minorEdit")
    public void setMinorEdit(Boolean minorEdit) {
        this.minorEdit = minorEdit;
    }

    /**
     * Gets the sync rev.
     *
     * @return the sync rev
     */
    @JsonProperty("syncRev")
    public String getSyncRev() {
        return syncRev;
    }

    /**
     * Sets the sync rev.
     *
     * @param syncRev the new sync rev
     */
    @JsonProperty("syncRev")
    public void setSyncRev(String syncRev) {
        this.syncRev = syncRev;
    }

    /**
     * Gets the sync rev source.
     *
     * @return the sync rev source
     */
    @JsonProperty("syncRevSource")
    public String getSyncRevSource() {
        return syncRevSource;
    }

    /**
     * Sets the sync rev source.
     *
     * @param syncRevSource the new sync rev source
     */
    @JsonProperty("syncRevSource")
    public void setSyncRevSource(String syncRevSource) {
        this.syncRevSource = syncRevSource;
    }

    /**
     * Gets the conf rev.
     *
     * @return the conf rev
     */
    @JsonProperty("confRev")
    public String getConfRev() {
        return confRev;
    }

    /**
     * Sets the conf rev.
     *
     * @param confRev the new conf rev
     */
    @JsonProperty("confRev")
    public void setConfRev(String confRev) {
        this.confRev = confRev;
    }

    /**
     * Gets the expandable.
     *
     * @return the expandable
     */
    @JsonProperty("_expandable")
    public Expandable__ getExpandable() {
        return expandable;
    }

    /**
     * Sets the expandable.
     *
     * @param expandable the new expandable
     */
    @JsonProperty("_expandable")
    public void setExpandable(Expandable__ expandable) {
        this.expandable = expandable;
    }

    /**
     * Gets the links.
     *
     * @return the links
     */
    @JsonProperty("_links")
    public Links__ getLinks() {
        return links;
    }

    /**
     * Sets the links.
     *
     * @param links the new links
     */
    @JsonProperty("_links")
    public void setLinks(Links__ links) {
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
