
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
 * The Class Expandable__________.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "currentuser",
    "properties",
    "frontend",
    "likes"
})
public class Expandable__________ {

    /** The currentuser. */
    @JsonProperty("currentuser")
    private String currentuser;
    
    /** The properties. */
    @JsonProperty("properties")
    private String properties;
    
    /** The frontend. */
    @JsonProperty("frontend")
    private String frontend;
    
    /** The likes. */
    @JsonProperty("likes")
    private String likes;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the currentuser.
     *
     * @return the currentuser
     */
    @JsonProperty("currentuser")
    public String getCurrentuser() {
        return currentuser;
    }

    /**
     * Sets the currentuser.
     *
     * @param currentuser the new currentuser
     */
    @JsonProperty("currentuser")
    public void setCurrentuser(String currentuser) {
        this.currentuser = currentuser;
    }

    /**
     * Gets the properties.
     *
     * @return the properties
     */
    @JsonProperty("properties")
    public String getProperties() {
        return properties;
    }

    /**
     * Sets the properties.
     *
     * @param properties the new properties
     */
    @JsonProperty("properties")
    public void setProperties(String properties) {
        this.properties = properties;
    }

    /**
     * Gets the frontend.
     *
     * @return the frontend
     */
    @JsonProperty("frontend")
    public String getFrontend() {
        return frontend;
    }

    /**
     * Sets the frontend.
     *
     * @param frontend the new frontend
     */
    @JsonProperty("frontend")
    public void setFrontend(String frontend) {
        this.frontend = frontend;
    }

    /**
     * Gets the likes.
     *
     * @return the likes
     */
    @JsonProperty("likes")
    public String getLikes() {
        return likes;
    }

    /**
     * Sets the likes.
     *
     * @param likes the new likes
     */
    @JsonProperty("likes")
    public void setLikes(String likes) {
        this.likes = likes;
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
