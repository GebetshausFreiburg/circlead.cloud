
package org.rogatio.circlead.control.synchronizer.atlassian.content;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

// TODO: Auto-generated Javadoc
/**
 * The Class Page.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "type",
    "status",
    "title",
    "space",
    "history",
    "version",
    "ancestors",
    "macroRenderedOutput",
    "body",
    "metadata",
    "extensions",
    "_expandable",
    "_links"
})
public class Page {

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
    
    /** The space. */
    @JsonProperty("space")
    private Space space;
    
    /** The history. */
    @JsonProperty("history")
    private History history;
    
    /** The version. */
    @JsonProperty("version")
    private Version version;
    
    /** The ancestors. */
    @JsonProperty("ancestors")
    private List<Ancestor> ancestors = null;
    
    /** The macro rendered output. */
    @JsonProperty("macroRenderedOutput")
    private MacroRenderedOutput_ macroRenderedOutput;
    
    /** The body. */
    @JsonProperty("body")
    private Body body;
    
    /** The metadata. */
    @JsonProperty("metadata")
    private Metadata metadata;
    
    /** The extensions. */
    @JsonProperty("extensions")
    private Extensions_ extensions;
    
    /** The expandable. */
    @JsonProperty("_expandable")
    private Expandable___________ expandable;
    
    /** The links. */
    @JsonProperty("_links")
    private Links_________ links;
    
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
     * Gets the space.
     *
     * @return the space
     */
    @JsonProperty("space")
    public Space getSpace() {
        return space;
    }

    /**
     * Sets the space.
     *
     * @param space the new space
     */
    @JsonProperty("space")
    public void setSpace(Space space) {
        this.space = space;
    }

    /**
     * Gets the history.
     *
     * @return the history
     */
    @JsonProperty("history")
    public History getHistory() {
        return history;
    }

    /**
     * Sets the history.
     *
     * @param history the new history
     */
    @JsonProperty("history")
    public void setHistory(History history) {
        this.history = history;
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    @JsonProperty("version")
    public Version getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version the new version
     */
    @JsonProperty("version")
    public void setVersion(Version version) {
        this.version = version;
    }

    /**
     * Gets the ancestors.
     *
     * @return the ancestors
     */
    @JsonProperty("ancestors")
    public List<Ancestor> getAncestors() {
        return ancestors;
    }

    /**
     * Sets the ancestors.
     *
     * @param ancestors the new ancestors
     */
    @JsonProperty("ancestors")
    public void setAncestors(List<Ancestor> ancestors) {
        this.ancestors = ancestors;
    }

    /**
     * Gets the macro rendered output.
     *
     * @return the macro rendered output
     */
    @JsonProperty("macroRenderedOutput")
    public MacroRenderedOutput_ getMacroRenderedOutput() {
        return macroRenderedOutput;
    }

    /**
     * Sets the macro rendered output.
     *
     * @param macroRenderedOutput the new macro rendered output
     */
    @JsonProperty("macroRenderedOutput")
    public void setMacroRenderedOutput(MacroRenderedOutput_ macroRenderedOutput) {
        this.macroRenderedOutput = macroRenderedOutput;
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    @JsonProperty("body")
    public Body getBody() {
        return body;
    }

    /**
     * Sets the body.
     *
     * @param body the new body
     */
    @JsonProperty("body")
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * Gets the metadata.
     *
     * @return the metadata
     */
    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets the metadata.
     *
     * @param metadata the new metadata
     */
    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    /**
     * Gets the extensions.
     *
     * @return the extensions
     */
    @JsonProperty("extensions")
    public Extensions_ getExtensions() {
        return extensions;
    }

    /**
     * Sets the extensions.
     *
     * @param extensions the new extensions
     */
    @JsonProperty("extensions")
    public void setExtensions(Extensions_ extensions) {
        this.extensions = extensions;
    }

    /**
     * Gets the expandable.
     *
     * @return the expandable
     */
    @JsonProperty("_expandable")
    public Expandable___________ getExpandable() {
        return expandable;
    }

    /**
     * Sets the expandable.
     *
     * @param expandable the new expandable
     */
    @JsonProperty("_expandable")
    public void setExpandable(Expandable___________ expandable) {
        this.expandable = expandable;
    }

    /**
     * Gets the links.
     *
     * @return the links
     */
    @JsonProperty("_links")
    public Links_________ getLinks() {
        return links;
    }

    /**
     * Sets the links.
     *
     * @param links the new links
     */
    @JsonProperty("_links")
    public void setLinks(Links_________ links) {
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
