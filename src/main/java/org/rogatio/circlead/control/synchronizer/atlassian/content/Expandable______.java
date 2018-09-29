
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
 * The Class Expandable______.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "collaborators",
    "content"
})
public class Expandable______ {

    /** The collaborators. */
    @JsonProperty("collaborators")
    private String collaborators;
    
    /** The content. */
    @JsonProperty("content")
    private String content;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the collaborators.
     *
     * @return the collaborators
     */
    @JsonProperty("collaborators")
    public String getCollaborators() {
        return collaborators;
    }

    /**
     * Sets the collaborators.
     *
     * @param collaborators the new collaborators
     */
    @JsonProperty("collaborators")
    public void setCollaborators(String collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
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
