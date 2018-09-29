
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
 * The Class Expandable.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "settings",
    "metadata",
    "operations",
    "lookAndFeel",
    "permissions",
    "icon",
    "description",
    "theme",
    "history",
    "homepage"
})
public class Expandable {

    /** The settings. */
    @JsonProperty("settings")
    private String settings;
    
    /** The metadata. */
    @JsonProperty("metadata")
    private String metadata;
    
    /** The operations. */
    @JsonProperty("operations")
    private String operations;
    
    /** The look and feel. */
    @JsonProperty("lookAndFeel")
    private String lookAndFeel;
    
    /** The permissions. */
    @JsonProperty("permissions")
    private String permissions;
    
    /** The icon. */
    @JsonProperty("icon")
    private String icon;
    
    /** The description. */
    @JsonProperty("description")
    private String description;
    
    /** The theme. */
    @JsonProperty("theme")
    private String theme;
    
    /** The history. */
    @JsonProperty("history")
    private String history;
    
    /** The homepage. */
    @JsonProperty("homepage")
    private String homepage;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the settings.
     *
     * @return the settings
     */
    @JsonProperty("settings")
    public String getSettings() {
        return settings;
    }

    /**
     * Sets the settings.
     *
     * @param settings the new settings
     */
    @JsonProperty("settings")
    public void setSettings(String settings) {
        this.settings = settings;
    }

    /**
     * Gets the metadata.
     *
     * @return the metadata
     */
    @JsonProperty("metadata")
    public String getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata.
     *
     * @param metadata the new metadata
     */
    @JsonProperty("metadata")
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    /**
     * Gets the operations.
     *
     * @return the operations
     */
    @JsonProperty("operations")
    public String getOperations() {
        return operations;
    }

    /**
     * Sets the operations.
     *
     * @param operations the new operations
     */
    @JsonProperty("operations")
    public void setOperations(String operations) {
        this.operations = operations;
    }

    /**
     * Gets the look and feel.
     *
     * @return the look and feel
     */
    @JsonProperty("lookAndFeel")
    public String getLookAndFeel() {
        return lookAndFeel;
    }

    /**
     * Sets the look and feel.
     *
     * @param lookAndFeel the new look and feel
     */
    @JsonProperty("lookAndFeel")
    public void setLookAndFeel(String lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
    }

    /**
     * Gets the permissions.
     *
     * @return the permissions
     */
    @JsonProperty("permissions")
    public String getPermissions() {
        return permissions;
    }

    /**
     * Sets the permissions.
     *
     * @param permissions the new permissions
     */
    @JsonProperty("permissions")
    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    /**
     * Gets the icon.
     *
     * @return the icon
     */
    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    /**
     * Sets the icon.
     *
     * @param icon the new icon
     */
    @JsonProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
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
     * Gets the theme.
     *
     * @return the theme
     */
    @JsonProperty("theme")
    public String getTheme() {
        return theme;
    }

    /**
     * Sets the theme.
     *
     * @param theme the new theme
     */
    @JsonProperty("theme")
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * Gets the history.
     *
     * @return the history
     */
    @JsonProperty("history")
    public String getHistory() {
        return history;
    }

    /**
     * Sets the history.
     *
     * @param history the new history
     */
    @JsonProperty("history")
    public void setHistory(String history) {
        this.history = history;
    }

    /**
     * Gets the homepage.
     *
     * @return the homepage
     */
    @JsonProperty("homepage")
    public String getHomepage() {
        return homepage;
    }

    /**
     * Sets the homepage.
     *
     * @param homepage the new homepage
     */
    @JsonProperty("homepage")
    public void setHomepage(String homepage) {
        this.homepage = homepage;
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
