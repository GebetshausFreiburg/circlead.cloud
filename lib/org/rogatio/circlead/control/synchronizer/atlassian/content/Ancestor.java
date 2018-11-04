
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
 * The Class Ancestor.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "status",
    "title",
    "macroRenderedOutput",
    "extensions",
    "_expandable",
    "_links"
})
public class Ancestor {

    /** The id. */
    @JsonProperty("id")
    private String id;
    
    /** The type. */
    @JsonProperty("type")
    private String type;
    
    /** The status. */
    @JsonProperty("status")
    private String status;
    
    /** The title. */
    @JsonProperty("title")
    private String title;
    
    /** The macro rendered output. */
    @JsonProperty("macroRenderedOutput")
    private MacroRenderedOutput macroRenderedOutput;
    
    /** The extensions. */
    @JsonProperty("extensions")
    private Extensions extensions;
    
    /** The expandable. */
    @JsonProperty("_expandable")
    private Expandable_______ expandable;
    
    /** The links. */
    @JsonProperty("_links")
    private Links_______ links;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
     * Gets the type.
     *
     * @return the type
     */
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the status.
     *
     * @return the status
     */
    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the macro rendered output.
     *
     * @return the macro rendered output
     */
    @JsonProperty("macroRenderedOutput")
    public MacroRenderedOutput getMacroRenderedOutput() {
        return macroRenderedOutput;
    }

    /**
     * Sets the macro rendered output.
     *
     * @param macroRenderedOutput the new macro rendered output
     */
    @JsonProperty("macroRenderedOutput")
    public void setMacroRenderedOutput(MacroRenderedOutput macroRenderedOutput) {
        this.macroRenderedOutput = macroRenderedOutput;
    }

    /**
     * Gets the extensions.
     *
     * @return the extensions
     */
    @JsonProperty("extensions")
    public Extensions getExtensions() {
        return extensions;
    }

    /**
     * Sets the extensions.
     *
     * @param extensions the new extensions
     */
    @JsonProperty("extensions")
    public void setExtensions(Extensions extensions) {
        this.extensions = extensions;
    }

    /**
     * Gets the expandable.
     *
     * @return the expandable
     */
    @JsonProperty("_expandable")
    public Expandable_______ getExpandable() {
        return expandable;
    }

    /**
     * Sets the expandable.
     *
     * @param expandable the new expandable
     */
    @JsonProperty("_expandable")
    public void setExpandable(Expandable_______ expandable) {
        this.expandable = expandable;
    }

    /**
     * Gets the links.
     *
     * @return the links
     */
    @JsonProperty("_links")
    public Links_______ getLinks() {
        return links;
    }

    /**
     * Sets the links.
     *
     * @param links the new links
     */
    @JsonProperty("_links")
    public void setLinks(Links_______ links) {
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
