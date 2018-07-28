
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

    @JsonProperty("settings")
    private String settings;
    @JsonProperty("metadata")
    private String metadata;
    @JsonProperty("operations")
    private String operations;
    @JsonProperty("lookAndFeel")
    private String lookAndFeel;
    @JsonProperty("permissions")
    private String permissions;
    @JsonProperty("icon")
    private String icon;
    @JsonProperty("description")
    private String description;
    @JsonProperty("theme")
    private String theme;
    @JsonProperty("history")
    private String history;
    @JsonProperty("homepage")
    private String homepage;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("settings")
    public String getSettings() {
        return settings;
    }

    @JsonProperty("settings")
    public void setSettings(String settings) {
        this.settings = settings;
    }

    @JsonProperty("metadata")
    public String getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("operations")
    public String getOperations() {
        return operations;
    }

    @JsonProperty("operations")
    public void setOperations(String operations) {
        this.operations = operations;
    }

    @JsonProperty("lookAndFeel")
    public String getLookAndFeel() {
        return lookAndFeel;
    }

    @JsonProperty("lookAndFeel")
    public void setLookAndFeel(String lookAndFeel) {
        this.lookAndFeel = lookAndFeel;
    }

    @JsonProperty("permissions")
    public String getPermissions() {
        return permissions;
    }

    @JsonProperty("permissions")
    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    @JsonProperty("icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("theme")
    public String getTheme() {
        return theme;
    }

    @JsonProperty("theme")
    public void setTheme(String theme) {
        this.theme = theme;
    }

    @JsonProperty("history")
    public String getHistory() {
        return history;
    }

    @JsonProperty("history")
    public void setHistory(String history) {
        this.history = history;
    }

    @JsonProperty("homepage")
    public String getHomepage() {
        return homepage;
    }

    @JsonProperty("homepage")
    public void setHomepage(String homepage) {
        this.homepage = homepage;
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
