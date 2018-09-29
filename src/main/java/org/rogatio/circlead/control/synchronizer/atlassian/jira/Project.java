
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
 * The Class Project.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "self",
    "id",
    "key",
    "name",
    "projectTypeKey",
    "avatarUrls"
})
public class Project {

    /** The self. */
    @JsonProperty("self")
    private String self;
    
    /** The id. */
    @JsonProperty("id")
    private String id;
    
    /** The key. */
    @JsonProperty("key")
    private String key;
    
    /** The name. */
    @JsonProperty("name")
    private String name;
    
    /** The project type key. */
    @JsonProperty("projectTypeKey")
    private String projectTypeKey;
    
    /** The avatar urls. */
    @JsonProperty("avatarUrls")
    private AvatarUrls avatarUrls;
    
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
     * Gets the key.
     *
     * @return the key
     */
    @JsonProperty("key")
    public String getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key the new key
     */
    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
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
     * Gets the project type key.
     *
     * @return the project type key
     */
    @JsonProperty("projectTypeKey")
    public String getProjectTypeKey() {
        return projectTypeKey;
    }

    /**
     * Sets the project type key.
     *
     * @param projectTypeKey the new project type key
     */
    @JsonProperty("projectTypeKey")
    public void setProjectTypeKey(String projectTypeKey) {
        this.projectTypeKey = projectTypeKey;
    }

    /**
     * Gets the avatar urls.
     *
     * @return the avatar urls
     */
    @JsonProperty("avatarUrls")
    public AvatarUrls getAvatarUrls() {
        return avatarUrls;
    }

    /**
     * Sets the avatar urls.
     *
     * @param avatarUrls the new avatar urls
     */
    @JsonProperty("avatarUrls")
    public void setAvatarUrls(AvatarUrls avatarUrls) {
        this.avatarUrls = avatarUrls;
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
