
package org.rogatio.circlead.control.synchronizer.atlassian.search;

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
    "container",
    "metadata",
    "extensions",
    "operations",
    "children",
    "history",
    "ancestors",
    "body",
    "version",
    "descendants",
    "space"
})
public class Expandable {

    /** The container. */
    @JsonProperty("container")
    private String container;
    
    /** The metadata. */
    @JsonProperty("metadata")
    private String metadata;
    
    /** The extensions. */
    @JsonProperty("extensions")
    private String extensions;
    
    /** The operations. */
    @JsonProperty("operations")
    private String operations;
    
    /** The children. */
    @JsonProperty("children")
    private String children;
    
    /** The history. */
    @JsonProperty("history")
    private String history;
    
    /** The ancestors. */
    @JsonProperty("ancestors")
    private String ancestors;
    
    /** The body. */
    @JsonProperty("body")
    private String body;
    
    /** The version. */
    @JsonProperty("version")
    private String version;
    
    /** The descendants. */
    @JsonProperty("descendants")
    private String descendants;
    
    /** The space. */
    @JsonProperty("space")
    private String space;
    
    /** The additional properties. */
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Gets the container.
     *
     * @return the container
     */
    @JsonProperty("container")
    public String getContainer() {
        return container;
    }

    /**
     * Sets the container.
     *
     * @param container the new container
     */
    @JsonProperty("container")
    public void setContainer(String container) {
        this.container = container;
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
     * Gets the extensions.
     *
     * @return the extensions
     */
    @JsonProperty("extensions")
    public String getExtensions() {
        return extensions;
    }

    /**
     * Sets the extensions.
     *
     * @param extensions the new extensions
     */
    @JsonProperty("extensions")
    public void setExtensions(String extensions) {
        this.extensions = extensions;
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
     * Gets the children.
     *
     * @return the children
     */
    @JsonProperty("children")
    public String getChildren() {
        return children;
    }

    /**
     * Sets the children.
     *
     * @param children the new children
     */
    @JsonProperty("children")
    public void setChildren(String children) {
        this.children = children;
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
     * Gets the ancestors.
     *
     * @return the ancestors
     */
    @JsonProperty("ancestors")
    public String getAncestors() {
        return ancestors;
    }

    /**
     * Sets the ancestors.
     *
     * @param ancestors the new ancestors
     */
    @JsonProperty("ancestors")
    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    /**
     * Gets the body.
     *
     * @return the body
     */
    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    /**
     * Sets the body.
     *
     * @param body the new body
     */
    @JsonProperty("body")
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Gets the version.
     *
     * @return the version
     */
    @JsonProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * Sets the version.
     *
     * @param version the new version
     */
    @JsonProperty("version")
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets the descendants.
     *
     * @return the descendants
     */
    @JsonProperty("descendants")
    public String getDescendants() {
        return descendants;
    }

    /**
     * Sets the descendants.
     *
     * @param descendants the new descendants
     */
    @JsonProperty("descendants")
    public void setDescendants(String descendants) {
        this.descendants = descendants;
    }

    /**
     * Gets the space.
     *
     * @return the space
     */
    @JsonProperty("space")
    public String getSpace() {
        return space;
    }

    /**
     * Sets the space.
     *
     * @param space the new space
     */
    @JsonProperty("space")
    public void setSpace(String space) {
        this.space = space;
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
