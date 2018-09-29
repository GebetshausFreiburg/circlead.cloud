
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
 * The Class ProfilePicture.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "path",
    "width",
    "height",
    "isDefault"
})
public class ProfilePicture {

    /** The path. */
    @JsonProperty("path")
    private String path;
    
    /** The width. */
    @JsonProperty("width")
    private Integer width;
    
    /** The height. */
    @JsonProperty("height")
    private Integer height;
    
    /** The is default. */
    @JsonProperty("isDefault")
    private Boolean isDefault;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the path.
     *
     * @return the path
     */
    @JsonProperty("path")
    public String getPath() {
        return path;
    }

    /**
     * Sets the path.
     *
     * @param path the new path
     */
    @JsonProperty("path")
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets the width.
     *
     * @return the width
     */
    @JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    /**
     * Sets the width.
     *
     * @param width the new width
     */
    @JsonProperty("width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * Gets the height.
     *
     * @return the height
     */
    @JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    /**
     * Sets the height.
     *
     * @param height the new height
     */
    @JsonProperty("height")
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Gets the checks if is default.
     *
     * @return the checks if is default
     */
    @JsonProperty("isDefault")
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the checks if is default.
     *
     * @param isDefault the new checks if is default
     */
    @JsonProperty("isDefault")
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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
