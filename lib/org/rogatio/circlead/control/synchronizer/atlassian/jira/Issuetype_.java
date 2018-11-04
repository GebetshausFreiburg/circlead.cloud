
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
 * The Class Issuetype_.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "self",
    "id",
    "description",
    "iconUrl",
    "name",
    "subtask",
    "avatarId"
})
public class Issuetype_ {

    /** The self. */
    @JsonProperty("self")
    private String self;
    
    /** The id. */
    @JsonProperty("id")
    private String id;
    
    /** The description. */
    @JsonProperty("description")
    private String description;
    
    /** The icon url. */
    @JsonProperty("iconUrl")
    private String iconUrl;
    
    /** The name. */
    @JsonProperty("name")
    private String name;
    
    /** The subtask. */
    @JsonProperty("subtask")
    private Boolean subtask;
    
    /** The avatar id. */
    @JsonProperty("avatarId")
    private Integer avatarId;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the self.
     *
     * @return the self
     */
    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    /**
     * Sets the self.
     *
     * @param self the new self
     */
    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the icon url.
     *
     * @return the icon url
     */
    @JsonProperty("iconUrl")
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Sets the icon url.
     *
     * @param iconUrl the new icon url
     */
    @JsonProperty("iconUrl")
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the subtask.
     *
     * @return the subtask
     */
    @JsonProperty("subtask")
    public Boolean getSubtask() {
        return subtask;
    }

    /**
     * Sets the subtask.
     *
     * @param subtask the new subtask
     */
    @JsonProperty("subtask")
    public void setSubtask(Boolean subtask) {
        this.subtask = subtask;
    }

    /**
     * Gets the avatar id.
     *
     * @return the avatar id
     */
    @JsonProperty("avatarId")
    public Integer getAvatarId() {
        return avatarId;
    }

    /**
     * Sets the avatar id.
     *
     * @param avatarId the new avatar id
     */
    @JsonProperty("avatarId")
    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
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
